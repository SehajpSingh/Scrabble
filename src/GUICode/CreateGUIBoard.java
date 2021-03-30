/**
 * Sehaj Punit Singh
 * This class creates the board for gui after reading the input file
 */
package GUICode;
import CommonCode.BoardObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class CreateGUIBoard {

    InputStream file = getClass().getClassLoader().getResourceAsStream("board.txt");

    //File file = new File("./resources/board.txt");
    Scanner scnr = new Scanner(file);
    String line = scnr.nextLine();
    String[] arr = line.split(" ");
    String size = arr[0];
    protected int boardSize = Integer.parseInt(size);
    protected BoardObject[][] board = new BoardObject[boardSize][boardSize];




    public CreateGUIBoard() throws FileNotFoundException {
    }


    /**
     * This function reads the file
     */
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
