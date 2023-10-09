package chess.pieces;

import chess.Piece;
import chess.Square;

//* A "phantom pawn" used for checking if en passant works. */
public class PhantomPawn extends Piece {
    private Pawn _linkedPawn;

    /** Creates a phantom pawn of the same color and one square behind the given pawn */
    public PhantomPawn(Pawn pawn) {
        super(
            new Square(
                pawn.getFile(), 
                pawn.getRank() + (pawn.isWhite() ? -1 : 1)
            ),
            pawn.isWhite()
        );
        _linkedPawn = pawn;
    }

    public String getType(){ return ""; }

    // All moves are invalid, as phantom pawns can not move.
    public void updateValidMoves() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                validMoves.setPosition(i, j, moveType.INVALID);
            }
        }
    }

    public Pawn getLinkedPawn() {
        return _linkedPawn;
    }
}
