package datastructuren;

/**
 * Node klasse.
 * Bevat informatie over een top van een gerichte graaf.
 *
 * @author Ruben Wambacq
 *
 */
public class Node {

    private int aantalBuren;
    private int[] buren;
    private char label;
    private int[] pointedFrom;

    /**
     * Node constructor, hieraan wordt het aantal buren meegegeven, een lijst van die buren en het label van de top, een karakter.
     * @param aantalBuren Het aantal buren van de top.
     * @param buren Een lijst met buren.
     * @param label Het label van deze node.
     */
    public Node(int aantalBuren, int[] buren, char label) {
        this.aantalBuren = aantalBuren;
        this.buren = buren;
        this.label = label;
    }

    /**
     * Get functie voor het label.
     * @return het label van deze node.
     */
    public char getLabel() {
        return label;
    }
    /**
     * Get functie voor het aantal buren.
     * @return het aantal buren van deze node.
     */
    public int getAantalBuren() {
        return aantalBuren;
    }
    /**
     * Get functie voor de lijst van de buren
     * @return Een lijst van buren.
     */
    public int[] getBuren() {
        return buren;
    }
    /**
     * Get functie voor de voorgangers van deze node.
     * @return De voorgangers van deze node.
     */
    public int[] getPointedFrom() {
        return pointedFrom;
    }

    /**
     * Set functie voor de voorgangers van deze node.
     * @param pointedFrom Een lijst van voorgangers.
     */
    public void setPointedFrom(int[] pointedFrom) {
        this.pointedFrom = pointedFrom;
    }
}
