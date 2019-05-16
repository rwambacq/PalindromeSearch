package datastructuren;

import hulpklassen.Intersection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * DirectedGraph klasse.
 * Bevat een lijst van Nodes die de boom voorstelt en alle functies die nodig zijn om het langste palindromisch pad op te zoeken.
 *
 * @author Ruben Wambacq
 *
 */
public class DirectedGraph {

    private Node[] boom;
    private int size;
    private Palindromes[][] rooster;
    private HashMap<Integer, ArrayList<Integer>> tbd;
    private boolean tbdfound;

    /**
     * DirectedGraph constructor, hieraan wordt de grootte en de lijst van Nodes meegegeven.
     * Maakt ook het 2D-rooster aan dat nodig is om de langste palindromen te vinden.
     * @param size Grootte van de graaf.
     * @param graaf Lijst van Node objecten die de volledige graaf beschrijven.
     */
    public DirectedGraph(int size, Node[] graaf) {
        this.size = size;
        this.boom = graaf;
        rooster = new Palindromes[size][size];
        tbd = new HashMap<>();
        tbdfound = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rooster[i][j] = new Palindromes();
            }
        }
    }

    /**
     * Deze functie wordt aangeroepen vanuit DAG, deze zal in het rooster op elke [i][i] een palindroom van lengte 1 gaan plaatsen.
     * Daarna wordt met maxPalindroom de rest van het rooster opgevuld
     */
    public void withoutCycles(){
        for (int i = 0; i < size; i++) {
            rooster[i][i].palindromen.add(new Palindrome());
            rooster[i][i].palindromen.get(0).palindroom.add(i);
            //Dit was nodig omdat ArrayLists van ArrayLists blijkbaar te abstract zijn voor java, ook al werkt het verder in de code wel, ik snap er niets van
        }
        maxPalindrome();
    }

    /**
     * Deze functie wordt aangeroepen vanuit DG,
     * deze zal in het rooster voor elke [i][i]gaan nakijken of er een palindroom is dat langer is dan 1 mbv fillPalindromeMatrix.
     * Indien dat zo blijkt te zijn, wordt gestopt en wordt 0 / teruggegeven.
     * Daarna wordt met maxPalindroom de rest van het rooster opgevuld
     */
    public void withCycles(){
        int i = 0;
        while ((i < size) && !tbdfound) {
            fillPalindromeMatrix(i, i);
            i++;
        }
        if(! tbdfound) {
            boolean palindromicCycle = false;
            for (int j = 0; j < size; j++) {
                if (rooster[j][j].palindromen.get(0).palindroom.size() > 1) {
                    palindromicCycle = true;
                }
            }
            if (palindromicCycle) {
                System.out.println("0 / ");
            } else {
                maxPalindrome();
            }
        } else {
            System.out.println("0 / ");
        }
    }

    /**
     * Deze functie zal 3 dingen doen:
     * Eerst zal het 2D-rooster worden opgevuld met op index (i, j) het langste palindroom van node i tot node j
     * of -1 indien er zo geen node is.
     * Daarna worden uit het rooster de langste palindromen gehaald.
     * Als laatste wordt er een doorsnede genomen van deze palindromen en wordt de correcte output geprint.
     */
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

    /**
     * Deze functie zal in het 2D-rooster voor index i,j zoeken wat het langste palindroom is dat start bij i en eindigt bij j.
     * Dit wordt gedaan door te kijken of het label van node i en node j gelijk is en door in het rooster te kijken of
     * de strings tussen node i en j palindromen zijn.
     * Als in het rooster nog niets is ingevuld voor deze tussenstrings wordt dit recursief gevuld.
     * @param start De index van de start node.
     * @param end De index van de end node.
     */
    public void fillPalindromeMatrix(int start, int end){
        if((tbd.get(start) == null) || (! tbd.get(start).contains(end) && (!tbdfound))) {
            tbd.computeIfAbsent(start, k -> new ArrayList<>());
            tbd.get(start).add(end);
            if (rooster[start][end].palindromen.isEmpty()) {
                Node s = boom[start];
                Node e = boom[end];
                if (s.getLabel() == e.getLabel()) {
                    if (contains(s.getBuren(), end)) {
                        ArrayList<Palindrome> longestBetween = new ArrayList<>();
                        for (int b : s.getBuren()) {
                            if (b != end) {
                                for (int f : e.getPointedFrom()) {
                                    if (f != start) {
                                        longestBetween = getPalindromes(longestBetween, b, f);
                                    }
                                }
                            }
                        }
                        if (longestBetween.isEmpty()) {
                            rooster[start][end].palindromen.add(new Palindrome());
                            rooster[start][end].palindromen.get(0).palindroom.add(start);
                            rooster[start][end].palindromen.get(0).palindroom.add(end);
                            tbd.get(start).remove((Integer)end);
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
                        if (longestBetween.isEmpty()) {
                            if(start == end){
                                rooster[start][end].palindromen.add(new Palindrome());
                                rooster[start][end].palindromen.get(0).palindroom.add(start);
                            } else {
                                rooster[start][end].palindromen.add(new Palindrome());
                                rooster[start][end].palindromen.get(0).palindroom.add(-1);
                                tbd.get(start).remove((Integer) end);
                            }
                        } else {
                            addToPalindromeList(start, end, longestBetween);
                            tbd.get(start).remove((Integer)end);
                        }
                    }
                } else {
                    tbd.get(start).remove((Integer)end);
                    rooster[start][end].palindromen.add(new Palindrome());
                    rooster[start][end].palindromen.get(0).palindroom.add(-1);
                }
            }
        } else {
            tbdfound = true;
        }
    }

    /**
     * Hulpfunctie voor fillPalindromeMatrix, wordt gebruikt om duplicaatcode te vermijden
     * @param start Een index van een startnode.
     * @param end Een index van een eindnode.
     * @param longestBetween Een lijst van palindromen.
     */
    private void addToPalindromeList(int start, int end, ArrayList<Palindrome> longestBetween) {
        for (int p = 0; p < longestBetween.size(); p++) {
            rooster[start][end].palindromen.add(new Palindrome());
            rooster[start][end].palindromen.get(p).palindroom.add(start);
            rooster[start][end].palindromen.get(p).palindroom.addAll(longestBetween.get(p).palindroom);
            rooster[start][end].palindromen.get(p).palindroom.add(end);
            tbd.get(start).remove((Integer)end);
        }
    }

    /**
     * Hulpfunctie voor fillPalindromeMatrix, wordt gebruikt om duplicaatcode te vermijden
     * @param b Een buur van de startnode in fillPalindromeMatrix.
     * @param f Een voorganger van de eindnode in fillPalindromeMatrix.
     * @param longestBetween Een lijst van palindromen.
     * @return Een lijst van palindromen die tussen 2 nodes zitten.
     */
    private ArrayList<Palindrome> getPalindromes(ArrayList<Palindrome> longestBetween, int b, int f) {
        if(!tbdfound) {
            if (rooster[b][f].palindromen.isEmpty()) {
                fillPalindromeMatrix(b, f);
                longestBetween = getPalindromes(longestBetween, b, f);
            } else {
                if (rooster[b][f].palindromen.get(0).palindroom.get(0) != -1) {
                    if (longestBetween.isEmpty() || rooster[b][f].palindromen.get(0).palindroom.size() > longestBetween.get(0).palindroom.size()) {
                        longestBetween = rooster[b][f].palindromen;
                    } else if (rooster[b][f].palindromen.get(0).palindroom.size() == longestBetween.get(0).palindroom.size()) {
                        longestBetween.addAll(rooster[b][f].palindromen);
                    }
                }
            }
            return longestBetween;
        } else {
            return longestBetween;
        }
    }

    /**
     * Hulpfunctie voor fillPalindromeMatrix, wordt gebruikt om te kijken of een integer in een lijst van integers zit.
     * @param el Een integer.
     * @param lijst Een lijst van integers.
     * @return een boolean die bepaalt of een integer in een lijst van integers zit.
     */
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

    /**
     * Get functie voor de grootte van de graaf.
     * @return De grootte van de graaf.
     */
    public int getSize() {
        return size;
    }

    /**
     * Get functie voor de graaf.
     * @return De graaf.
     */
    public Node[] getBoom() {
        return boom;
    }

    /**
     * Hulpfunctie voor maxPalindrome, wordt gebruikt om de langste palindromen te vinden in het 2D-rooster.
     * @return De langste palindromen in het rooster.
     */
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
