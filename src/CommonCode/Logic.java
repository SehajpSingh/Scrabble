package CommonCode;

import CommonCode.DictionaryEdit;
import CommonCode.createTile;
import SolverCode.CreateBoard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Logic {
    DictionaryEdit dictionaryEdit;
    CreateBoard createBoard;
    createTile tile;
    ReadFile readFile;

    public Logic(DictionaryEdit dictionaryEdit, CreateBoard createBoard, createTile tile, ReadFile file)
    {
        this.dictionaryEdit = dictionaryEdit;
        this.createBoard = createBoard;
        this.tile = tile;
        this.readFile = file;
    }

    ArrayList<Coordinates> ankers = new ArrayList<>();

    public void printBoard() {
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

//        for (int i = 0; i < ankers.size(); i++) {
//            System.out.println(ankers.get(i).getRow()+" "+ ankers.get(i).getCol());
//        }

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
            row = ankers.get(i).getRow();
            boolean rand1 = true;

            while (rand1) {
                if (row <= createBoard.board.length && createBoard.board[row + 1][col].getPlayedStatus() == true) {
                    strSufix = strSufix+createBoard.board[row + 1][col].getLetter() ;
                    row++;
                } else{
                    rand1 = false;
                }
            }

            row = ankers.get(i).getRow();
            if(strPrefix.length()+strSufix.length()==0) {
                 ankers.get(i).addChar('0');

            }else if(strPrefix.length()>0 && strSufix.length()==0){
                for(int k = 0; k < 26; k++){
                    int ascii = 'a'+k;
                    String temp = strPrefix+ (char) ascii;

                    if(dictionaryEdit.isWord(temp, readFile.getRoot())){
                        ankers.get(i).addChar((char) ascii);
                    }
                }
            }else if(strPrefix.length()==0 && strSufix.length()>0){
                for(int k = 0; k < 26; k++){
                    int ascii = 'a'+k;
                    String temp =  (char) ascii+strSufix;

                    if(dictionaryEdit.isWord(temp, readFile.getRoot())){
                        ankers.get(i).addChar((char) ascii);
                        //System.out.println("VALID WORD "+temp);
                    }
                }
            }else if(strPrefix.length()> 0 && strSufix.length()>0){
                    for(int k = 0; k < 26; k++){
                        int ascii = 'a'+k;
                        String temp =  strPrefix+(char) ascii+strSufix;

                        if(dictionaryEdit.isWord(temp, readFile.getRoot())){
                            ankers.get(i).addChar((char) ascii);
                            //System.out.println("VALID WORD "+temp);
                        }
                    }
            }

        }
    }

    private HashSet<String> prefixes = new HashSet<String>();
    private HashSet<String> allPrefix = new HashSet<String>();
    public void findPrefix(){
        combination("","asdt");

        Iterator value = prefixes.iterator();
        while (value.hasNext()) {
            permutation("", (String) value.next());
        }
        Iterator val = allPrefix.iterator();

        while (val.hasNext()) {
            System.out.println(val.next());
        }
    }

    private void combination(String prefix, String s){
        int N = s.length();
        prefixes.add(prefix);

        for (int i = 0 ; i < N ; i++)
            combination(prefix + s.charAt(i), s.substring(i+1));
    }

    private void permutation(String prefix, String s) {
        int N = s.length();

        if (N == 0) {
            allPrefix.add(prefix);
        }

        for(int i = 0; i < N; i++){
            boolean ran = dictionaryEdit.isPrefix(prefix, dictionaryEdit.tree);
            System.out.println("this is the boolean: "+ran);
            System.out.println("this is the prefix: "+prefix);

            if (ran) {
            System.out.println("this one: "+prefix);
                permutation(prefix + s.charAt(i), s.substring(0, i) + s.substring(i + 1, N));
            }
        }
    }


    private void tradeTiles() {
        //trading tiles means loosing turn
    }

    private void legalMove() {}

    private void detectingWin() {}

    private void gameOver() {}


}
