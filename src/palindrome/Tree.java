package palindrome;

import java.util.ArrayList;

public class Tree {

    private Node[] boom;
    private int size;
    private int aantalloops;

    private String[][] rooster;

    public Tree (int size, Node[] boom){
        this.size = size;
        this.boom = boom;
        rooster = new String[size][size];
        aantalloops = 0;
    }

    public void maxPalindrome (){
        ArrayList<NextIt> nextOnes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            nextOnes.addAll(functietje(i, i));
        }

        while(! nextOnes.isEmpty()){
            ArrayList<NextIt> temp = new ArrayList<>();
            for (NextIt ni : nextOnes) {
                temp.addAll(functietje(ni.start, ni.end));
            }
            nextOnes = temp;
        }

        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                if(rooster[j][k] != null){
                    System.out.print(rooster[j][k] + " ");
                } else {
                    System.out.print("[LEEG] ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("Er zijn " + aantalloops + " loops genomen");
    }

    private ArrayList<NextIt> functietje(int start, int end){
        if(start == end){
            rooster[start][end] = boom[start].getLabel() + "";
            aantalloops++;
        } else if (rooster[start][end] == null && contains(boom[start].getBuren(), boom[start].getAantalBuren(), end) && (boom[start].getLabel() == boom[end].getLabel())){
            rooster[start][end] = boom[start].getLabel() + "" + boom[start].getLabel();
            aantalloops++;
        } else {
            if(boom[start].getLabel() == boom[end].getLabel()){
                for (int i : boom[start].getBuren()) {
                    for (int j : boom[end].getPointedFrom()) {
                        if(rooster[i][j] != null){
                            if((rooster[start][end] == null) ||
                                    (rooster[start][end] != null && (rooster[i][j].length() + 2 > rooster[start][end].length()))){
                                rooster[start][end] = boom[start].getLabel() + rooster[i][j] + boom[start].getLabel();
                                aantalloops++;
                            }
                        }
                    }
                }
            }
        }
        ArrayList<NextIt> nieuwtjes= new ArrayList<>();
        for ( int k : boom[end].getBuren()) {
            nieuwtjes.add(new NextIt(start, k));
        }
        return nieuwtjes;
    }

    private boolean contains(int[] lijst, int size, int el){
        boolean found = false;
        int n = 0;
        while ((!found) && (n < size)){
            if(lijst[n] == el){
                found = true;
            }
            n++;
        }
        return found;
    }

    public int getSize(){
        return size;
    }

    public Node[] getBoom() {
        return boom;
    }
}
