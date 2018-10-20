package palindrome;

public class Node {

    private int aantalBuren;
    private int[] buren;
    private char label;
    private int[] pointedFrom;

    public Node(int aantalBuren, int[] buren, char label) {
        this.aantalBuren = aantalBuren;
        this.buren = buren;
        this.label = label;
    }

    public char getLabel() {
        return label;
    }

    public int getAantalBuren() {
        return aantalBuren;
    }

    public int[] getBuren() {
        return buren;
    }

    public int[] getPointedFrom() {
        return pointedFrom;
    }

    public void setPointedFrom(int[] pointedFrom) {
        this.pointedFrom = pointedFrom;
    }
}
