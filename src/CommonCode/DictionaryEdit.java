package CommonCode;

import CommonCode.Dictionary;

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

    protected boolean isWord(String str, Dictionary root) {
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

    protected boolean isPrefix(String str, Dictionary root){
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
        if (tree != null) {
            isFound = true;
        }
        return isFound;

    }




}
