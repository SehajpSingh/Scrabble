/**
 * Sehaj Punit Singh
 * This file creates the basic blueprint for tile object
 */


package CommonCode;

public class TileObject {
    private char letter;
    private int multiplier;
    private int frequency;

    /**
     * This is the constructor for the tile object
     * @param letter
     * @param mult
     * @param freq
     */
    protected TileObject(char letter, int mult, int freq) {
        this.letter = letter;
        this.multiplier = mult;
        this.frequency = freq;

    }

    /**
     * this function checks if a specific letter can be withdrawn
     * @return
     */
    public boolean withdrawLetter(){
        if(this.frequency>0){
            frequency--;
            return true;
        }
        return false;
    }

    /**
     * this function gets the letter for specific object
     * @return
     */
    public char getLetter() {
        return this.letter;
    }

    /**
     * this function returns the multiplying value of the letter
     * @return
     */
    public int getMultiplier() {
        return this.multiplier;
    }

    /**
     * this function returns the frequency for specific letter
     * @return
     */
    public int getFrequency() {
        return this.frequency;
    }

    /**
     * this function increases the frequency of the letter
     */
    public void incrementFreq(){
        this.frequency++;
    }


}
