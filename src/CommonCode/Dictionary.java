/**
 * Sehaj Punit Singh
 * This class creates the basic format for a dictionary
 */

package CommonCode;
public class Dictionary {

    protected Dictionary arr[] = new Dictionary[26];
    protected boolean isLeaf = false;

    /**
     * This function creates the dictionary and stores the letters
     */
    public Dictionary() {
        for (int i = 0; i < 26; i++) {
            arr[i] = null;
        }
    }

    /**
     * this function sets the leaf for dictionary
     */
    public void setLeaf() {
        this.isLeaf = true;
    }

    /**
     * this function returns if the letter is a leaf or not
     * @return returns the value of the boolean
     */
    public boolean isLeaf() {
        return this.isLeaf;
    }

}
