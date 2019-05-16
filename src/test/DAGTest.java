package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import palindrome.DAG;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 * Testklasse voor het algoritme in de DAG klasse.
 *
 * @author Ruben Wambacq
 *
 */
public class DAGTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private DAG dag =  new DAG();

    /**
     * Zet de output en error streams naar custom streams.
     */
    @Before
    public void openStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * Test om te kijken of de input werkt.
     */
    @Test
    public void basicInputTest() {
        final InputStream original = System.in;
        InputStream inContent = new ByteArrayInputStream("5\nA 2\n2 3\nB 0\n\nB 1\n3\nA 2\n1 4\nC 0\n\n".getBytes());
        System.setIn(inContent);
        DAG.main(null);
        assertEquals("3 AB 0 2 3\r\n", outContent.toString());
        System.setIn(original);
    }

    /**
     * Checkt of het algoritme werkt voor een lege graaf.
     */
    @Test
    public void emptyTest() {
        final InputStream original = System.in;
        InputStream inContent = new ByteArrayInputStream("0\n".getBytes());
        System.setIn(inContent);
        DAG.main(null);
        assertEquals("0 / \r\n", outContent.toString());
        System.setIn(original);
    }

    /**
     * Checkt of het algoritme werkt voor een graaf die in het iteratief algoritme dat ik in deel 1 had gemaakt een speciaal geval was.
     * Ik laat het staan omdat te veel testen geen kwaad kan.
     */
    @Test
    public void changeNeededTest() {
        final InputStream original = System.in;
        InputStream inContent = new ByteArrayInputStream("5\nA 1\n1\nB 2\n2 3\nA 1\n3\nB 1\n4\nA 0\n\n".getBytes());
        System.setIn(inContent);
        DAG.main(null);
        assertEquals("5 AB 0 1 2 3 4\r\n", outContent.toString());
        System.setIn(original);
    }

    /**
     * Checkt of het algoritme werkt voor een enkele node.
     */
    @Test
    public void singleNodeTest() {
        final InputStream original = System.in;
        InputStream inContent = new ByteArrayInputStream("1\nA 0\n\n".getBytes());
        System.setIn(inContent);
        DAG.main(null);
        assertEquals("1 A 0\r\n", outContent.toString());
        System.setIn(original);
    }

    /**
     * Checkt of het algoritme werkt voor twee niet-gelijke nodes.
     */
    @Test
    public void twoNonEqualNodes() {
        final InputStream original = System.in;
        InputStream inContent = new ByteArrayInputStream("2\nA 1\n1\nB 0\n\n".getBytes());
        System.setIn(inContent);
        DAG.main(null);
        assertEquals("1 / 0\r\n", outContent.toString());
        System.setIn(original);
    }

    /**
     * Checkt of het algoritme werkt voor twee gelijke nodes.
     */
    @Test
    public void twoEqualNodes() {
        final InputStream original = System.in;
        InputStream inContent = new ByteArrayInputStream("2\nA 1\n1\nA 0\n\n".getBytes());
        System.setIn(inContent);
        DAG.main(null);
        assertEquals("2 A 0 1\r\n", outContent.toString());
        System.setIn(original);
    }

    /**
     * Checkt of het algoritme werkt voor een graaf die meerdere palindromen bevat.
     */
    @Test
    public void multiplePalindromes() {
        final InputStream original = System.in;
        InputStream inContent = new ByteArrayInputStream("3\nA 2\n1 2\nA 0\n\nA 0\n\n".getBytes());
        System.setIn(inContent);
        DAG.main(null);
        assertEquals("2 A 0 1\r\n", outContent.toString()); // Hier zou 2 A 0 2 ook goed zijn, maar mijn algoritme geeft hier 2 A 0 1 terug
        System.setIn(original);
    }

    /**
     * Checkt of het algoritme werkt voor een grote graaf.
     */
    @Test
    public void longTreeTest() {
        final InputStream original = System.in;
        InputStream inContent = new ByteArrayInputStream("20\nA 1\n6\nD 2\n0 6\nE 2\n1 3\nA 1\n4\nC 0\n\nB 2\n10 11\nA 2\n3 12\nE 1\n3\nD 3\n7 3 4\nA 1\n8\nE 1\n11\nA 2\n6 7\nD 2\n8 13\nA 1\n9\nC 2\n13 19\nC 1\n11\nE 2\n11 12\nA 1\n12\nD 2\n14 19\nA 0\n\n".getBytes());
        // Willekeurig gegenereerde waarden (van A-E) voor 20 nodes en deze zo verbonden zodat er een lang palindroom in zit
        System.setIn(inContent);
        DAG.main(null);
        assertEquals("9 ADE 2 1 0 6 12 13 9 8 7\r\n", outContent.toString());
        System.setIn(original);
    }

    /**
     * Creeert een test voor een willekeurige graaf, checkt gewoon of er output is.
     */
    @Test
    public void randomShortTreeTest() {
        final InputStream original = System.in;
        InputStream inContent = new ByteArrayInputStream(randomShortTree(5, new String[]{"A", "B", "C"}).getBytes());
        System.setIn(inContent);
        DAG.main(null);
        assertTrue(outContent.toString().length() > 0);
        System.setIn(original);
    }

    /**
     * Dit voert randomShortTreeTest 3 keer uit, kan ook meer.
     */
    @Test
    public void randomShortTreeTests(){
        for (int i = 0; i < 3; i++) {
            randomShortTreeTest();
        }
    }

    /**
     * Genereert een string die een willekeurige graaf voorstelt.
     * @param grootte De gevraagde grootte van de graaf.
     * @param chars De karakters die in de graaf mogen zitten.
     * @return Een string die een willekeurige graaf voorstelt.
     */
    public String randomShortTree(int grootte, String[] chars){
        Random r = new Random();

        String[] karakters = chars;
        int lengte = grootte;

        String[] output = new String[lengte];
        for (int i = 0; i < lengte; i++) {
            output[i] = karakters[r.nextInt(karakters.length)];
        }

        String[] toReturn = new String[2*lengte + 1];
        toReturn[0] = lengte + "";

        for (int i = 0; i < lengte; i++) {
            int verbindingen = r.nextInt(2) + 1;
            if (i == (lengte-1)){
                verbindingen = 0;
            } else if (i == (lengte-2)){
                verbindingen = r.nextInt(2);
            }

            toReturn[2*i + 1] = output[i] + " " + verbindingen;

            int[] conn = new int[verbindingen];
            for (int j = 0; j < verbindingen; j++) {
                conn[j] = -1;
            }
            for (int j = 0; j < verbindingen; j++) {
                if(i < lengte - 1){
                    int newInt = r.nextInt((lengte-1) - i) + (i + 1);
                    while(contains(conn, newInt)){
                        newInt = r.nextInt((lengte-1) - i) + (i + 1);
                    }
                    conn[j] = newInt;
                }
            }
            String[] stringconn = new String[verbindingen];
            for (int j = 0; j < verbindingen; j++) {
                stringconn[j] = conn[j] + "";
            }
            toReturn[2*i + 2] = String.join(" " ,stringconn);

        }
        return String.join("\n", toReturn) + "\n";
    }

    /**
     * Hulpfunctie voor randomShortTree, wordt gebruikt om te kijken of een integer in een lijst van integers zit.
     * @param el Een integer.
     * @param lijst Een lijst van integers.
     * @return een boolean die bepaalt of een integer in een lijst van integers zit.
     */
    private boolean contains(int[] lijst, int el){
        boolean erin = false;
        int teller = 0;
        while((!erin) && teller < lijst.length){
            erin = (lijst[teller] == el);
            teller++;
        }
        return erin;
    }

    /**
     * Zet de output en error streams terug naar de standaard streams.
     */
    @After
    public void closeStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}