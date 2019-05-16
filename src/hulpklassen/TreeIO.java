package hulpklassen;

import datastructuren.DirectedGraph;
import datastructuren.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TreeIO klasse.
 * Dit is een hulpklasse om de input van een DirectedGraph te abstraheren uit de main functies
 *
 * @author Ruben Wambacq
 *
 */
public class TreeIO {

    /**
     * Leest input uit stdio en zet dit om naar een DirectedGraph datastructuur
     * @param br Een BufferedReader van waar de input moet worden gelezen.
     * @param input Een lege string, wordt meegegeven uit de main.
     * @return Een DirectedGraph datastructuur object.
     */
    public DirectedGraph getTree(BufferedReader br, String input) {

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
            DirectedGraph boom = new DirectedGraph(n, nodes);
            return boom;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
