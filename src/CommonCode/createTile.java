/**
 * Sehaj Punit Singh
 * This class creates the tiles by reading the tile input file
 * It uses the tile object format
 */

package CommonCode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class createTile {

    /**
     * This tile array is storing the basics for each of the
     * letters given in the tile object
     */
    public TileObject tile[] = new TileObject[27];

    /**
     * This arraylist stores the first tile tray for the human player
     */
    public ArrayList<Character> tray = new ArrayList<Character>();

    /**
     * This program reads the file for tray and creates the tiles
     * @throws FileNotFoundException this exception is thrown if file path is incorrect
     */
    public void tiles() throws FileNotFoundException {
        int tileIndex = 0;
        File file = new File("./resources/tiles.txt");
        Scanner scnr = new Scanner(file);
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

    /**
     * This function creates the tray for the human player
     */
    public void trays() {
        int size = tray.size();

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
