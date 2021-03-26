package SolverCode;

import CommonCode.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class main {

    public BoardObject board[][];


    Transpose trans;

    public void create() throws FileNotFoundException {

        char tray[];
        int lines = 0;
        File file = new File("/Users/sehajpunitsingh/Desktop/table.txt");
        Scanner scnr = new Scanner(file);

        //initializing the board
        String line = scnr.nextLine();
        String[] arr = line.split(" ");
        String size = arr[0];
        int boardSize = Integer.parseInt(size);
        board = new BoardObject[boardSize][boardSize];

        for (int i = 0; i < arr.length; i++) {
            line = scnr.nextLine();

            while (line.equals("")) {
                // System.out.println("its a space");
                line = scnr.nextLine();
            }

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
        line = scnr.nextLine();
        tray = line.toCharArray();
        trans = new Transpose(board);
        trans.transpose();
        trans.printBoard();


    }


    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        //setting up dictionary
        ReadFile read = new ReadFile();
        read.readFile();

        //setting up tile
        createTile crea = new createTile();
        crea.tiles();


        //setting up board
        CreateBoard board = new CreateBoard();
        board.create();

        //caling the logic to solve the puzzle
        for (int i = 0; i < board.boards.size(); i++) {
            System.out.println();
            board.board = board.boards.get(i);

            Logic logic = new Logic(read.dictEdit, board, crea, read, board.trays.get(i));
            logic.printBoard();
            logic.findPrefixfromTray();
            logic.getSuffixFromBoard();
            logic.getPrefixFromBoard();
            //get best row, col, and score


            //transpose
            System.out.println("this is transpose: ");
            System.out.println();
            board.board = board.transPose.get(i);
            //board.transbBoard = board.transPose.get(i);
            Logic logic1 = new Logic(read.dictEdit, board, crea, read, board.trays.get(i));
            logic1.printBoard();
            logic1.findPrefixfromTray();
            logic1.getSuffixFromBoard();
            logic1.getPrefixFromBoard();
            //get best row, col, and score

            //compare which has the highest score
            //put string on the board but if the board is trasnposed, transpose it once again once before printing


        }
    }


}
