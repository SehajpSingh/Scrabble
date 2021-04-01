/**
 * Sehaj Punit Singh
 * This class creates the transpose for the board
 */


package CommonCode;


public class Transpose {
    private BoardObject transposeBoard[][];
    private BoardObject origBoard[][];

    /**
     * This is the constructor for the transpose class
     * @param board
     */
    public Transpose(BoardObject board[][]) {
        origBoard = board;
        System.out.println("refrence from transpose: "+origBoard);
    }

    /**
     * this function generates tranpose for the given board
     * @return
     */
    public BoardObject[][] transpose(){
        if(origBoard==null){
        }

        transposeBoard =  new BoardObject[origBoard.length][origBoard.length];

        for(int i = 0; i < origBoard.length; i++){
            for(int j = 0; j < origBoard.length;j++){
                if(origBoard[j][i]==null){
                    System.out.println("transpose null for orig");
                }
                transposeBoard[i][j]= new BoardObject(origBoard[j][i].getWordMult(),origBoard[j][i].getLetterMult(),origBoard[j][i].getLetter(),origBoard[j][i].getPlayedStatus());
                if(origBoard[j][i].isUpperCase()){
                    transposeBoard[i][j].setUpperCase(true);
                }
                //origBoard[j][i];
            }
        }
        return transposeBoard;
    }

    /**
     * this function prints the transposed board
     */
    public void printBoard() {
        //System.out.println("this is transpose board");

        int loop = transposeBoard.length;

        for (int i = 0; i < loop; i++) {
            for (int j = 0; j < loop; j++) {
                char letter = transposeBoard[i][j].getLetter();
                int wordMul = transposeBoard[i][j].getWordMult();
                int letterMult = transposeBoard[i][j].getLetterMult();

                if (wordMul == 0 && letterMult == 0) {
                    if (letter != '0') {
                        System.out.print(" " + letter + " ");
                    } else {
                        System.out.print(".. ");
                    }
                } else if (wordMul != 0) {
                    System.out.print(wordMul + "." + " ");
                } else if (letterMult != 0) {
                    System.out.print("." + letterMult + " ");
                }
            }
            System.out.println();
        }

    }

}
