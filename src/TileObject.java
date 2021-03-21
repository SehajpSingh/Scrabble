public class TileObject {
    private char letter;
    private int multiplier;
    private int frequency;

    protected TileObject(char letter, int mult, int freq) {
        this.letter = letter;
        this.multiplier = mult;
        this.frequency = freq;


    }
    protected boolean withdrawLetter(){
        if(this.frequency>0){
            frequency--;
            return true;
        }
        return false;
    }


    protected char getLetter() {
        return this.letter;
    }

    protected int getMultiplier() {
        return this.multiplier;
    }

    protected int getFrequency() {
        return this.frequency;
    }
}
