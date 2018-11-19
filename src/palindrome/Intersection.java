package palindrome;

import java.util.ArrayList;

public class Intersection {

    public int charToInt(char c) {
        int ascii = (int) c;
        if (ascii >= 48 && ascii <= 57) {
            return ascii - 48;
        } else if (ascii >= 65 && ascii <= 90) {
            return ascii - 55;
        } else if (ascii >= 97 && ascii <= 122) {
            return ascii - 61;
        } else {
            return -1;
        }
    }

    public char intToChar(int i) {
        if (i >= 0 && i <= 9) {
            i += 48;
        } else if (i >= 10 && i <= 35) {
            i += 55;
        } else if (i >= 36 && i <= 61) {
            i += 61;
        }
        return (char) i;

    }

    public long stringToLong(String s) {
        long toReturn = 0;
        for (char c : s.toCharArray()) {
            toReturn = toReturn | (1L << charToInt(c));
        }
        return toReturn;
    }

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
