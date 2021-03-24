package CommonCode;

import SolverCode.CreateBoard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Logic {
    private DictionaryEdit dictionaryEdit;
    private CreateBoard createBoard;
    private createTile tile;
    private ReadFile readFile;
    private HashSet<String> prefixes = new HashSet<String>();
    private HashSet<String> allPrefix = new HashSet<String>();
    private HashSet<String> suffixFromTray = new HashSet<String>();
    private HashSet<String> suffixFromBoardTray = new HashSet<String>();
    private HashSet<String> prefixBoardSuffixTray = new HashSet<String>();
    private HashSet<String> wordBoardTray = new HashSet<String>();
    private ArrayList<Coordinates> ankers = new ArrayList<>();
    private String tray;
    private String trayNoWild;

    public Logic(DictionaryEdit dictionaryEdit, CreateBoard createBoard, createTile tile, ReadFile file) {
        this.dictionaryEdit = dictionaryEdit;
        this.createBoard = createBoard;
        this.tile = tile;
        this.readFile = file;
    }

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

                } else {
                    rand = false;
                }
            }
            row = ankers.get(i).getRow();
            boolean rand1 = true;

            while (rand1) {
                if (row <= createBoard.board.length && createBoard.board[row + 1][col].getPlayedStatus() == true) {
                    strSufix = strSufix + createBoard.board[row + 1][col].getLetter();
                    row++;
                } else {
                    rand1 = false;
                }
            }

            row = ankers.get(i).getRow();
            if (strPrefix.length() + strSufix.length() == 0) {
                ankers.get(i).addChar('0');

            } else if (strPrefix.length() > 0 && strSufix.length() == 0) {
                for (int k = 0; k < 26; k++) {
                    int ascii = 'a' + k;
                    String temp = strPrefix + (char) ascii;

                    if (dictionaryEdit.isWord(temp, readFile.getRoot())) {
                        ankers.get(i).addChar((char) ascii);
                    }
                }
            } else if (strPrefix.length() == 0 && strSufix.length() > 0) {
                for (int k = 0; k < 26; k++) {
                    int ascii = 'a' + k;
                    String temp = (char) ascii + strSufix;

                    if (dictionaryEdit.isWord(temp, readFile.getRoot())) {
                        ankers.get(i).addChar((char) ascii);
                        //System.out.println("VALID WORD "+temp);
                    }
                }
            } else if (strPrefix.length() > 0 && strSufix.length() > 0) {
                for (int k = 0; k < 26; k++) {
                    int ascii = 'a' + k;
                    String temp = strPrefix + (char) ascii + strSufix;

                    if (dictionaryEdit.isWord(temp, readFile.getRoot())) {
                        ankers.get(i).addChar((char) ascii);
                        //System.out.println("VALID WORD "+temp);
                    }
                }
            }

        }
    }

    public void findPrefixfromTray() {
        tray = "ntnp*oi";
        int countWild = 0;
        boolean asterik = false;
        for (int i = 0; i < tray.length(); i++) {
            if (tray.charAt(i) == '*') {
                countWild++;

                asterik = true;
            }
        }
        tray = tray.replaceAll("\\*", "");
        trayNoWild = tray;
        if (countWild == 1) {
            for (int i = 0; i < 26; i++) {
                String str1 = tray + (char) ('a' + i);
                trayNoWild = str1;
                combination("", str1, prefixes );
            }
        } else if (countWild == 2) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    String str1 = tray + (char) ('a' + i) + (char) ('a' + j);
                    trayNoWild = str1;
                    combination("", str1, prefixes);
                }
            }
        } else {
            combination("", tray, prefixes);
        }
        Iterator value = prefixes.iterator();
        while (value.hasNext()) {
            permutation("", (String) value.next());
        }
        Iterator val = allPrefix.iterator();
        System.out.println("this is combination " + allPrefix.size());

    }

    private void combination(String prefix, String s, HashSet hashSet) {
        int N = s.length();
        hashSet.add(prefix);

        for (int i = 0; i < N; i++)
            combination(prefix + s.charAt(i), s.substring(i + 1), hashSet);
    }



    private void permutation(String prefix, String s) {
        int N = s.length();
        if (N == 0) {
            if (dictionaryEdit.isPrefix(prefix, readFile.getRoot())) {
                allPrefix.add(prefix);
                //System.out.println("here is :"+prefix);
            }
        }

        for (int i = 0; i < N; i++) {
            boolean ran = dictionaryEdit.isPrefix(prefix, readFile.getRoot()) && prefix.length() > 0;

            if (ran || prefix.length() == 0) {
                permutation(prefix + s.charAt(i), s.substring(0, i) + s.substring(i + 1, N));
            }
        }
    }

    public void getSuffixFromBoard() {
        //1. make sure the left of anker is empty

        for (int i = 0; i < ankers.size(); i++) {
            int col = ankers.get(i).getCol();
            int row = ankers.get(i).getRow();


            if ((col > 0 && !(createBoard.board[row][col - 1].getPlayedStatus())) || col == 0) {

                Iterator value = allPrefix.iterator();
                while (value.hasNext()) {
                    String prefix = (String) value.next();
                    int currentCol = col;
                    String strSufix = "";
                    int leftMostLetter = col - prefix.length() + 1;
                    for (int k = 0; k < prefix.length(); k++) {
                        currentCol = leftMostLetter + k;

                        for (int j = 0; j < prefix.length() && currentCol + 1 < createBoard.board.length && currentCol > 0; j++) {
                            if (createBoard.board[row][currentCol + 1].getPlayedStatus() == false) {
                                currentCol++;
                                if (currentCol >= createBoard.board.length) {
                                    break;
                                }
                            } else if (createBoard.board[row][currentCol + 1].getPlayedStatus() == true && j == prefix.length() - 1) {
                                boolean rand1 = true;


                                while (rand1) {
                                    if (currentCol < createBoard.board.length && createBoard.board[row][currentCol + 1].getPlayedStatus() == true) {
                                        strSufix = strSufix + createBoard.board[row][currentCol + 1].getLetter();
                                        currentCol++;
                                    } else if (currentCol < createBoard.board.length) {
                                        String leftOver = trayNoWild;
                                        for (int a = 0; a < prefix.length(); a++) {
                                            leftOver = leftOver.replaceFirst("" + prefix.charAt(a), "");

                                        }
                                        //System.out.println("used = "+prefix+" leftover= "+leftOver+" "+(prefix.length()+leftOver.length()));
                                        if (leftOver.length() > 0) {

                                            combination("", leftOver, suffixFromBoardTray);
                                            Iterator value1 = suffixFromBoardTray.iterator();

                                            while (value1.hasNext()) {
                                                String test = (String) value1.next();
                                              //  System.out.println("leftOverPerm " + test + "     usedLetters:  " + prefix + " " + strSufix);
                                                permutationfromTray(prefix + strSufix, test);
                                            }
                                            suffixFromBoardTray.clear();


                                        }
                                        rand1 = false;
                                    }
                                }
                                //System.out.println("foundword    "+prefix+" "+strSufix);
                            } else {
                                break;
                            }
                        }

                        if (dictionaryEdit.isWord(prefix + strSufix, readFile.getRoot())) {
                            //if((prefix+strSufix).equals("bodgiest"))
                            //System.out.println("foundword  " + prefix + " " + strSufix);
                        }
                    }


                }
            }
        }
    }

    public void getPrefixFromBoard() {
        for (int i = 0; i < ankers.size(); i++) {
            int col = ankers.get(i).getCol();
            int row = ankers.get(i).getRow();

            if (col - 1 >= 0 && createBoard.board[row][col - 1].getPlayedStatus() == true) {
                boolean rand = true;
                String strPrefix = "";
                while (rand) {
                    //may be row and column are switched
                    if (col >= 1 && createBoard.board[row][col - 1].getPlayedStatus() == true) {
                        strPrefix = createBoard.board[row][col - 1].getLetter() + strPrefix;
                        col--;

                    } else {
                        rand = false;
                    }
                }
                //System.out.println("prefix from board "+strPrefix);
                Iterator value = prefixes.iterator();
                while (value.hasNext()) {
                    permutationfromTrayone(strPrefix, (String) value.next());
                }
            }
        }
    }

    int bestScore = -1;
    int bestRow = 2;
    int bestCol = 8;
    char firstWild = 'n';
    char secondWild;
    String bestStr = "lemoned";

    private void calcScore(int row, int col, String str){
        boolean isValid = true;
        for(int i = 0; i < str.length(); i++){
            //if(ankers[row][col].getSet.contains(str.charAt(i)) )
        }
    }





    private void permutationfromTrayone(String prefix, String s) {
        int N = s.length();
        if (N == 0) {
            if (dictionaryEdit.isWord(prefix, readFile.getRoot())) {
                prefixBoardSuffixTray.add(prefix);
                System.out.println("1 here is : " + prefix);
            }
        }

        for (int i = 0; i < N; i++) {
            boolean ran = dictionaryEdit.isPrefix(prefix, readFile.getRoot()) && prefix.length() > 0;

            if (ran || prefix.length() == 0) {
                permutationfromTray(prefix + s.charAt(i), s.substring(0, i) + s.substring(i + 1, N));
            }
        }
    }

    private void permutationfromTray(String prefix, String s) {
        int N = s.length();
        if (N == 0) {
            if (dictionaryEdit.isWord(prefix, readFile.getRoot())) {
                prefixBoardSuffixTray.add(prefix);
                System.out.println("1 here is : " + prefix);
            }
        }

        for (int i = 0; i < N; i++) {
            boolean ran = dictionaryEdit.isPrefix(prefix, readFile.getRoot()) && prefix.length() > 0;

            if (ran || prefix.length() == 0) {
                permutationfromTray(prefix + s.charAt(i), s.substring(0, i) + s.substring(i + 1, N));
            }
        }
    }

    private void Score() {

    }

    protected void clearAllSets() {

    }
}
