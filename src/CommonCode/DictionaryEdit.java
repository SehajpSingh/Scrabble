/**
 * Sehaj Punit Singh
 * This class uses the dictionary class and implements the dictionary
 */
package CommonCode;
import java.util.ArrayList;

public class DictionaryEdit {

    protected ArrayList<String> perms = new ArrayList<>();
    protected Dictionary tree = new Dictionary();

    protected void addWords(String str, Dictionary tree) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            int tempAscii = (int) str.charAt(i) - 'a';

            if (tree.arr[tempAscii] == null) {

                tree.arr[tempAscii] = new Dictionary();
            }
            tree = tree.arr[tempAscii];
        }
        tree.setLeaf();
    }

    /**
     * This function checks if a given String is a valid word
     * @param str string that needs to be checked if valid
     * @param root root of tree to be checked in
     * @return returns the status of the string if its a word or not
     */
    public boolean isWord(String str, Dictionary root) {
        int len = str.length();
        int position;
        Dictionary tree = root;
        for (int i = 0; i < len; i++) {
            position = str.charAt(i) - 'a';
            if (tree.arr[position] == null) {
                return false;
            }
            tree = tree.arr[position];
        }
        boolean isFound = false;
        if (tree != null && tree.isLeaf) {
            isFound = true;
        }
        return isFound;
    }

    /**
     * This function checks if the strinn is a prefix of word or not
     * @param str string that needs to be compared and checked
     * @param root the root where to check if its a root
     * @return returns the status of the prefix
     */
    public boolean isPrefix(String str, Dictionary root){
        int len = str.length();
        int position;
        Dictionary tree = root;
        for (int i = 0; i < len; i++) {
            position = str.charAt(i) - 'a';

            //out of bounds exception
            if (tree.arr[position] == null) {
                return false;
            }
            tree = tree.arr[position];
        }
        boolean isFound = false;
        if (tree != null) {
            isFound = true;
        }
        return isFound;

    }


}
