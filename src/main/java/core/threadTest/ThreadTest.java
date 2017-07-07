package core.threadTest;

class InsertAction extends Thread {

	String name;
	Integer[] lock;
	int sleepTime;

	public InsertAction(Integer[] lock, String name, int sleepTime) {
		this.lock = lock;
		this.name = name;
		this.sleepTime = sleepTime;
	}

	public void run() {

		while (true) {
			try {
				synchronized (lock) {
					System.out.println(name);
					lock.notify();
					lock.wait();
				}
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

public class ThreadTest {
	public static void main(String args[]) {
		Integer[] lock = new Integer[1];
		lock[0] = 1;
		InsertAction ia1 = new InsertAction(lock, "A", 1000);
		InsertAction ia2 = new InsertAction(lock, "B", 1000);
		ia1.start();
		ia2.start();
	}
}
