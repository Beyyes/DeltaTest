import core.common.GraphEdge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by stefanie on 10/01/2017.
 */
public class CircleDetectTest {

    public int actionCount;

    // -1 means unvisited, 0 means being visited, 1 means visited
    private int[] visited;
    private boolean circleFlag;
    public HashMap<Integer, ArrayList<Integer>> suffixNodes = new HashMap<>();

    public CircleDetectTest() {

        actionCount = 8;
        ArrayList<Integer> a = new ArrayList<>();

        a.add(1);
        a.add(6);
        suffixNodes.put(0, a);
        a = new ArrayList<>();

        a.add(2);
        a.add(7);
        suffixNodes.put(1, a);
        a = new ArrayList<>();

        a.add(3);
        suffixNodes.put(2, a);
        a = new ArrayList<>();

        a.add(4);
        suffixNodes.put(3, a);
        a = new ArrayList<>();

        a.add(5);
        suffixNodes.put(4, a);
        a = new ArrayList<>();

        suffixNodes.put(5, a);
        a = new ArrayList<>();

        a.add(2);
        suffixNodes.put(6, a);
        a = new ArrayList<>();

        a.add(4);
        suffixNodes.put(7, a);

        visited = new int[actionCount];
        for (int i = 0; i < actionCount; i++) {
            visited[i] = -1;
        }

    }

    public boolean hasCircle() {

        for (int i = 0; i < actionCount; i++) {
            dfs(i);
            if (circleFlag == true) {
                return true;
            }
        }
        return false;
    }

    private void dfs (int s) {

        visited[s] = 0;
        List<Integer> suffix = suffixNodes.get(s);

        for (int i = 0; i < suffix.size(); i++) {
            int currentNode = suffix.get(i);
            if (visited[currentNode] == -1) {
                dfs(suffix.get(i));
            } else if (visited[currentNode] == 0) {
                circleFlag = true;
            }
        }

        visited[s] = 1;
    }

    public static void main(String[] args) {
        CircleDetectTest test = new CircleDetectTest();
        System.out.println(test.hasCircle());
    }
}
