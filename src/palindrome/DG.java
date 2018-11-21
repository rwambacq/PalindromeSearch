package palindrome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DG {
    public static void main(String[] args) {
        BufferedReader br = null;
        TreeIO tio = new TreeIO();
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();

            while (true) {
                Tree boom = tio.getTree(br, input);
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
