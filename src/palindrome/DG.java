package palindrome;

import datastructuren.DirectedGraph;
import hulpklassen.TreeIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Directed Graph class.
 * Deze klasse wordt gebruikt om het langste palindromisch pad te vinden in een gerichte graaf, mogelijks met cykels.
 *
 * @author Ruben Wambacq
 *
 */
public class DG {

    /**
     * Main methode, start een loop die input vraagt in de vorm van een graaf en daarna de langste palindromische paden als output geeft
     * @param args standaard main argument
     */
    public static void main(String[] args) {
        BufferedReader br = null;
        TreeIO tio = new TreeIO();
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();

            while (true) {
                DirectedGraph boom = tio.getTree(br, input);
                boom.withCycles();
                input = br.readLine();
            }

        } catch (NumberFormatException ignored){}
        catch (Exception e) {
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
}
