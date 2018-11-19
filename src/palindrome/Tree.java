package palindrome;

import java.util.ArrayList;

public class Tree {

    private Node[] boom;
    private int size;
    private int aantalloops;

    private ArrayList[][] rooster;

    public Tree(int size, Node[] boom) {
        this.size = size;
        this.boom = boom;
        rooster = new ArrayList[size][size];
        aantalloops = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rooster[i][j] = new ArrayList<Integer>();
            }
        }
    }

    public void maxPalindrome() {
        ArrayList<NextIt> nextOnes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            nextOnes.addAll(functietje(i, i));
        }

        while (!nextOnes.isEmpty()) {
            ArrayList<NextIt> temp = new ArrayList<>();
            for (NextIt ni : nextOnes) {
                temp.addAll(functietje(ni.start, ni.end));
            }
            nextOnes = temp;
        }

        ArrayList<ArrayList<Integer>> resultaat = longestStringsInArray(rooster);

        int stringLengte = resultaat.get(0).size();
        ArrayList<String> longestStrings = new ArrayList<>();
        for (ArrayList<Integer> al : resultaat) {
            String s = "";
            for (int i = 0; i < stringLengte; i++) {
                s += boom[al.get(i)].getLabel();
            }
            longestStrings.add(s);
        }
        Intersection is = new Intersection();
        char[] doorsnede = is.intersection(longestStrings);

        String[] finalOutput = new String[3];

        String eindString;

        if (!longestStrings.isEmpty()) {
            finalOutput[0] = longestStrings.get(0).length() + "";
        } else {
            finalOutput[0] = "0";
        }

        if (doorsnede.length > 0) {
            finalOutput[1] = new String(doorsnede);
        } else {
            finalOutput[1] = "/";
        }

        if (!longestStrings.isEmpty()) {
            int maxlengte = longestStrings.get(0).length();
            String[] toBeJoined = new String[maxlengte];
            for (int i = 0; i < maxlengte; i++) {
                toBeJoined[i] = resultaat.get(0).get(i) + "";
            }
            finalOutput[2] = String.join(" ", toBeJoined);
        }

        System.out.println(String.join(" ", finalOutput));
    }

    private ArrayList<NextIt> functietje(int start, int end) {
        if (start == end) {
            rooster[start][end].add(start);
            aantalloops++;
        } else if ((rooster[start][end].isEmpty()) && contains(boom[start].getBuren(), boom[start].getAantalBuren(), end) && (boom[start].getLabel() == boom[end].getLabel())) {
            rooster[start][end].add(0, start);
            rooster[start][end].add(end);
            aantalloops++;
        } else {
            if (boom[start].getLabel() == boom[end].getLabel()) {
                for (int i : boom[start].getBuren()) {
                    for (int j : boom[end].getPointedFrom()) {
                        if (!rooster[i][j].isEmpty()) {
                            if ((rooster[start][end].isEmpty()) ||
                                    ((!rooster[start][end].isEmpty()) && (rooster[i][j].size() + 2 > rooster[start][end].size()))) {
                                rooster[start][end] = new ArrayList<Integer>(rooster[i][j]);
                                rooster[start][end].add(0, start);
                                rooster[start][end].add(end);
                                aantalloops++;
                            }
                        }
                    }
                }
            }
        }
        ArrayList<NextIt> nieuwtjes = new ArrayList<>();
        for (int k : boom[end].getBuren()) {
            nieuwtjes.add(new NextIt(start, k));
        }
        return nieuwtjes;
    }

    private boolean contains(int[] lijst, int size, int el) {
        boolean found = false;
        int n = 0;
        while ((!found) && (n < size)) {
            if (lijst[n] == el) {
                found = true;
            }
            n++;
        }
        return found;
    }

    public int getSize() {
        return size;
    }

    public Node[] getBoom() {
        return boom;
    }

    public ArrayList<ArrayList<Integer>> longestStringsInArray(ArrayList<Integer>[][] strings) {

        ArrayList<Integer> longest = new ArrayList<>();

        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings.length; j++) {
                if ((!strings[i][j].isEmpty()) && (strings[i][j].size() > longest.size())) {
                    longest = strings[i][j];
                }
            }
        }

        ArrayList<ArrayList<Integer>> evenLang = new ArrayList<>();
        evenLang.add(longest);

        for (int v = 0; v < strings.length; v++) {
            for (int w = 0; w < strings.length; w++) {
                if (!strings[v][w].isEmpty()) {
                    if ((!strings[v][w].equals(longest)) && (strings[v][w].size() == longest.size())) {
                        evenLang.add(strings[v][w]);
                    }

                }
            }
        }
        return evenLang;
    }
}
