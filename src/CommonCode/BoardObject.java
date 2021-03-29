/**
 * Sehaj Punit Singh
 * This class intializes the blueprint for board
 * such as all the basic properties
 */
package CommonCode;

public class BoardObject {
    private int word_Mult;
    private int letter_mult;
    private boolean played;
    private char letter;
    private boolean upperCase;

    /**
     * This is the constructor function and takes in all the inputs for board
     * @param word_Mult is the value that board has and used in score
     * @param letter_mult is the value in board also used in score
     * @param letter storing the letter on board
     * @param played storing the status whether the tile has bee played or not
     */
    public BoardObject(int word_Mult, int letter_mult, char letter, boolean played) {
        this.played = played;
        this.word_Mult = word_Mult;
        this.letter_mult = letter_mult;
        this.letter = letter;
        this.upperCase=false;

        if(letter>=65 && letter<=90){
            this.letter = (char)  ((int)letter+32);
            this.upperCase=true;
        }

    }

    /**
     * This is the getter function for letter
     * @return returns the letter
     */
    public char getLetter(){
        return this.letter;
    }

    /**
     * This is the setter function for letter
     * @param letter sets the letter
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }

    /**
     * This is the getter for word multiplier
     * @return it returns the word multiplier
     */
    public int getWordMult() {
        return this.word_Mult;
    }

    /**
     * This is the getter for the letter
     * @return it returns the letter
     */
    public int getLetterMult() {
        return this.letter_mult;
    }

    /**
     * this is the getter for status
     * @return returns the status
     */
    public boolean getPlayedStatus() {
        return this.played;
    }

    /**
     * this is the setter for status
     * @param played it returns the status
     */
    public void setPlayed(boolean played) {
        this.played = played;
    }

    /**
     * this returns if the status is set to upper case
     * @return returns the upper case value
     */
    public boolean isUpperCase(){
        return upperCase;
    }

    /**
     * this sets the upper case value to specified boolean
     * @param bool input for the upper case desire
     */
    public void setUpperCase(boolean bool){
        this.upperCase=bool;
    }
}

