/**
 * Sehaj Punit Singh
 * This class defines the blueprint for solver code
 */

package SolverCode;
import CommonCode.BoardObject;
import CommonCode.Transpose;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateBoard {
    /**
     * These variables are used for board realted book keeping
     */
    public BoardObject board[][];
    public ArrayList<BoardObject[][]> boards = new ArrayList<BoardObject[][]>();
    public ArrayList<BoardObject[][]> origBoards = new ArrayList<BoardObject[][]>();
    public ArrayList<BoardObject[][]> transPose = new ArrayList<BoardObject[][]>();
    public ArrayList<String> trays = new ArrayList<String>();


    //delete
    Transpose trans;

    /**
     * This function creates the board
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public void create() throws FileNotFoundException, InterruptedException {

        int lines;
        int boardSize;
        File file = new File("./resources/table.txt");
        Scanner scnr = new Scanner(file);
        String line;

        while (scnr.hasNext()) {
            lines = 0;
            line = scnr.nextLine();
            //System.out.println("THIS IS SIZE: "+line);
            String[] arr = line.split(" ");
            String size = arr[0];
            boardSize = Integer.parseInt(size);
            board = new BoardObject[boardSize][boardSize];

            for (int i = 0; i < boardSize; i++) {
                line = scnr.nextLine();

               // while (line.equals("")) {
                 //   line = scnr.nextLine();
                //}

                arr = line.split(" ");
                List<String> arrayList = new ArrayList<String>();

                for (String str : arr) {
                    if (str.length() != 0 && !(str.equals(" "))) {
                        arrayList.add(str);
                    }
                }

                for (int j = 0; j < arrayList.size(); j++) {
                    if (arrayList.get(j).length() == 1) {
                        //System.out.println("j = " + j + "what is this =" + arrayList.get(j).charAt(0));
                        board[lines][j] = new BoardObject(0, 0, arrayList.get(j).charAt(0), true);
                        //System.out.println("this is the character: " + board[lines][j].getLetter());
                    } else if (arrayList.get(j).charAt(0) == '.' && arrayList.get(j).charAt(1) == '.') {
                        board[lines][j] = new BoardObject(0, 0, '0', false);
                        //System.out.println("1. "+board[lines][j].getWordMult() +" "+board[lines][j].getLetterMult());

                    } else if (arrayList.get(j).charAt(0) == '.') {
                        int ran = Character.getNumericValue(arrayList.get(j).charAt(1));
                        board[lines][j] = new BoardObject(0, ran, '0', false);
                        // System.out.println("2. "+"ran is: "+ran+" "+board[lines][j].getWordMult() +" "+board[lines][j].getLetterMult());

                    } else if (arrayList.get(j).charAt(1) == '.') {
                        int ran1 = Character.getNumericValue(arrayList.get(j).charAt(0));
                        board[lines][j] = new BoardObject(ran1, 0, '0', false);
                    }

                }
                lines++;
            }
           // System.out.println("line before tray: "+line);
            line = scnr.nextLine();
            trays.add(trays.size(),line);

            //tray = line;
            //System.out.println("this is tray " +tray);
            boards.add(boards.size(),board);
            origBoards.add(origBoards.size(),board);
           // printBoard();

            trans = new Transpose(board);
            transPose.add(transPose.size(),trans.transpose());
            //trans.printBoard();


        }

    }

    /**
     * This function prints the board
     */
    public void printBoard() {
        int loop = board.length;
        System.out.println();
        for (int i = 0; i < loop; i++) {
            for (int j = 0; j < loop; j++) {
                char letter = board[i][j].getLetter();
                int wordMul = board[i][j].getWordMult();
                int letterMult = board[i][j].getLetterMult();

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
}

