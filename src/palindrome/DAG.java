package palindrome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DAG {


    public static void main(String[] args) {
        BufferedReader br = null;
        TreeIO tio = new TreeIO();
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();

            while (true) {
                Tree boom = tio.getTree(br, input);
                boom.withoutCycles();
                input = br.readLine();
            }

        } catch (Exception ignored) {
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
