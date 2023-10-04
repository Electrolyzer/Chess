package chess.pieces;
import chess.Piece;
import chess.Square;

public class Queen extends Piece {

    public Queen(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public String getType(){ return "Q"; }

    public void updateValidMoves(){

        //Initialize all moves to invalid
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                validMoves.setPosition(i, j, moveType.INVALID);
            }    
        }
        int distance;
        int rank;
        int file;

        //The two for loops iterate the 8 possible directions
        for (int up = -1; up <= 1; up ++) {
            for (int right = -1; right <= 1; right ++) {

                //Don't check for valid moves when there is no direction
                if( (right==0) && (up==0) ){ continue; }
                
                distance = 1;
                file = getFile() + right * distance;
                rank = getRank() + up * distance;

                //Checks each direction until it hits a piece, then checks whether that piece is capturable but stops
                //after hitting a piece whether or not it is. While condition ensures piece does not move out of bounds
                while (0<=rank && rank<8 && 0<=file && file<8) {

                    if (null == Board.getPosition(file, rank)) {
                        validMoves.setPosition(file, rank, moveType.VALID);
                        distance++;
                    } else if (!isSameColor(Board.getPosition(file, rank))) {
                        validMoves.setPosition(file, rank, moveType.VALID);
                        break;
                    } else {
                        break;
                    }
                    file = getFile() + right * distance;
                    rank = getRank() + up * distance;

                }
            }
        }
    }
}
