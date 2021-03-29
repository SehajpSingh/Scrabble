/**
 * Sehaj Punit Singh
 * This handles the main function for GUI
 */

package GUICode;
import CommonCode.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    private Pane layout = new Pane();
    private Rectangle[][] rects = new Rectangle[15][15];
    private Label[][] labels = new Label[15][15];
    private char[] tray = new char[7];
    private ArrayList<Rectangle> tiles = new ArrayList<>();
    private ArrayList<Label> tileLetter = new ArrayList<>();
    private ArrayList<Integer> usedIndices = new ArrayList<>();
    private ArrayList<Integer> thisMove = new ArrayList<>();
    private ArrayList<Integer> Row = new ArrayList<>();
    private ArrayList<Integer> Col = new ArrayList<>();
    private ArrayList<Integer> swapLetters = new ArrayList<>();
    private GUILogic logic;
    private GUILogic logic1;
    private Button Play = new Button();
    private Button Pass = new Button();
    private Button Clear = new Button();
    private Button Swap = new Button();
    private Button ok = new Button();
    private Rectangle scoreBoard;
    private boolean swap;
    private boolean tileClicked;
    private boolean firstMove = true;
    private Label label;
    private Label name1;
    private Label humScor;
    private Label comScor;
    private Label trayLetter;
    private String tileText;
    private int tileClickedNum;
    protected static createTile tile;
    private static CreateGUIBoard boards;
    private static ReadFile read;
    private boolean turnHuman = true;
    private char[] cmpTray = new char[7];
    private int lastTile = 0;
    private Transpose trans;
    protected BoardObject[][] transBoard;
    protected BoardObject[][] origBoard;
    protected BoardObject[][] tempBoard;
    private int anchorRow;
    private int anchorCol;
    private int startRow;
    private int startCol;
    private int compScore = 0;

    public static void main(String[] args) throws FileNotFoundException {
        read = new ReadFile();
        read.readFile();

        boards = new CreateGUIBoard();
        boards.readBoard();

        tile = new createTile();
        tile.tiles();
        tile.trays();

        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Scrabble");
        Scene scene = new Scene(layout, 600, 600);
        tasks();
        createTray();
        playInstructions();
        passInstructions();
        clearInstructions();
        swapInstructions();
        okInstructions();
        scoreBoard();
        layout.getChildren().addAll(Play, Pass, Swap, Clear, scoreBoard, humScor, label, comScor, ok);

        //System.out.println("THIS IS THE SIZE: " + rects.size());
        for (int j = 0; j < rects.length; j++) {
            for (int k = 0; k < rects.length; k++) {
                layout.getChildren().addAll(rects[j][k]);
            }

        }
        for (int i = 0; i < 7; i++) {
            layout.getChildren().addAll(tiles.get(i), tileLetter.get(i));
        }
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                layout.getChildren().addAll(labels[i][j]);
            }
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void playInstructions() {
        Play.setLayoutX(10);
        Play.setLayoutY(200);
        Play.setPrefSize(60, 10);
        Play.setText("PLAY");
        Play.setOnAction(this::handle);
    }

    private void passInstructions() {
        Pass.setLayoutX(10);
        Pass.setLayoutY(250);
        Pass.setPrefSize(60, 10);
        Pass.setText("PASS");
        Pass.setOnAction(this::handle);
    }

    private void clearInstructions() {
        Clear.setLayoutX(10);
        Clear.setLayoutY(350);
        Clear.setPrefSize(60, 10);
        Clear.setText("CLEAR");
        Clear.setOnAction(this::handle);
    }

    private void swapInstructions() {
        Swap.setLayoutX(10);
        Swap.setLayoutY(300);
        Swap.setPrefSize(60, 10);
        Swap.setText("SWAP");
        Swap.setOnAction(this::handle);
    }

    private void okInstructions() {
        ok.setLayoutX(80);
        ok.setLayoutY(300);
        ok.setPrefSize(60, 10);
        ok.setText("OK");
        ok.setOnAction(this::handle);
    }

    public void handle(ActionEvent event) {
        if (event.getSource() == Play) {
            ifPlay();
        } else if (event.getSource() == Pass) {
            //System.out.println("singh is king");
        } else if (event.getSource() == Clear) {
            ifClear();
            refreshTray();
        } else if (event.getSource() == Swap) {
            if (turnHuman) {
                turnHuman = false;
                swap = true;
            }

        } else if (event.getSource() == ok) {
            swap = false;
            tradeTiles();
            cmpTurn();
        }
    }

    private void ifPlay() {

        if (turnHuman) {
            if (firstMove && Row.size() > 0 && Col.size() > 0) {
                createCompTray();
                int firstIndices = boards.boardSize;
                int half = (firstIndices / 2);
                if (Row.contains(half) && Col.contains(half)) {
                    getString();
                } else {
                    ifClear();
                }

            } else {
                //first get the input such as the strings and stuff
                //calculate new anchor points
                //check if tile is placed on any of the anchors
                //if so then
                System.out.println("second human turn");
                humanMove();
            }
        }

    }

    private void cmpTurn(){
        System.out.println("here in computer turn");
        if (!turnHuman) {
            turnHuman = true;

            String cmp = "";
            for (char s : cmpTray) {
                cmp += s;
            }

            logic = new GUILogic(read.dictEdit, boards, tile, read, cmp);
            logic.ankerPoints();
            logic.storeCrossChecks();
            logic.findPrefixfromTray();
            logic.getSuffixFromBoard();
            logic.getPrefixFromBoard();
            int bestSocre = logic.getBestScore();
            String bestString = logic.getBestStr();


            trans = new Transpose(boards.board);
            transBoard = new BoardObject[boards.boardSize][boards.boardSize];
            transBoard = trans.transpose();
            origBoard = boards.board;
            boards.board = transBoard;
            logic1 = new GUILogic(read.dictEdit, boards, tile, read, cmp);
            logic1.ankerPoints();
            logic1.storeCrossChecks();
            logic1.findPrefixfromTray();
            logic1.getSuffixFromBoard();
            logic1.getPrefixFromBoard();
            //logic1.printBoard1();

            int bestSocre1 = logic1.getBestScore();
            String bestString1 = logic1.getBestStr();
            // System.out.println("this is best score 1 : " + bestSocre1);
            //System.out.println("this is best string 1 : " + bestString1);

            if (bestSocre >= bestSocre1) {
                boards.board = origBoard;
                System.out.println("normal board before update");
                logic.printBoard();
                int r = logic.bestRow;
                int c = logic.bestCol;

                for (int l = 0; l < bestString.length(); l++) {
                    boards.board[r][c].setLetter(bestString.charAt(l));
                    boards.board[r][c].setPlayed(true);
                    c++;
                }
                updateGui();
                System.out.println("normal board after update");
                logic.printBoard1();
                compScore += bestSocre;
                comScor.setText("Computer Score: " + compScore);
                updateCompStr(cmp, logic.getBestStr());

                //System.out.println("no transpose");

            } else {
                //System.out.println("transpose board");
                int r1 = logic1.bestRow;
                int c1 = logic1.bestCol;
                System.out.println("transpose board before update");
                logic1.printBoard();
                for (int l = 0; l < bestString1.length(); l++) {
                    transBoard[r1][c1].setLetter(bestString1.charAt(l));
                    transBoard[r1][c1].setPlayed(true);
                    c1++;
                }
                BoardObject tempBoard[][] = transBoard;
                Transpose trans = new Transpose(tempBoard);
                boards.board = trans.transpose();
                System.out.println("updated transpose board");
                logic1.printBoard();
                compScore = bestSocre1;
                comScor.setText("Computer Score: " + compScore);
                updateGui();
                updateCompStr(cmp, logic1.getBestStr());
            }
        }
    }

    private void humanMove() {


        if (!anchorsCheck()) {
            wrongMove();
            resetBookeping();
        }

        else {

            boolean played = false;
            String sol = leftString();
            String sol1 = upString();

            if ((sol.length() > 1) && (read.dictEdit.isWord(sol, read.getRoot()))) {
                System.out.println("correct move by human on left+right");
                updateBoard("sehaj");
                turnHuman = false;
                cmpTurn();
                played = true;
                humanScore(sol);
                updateTray();
                refreshTray();
                resetBookeping();



            }

            if ((sol1.length() > 1) && (read.dictEdit.isWord(sol1, read.getRoot()))) {
                System.out.println("correct move by human on up+down");
                //update board function
                updateBoard("sehaj");
                turnHuman = false;
                cmpTurn();
                humanScore(sol1);
                played = true;
                updateTray();
                refreshTray();
                resetBookeping();
            }

            if (!played) {
                System.out.println("wrong move by the player");
                reverseTempBoard();
                wrongMove();
                resetBookeping();
            }

        }


    }

    private void humanScore(String str){
        System.out.println("human score called");
        int score=0;
        if(str.length()==7){
            score+=50;
        }
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            int ind = c-'a';
            score+= tile.tile[ind].getMultiplier();
        }

        humScor.setText("Human Score: " + score);

    }

    private boolean ifConnected() {

        boolean rowsEqual = false;
        boolean colsEqual = false;

        for (int i = 0; i < Row.size() - 1; i++) {
            // System.out.println("inside the for loop 1");
            if (Row.get(i) == Row.get(i + 1)) {
                //System.out.println("inside rows equal");
                rowsEqual = true;
            } else {
                rowsEqual = false;
                break;
            }
        }

        for (int i = 0; i < Col.size() - 1; i++) {
            //System.out.println("inside the for loop 2");
            if (Col.get(i) == Col.get(i + 1)) {
                //System.out.println("inside cols equal");
                colsEqual = true;
            } else {
                colsEqual = false;
                break;
            }
        }

        if (rowsEqual) {
            //System.out.println("rows equaled so cols");
            boolean test = false;
            for (int i = 0; i < Col.size() - 1; i++) {
                int thisCol = Col.get(i);
                thisCol = thisCol + 1;
                int nextCol = Col.get(i + 1);

                if (thisCol == nextCol) {
                    // System.out.println("both row and col worked");
                    test = true;
                } else {
                    //System.out.println("both row and col did not work");
                    test = false;
                    break;
                }
            }

            if (test) {
                return true;
            }

        }


        if (colsEqual) {
            boolean test1 = false;

            for (int i = 0; i < Row.size() - 1; i++) {
                int thisRow = Row.get(i);
                thisRow = thisRow + 1;
                int nextRow = Row.get(i + 1);

                if (thisRow == nextRow) {
                    // System.out.println("both row and col worked 2");
                    test1 = true;

                } else {
                    //System.out.println("both row and col not worked 2");
                    test1 = false;
                    break;
                }
            }
            if (test1) {
                return true;
            }
        }
        return false;
    }

    private String leftString() {
        tempBoard = boards.board;
        updateTempBoard();
        // System.out.println("temp board after printing");
        logic.printBoard2(tempBoard);

        boolean run = true;
        boolean run1 = true;
        int col = anchorCol;
        String temp = "";

        boolean start = false;
        int startR;
        int startC;

        while (run) {
            //  System.out.println("inside the left loop");
            // System.out.println("this is where we start: row : " + anchorRow + " col: " + col);

            System.out.println(tempBoard[anchorRow][col].getLetter() != '0');

            if ((col > 0 && tempBoard[anchorRow][col].getLetter() != '0') && !start) {
                startRow = anchorRow;
                startCol = col;
                startR = anchorRow;
                startC = col;
                start = true;
            }

            if (col > 0 && tempBoard[anchorRow][col].getLetter() != '0') {
                //  System.out.println("inside left");
                temp += tempBoard[anchorRow][col].getLetter();
                col--;
            } else {
                run = false;
            }
        }

        char[] as = temp.toCharArray();
        String ans = "";
        for (int a = as.length - 1; a >= 0; a--) {
            ans += as[a];
        }

        temp = ans;
        System.out.println("THIS IS LEFT STRING: " + temp);

        if (temp.length() > 0) {
            col = anchorCol + 1;
        } else {
            col = anchorCol;
        }

        while (run1) {
            //    System.out.println("inside the right loop");
            System.out.println(tempBoard[anchorRow][col].getLetter() != '0');

            if ((col < tempBoard.length && tempBoard[anchorRow][col].getLetter() != '0') && !start) {
                startRow = anchorRow;
                startCol = col;
                startR = anchorRow;
                startC = col;
                start = true;
            }

            if (col < tempBoard.length && tempBoard[anchorRow][col].getLetter() != '0') {
                //     System.out.println("inside right");
                temp += tempBoard[anchorRow][col].getLetter();
                col++;
            } else {
                run1 = false;
            }
        }
        System.out.println("this is the left+right string: " + temp);
        return temp;
    }

    private String upString() {
        boolean run = true;
        boolean run1 = true;
        int row = anchorRow;
        String temp = "";

        boolean start = false;
        int startR;
        int startC;

        while (run) {
            // System.out.println("inside the left loop");
            // System.out.println("this is where we start: row : "+anchorRow+" col: "+row);

            System.out.println(tempBoard[row][anchorCol].getLetter() != '0');

            if ((row > 0 && tempBoard[row][anchorCol].getLetter() != '0') && !start) {
                startRow = row;
                startCol = anchorCol;
                startR = row;
                startC = anchorCol;
                start = true;
            }

            if (row > 0 && tempBoard[row][anchorCol].getLetter() != '0') {
                // System.out.println("inside up");
                temp += tempBoard[row][anchorCol].getLetter();
                row--;
            } else {
                run = false;
            }
        }

        char[] as = temp.toCharArray();
        String ans = "";
        for (int a = as.length - 1; a >= 0; a--) {
            ans += as[a];
        }

        temp = ans;
        System.out.println("THIS IS Up STRING: " + temp);

        if (temp.length() > 0) {
            row = anchorRow + 1;
        } else {
            row = anchorRow;
        }


        while (run1) {
            // System.out.println("inside the bottom loop");
            System.out.println(tempBoard[row][anchorCol].getLetter() != '0');

            if ((row < tempBoard.length && tempBoard[row][anchorCol].getLetter() != '0') && !start) {
                startRow = row;
                startCol = anchorCol;
                startR = row;
                startC = anchorCol;
                start = true;
            }

            if (row < tempBoard.length && tempBoard[row][anchorCol].getLetter() != '0') {
                //   System.out.println("inside bottom");
                temp += tempBoard[row][anchorCol].getLetter();
                row++;
            } else {
                run1 = false;
            }
        }
        System.out.println("this is the up string complete: " + temp);
        return temp;

    }

    private void updateTempBoard() {

        for (int i = 0; i < Row.size(); i++) {
            int row = Row.get(i);
            int col = Col.get(i);
            tempBoard[row][col].setLetter(tray[thisMove.get(i)]);
            tempBoard[row][col].setPlayed(true);
        }
    }

    private void reverseTempBoard() {

        for (int i = 0; i < Row.size(); i++) {
            int row = Row.get(i);
            int col = Col.get(i);
            tempBoard[row][col].setLetter('0');
            tempBoard[row][col].setPlayed(false);
        }
    }

    private boolean anchorsCheck() {
        ArrayList<Integer> ankerRow = new ArrayList<>();
        ArrayList<Integer> ankeraCol = new ArrayList<>();
        ReadFile red = read;
        CreateGUIBoard crea = boards;
        String temp = "";
        for (char c : tray) {
            temp += c;
        }
        GUILogic logic12 = new GUILogic(red.dictEdit, crea, tile, red, temp);
        logic12.ankerPoints();

        int b = 0;
        for (Coordinates coor : logic12.ankers) {
            ankerRow.add(coor.getRow());
            ankeraCol.add(coor.getCol());
            //System.out.println("these are anker rows: "+ankerRow.get(b) + " anker col: "+ankeraCol.get(b));
            b++;
        }


        for (int i = 0; i < Row.size(); i++) {
            int row = Row.get(i);
            int col = Col.get(i);
            //System.out.println("this is row: "+row+" this is col: "+col);
            if (ankerRow.contains(row) && ankeraCol.contains(col)) {
                anchorRow = row;
                anchorCol = col;
                //System.out.println("they are on ankers");
                return true;
            }
        }
        return false;
    }

    private void updateCompStr(String cmp, String best) {
        char[] cmp1 = cmp.toCharArray();
        char[] best1 = best.toCharArray();
        //System.out.println("this is best cmp String: " + best);
        //System.out.println("this is comp tray before: " + cmp);

        for (int i = 0; i < cmp.length(); i++) {
            for (int j = 0; j < best.length(); j++) {
                if (i < cmp.length() && cmp1[i] == best1[j]) {
                    // System.out.println("yes they matched");
                    // System.out.println("this letter matched: " + cmp1[i] + " " + best1[j]);

                    boolean validMov = false;
                    while (!validMov) {
                        Random rand = new Random();
                        int rand_int1 = rand.nextInt(26);

                        if (tile.tile[rand_int1].getFrequency() >= 1) {
                            validMov = true;
                            //    System.out.println("this is new letter: " + tile.tile[rand_int1].getLetter());
                            cmpTray[i] = tile.tile[rand_int1].getLetter();
                            tile.tile[rand_int1].withdrawLetter();
                        }
                    }
                }
            }
        }
    }

    private void getString() {
        boolean rowsEqual = false;
        boolean colsEqual = false;

        for (int i = 0; i < Row.size() - 1; i++) {
            if (Row.get(i) == Row.get(i + 1)) {
                rowsEqual = true;
            } else {
                rowsEqual = false;
                break;
            }
        }


        for (int i = 0; i < Col.size() - 1; i++) {
            if (Col.get(i) == Col.get(i + 1)) {
                colsEqual = true;
            } else {
                colsEqual = false;
                break;
            }
        }

        if (rowsEqual) {
            boolean test = false;
            String str = "";
            for (int i = 0; i < Col.size() - 1; i++) {
                int thisCol = Col.get(i);
                thisCol = thisCol + 1;
                int nextCol = Col.get(i + 1);

                if (thisCol == nextCol) {
                    test = true;
                } else {
                    test = false;
                    wrongMove();
                    resetBookeping();
                    break;
                }
            }

            if (test) {
                for (int i = 0; i < thisMove.size(); i++) {
                    str += tray[thisMove.get(i)];
                }

                if (read.dictEdit.isWord(str, read.getRoot())) {
                    updateBoard(str);
                    updateGui();
                    updateTray();
                    refreshTray();
                    resetBookeping();
                    humanScore(str);
                    firstMove = false;
                    turnHuman = false;
                    cmpTurn();
                } else {
                    wrongMove();
                    resetBookeping();
                }
            }
        } else if (colsEqual) {
            boolean test1 = false;
            String str1 = "";
            for (int i = 0; i < Row.size() - 1; i++) {
                int thisRow = Row.get(i);
                thisRow = thisRow + 1;
                int nextRow = Row.get(i + 1);

                if (thisRow == nextRow) {
                    test1 = true;

                } else {
                    test1 = false;
                    wrongMove();
                    resetBookeping();
                    return;
                }

                if (test1) {
                    for (int j = 0; j < thisMove.size(); j++) {
                        str1 += tray[thisMove.get(j)];
                    }

                    if (read.dictEdit.isWord(str1, read.getRoot())) {
                        updateBoard(str1);
                        humanScore(str1);
                        updateGui();
                        updateTray();
                        refreshTray();
                        resetBookeping();
                        firstMove = false;
                        turnHuman = false;
                        cmpTurn();

                    } else {
                        wrongMove();
                        resetBookeping();
                    }
                }
            }
        } else if (!rowsEqual && !colsEqual) {
            wrongMove();
            resetBookeping();
        }
    }

    private void resetBookeping() {
        Row.clear();
        Col.clear();
        thisMove.clear();
        usedIndices.clear();
    }

    private void wrongMove() {
        updateGui();
        ifClear();
        refreshTray();
    }

    private void updateBoard(String str) {
        //row, col, and this move
        //loop for the size of row
        // pick up first row, col and add the new value at those indices
        //create a universal print function that updates the values for all of theme everytime its called

        for (int i = 0; i < Row.size(); i++) {
            int row = Row.get(i);
            int col = Col.get(i);
            boards.board[row][col].setLetter(tray[thisMove.get(i)]);
            boards.board[row][col].setPlayed(true);
        }

    }

    private void updateGui() {
        //System.out.println("update gui called");
        for (int i = 0; i < boards.boardSize; i++) {
            for (int j = 0; j < boards.boardSize; j++) {

                if (boards.board[i][j].getLetter() != '0') {
                    labels[i][j].setText(String.valueOf(boards.board[i][j].getLetter()));
                    rects[i][j].setFill(Color.BROWN);

                } else if (boards.board[i][j].getLetterMult() != 0) {
                    labels[i][j].setText(String.valueOf(boards.board[i][j].getLetterMult()));
                } else if (boards.board[i][j].getWordMult() != 0) {
                    labels[i][j].setText(String.valueOf(boards.board[i][j].getWordMult()));
                }
            }
        }
    }

    private void createCompTray() {
        for (int i = 0; i < 7; i++) {

            boolean validMov = false;

            while (!validMov) {

                Random rand = new Random();
                int rand_int1 = rand.nextInt(26);

                if (tile.tile[rand_int1].getFrequency() >= 1) {
                    validMov = true;
                    cmpTray[i] = tile.tile[rand_int1].getLetter();
                    tile.tile[rand_int1].withdrawLetter();
                }
            }


        }
    }

    private void updateTray() {

        for (int i = 0; i < thisMove.size(); i++) {
            int index = thisMove.get(i);
            boolean validMov = false;

            while (!validMov) {

                Random rand = new Random();
                int rand_int1 = rand.nextInt(26);

                if (tile.tile[rand_int1].getFrequency() >= 1) {
                    validMov = true;
                    tray[index] = tile.tile[rand_int1].getLetter();
                    tile.tile[rand_int1].withdrawLetter();
                }
            }
        }

    }

    private void refreshTray() {
        for (int i = 0; i < tray.length; i++) {
            tileLetter.get(i).setText(String.valueOf(tray[i]));
        }
    }

    private void ifClear() {
        System.out.println("if clear called");
        int a = 0;
        for (int i = 0; i < thisMove.size(); i++) {
            int ind = thisMove.get(i);

            for (int j = 0; j < usedIndices.size(); j++) {
                if (usedIndices.get(j) == ind) {
                    int row = Row.get(a);
                    int col = Col.get(a);
                    a++;
                    usedIndices.remove(j);

                    if (boards.board[row][col].getLetterMult() == 0 && boards.board[row][col].getWordMult() == 0) {
                        //System.out.println("here");
                        labels[row][col].setText("0");

                    } else if (boards.board[row][col].getLetterMult() != 0 && boards.board[row][col].getWordMult() == 0) {
                        //System.out.println("here 1");
                        int name2 = boards.board[row][col].getLetterMult();
                        labels[row][col].setText(String.valueOf(name2));
                    } else if (boards.board[row][col].getLetterMult() == 0 && boards.board[row][col].getWordMult() != 0) {
                        //System.out.println("here 2");
                        int name2 = boards.board[row][col].getWordMult();
                        labels[row][col].setText(String.valueOf(name2));
                    }
                    // Label tmps = new Label("8");

                }

            }

        }
        thisMove.clear();
        Row.clear();
        Col.clear();
    }

    private void tasks() {
        int size = 15;
        int x = 300;
        int y = 50;
        int tile = 0;
        for (int i = 0; i < 15; i++) {
            x = 350;
            y = y + 45;
            for (int j = 0; j < 15; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setX(x);
                rectangle.setY(y);
                rectangle.setHeight(40.0f);
                rectangle.setWidth(40.0f);
                String name = Integer.toString(tile);
                rectangle.setAccessibleHelp(name);
                rectangle.setStroke(javafx.scene.paint.Color.RED);
                tile++;
                rectangle.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        String temp = rectangle.getAccessibleHelp();
                        int tile = Integer.valueOf(temp);
                        int x = tile / size;
                        int y = tile % size;

                        if (Row.contains(x) && Col.contains(y)) {
                            //does nothing but prevents from placing a tile on top of another one
                        } else if ((tileClicked) && (boards.board[x][y].getPlayedStatus() == false) && !usedIndices.contains(tileClickedNum)) {
                            tileClicked = false;
                            thisMove.add(thisMove.size(), tileClickedNum);
                            labels[x][y].setText(tileText);
                            tiles.get(tileClickedNum).setFill(Color.RED);
                            tileLetter.get(tileClickedNum).setText("");
                            usedIndices.add(usedIndices.size(), tileClickedNum);
                            Row.add(Row.size(), x);
                            Col.add(Col.size(), y);
                        }
                    }
                });

                if (boards.board[i][j].getLetterMult() == 0 && boards.board[i][j].getWordMult() == 0) {
                    rectangle.setFill(Color.RED);
                    name1 = new Label();
                    String name2 = String.valueOf(0);
                    name1.setText(name2);
                    name1.setTextFill(Color.YELLOW);
                    name1.setLayoutX(x + 20);
                    name1.setLayoutY(y + 20);
                    labels[i][j] = name1;
                } else if (boards.board[i][j].getLetterMult() != 0 && boards.board[i][j].getWordMult() == 0) {
                    rectangle.setFill(Color.BLUE);
                    name1 = new Label();
                    int name2 = boards.board[i][j].getLetterMult();
                    String names = String.valueOf(name2);
                    name1.setText(names);
                    name1.setTextFill(Color.YELLOW);
                    name1.setLayoutX(x + 20);
                    name1.setLayoutY(y + 20);
                    labels[i][j] = name1;
                } else if (boards.board[i][j].getLetterMult() == 0 && boards.board[i][j].getWordMult() != 0) {
                    rectangle.setFill(Color.GREY);
                    name1 = new Label();
                    int a = boards.board[i][j].getWordMult();
                    String ab = String.valueOf(a);
                    name1.setText(ab);
                    name1.setTextFill(Color.YELLOW);
                    name1.setLayoutX(x + 20);
                    name1.setLayoutY(y + 20);
                    labels[i][j] = name1;
                }
                rects[i][j] = rectangle;
                x = x + 45;
            }
        }

    }

    private void createTray() {
        int x = 500;
        int y = 850;
        for (int i = 0; i < 7; i++) {
            trayLetter = new Label();
            String name2 = String.valueOf(tile.tray.get(i));
            tray[i] = name2.charAt(0);
            trayLetter.setText(name2);
            trayLetter.setTextFill(Color.YELLOW);
            trayLetter.setLayoutX(x + 20);
            trayLetter.setLayoutY(y + 20);
            tileLetter.add(trayLetter);

            Rectangle rect = new Rectangle();
            rect.setX(x);
            rect.setY(y);
            rect.setHeight(40);
            rect.setWidth(40);
            rect.setFill(Color.RED);
            rect.setAccessibleHelp(Integer.toString(i));

            rect.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    String num = rect.getAccessibleHelp();

                    if (swap) {
                        System.out.println("in swap: ");
                        if (swapLetters.contains(Integer.valueOf(num))) {
                            int val = Integer.valueOf(num);
                            int y = (int) (tiles.get(val).getY());
                            tiles.get(val).setY(y + 40);

                            int labelY = (int) tileLetter.get(val).getLayoutY();
                            tileLetter.get(val).setLayoutY(labelY + 40);
                            swapLetters.remove(Integer.valueOf(num));
                        } else {
                            int val = Integer.valueOf(num);
                            int y = (int) (tiles.get(val).getY());
                            tiles.get(val).setY(y - 40);

                            int labelY = (int) tileLetter.get(val).getLayoutY();
                            tileLetter.get(val).setLayoutY(labelY - 40);
                            swapLetters.add(Integer.valueOf(num));
                        }
                    } else {
                        int val = Integer.valueOf(num);
                        int size = thisMove.size();

                        tiles.get(lastTile).setFill(Color.RED);
                        lastTile = val;

                        if (!usedIndices.contains(val)) {
                            tileClicked = true;
                            tileText = tileLetter.get(val).getText();
                            //System.out.println(tileLetter.get(val).getText()+ " :this is the text");
                            tileClickedNum = val;
                            tiles.get(val).setFill(Color.LIGHTSLATEGREY);
                        }


                    }
                }
            });
            tiles.add(rect);
            x = x + 50;
        }
    }

    private void tradeTiles() {
        Random rand = new Random();
        for (int i = 0; i < swapLetters.size(); i++) {
            int val = swapLetters.get(i);
            int y = (int) (tiles.get(val).getY());
            tiles.get(val).setY(y + 40);

            int labelY = (int) tileLetter.get(val).getLayoutY();
            tileLetter.get(val).setLayoutY(labelY + 40);
            char letter = (tileLetter.get(val).getText()).charAt(0);
            int ascii = (int) letter;

            if (ascii == 42) {
                //this is star and the array is 26th index
                // System.out.println("star ascii" + ascii);
                //System.out.println("this is the letter: " + letter);

                boolean starExch = false;
                while (!starExch) {
                    int rand_int1 = rand.nextInt(27);
                    if (tile.tile[rand_int1].withdrawLetter()) {
                        starExch = true;
                        // System.out.println(" star letter before exchange " + tile.tray.get(val));
                        tile.tray.set(val, tile.tile[rand_int1].getLetter());
                        tileLetter.get(val).setText(String.valueOf(tile.tile[rand_int1].getLetter()));
                        //System.out.println(" star letter after exchange " + tile.tray.get(val));
                    }
                }


            } else {
                boolean exchanged = false;
                ascii = ascii - 'a';
                //System.out.println("this is the letter: " + letter + " " + ascii);
                //System.out.println("this is the value: " + tile.tray.get(val));
                //steps loop until not exchanged


                while (!exchanged) {
                    int rand_int1 = rand.nextInt(27);
                    if (tile.tile[rand_int1].withdrawLetter()) {
                        exchanged = true;
                        //System.out.println("letter before exchange " + tile.tray.get(val));
                        tile.tray.set(val, tile.tile[rand_int1].getLetter());
                        tileLetter.get(val).setText(String.valueOf(tile.tile[rand_int1].getLetter()));
                        //System.out.println("letter after exchange " + tile.tray.get(val));
                    }
                }

                tile.tile[ascii].incrementFreq();

                //tile.tray.add(tile.tile[rand_int1].getLetter());
                //tile.tile[rand_int1].withdrawLetter();
            }
        }

        swapLetters.clear();
    }

    private void scoreBoard() {
        int humanScore = 50;
        int computerScore = 100;
        scoreBoard = new Rectangle();
        humScor = new Label();
        comScor = new Label();
        label = new Label();

        scoreBoard.setX(1200);
        scoreBoard.setY(100);
        scoreBoard.setWidth(160);
        scoreBoard.setHeight(80);
        scoreBoard.setFill(Color.BROWN);

        label.setText("Score Board");
        label.setTextFill(Color.YELLOW);
        label.setLayoutX(1250);
        label.setLayoutY(100);

        humScor.setText("Human Score: " + 0);
        humScor.setTextFill(Color.YELLOW);
        humScor.setLayoutX(1220);
        humScor.setLayoutY(120);


        comScor.setText("Computer Score: " + compScore);
        comScor.setTextFill(Color.YELLOW);
        comScor.setLayoutX(1220);
        comScor.setLayoutY(140);


    }

}

