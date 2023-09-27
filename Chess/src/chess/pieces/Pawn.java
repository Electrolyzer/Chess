package chess.pieces;
import chess.Piece;
import chess.Square;

public class Pawn extends Piece {
/** Specific class for Pawns*/
    public Pawn(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public boolean isValidMove(Square destination){

        if(!super.isValidMove(destination)) return false;
        if(
            this.getPosition().getFile() - destination.getFile() == 0 &&
            this.getPosition().getRank() - destination.getRank() == 1 &&
            this.isWhite()
            ) return true;
        if(
            this.getPosition().getFile() - destination.getFile() == 0 &&
            this.getPosition().getRank() - destination.getRank() == -1 &&
            !this.isWhite()
            ) return true;

        return false;
    }
    
}
