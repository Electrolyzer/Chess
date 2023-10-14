package chess.pieces;
import chess.Board;
import chess.Piece;
import chess.Square;

public class Rook extends Piece{

    public Rook(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public String getType(){ return "R"; }

    public void updateValidMoves(){

        //Initialize all moves to invalid
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                validMoves.setPosition(i, j, moveType.INVALID);
            }    
        }

        int distance = 1;
        int rank = 0;
        int file = 0;

        //The two for loops iterate the 4 possible directions
        for (int up = -1; up <= 1; up ++) {
            for (int right = -1; right <= 1; right ++) {

                //Only check for valid moves if exactly one of right or up is zero so the rook only moves straight
                if(!((right==0) ^ (up==0))){ continue; }
                
                file = getFile() + right * distance;
                rank = getRank() + up * distance;
                //Checks each direction until it hits a piece, then checks whether that piece is capturable but stops
                //after hitting a piece whether or not it is. 
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
        return new Rook(getPosition(), isWhite()).setBoard(board);
    }
}

