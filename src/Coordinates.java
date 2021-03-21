import java.util.HashSet;

public class Coordinates {

    private int row;
    private int col;
    private HashSet<Character> set =new HashSet();

    public Coordinates(int row, int col){
        this.row = row;
        this.col = col;
    }

    protected void addChar(char x){
        set.add(x);
    }
   protected boolean hasChar(char x){
        return set.contains(x);
    }

    public int getRow() { return row; }

    public int getCol() {
        return col;
    }
}
