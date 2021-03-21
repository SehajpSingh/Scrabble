public class BoardObject {
    private int word_Mult;
    private int letter_mult;
    private boolean played;
    private char letter;

    protected BoardObject(int word_Mult, int letter_mult, char letter, boolean played) {
        this.played = played;
        this.word_Mult = word_Mult;
        this.letter_mult = letter_mult;
        this.letter = letter;
    }

    protected char getLetter(){
        return this.letter;
    }

    protected int getWordMult() {
        return this.word_Mult;
    }

    protected int getLetterMult() {
        return this.letter_mult;
    }

    protected boolean getPlayedStatus() {
        return this.played;
    }
}
