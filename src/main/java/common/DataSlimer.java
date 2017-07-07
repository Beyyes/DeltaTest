package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataSlimer {
	
	private static int groupSize = 3;
	private static int rate = 2000;
	
	public static void slim(String input, String output) throws IOException{
		FileReader fr = new FileReader(input);
		BufferedReader br = new BufferedReader(fr); 
		
		FileWriter fw = new FileWriter(output);
		BufferedWriter bw = new BufferedWriter(fw);
		
		int skipLine = (rate - 1) * groupSize;
		String s;
		while((s = br.readLine()) != null){
			bw.write(s);
			bw.newLine();
			
			for(int i = 0 ; i < groupSize - 1; i ++){
				s = br.readLine();
				bw.write(s);
				bw.newLine();
			}
			
			//skip some line
			for(int i = 0 ; i < skipLine && (s = br.readLine()) != null; i++){
				
			}
		}
		br.close();
		bw.close();
		System.out.println("Done");
	}
	
	public static void main(String args[]) throws IOException{
		String input = "/Users/zhangjinrui/Desktop/vehicle-2000w.csv";
		String output = "/Users/zhangjinrui/Desktop/vehicle-1w.csv";
		slim(input, output);
	}
}
