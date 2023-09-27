package chess.pieces;
import chess.Piece;
import chess.Square;

public class Pawn extends Piece {
/** Specific class for Pawns*/
    public Pawn(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public void updateValidMoves(){
        int direction = isWhite() ? 1 : -1;
        
        validMoves[getFile()][getRank()+direction] = (null == (Board.Board[getFile()][getRank()+direction]));
        validMoves[getFile()+1][getRank()+direction] =  isWhite() != (Board.Board[getFile()+1][getRank()+direction]).isWhite();
        validMoves[getFile()-1][getRank()+direction] =  isWhite() != (Board.Board[getFile()-1][getRank()+direction]).isWhite();
    }
    
}
