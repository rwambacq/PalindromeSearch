package palindrome;

import java.util.ArrayList;

public class Tree {

    private Node[] boom;
    private int size;
    private Palindromes[][] rooster;

    public Tree(int size, Node[] boom) {
        this.size = size;
        this.boom = boom;
        rooster = new Palindromes[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rooster[i][j] = new Palindromes();
            }
        }
    }

    public void withoutCycles(){
        for (int i = 0; i < size; i++) {
            rooster[i][i].palindromen.add(new Palindrome());
            rooster[i][i].palindromen.get(0).palindroom.add(i);
            //Dit was nodig omdat ArrayLists van ArrayLists blijkbaar te abstract zijn voor java, ook al werkt het verder in de code wel, ik snap er niets van
        }
        maxPalindrome();
    }

    public void withCycles(){
        for (int i = 0; i < size; i++) {
            fillPalindromeMatrix(i, i);
        }
        boolean palindromicCycle = false;
        for (int i = 0; i < size; i++) {
            if (rooster[i][i].palindromen.get(0).palindroom.size() > 1) {
                palindromicCycle = true;
            }
        }
        if(palindromicCycle){
            System.out.println("0 / ");
        } else {
            maxPalindrome();
        }
    }

    public void maxPalindrome() {
        if(size > 0) {

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (j != i) {
                        fillPalindromeMatrix(i, j);
                    }
                }
            }

            ArrayList<Palindrome> resultaat = longestStringsInArray();

            int stringLengte = resultaat.get(0).palindroom.size();
            ArrayList<String> longestStrings = new ArrayList<>();
            for (Palindrome pal : resultaat) {
                String s = "";
                for (int i = 0; i < stringLengte; i++) {
                    s += boom[pal.palindroom.get(i)].getLabel();
                }
                longestStrings.add(s);
            }
            Intersection is = new Intersection();
            char[] doorsnede = is.intersection(longestStrings);

            String[] finalOutput = new String[3];

            String eindString;

            finalOutput[0] = longestStrings.get(0).length() + "";
            if(doorsnede.length > 0) {
                finalOutput[1] = new String(doorsnede);
            } else {
                finalOutput[1] = "/";
            }

            int maxlengte = longestStrings.get(0).length();
            String[] toBeJoined = new String[maxlengte];
            for (int i = 0; i < maxlengte; i++) {
                toBeJoined[i] = resultaat.get(0).palindroom.get(i) + "";
            }
            finalOutput[2] = String.join(" ", toBeJoined);

            System.out.println(String.join(" ", finalOutput));
        } else {
            System.out.println("0 / ");
        }
    }

    public void fillPalindromeMatrix(int start, int end){
        if(rooster[start][end].palindromen.isEmpty()){
            Node s = boom[start];
            Node e = boom[end];
            if(s.getLabel() == e.getLabel()){
                if(contains(s.getBuren(), end)){
                    ArrayList<Palindrome> longestBetween = new ArrayList<>();
                    for (int b : s.getBuren()) {
                        if(b != end) {
                            for (int f : e.getPointedFrom()) {
                                if(f != start){
                                    longestBetween = getPalindromes(longestBetween, b, f);
                                }
                            }
                        }
                    }
                    if(longestBetween.isEmpty()){
                        rooster[start][end].palindromen.add(new Palindrome());
                        rooster[start][end].palindromen.get(0).palindroom.add(start);
                        rooster[start][end].palindromen.get(0).palindroom.add(end);
                    } else {
                        addToPalindromeList(start, end, longestBetween);
                    }
                } else {
                    ArrayList<Palindrome> longestBetween = new ArrayList<>();
                    for (int b : s.getBuren()) {
                            for (int f : e.getPointedFrom()) {
                                longestBetween = getPalindromes(longestBetween, b, f);
                            }
                    }
                    if(longestBetween.isEmpty()){
                        rooster[start][end].palindromen.add(new Palindrome());
                        rooster[start][end].palindromen.get(0).palindroom.add(-1);
                    } else {
                        addToPalindromeList(start, end, longestBetween);
                    }
                }
            } else {
                rooster[start][end].palindromen.add(new Palindrome());
                rooster[start][end].palindromen.get(0).palindroom.add(-1);
            }
        }
    }

    private void addToPalindromeList(int start, int end, ArrayList<Palindrome> longestBetween) {
        for (int p = 0; p < longestBetween.size(); p++) {
            rooster[start][end].palindromen.add(new Palindrome());
            rooster[start][end].palindromen.get(p).palindroom.add(start);
            rooster[start][end].palindromen.get(p).palindroom.addAll(longestBetween.get(p).palindroom);
            rooster[start][end].palindromen.get(p).palindroom.add(end);
        }
    }

    private ArrayList<Palindrome> getPalindromes(ArrayList<Palindrome> longestBetween, int b, int f) {
        if(rooster[b][f].palindromen.isEmpty()){
            fillPalindromeMatrix(b, f);
            longestBetween = getPalindromes(longestBetween, b, f);
        } else {
            if(rooster[b][f].palindromen.get(0).palindroom.get(0) != -1){
                if(longestBetween.isEmpty() || rooster[b][f].palindromen.get(0).palindroom.size() > longestBetween.get(0).palindroom.size()) {
                    longestBetween = rooster[b][f].palindromen;
                } else if (rooster[b][f].palindromen.get(0).palindroom.size() == longestBetween.get(0).palindroom.size()){
                    longestBetween.addAll(rooster[b][f].palindromen);
                }
            }
        }
        return longestBetween;
    }

    private boolean contains(int[] lijst, int el) {
        boolean found = false;
        int n = 0;
        while ((!found) && (n < lijst.length)) {
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

    public ArrayList<Palindrome> longestStringsInArray() {

        ArrayList<Palindrome> longest = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ( ( ! rooster[i][j].palindromen.isEmpty() ) && ( rooster[i][j].palindromen.get(0).palindroom.get(0) != -1 ) ) {
                    if( longest.isEmpty() || ( rooster[i][j].palindromen.get(0).palindroom.size() > longest.get(0).palindroom.size() ) ){
                        longest = rooster[i][j].palindromen;
                    } else if ( rooster[i][j].palindromen.get(0).palindroom.size() == longest.get(0).palindroom.size() ){
                        longest.addAll(rooster[i][j].palindromen);
                    }
                }
            }
        }
        return longest;
    }
}
