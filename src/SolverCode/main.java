/**
 * Sehaj Punit Singh
 * This is the main class for solver
 */


package SolverCode;

import CommonCode.*;

import java.io.File;
import java.io.FileNotFoundException;

public class main {

    private Transpose trans;
    private static createTile crea;
    private static CreateBoard board;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        if (args.length == 0) {
            System.out.println("no files are given: ");
        } else {
            //File file = new File args[0];
        }


        //setting up dictionary
        ReadFile read = new ReadFile();
        read.readFile();

        //setting up tile
        crea = new createTile();
        crea.tiles();


        //setting up board
        board = new CreateBoard();
        board.create();

        //caling the logic to solve the puzzle
        for (int i = 0; i < board.boards.size(); i++) {
            System.out.println();
            board.board = board.boards.get(i);

            Logic logic = new Logic(read.dictEdit, board, crea, read, board.trays.get(i));
            logic.ankerPoints();
            logic.storeCrossChecks();
            logic.findPrefixfromTray();
            logic.getSuffixFromBoard();
            logic.getPrefixFromBoard();

            int bestSocre = logic.getBestScore();
            String bestString = logic.getBestStr();


            System.out.println();
            board.board = board.transPose.get(i);
            Logic logic1 = new Logic(read.dictEdit, board, crea, read, board.trays.get(i));
            logic1.ankerPoints();
            logic1.storeCrossChecks();
            logic1.findPrefixfromTray();
            logic1.getSuffixFromBoard();
            logic1.getPrefixFromBoard();

            int bestSocreT = logic1.getBestScore();
            String bestStringT = logic1.getBestStr();


            if (bestSocre > bestSocreT) {
                System.out.println("Input Board:");
                logic.printBoard();
                int r = logic.bestRow;
                int c = logic.bestCol;

                for (int l = 0; l < bestString.length(); l++) {
                    board.board[r][c].setLetter(bestString.charAt(l));
                    c++;
                }

                System.out.println("Tray: " + board.trays.get(i));
                System.out.println("Solution " + bestString + " has " + bestSocre + " points");
                System.out.println("Solution Board: ");
                logic.printBoard1();


            } else {

                BoardObject temp[][] = board.transPose.get(i);
                System.out.println("Input Board:");
                logic1.printBoard2(board.origBoards.get(i));
                int r1 = logic1.bestRow;
                int c1 = logic1.bestCol;

                for (int l = 0; l < bestStringT.length(); l++) {
                    temp[r1][c1].setLetter(bestStringT.charAt(l));
                    c1++;
                }

                System.out.println("Tray: " + board.trays.get(i));
                System.out.println("Solution " + bestStringT + " has " + bestSocreT + " points");
                System.out.println("Solution Board: ");
                Transpose trans = new Transpose(temp);
                logic1.printBoard2(trans.transpose());
            }


        }
    }


}
