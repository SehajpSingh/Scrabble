package CommonCode;

public class Transpose {
    private BoardObject transposeBoard[][];
    private BoardObject origBoard[][];

    public Transpose(BoardObject board[][]) {
        origBoard = board;
    }

    public BoardObject[][] transpose(){
        transposeBoard =  new BoardObject[origBoard.length][origBoard.length];

        for(int i = 0; i < origBoard.length; i++){
            for(int j = 0; j < origBoard.length;j++){
                transposeBoard[i][j]= new BoardObject(origBoard[j][i].getWordMult(),origBoard[j][i].getLetterMult(),origBoard[j][i].getLetter(),origBoard[j][i].getPlayedStatus());
                        //origBoard[j][i];
            }
        }
        return transposeBoard;
    }

    public void printBoard() {
        System.out.println("this is transpose board");

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
