package GUICode;

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
    ArrayList<Rectangle> rects = new ArrayList<>();
    ArrayList<Label> labels = new ArrayList<>();
    ArrayList<Rectangle> tiles = new ArrayList<>();
    ArrayList<Label> tileLetter = new ArrayList<>();
    ArrayList<Integer> swapLetters = new ArrayList<>();

    private Button Play = new Button();
    private Button Pass = new Button();
    private Button Clear = new Button();
    private Button Swap = new Button();
    private Button ok = new Button();
    private static CreateGUIBoard boards;
    private Rectangle scoreBoard;
    private boolean swap;
    private boolean tileClicked;

    private Label label;
    private Label name1;
    private Label humScor;
    private Label comScor;
    private Label trayLetter;



    protected static createTile tile;

    public static void main(String[] args) throws FileNotFoundException {
        boards = new CreateGUIBoard();
        boards.readBoard();

        tile = new createTile();
        tile.tiles();
        tile.trays();

        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Domino Game");
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

        for (int j = 0; j < rects.size(); j++) {
            layout.getChildren().addAll(rects.get(j), labels.get(j));
        }
        for (int i = 0; i < 7; i++) {
            layout.getChildren().addAll(tiles.get(i), tileLetter.get(i));
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
            System.out.println("singh is king");
            //System.exit(0);
        } else if (event.getSource() == Pass) {
            System.out.println("singh is king");
            //System.exit(0);
        } else if (event.getSource() == Clear) {
            System.out.println("singh is king");
            //System.exit(0);
        } else if (event.getSource() == Swap) {
            swap = true;
            // System.exit(0);
        } else if (event.getSource() == ok) {
            swap = false;
            tradeTiles();
            // System.exit(0);
        }
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

                        if((tileClicked) && (boards.board[x][y].getPlayedStatus() == false)){
                            tileClicked = false;

                           // boards.board[x][y].setLetter();
                            System.out.println("this is letter mult: "+boards.board[x][y].getLetterMult()+" this is word mult: "+boards.board[x][y].getWordMult());
                            System.out.println("yes it is false");
                        }
                        System.out.println("this is x: " + x + " this is y: " + y);
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
                    labels.add(name1);

                } else if (boards.board[i][j].getLetterMult() != 0 && boards.board[i][j].getWordMult() == 0) {
                    rectangle.setFill(Color.BLUE);
                    name1 = new Label();

                    int name2 = boards.board[i][j].getLetterMult();
                    //System.out.println("this is string value: "+name2);

                    String names = String.valueOf(name2);
                    name1.setText(names);
                    name1.setTextFill(Color.YELLOW);
                    name1.setLayoutX(x + 20);
                    name1.setLayoutY(y + 20);
                    labels.add(name1);


                } else if (boards.board[i][j].getLetterMult() == 0 && boards.board[i][j].getWordMult() != 0) {
                    rectangle.setFill(Color.GREY);
                    name1 = new Label();
                    int a = boards.board[i][j].getWordMult();
                    String ab = String.valueOf(a);
                    name1.setText(ab);
                    name1.setTextFill(Color.YELLOW);
                    name1.setLayoutX(x + 20);
                    name1.setLayoutY(y + 20);
                    labels.add(name1);

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
                        tileClicked = true;
                        tiles.get(val).setFill(Color.LIGHTSLATEGREY);

                    }
                }
            });

            trayLetter = new Label();

            //throws object out of bounds exception sometimes
            String name2 = String.valueOf(tile.tray.get(i));

            trayLetter.setText(name2);
            trayLetter.setTextFill(Color.YELLOW);
            trayLetter.setLayoutX(x + 20);
            trayLetter.setLayoutY(y + 20);
            tileLetter.add(trayLetter);

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

