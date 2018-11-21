package palindrome;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TreeIO {

    public Tree getTree(BufferedReader br, String input) {

        try {
            int n = Integer.parseInt(input);

            Node[] nodes = new Node[n];

            ArrayList[] pointedFrom = new ArrayList[n];
            for (int p = 0; p < n; p++) {
                pointedFrom[p] = new ArrayList<Integer>();
            }

            for (int i = 0; i < n; i++) {
                input = br.readLine();
                String[] lijnEen = input.split(" ");

                char label = lijnEen[0].toCharArray()[0];
                String outgoing = lijnEen[1];
                int neighbours = Integer.parseInt(outgoing);

                input = br.readLine();
                String[] lijnTwee = input.split(" ");
                int[] buren = new int[neighbours];
                for (int j = 0; j < neighbours; j++) {
                    int neighbourIndex = Integer.parseInt(lijnTwee[j]);
                    buren[j] = neighbourIndex;
                    pointedFrom[neighbourIndex].add(i);
                }
                Node toAdd = new Node(neighbours, buren, label);
                nodes[i] = toAdd;
            }
            for (int o = 0; o < n; o++) {
                nodes[o].setPointedFrom(((List<Integer>) pointedFrom[o]).stream().mapToInt(i -> i).toArray());
            }
            Tree boom = new Tree(n, nodes);
            return boom;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
