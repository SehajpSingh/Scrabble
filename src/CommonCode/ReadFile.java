/**
 * Sehaj Punit Singh
 * this class reads the dictionary and uses it
 */

package CommonCode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
    /**
     * dictionary edit is the blueprint for creating and storing the dictionary
     */
   public DictionaryEdit dictEdit = new DictionaryEdit();
   private Dictionary root = new Dictionary();

    /**
     * this functions reads the given file and stores it
     * @throws FileNotFoundException
     */
    public void readFile(File file) throws FileNotFoundException {

        //File file = new File("/Users/sehajpunitsingh/Desktop/sowpods.txt");
        Scanner scnr = new Scanner(file);

        int lineNumber = 1;
        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();

            dictEdit.addWords(line, root);
            lineNumber++;

        }
    }

    /**
     * this function finds the root for the file
     * @return
     */
    public Dictionary getRoot(){
        return root;
    }
}
