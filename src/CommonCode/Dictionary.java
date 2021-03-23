package CommonCode;


public class Dictionary {

    Dictionary arr[] = new Dictionary[26];
    boolean isLeaf = false;

    public Dictionary() {
        for (int i = 0; i < 26; i++) {
            arr[i] = null;
        }
    }

    public void setLeaf() {
        this.isLeaf = true;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

}
