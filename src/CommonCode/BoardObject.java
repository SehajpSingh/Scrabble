package CommonCode;

public class BoardObject {
    private int word_Mult;
    private int letter_mult;
    private boolean played;
    private char letter;
    private boolean upperCase;

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

    public char getLetter(){
        return this.letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getWordMult() {
        return this.word_Mult;
    }

    public int getLetterMult() {
        return this.letter_mult;
    }

    public boolean getPlayedStatus() {
        return this.played;
    }

    public boolean isUpperCase(){
        return upperCase;
    }

    public void setUpperCase(boolean bool){
        this.upperCase=bool;
    }
}

