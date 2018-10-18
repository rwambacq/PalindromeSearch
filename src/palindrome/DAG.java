package palindrome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DAG {


    public static void main(String[] args) {
        BufferedReader br = null;
        DAG dag = new DAG();
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();
            boolean done = false;

            while(!done){
                dag.getTree(br, input);
                input = br.readLine();      // Neemt nieuwe input, indien deze leeg is, wordt uit de loop gegaan, anders wordt opnieuw in de loop gegaan en wordt de nieuwe input in getTree gestoken.
                if(input.equals("")){
                    done = true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void getTree(BufferedReader br, String input){

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
                nodes[o].setPointedFrom(((List<Integer>)pointedFrom[o]).stream().mapToInt(i -> i).toArray());
            }
            Tree boom = new Tree(n, nodes);

                        /*System.out.println("This tree has size " + boom.getSize());
                        System.out.println("");
                        System.out.println("De labels zijn: ");
                        for (Node node : boom.getBoom()) {
                            System.out.println("" + node.getLabel());
                        }*/
            boom.maxPalindrome();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
