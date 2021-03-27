package GUICode;

import CommonCode.BoardObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CreateGUIBoard {

    File file = new File("./resources/board.txt");
    Scanner scnr = new Scanner(file);
    String line = scnr.nextLine();
    String[] arr = line.split(" ");
    String size = arr[0];
    protected int boardSize = Integer.parseInt(size);
    protected BoardObject[][] board = new BoardObject[boardSize][boardSize];

    public CreateGUIBoard() throws FileNotFoundException {
    }

    protected void createBoard(int word_Mult, int letter_mult, char letter, boolean played) throws FileNotFoundException {
        BoardObject guiBoard = new BoardObject(word_Mult, letter_mult, letter, played);
    }

    protected void readBoard() {

        for (int i = 0; i < boardSize; i++) {
            line = scnr.nextLine();
            arr = line.split(" ");

            for (int j = 0; j < boardSize; j++) {

                if (arr[j].charAt(0) == '.' && arr[j].charAt(1) == '.') {
                    board[i][j] = new BoardObject(0, 0, '0', false);

                } else if (arr[j].charAt(0) == '.') {
                    board[i][j] = new BoardObject(0, Integer.parseInt(String.valueOf(arr[j].charAt(1))), '0', false);

                } else {
                    board[i][j] = new BoardObject(Integer.parseInt(String.valueOf(arr[j].charAt(0))), 0, '0', false);

                }
            }
        }

    }


}
