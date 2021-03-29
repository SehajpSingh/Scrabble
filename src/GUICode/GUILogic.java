/**
 * Sehaj Punit Singh
 * This is Logic class and handles all the logic for computer move.
 * It generates the best possible move for the computer and calculates the score.
 */
package GUICode;
import CommonCode.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class GUILogic {
    private DictionaryEdit dictionaryEdit;
    private CreateGUIBoard createBoard;
    private createTile tile;
    private ReadFile readFile;
    private HashSet<String> prefixes = new HashSet<String>();
    private HashSet<String> allPrefix = new HashSet<String>();
    private HashSet<String> suffixFromTray = new HashSet<String>();
    private HashSet<String> suffixFromBoardTray = new HashSet<String>();
    private HashSet<String> prefixBoardSuffixTray = new HashSet<String>();
    private HashSet<String> wordBoardTray = new HashSet<String>();
    public ArrayList<Coordinates> ankers = new ArrayList<>();
    private Object[][] storeRefs;
    private String tray;
    private String trayNoWild;
    protected int BestCol;
    protected int BestRow;
    private int countWild = 0;
    private int bestScore = -1;
    public int bestRow = 2;
    public int bestCol = 8;
    private char fWild;
    private char sWild;
    private String bestStr = "";
    private String leftOver = "";

    /**
     * getter for the best string
     * @return returns the best string
     */
    public String getBestStr() {
        return bestStr;
    }

    /**
     * getter for the best score
     * @return returns the best score
     */
    public int getBestScore() {
        return bestScore;
    }

    /**
     * the constructor for the logic and takes the essential parameters
     * @param dictionaryEdit
     * @param createBoard
     * @param tile
     * @param file
     * @param tray
     */
    public GUILogic(DictionaryEdit dictionaryEdit, CreateGUIBoard createBoard, createTile tile, ReadFile file, String tray) {
        this.dictionaryEdit = dictionaryEdit;
        this.createBoard = createBoard;
        this.tile = tile;
        this.readFile = file;
        this.tray = tray;
        storeRefs = new Object[createBoard.board.length][createBoard.board.length];
        //System.out.println("this is board length: "+createBoard.board.length);
        //printBoard();
    }

    /**
     * prints the board
     */
    public void printBoard() {
        int loop = createBoard.board.length;
        System.out.println();
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

    }

    /**
     * prints the board
     */
    public void printBoard1() {
        int loop = createBoard.board.length;
        System.out.println();
        for (int i = 0; i < loop; i++) {
            for (int j = 0; j < loop; j++) {
                char letter = createBoard.board[i][j].getLetter();
                int wordMul = createBoard.board[i][j].getWordMult();
                int letterMult = createBoard.board[i][j].getLetterMult();

                if (letter != '0') {
                    System.out.print(" " + letter + " ");
                } else if (wordMul == 0 && letterMult == 0) {
                    System.out.print(".. ");
                } else if (wordMul != 0) {
                    System.out.print(wordMul + "." + " ");
                } else if (letterMult != 0) {
                    System.out.print("." + letterMult + " ");
                }
            }
            System.out.println();
        }

    }

    /**
     * prints the board
     */
    public void printBoard2(BoardObject boards[][]) {
        int loop = boards.length;
        System.out.println();
        for (int i = 0; i < loop; i++) {
            for (int j = 0; j < loop; j++) {
                char letter = boards[i][j].getLetter();
                int wordMul = boards[i][j].getWordMult();
                int letterMult = boards[i][j].getLetterMult();

                if (letter != '0') {
                    System.out.print(" " + letter + " ");
                } else if (wordMul == 0 && letterMult == 0) {
                    System.out.print(".. ");
                } else if (wordMul != 0) {
                    System.out.print(wordMul + "." + " ");
                } else if (letterMult != 0) {
                    System.out.print("." + letterMult + " ");
                }
            }
            System.out.println();
        }

    }

    /**
     * generates the anker points for board
     */
    public void ankerPoints() {

        int loop = createBoard.board.length;
        System.out.println("this is loop: ");
        for (int i = 0; i < loop; i++) {


            for (int j = 0; j < loop; j++) {
                if (createBoard.board[i][j].getPlayedStatus() == false) {

                    if (j + 1 <= loop - 1 && createBoard.board[i][j + 1].getPlayedStatus() == true) {
                        Coordinates temp = new Coordinates(i, j);
                        storeRefs[i][j] = (Object) temp;
                        ankers.add(temp);

                        // System.out.println("the refs are: "+temp1.getRow());
                    } else if (j - 1 >= 0 && createBoard.board[i][j - 1].getPlayedStatus() == true) {
                        Coordinates temp = new Coordinates(i, j);
                        ankers.add(temp);
                        storeRefs[i][j] = (Object) temp;
                    } else if (i + 1 <= loop - 1 && createBoard.board[i + 1][j].getPlayedStatus() == true) {
                        Coordinates temp = new Coordinates(i, j);
                        ankers.add(temp);
                        storeRefs[i][j] = (Object) temp;
                    } else if (i - 1 >= 0 && createBoard.board[i - 1][j].getPlayedStatus() == true) {
                        Coordinates temp = new Coordinates(i, j);
                        ankers.add(temp);
                        storeRefs[i][j] = (Object) temp;
                    }

                }
            }
        }

    }

    /**
     * generates the croosschecks
     */
    public void storeCrossChecks() {

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
                if (row + 1 < createBoard.board.length && createBoard.board[row + 1][col].getPlayedStatus() == true) {
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

                    if (dictionaryEdit.isWord(temp.toLowerCase(), readFile.getRoot())) {
                        ankers.get(i).addChar((char) ascii);
                        //System.out.println("VALID WORD "+temp);
                    }
                }
            }

        }
    }

    /**
     * finds the prefix for the word from given tray
     */
    public void findPrefixfromTray() {

        //tray = "ntnbtoi";

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
                fWild = (char) ('a' + i);
                String str1 = tray + (char) ('a' + i);
                trayNoWild = str1;
                combination("", str1, prefixes);
            }
        } else if (countWild == 2) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    fWild = (char) ('a' + i);
                    sWild = (char) ('a' + j);
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
        //System.out.println("this is combination " + allPrefix.size());

    }

    /**
     * generates the combinations for the prefix
     * @param prefix
     * @param s
     * @param hashSet
     */
    private void combination(String prefix, String s, HashSet hashSet) {
        int N = s.length();
        hashSet.add(prefix);

        for (int i = 0; i < N; i++)
            combination(prefix + s.charAt(i), s.substring(i + 1), hashSet);
    }

    /**
     * generates the premutations for the prefix
     * @param prefix
     * @param s
     */
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

    /**
     * gets the suffix or left part of the word
     */
    public void getSuffixFromBoard() {

        //1. make sure the left of anker is empty

        for (int i = 0; i < ankers.size(); i++) {
            int col = ankers.get(i).getCol();
            int row = ankers.get(i).getRow();
            //System.out.println("row "+row + "col " + col);
            if ((col > 0 && !(createBoard.board[row][col - 1].getPlayedStatus())) || col == 0) {

                Iterator value = allPrefix.iterator();
                while (value.hasNext()) {
                    leftOver = "";
                    String prefix = (String) value.next();
                    int currentCol = col;
                    String strSufix = "";

                    int leftMostLetter = col - prefix.length() + 1;
                    for (int k = 0; k < prefix.length(); k++) {
                        currentCol = leftMostLetter + k;

                        int firstLetterCol = currentCol;
                        int firstLetterRow = row;


                        for (int j = 0; j < prefix.length() && currentCol + 1 < createBoard.board.length && currentCol >= 0; j++) {

                            if (createBoard.board[row][currentCol + 1].getPlayedStatus() == false) {
                                currentCol++;

                                if (dictionaryEdit.isWord(prefix, readFile.getRoot())) {
                                    if (firstLetterCol >= 0 && firstLetterCol + prefix.length() - 1 < createBoard.board.length) {

                                        calcScore(firstLetterRow, firstLetterCol, prefix);
                                        //break;
                                    }
                                }

                                if (currentCol >= createBoard.board.length) {
                                    break;
                                }

                            } else if (createBoard.board[row][currentCol + 1].getPlayedStatus() == true && j == prefix.length() - 1) {
                                boolean rand1 = true;


                                while (rand1) {
                                    if (currentCol + 1 < createBoard.board.length && createBoard.board[row][currentCol + 1].getPlayedStatus() == true) {
                                        strSufix = strSufix + createBoard.board[row][currentCol + 1].getLetter();
                                        currentCol++;
                                    } else if (currentCol < createBoard.board.length) {
                                        leftOver = trayNoWild;
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
                                                permutationfromTray(firstLetterRow, firstLetterCol, prefix + strSufix, test);
                                            }
                                            suffixFromBoardTray.clear();


                                        }
                                        leftOver = "";
                                        rand1 = false;
                                    }
                                }
                                //System.out.println("foundword    "+prefix+" "+strSufix);
                            } else {
                                break;
                            }
                        }
                    }

                }
            }
        }
        leftOver = "";
    }

    /**
     * gets the prefix or left part from board
     */
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

                Iterator value = prefixes.iterator();

                while (value.hasNext()) {
                    permutationfromTrayone(row, col, strPrefix, (String) value.next());
                    leftOver = "";
                }
            }
        }

    }

    /**
     * calculates the score for board
     * @param row
     * @param col
     * @param str
     * @return
     */
    public int calcScore(int row, int col, String str) {
        int tempCol = col;
        int tempRow = row;


        for (int i = 0; i < str.length(); i++) {
            Coordinates temp1 = (Coordinates) storeRefs[row][tempCol];
            if (temp1 != null) {
                if (!temp1.getSet().contains(str.charAt(i))) {
                    if (!temp1.getSet().contains('0')) {
                        return 0;
                    }

                }
            }
            tempCol++;
        }


        int totalScore = 0;
        int wordMulti = 1;
        int wordScore = 0;
        char firstWild = fWild;
        char secondWild = sWild;
        tempCol = col;
        int countLetterOnboard = 0;
        String temp = str;

        for (int k = 0; k < str.length(); k++) {
            if (createBoard.board[row][tempCol].getPlayedStatus() && !(str.charAt(k) == createBoard.board[row][tempCol].getLetter())) {

                if (!createBoard.board[row][tempCol].equals(str.charAt(k))) {

                    return 0;
                }
            }
            if (k == str.length() - 1 && tempCol + 1 < createBoard.board.length) {
                if (createBoard.board[row][tempCol + 1].getPlayedStatus()) {

                    return 0;
                }
            }
            if (createBoard.board[row][tempCol].getWordMult() != 0) {
                wordMulti = wordMulti * createBoard.board[row][tempCol].getWordMult();
                //System.out.println("this is wordMulti: " +wordMulti);
            }
            if (createBoard.board[row][tempCol].getLetterMult() == 0) {


                if (!(countWild > 0 && (str.charAt(k) == 'n' || str.charAt(k) == 'b' || str.charAt(k) == 'p' || str.charAt(k) == 'b' || str.charAt(0) == 'd' || str.charAt(0) == 'g'))) {
                    wordScore += tile.tile[str.charAt(k) - 'a'].getMultiplier();
                }

                //}

            } else {
                wordScore += tile.tile[str.charAt(k) - 'a'].getMultiplier() * createBoard.board[row][tempCol].getLetterMult();
            }
            if (createBoard.board[row][tempCol].getPlayedStatus()) {
                countLetterOnboard++;
            }
            tempCol++;


        }

        totalScore += wordScore * wordMulti;


        if (leftOver.length() == 0 || leftOver.equals("z")) {
            if (str.length() - countLetterOnboard >= 7) {

                totalScore += 50;
            }


        }


        tempCol = col;
        for (int s = 0; s < str.length(); s++) {
            wordScore = 0;
            String strPrefix = "";
            String strSufix = "";

            boolean rand = true;

            int wordMultip = createBoard.board[tempRow][tempCol].getWordMult();
            int letterMultip = createBoard.board[tempRow][tempCol].getLetterMult();
            boolean isAcross = false;
            while (rand) {
                //may be row and column are switched
                if (tempRow >= 1 && createBoard.board[tempRow - 1][tempCol].getPlayedStatus() == true && createBoard.board[row][tempCol].getPlayedStatus() == false) {
                    strPrefix = createBoard.board[tempRow - 1][tempCol].getLetter() + strPrefix;
                    if (!createBoard.board[tempRow - 1][tempCol].isUpperCase()) {
                        wordScore += tile.tile[createBoard.board[tempRow - 1][tempCol].getLetter() - 'a'].getMultiplier();
                    }
                    tempRow--;
                    isAcross = true;
                } else {
                    rand = false;

                }
            }


            tempRow = row;
            boolean rand1 = true;

            while (rand1) {
                if (tempRow + 1 < createBoard.board.length && createBoard.board[tempRow + 1][tempCol].getPlayedStatus() == true && createBoard.board[row][tempCol].getPlayedStatus() == false) {
                    strSufix = strSufix + createBoard.board[row + 1][tempCol].getLetter();
                    wordScore += tile.tile[createBoard.board[tempRow + 1][tempCol].getLetter() - 'a'].getMultiplier();
                    tempRow++;
                    isAcross = true;
                } else {
                    rand1 = false;

                }
            }

            if (isAcross) {
                if (letterMultip != 0) {
                    if (str.charAt(s) != firstWild) {
                        wordScore += letterMultip * tile.tile[str.charAt(s) - 'a'].getMultiplier();
                    }

                } else {
                    wordScore += tile.tile[str.charAt(s) - 'a'].getMultiplier();
                }

                if (wordMultip != 0) {
                    wordScore = wordScore * wordMultip;
                }

            }
            tempCol++;

            totalScore += wordScore;

        }
        //System.out.println("this is the final score: "+totalScore + "  ");


        if (totalScore > bestScore) {
            if (col - 1 >= 0 && createBoard.board[row][col - 1].getPlayedStatus()) {
                return 0;
            }
            bestScore = totalScore;
            bestCol = col;
            bestRow = row;
            bestStr = str;
             //System.out.println("best score: " + bestScore + " bestRow: " + bestRow + " bestCol: " + bestCol + " bestStr " + bestStr);

        }
        return totalScore;
    }

    /**
     * genereates the premuations
     * @param row
     * @param col
     * @param prefix
     * @param s
     */
    private void permutationfromTrayone(int row, int col, String prefix, String s) {
        //System.out.println("INSIDE PERM FROM TRAY ONE: ");
        int N = s.length();
        if (N == 0) {
            if (dictionaryEdit.isWord(prefix, readFile.getRoot())) {
                prefixBoardSuffixTray.add(prefix);


                if (col + prefix.length() < createBoard.board.length) {
                    calcScore(row, col, prefix);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            boolean ran = dictionaryEdit.isPrefix(prefix, readFile.getRoot()) && prefix.length() > 0;

            if (ran || prefix.length() == 0) {
                permutationfromTrayone(row, col, prefix + s.charAt(i), s.substring(0, i) + s.substring(i + 1, N));
            }
        }
    }

    /**
     * generates the permutations
     * @param row
     * @param col
     * @param prefix
     * @param s
     */
    private void permutationfromTray(int row, int col, String prefix, String s) {
        int N = s.length();
        if (N == 0) {
            if (dictionaryEdit.isWord(prefix, readFile.getRoot())) {
                prefixBoardSuffixTray.add(prefix);

                if (row < createBoard.board.length && col + prefix.length() < createBoard.board.length) {

                    calcScore(row, col, prefix);
                }

                //System.out.println("this is the word " + prefix + " with row "+row + " with col" + col);
                //System.out.println("1 here is : " + prefix);
            }
        }

        for (int i = 0; i < N; i++) {
            boolean ran = dictionaryEdit.isPrefix(prefix, readFile.getRoot()) && prefix.length() > 0;

            if (ran || prefix.length() == 0) {
                permutationfromTray(row, col, prefix + s.charAt(i), s.substring(0, i) + s.substring(i + 1, N));
            }
        }
    }

}


