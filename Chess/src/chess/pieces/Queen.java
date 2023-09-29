package chess.pieces;
import chess.Piece;
import chess.Square;

public class Queen extends Piece {

    public Queen(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public void updateValidMoves(){

        //Initialize all moves to invalid
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                validMoves.setPosition(i, j, moveType.INVALID);
            }    
        }
        int distance = 1;

        //The two for loops iterate the 8 possible directions
        for (int up = -1; up <= 1; up ++) {
            for (int right = -1; right <= 1; right ++) {

                //Don't check for valid moves when there is no direction
                if(!((right==0) && (up==0))){ continue; }

                //Checks each direction until it hits a piece, then checks whether that piece is capturable but stops
                //after hitting a piece whether or not it is
                while (true) {
                    while (true) {
                        if (null == Board.Board[getFile() + up * distance][getRank() + right * distance]) {
                            validMoves.setPosition(getFile() + up * distance,getRank() + right * distance, moveType.VALID);
                            distance++;
                            continue;
                        } else if (!isSameColor(Board.Board[getFile() + up * distance][getRank() + right * distance])) {
                            validMoves.setPosition(getFile() + up * distance, getRank() + right * distance, moveType.VALID);
                            distance = 1;
                            break;
                        } else {
                            distance = 1;
                            break;
                        }
                    }
                }

            }
        }

    }
    
}
