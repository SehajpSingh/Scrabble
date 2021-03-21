import java.util.ArrayList;
import java.util.HashSet;

public class Logic {
    DictionaryEdit dictionaryEdit;
    CreateBoard createBoard;
    createTile tile;
    ReadFile readFile;

    protected Logic(DictionaryEdit dictionaryEdit, CreateBoard createBoard, createTile tile, ReadFile file)
    {
        this.dictionaryEdit = dictionaryEdit;
        this.createBoard = createBoard;
        this.tile = tile;
        this.readFile = file;
    }

    ArrayList<Coordinates> ankers = new ArrayList<>();

    protected void printBoard() {
        int loop = createBoard.board.length;

        for (int i = 0; i < loop; i++) {
            for (int j = 0; j < loop; j++) {
                char letter = createBoard.board[i][j].getLetter();
                int wordMul = createBoard.board[i][j].getWordMult();
                int letterMult = createBoard.board[i][j].getLetterMult();

                if (wordMul == 0 && letterMult == 0) {
                    if (letter != '0') {
                        System.out.print(" " + letter + " ");
                    } else {
                        System.out.print(".. ");
                    }
                } else if (wordMul != 0) {
                    System.out.print(wordMul + "." + " ");
                } else if (letterMult != 0) {
                    System.out.print("." + letterMult + " ");
                }
            }
            System.out.println();
        }
        //delete these
        ankerPoints();
        storeCrossChecks();
    }

    private void compMove() {
        //this method can be used for both computer and puzzle solver
    }

    private void ankerPoints() {

        int loop = createBoard.board.length;
        for (int i = 0; i < loop; i++) {
            for (int j = 0; j < loop; j++) {
                if (createBoard.board[i][j].getPlayedStatus() == false) {

                    if (j + 1 <= loop - 1 && createBoard.board[i][j + 1].getPlayedStatus() == true) {
                        ankers.add(new Coordinates(i, j));
                    } else if (j - 1 >= 0 && createBoard.board[i][j - 1].getPlayedStatus() == true) {
                        ankers.add(new Coordinates(i, j));
                    } else if (i + 1 <= loop - 1 && createBoard.board[i + 1][j].getPlayedStatus() == true) {
                        ankers.add(new Coordinates(i, j));
                    } else if (i - 1 >= 0 && createBoard.board[i - 1][j].getPlayedStatus() == true) {
                        ankers.add(new Coordinates(i, j));
                    }

                }
            }
        }

        for (int i = 0; i < ankers.size(); i++) {
            System.out.println(ankers.get(i).getRow()+" "+ ankers.get(i).getCol());
        }

    }

    private void storeCrossChecks() {
        System.out.println("here in the function ");
        for (int i = 0; i < ankers.size(); i++) {

            String strPrefix = "";
            String strSufix = "";
            int row = ankers.get(i).getRow();
            int col = ankers.get(i).getCol();
            boolean rand = true;

            while (rand) {
                //may be row and column are switched
                if (row >= 1 && createBoard.board[row - 1][col].getPlayedStatus() == true) {
                    strPrefix = createBoard.board[row - 1][col].getLetter() + strPrefix;
                    row--;

                } else{
                    rand = false;
                }
            }
            //System.out.println(row + " " + col +" pre :"+strPrefix);
            row = ankers.get(i).getRow();
            boolean rand1 = true;

            while (rand1) {
                //may be row and column are switched
                if (row <= createBoard.board.length && createBoard.board[row + 1][col].getPlayedStatus() == true) {
                    strSufix = strSufix+createBoard.board[row + 1][col].getLetter() ;
                    row++;
                } else{
                    rand1 = false;
                }
            }
            System.out.println(row + " " + col +" suff :"+strSufix);
            row = ankers.get(i).getRow();
            if(strPrefix.length()+strSufix.length()==0) {
                 ankers.get(i).addChar('0');

            }else if(strPrefix.length()>0 && strSufix.length()==0){
                for(int k = 0; k < 26; k++){
                    int ascii = 'a'+k;
                    String temp = strPrefix+ (char) ascii;

                    if(dictionaryEdit.isWord(temp, readFile.getRoot())){
                        ankers.get(i).addChar((char) ascii);
                        //System.out.println("VALID WORD "+temp);
                    }
                }
            }else if(strPrefix.length()==0 && strSufix.length()>0){
                for(int k = 0; k < 26; k++){
                    int ascii = 'a'+k;
                    String temp =  (char) ascii+strSufix;

                    if(dictionaryEdit.isWord(temp, readFile.getRoot())){
                        ankers.get(i).addChar((char) ascii);
                        System.out.println("VALID WORD "+temp);
                    }
                }
            }else if(strPrefix.length()> 0 && strSufix.length()>0){
                    for(int k = 0; k < 26; k++){
                        int ascii = 'a'+k;
                        String temp =  strPrefix+(char) ascii+strSufix;

                        if(dictionaryEdit.isWord(temp, readFile.getRoot())){
                            ankers.get(i).addChar((char) ascii);
                            System.out.println("VALID WORD "+temp);
                        }
                    }
            }

        }

    }

    private void tradeTiles() {
        //trading tiles means loosing turn
    }

    private void legalMove() {

    }


    private void detectingWin() {
        //this function detects win
    }

    private void gameOver() {

    }


}
