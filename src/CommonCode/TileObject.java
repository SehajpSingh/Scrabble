package CommonCode;

public class TileObject {
    private char letter;
    private int multiplier;
    private int frequency;

    protected TileObject(char letter, int mult, int freq) {
        this.letter = letter;
        this.multiplier = mult;
        this.frequency = freq;

    }

    public boolean withdrawLetter(){
        if(this.frequency>0){
            frequency--;
            return true;
        }
        return false;
    }

    public char getLetter() {
        return this.letter;
    }

    protected int getMultiplier() {
        return this.multiplier;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void incrementFreq(){
        this.frequency++;
    }


}
