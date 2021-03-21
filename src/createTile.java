import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class createTile {
    TileObject tile[] = new TileObject[27];


    protected void tiles() throws FileNotFoundException {
        int tileIndex = 0;
        File file = new File("/Users/sehajpunitsingh/Desktop/tiles.txt");
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
                //tileIndex++;
            }
            //lineNumber++;

        }

        
    }
}
