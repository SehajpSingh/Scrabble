import java.io.FileNotFoundException;


public class main {

    public static void main(String[] args) throws FileNotFoundException {

        //setting up dictionary
        ReadFile read = new ReadFile();
        read.readFile();

        //setting up board
        CreateBoard board = new CreateBoard();
        board.create();

        //setting up tile
        createTile crea = new createTile();
        crea.tiles();

        //caling the logic to solve the puzzle
        Logic logic = new Logic(read.dictEdit, board, crea, read);
        logic.printBoard();
        //System.out.println("this is the one "+
        //read.dictEdit.isWord("aa", read.getRoot()));

        //System.out.println(read.dictEdit.isPrefix("aardvarks", read.getRoot()));
        //System.out.println("this is the one" +read.dictEdit.getPerms( read.getRoot(), "heatpb"));

        //System.out.println("this onesssss "+read.dictEdit.isPrefix("tad", read.getRoot()));
        //System.out.println("this onesssss "+read.dictEdit.isPrefix("dt", read.getRoot()));

        logic.findPrefix();
    }
}
