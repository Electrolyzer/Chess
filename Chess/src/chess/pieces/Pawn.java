package chess.pieces;

import chess.Piece;
import chess.Square;

public class Pawn extends Piece {
    /** Specific class for Pawns */

    public boolean Enpessantable = false;

    public Pawn(Square position, boolean isWhite) {
        super(position, isWhite);
    }

    public String getType(){ return "P"; }

    public void updateValidMoves() {

        // Initialize all moves to invalid
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                validMoves.setPosition(i, j, moveType.INVALID);
            }
        }
        // Check which direction Pawn moves in
        int direction = isWhite() ? 1 : -1;

        // Classic Pawn movement
        if ((Board.getPosition(getFile(), getRank() + direction)) == null) {
            validMoves.setPosition(getFile(), getRank() + direction, moveType.VALID);
        }

        // Pawn Capture movement with edge detection
        if (getFile() < 7) {
            if(Board.getPosition(getFile() + 1, getRank() + direction) != null){
                if (!isSameColor(Board.getPosition(getFile() + 1, getRank() + direction))) {
                    moveType type = Board.getPosition(getFile() + 1, getRank() + direction) instanceof PhantomPawn ? moveType.ENPASSANT : moveType.VALID;
                    validMoves.setPosition(getFile() + 1, getRank() + direction, type);
                }
            }
        }
        if (getFile() > 0) {
            if(Board.getPosition(getFile() - 1, getRank() + direction) != null){
                if (!isSameColor(Board.getPosition(getFile() - 1, getRank() + direction))) {
                    moveType type = Board.getPosition(getFile() - 1, getRank() + direction) instanceof PhantomPawn ? moveType.ENPASSANT : moveType.VALID;
                    validMoves.setPosition(getFile() - 1, getRank() + direction, type);
                }
            }
        }

        // First Pawn move option
        if (!hasMoved() && (null == (Board.getPosition(getFile(), getRank() + direction))) &&
                (null == (Board.getPosition(getFile(), getRank() + 2 * direction)))) {
            validMoves.setPosition(getFile(), getRank() + 2 * direction, moveType.VALID);
        }

        // En passant
        // TODO

        // Pawns that reach the other side should be promoted
        int otherSide = isWhite() ? 7 : 0;
        for (int i = 0; i < 8; i++) {
            if (validMoves.getPosition(i, otherSide) == moveType.VALID){
                validMoves.setPosition(i, otherSide, moveType.PROMOTE);
            }
        }
    }

    /** Transform into the chosen piece when the Pawn reaches the last row */ 
    public void promotePawn(char pieceToBecome) {
        switch (pieceToBecome) {
            case 'N':
                Board.setPosition(getFile(), getRank(), new Knight(getPosition(), isWhite()).promoteSetup());
                break;
            case 'B':
                Board.setPosition(getFile(), getRank(), new Bishop(getPosition(), isWhite()).promoteSetup());
                break;
            case 'R':
                Board.setPosition(getFile(), getRank(), new Rook(getPosition(), isWhite()).promoteSetup());
                break;
            default:
                Board.setPosition(getFile(), getRank(), new Queen(getPosition(), isWhite()).promoteSetup());
                break;
        }
    }

    public void move(Square destination)
    {
        boolean movingTwo = Math.abs(getPosition().getRank() - destination.getRank()) == 2;
        super.move(destination);
        if (movingTwo)
        {
            PhantomPawn phantom = new PhantomPawn(this);
            Board.setPosition(getFile(), getRank(), phantom);
        }
    }
}
