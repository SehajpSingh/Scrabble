import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateBoard {
    //read the file and store in array as in last file
    // create a board of appropriate size
    //create the board object blueprint
    //assigning the multiplier to each board object and see the asci value

    BoardObject board[][];

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
                if (str.length()!= 0 && !(str.equals(" "))) {
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
    }




}

