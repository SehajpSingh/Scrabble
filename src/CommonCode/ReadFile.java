package CommonCode;

import CommonCode.Dictionary;
import CommonCode.DictionaryEdit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {

   public DictionaryEdit dictEdit = new DictionaryEdit();
    private Dictionary root = new Dictionary();


    public void readFile() throws FileNotFoundException {

        //File file = new File("/Users/sehajpunitsingh/Desktop/animal.txt");
        File file = new File("/Users/sehajpunitsingh/Desktop/sowpods.txt");
        Scanner scnr = new Scanner(file);

        int lineNumber = 1;
        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();

            //System.out.println("line " + lineNumber + " :" + line);
            dictEdit.addWords(line, root);

            //System.out.println(dictEdit.isWord(line, root));
            //if (lineNumber == 140) {
               // break;
            //}
            lineNumber++;

        }
    }
    protected Dictionary getRoot(){
        return root;
    }
}
