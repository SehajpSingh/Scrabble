package CommonCode;

public class BoardObject {
    private int word_Mult;
    private int letter_mult;
    private boolean played;
    private char letter;

    public BoardObject(int word_Mult, int letter_mult, char letter, boolean played) {
        this.played = played;
        this.word_Mult = word_Mult;
        this.letter_mult = letter_mult;
        this.letter = letter;
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
}

