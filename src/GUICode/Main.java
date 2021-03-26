package GUICode;

import CommonCode.ReadFile;
import CommonCode.createTile;
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

    //board
    private ArrayList<Rectangle> rects = new ArrayList<>();
    private Label[][] labels = new Label[15][15];

    //tiles book-keeping
    private char[] tray = new char[7];
    private ArrayList<Rectangle> tiles = new ArrayList<>();
    private ArrayList<Label> tileLetter = new ArrayList<>();
    private ArrayList<Integer> usedIndices = new ArrayList<>();
    private ArrayList<Integer> thisMove = new ArrayList<>();

    //row and col of the tile clicked
    private ArrayList<Integer> Row = new ArrayList<>();
    private ArrayList<Integer> Col = new ArrayList<>();

    //stores the letters to be swapped
    private ArrayList<Integer> swapLetters = new ArrayList<>();


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
    //private static


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

        System.out.println("THIS IS THE SIZE: " + rects.size());
        for (int j = 0; j < rects.size(); j++) {

            layout.getChildren().addAll(rects.get(j));
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
        ok.setLayoutX(10);
        ok.setLayoutY(400);
        ok.setPrefSize(60, 10);
        ok.setText("OK");
        ok.setOnAction(this::handle);
    }

    public void handle(ActionEvent event) {
        if (event.getSource() == Play) {
            ifPlay();
        } else if (event.getSource() == Pass) {
            System.out.println("singh is king");
        } else if (event.getSource() == Clear) {
            ifClear();
        } else if (event.getSource() == Swap) {
            swap = true;
        } else if (event.getSource() == ok) {
            swap = false;
            tradeTiles();
        }
    }

    private void ifPlay() {
        if (firstMove && Row.size() > 0 && Col.size() > 0) {
            int firstIndices = boards.boardSize;
            int half = (firstIndices / 2);
            if (Row.contains(half) && Col.contains(half)) {
                getString();
                firstMove = false;
                thisMove.clear();
                Row.clear();
                Col.clear();
            } else {
                ifClear();
            }

        }
    }

    private void getString() {
        //System.out.println("in the func");
        boolean rowsEqual = false;
        boolean colsEqual = false;

        for (int i = 0; i < Row.size() - 1; i++) {

            if (Row.get(i) == Row.get(i + 1)) {
                rowsEqual = true;
            } else {
                rowsEqual = false;

            }
        }

        for (int i = 0; i < Col.size() - 1; i++) {
            if (Col.get(i) == Col.get(i + 1)) {
                colsEqual = true;
            } else {
                colsEqual = false;
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
                    //System.out.println("Rows are good");

                } else {
                    test = false;
                    ifClear();
                    return;

                }
            }

            if (test) {
                for (int i = 0; i < thisMove.size(); i++) {
                    str += tray[thisMove.get(i)];

                }

                if(read.dictEdit.isWord(str, read.getRoot())){
                    System.out.println("this is correct word");
                    updateBoard(str);
                    updateGui();
                }else{
                    ifClear();
                    updateGui();
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
                    ifClear();
                    return;
                }

                if (test1) {
                    for (int j = 0; j < thisMove.size(); j++) {
                        str1 += tray[thisMove.get(j)];

                    }

                    if(read.dictEdit.isWord(str1, read.getRoot())){
                        System.out.println("this is correct word");
                        updateBoard(str1);
                        updateGui();
                    }else{
                        ifClear();
                        updateGui();
                    }

                }

            }

        } else if (!rowsEqual && !colsEqual) {
            ifClear();
        }


    }

    private void updateBoard(String str){
       //row, col, and this move
       //loop for the size of row
       // pick up first row, col and add the new value at those indices
        //create a universal print function that updates the values for all of theme everytime its called


        for(int i = 0; i < Row.size(); i++){
            int row=Row.get(i);
            int col=Col.get(i);
            boards.board[row][col].setLetter(tray[thisMove.get(i)]);
        }

    }

    private void updateGui(){
        System.out.println("update gui called");
    for(int i = 0; i < boards.boardSize; i ++){
        for(int j = 0; j < boards.boardSize; j++){
            if(boards.board[i][j].getLetter() == '0'){
                System.out.println("inside the if statement");
                System.out.println("this is the text from board: "+ boards.board[i][j].getLetter());
                labels[i][j].setText(String.valueOf(boards.board[i][j].getLetter()));
            }
        }
    }
    }


    private void ifClear() {
        int a = 0;
        for (int i = 0; i < thisMove.size(); i++) {
            int ind = thisMove.get(i);

            for (int j = 0; j < usedIndices.size(); j++) {
                if (usedIndices.get(j) == ind) {
                    int row = Row.get(a);
                    int col = Col.get(a);
                    a++;
                    usedIndices.remove(j);
                    tileLetter.get(i).setText(String.valueOf(tray[i]));

                    if (boards.board[row][col].getLetterMult() == 0 && boards.board[row][col].getWordMult() == 0) {
                        labels[row][col].setText("0");
                    } else if (boards.board[row][col].getLetterMult() != 0 && boards.board[row][col].getWordMult() == 0) {
                        int name2 = boards.board[row][col].getLetterMult();
                        labels[row][col].setText(String.valueOf(name2));
                    } else if (boards.board[row][col].getLetterMult() == 0 && boards.board[row][col].getWordMult() != 0) {
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

                        if ((tileClicked) && (boards.board[x][y].getPlayedStatus() == false) && !usedIndices.contains(tileClickedNum)) {
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
                rects.add(rectangle);
                x = x + 45;

            }
        }

    }

    private void createTray() {
        int x = 500;
        int y = 850;
        for (int i = 0; i < 7; i++) {
            trayLetter = new Label();

            //out of bounds exception sometimes index 6 out of length of for 6]
            //there were duplicates last time when there was error [q, w, a, h, h, p] and next to each other otherwise duplicates are fine
            //[h, c, t, r, s, z]
            System.out.println("THIS IS THE TRAY: " + tile.tray);
            System.out.println("THIS IS THE TRAY length: " + tile.tray.size());
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

        humScor.setText("Human Score: " + humanScore);
        humScor.setTextFill(Color.YELLOW);
        humScor.setLayoutX(1220);
        humScor.setLayoutY(120);


        comScor.setText("Computer Score: " + computerScore);
        comScor.setTextFill(Color.YELLOW);
        comScor.setLayoutX(1220);
        comScor.setLayoutY(140);


    }


}

