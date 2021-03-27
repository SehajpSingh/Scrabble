package CommonCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class createTile {

    public TileObject tile[] = new TileObject[27];

    public ArrayList<Character> tray = new ArrayList<Character>();

    public void tiles() throws FileNotFoundException {
        int tileIndex = 0;
        File file = new File("./resources/tiles.txt");
        Scanner scnr = new Scanner(file);

        //Reading each line of file using Scanner class
        int lineNumber = 1;

        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();
            String[] arr = line.split(" ");
            int mult = Integer.parseInt(arr[1]);
            int freq = Integer.parseInt(arr[2]);

            if (arr[0].charAt(0) == 42) {
                tile[26] = new TileObject(arr[0].charAt(0), mult, freq);
            } else {
                tile[arr[0].charAt(0) - 'a'] = new TileObject(arr[0].charAt(0), mult, freq);
            }
        }
    }

    public void trays() {
        int size = tray.size();
        //for (int i = size; i < 7; i++)
        while (size < 7) {
            Random rand = new Random();
            int rand_int1 = rand.nextInt(26);
            if (tile[rand_int1].getFrequency() >= 1) {
                tray.add(tile[rand_int1].getLetter());
                tile[rand_int1].withdrawLetter();
            }
            size = tray.size();
        }

    }
}
