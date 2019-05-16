package hulpklassen;

import java.util.ArrayList;

/**
 * Intersection klasse.
 * Dit is een hulpklasse om de doorsnede van de karakters in een lijst strings te bepalen
 *
 * @author Ruben Wambacq
 *
 */
public class Intersection {

    /**
     * Zet een bepaald karakter om naar een getal tussen 0 en 62.
     * @param c Een karakter.
     * @return Een integer tussen 0 en 62.
     */
    public int charToInt(char c) {
        int ascii = (int) c;
        if (ascii >= 48 && ascii <= 57) {
            return ascii + 5;
        } else if (ascii >= 65 && ascii <= 90) {
            return ascii - 65;
        } else if (ascii >= 97 && ascii <= 122) {
            return ascii - 70;
        } else {
            return -1;
        }
    }

    /**
     * Zet een bepaalde int tussen 0 en 62 om naar een karakter.
     * @param i Een integer.
     * @return Een karakter.
     */
    public char intToChar(int i) {
        if (i >= 0 && i <= 26) {
            i += 65;
        } else if (i >= 27 && i <= 52) {
            i += 70;
        } else if (i >= 53 && i <= 62) {
            i -= 5;
        }
        return (char) i;

    }

    /**
     * Zet een bepaalde string om naar een long die alle karakters voorstelt die de string bevat.
     * @param s Een string.
     * @return Een long waarvan de bits die op 1 staan, willen zeggen dat het karakter dat die bit voorstelt in de string zit
     */
    public long stringToLong(String s) {
        long toReturn = 0;
        for (char c : s.toCharArray()) {
            toReturn = toReturn | (1L << charToInt(c));
        }
        return toReturn;
    }

    /**
     * Deze functie returnt de doorsnede van de karakters in een lijst strings
     * @param strings Een lijst van strings.
     * @return Een lijst karakters die de doorsnede van de karakters in een lijst strings voorstelt.
     */
    public char[] intersection(ArrayList<String> strings) {

        if ((strings.size() != 0)) {
            long allemaalSamenNu;
            allemaalSamenNu = stringToLong(strings.get(0));

            for (int i = 1; i < strings.size(); i++) {
                allemaalSamenNu = allemaalSamenNu & stringToLong(strings.get(i));
            }

            ArrayList<Integer> al = new ArrayList<>();
            for (int i = 0; i < 64; i++) {
                if ((allemaalSamenNu & (1L << i)) != 0) {
                    al.add(i);
                }
            }

            char[] toReturn = new char[al.size()];
            int m = 0;

            for (int k : al) {
                toReturn[m] = intToChar(k);
                m++;
            }

            return toReturn;
        } else {
            return new char[0];
        }
    }

}
