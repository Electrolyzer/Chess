package chess.pieces;
import chess.Board;
import chess.Piece;
import chess.Square;

public class Bishop extends Piece{

    public Bishop(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public String getType(){ return "B"; }

    public void updateValidMoves(){

        //Initialize all moves to invalid
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                validMoves.setPosition(i, j, moveType.INVALID);
            }    
        }

        //The two for loops iterate the 4 possible directions
        for (int up = -1; up <= 1; up += 2) {
            for (int right = -1; right <= 1; right += 2) {

                int distance = 1;
                int file = getFile() + right * distance;
                int rank = getRank() + up * distance;

                //Checks each direction until it hits a piece, then checks whether that piece is capturable but stops
                //after hitting a piece whether or not it is. While condition ensures piece does not move out of bounds
                while (0<=rank && rank<8 && 0<=file && file<8) {

                    if (null == getBoard().getPosition(file, rank) || getBoard().getPosition(file, rank) instanceof PhantomPawn) {
                        validMoves.setPosition(file, rank, moveType.VALID);
                        distance++;
                    } else if (!isSameColor(getBoard().getPosition(file, rank))) {
                        validMoves.setPosition(file,rank, moveType.VALID);
                        distance = 1;
                        break;
                    } else {
                        distance = 1;
                        break;
                    }
                    file = getFile() + right * distance;
                    rank = getRank() + up * distance;
                }

            }
        }
        updateValidMovesCheck();
    }

    public Piece copyToBoard(Board<Piece> board) {
        return new Bishop(getPosition(), isWhite()).setBoard(board);
    }
}
