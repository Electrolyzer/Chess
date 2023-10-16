package chess.pieces;

import chess.Board;
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
        if (getRank() + direction <= 7 && getRank() + direction >= 0
            && (getBoard().getPosition(getFile(), getRank() + direction)) == null) {
            validMoves.setPosition(getFile(), getRank() + direction, moveType.VALID);
        }

        // Pawn Capture movement with edge detection
        if (getFile() < 7 && getRank() + direction <= 7 && getRank() + direction >= 0) {
            if(getBoard().getPosition(getFile() + 1, getRank() + direction) != null){
                if (!isSameColor(getBoard().getPosition(getFile() + 1, getRank() + direction))) {
                    moveType type = getBoard().getPosition(getFile() + 1, getRank() + direction) instanceof PhantomPawn ? moveType.ENPASSANT : moveType.VALID;
                    validMoves.setPosition(getFile() + 1, getRank() + direction, type);
                }
            }
        }
        if (getFile() > 0 && getRank() + direction <= 7 && getRank() + direction >= 0) {
            if(getBoard().getPosition(getFile() - 1, getRank() + direction) != null){
                if (!isSameColor(getBoard().getPosition(getFile() - 1, getRank() + direction))) {
                    moveType type = getBoard().getPosition(getFile() - 1, getRank() + direction) instanceof PhantomPawn ? moveType.ENPASSANT : moveType.VALID;
                    validMoves.setPosition(getFile() - 1, getRank() + direction, type);
                }
            }
        }

        // First Pawn move option
        if (!hasMoved() && (null == (getBoard().getPosition(getFile(), getRank() + direction))) &&
                (null == (getBoard().getPosition(getFile(), getRank() + 2 * direction)))) {
            validMoves.setPosition(getFile(), getRank() + 2 * direction, moveType.VALID);
        }

        // Pawns that reach the other side should be promoted
        int otherSide = isWhite() ? 7 : 0;
        for (int i = 0; i < 8; i++) {
            if (validMoves.getPosition(i, otherSide) == moveType.VALID){
                validMoves.setPosition(i, otherSide, moveType.PROMOTE);
            }
        }
        updateValidMovesCheck();
    }

    /** Transform into the chosen piece when the Pawn reaches the last row */ 
    public void promotePawn(char pieceToBecome) {
        switch (pieceToBecome) {
            case 'N':
                getBoard().setPosition(getFile(), getRank(), new Knight(getPosition(), isWhite()).promoteSetup());
                break;
            case 'B':
                getBoard().setPosition(getFile(), getRank(), new Bishop(getPosition(), isWhite()).promoteSetup());
                break;
            case 'R':
                getBoard().setPosition(getFile(), getRank(), new Rook(getPosition(), isWhite()).promoteSetup());
                break;
            default:
                getBoard().setPosition(getFile(), getRank(), new Queen(getPosition(), isWhite()).promoteSetup());
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
            getBoard().setPosition(getFile(), getRank() + (isWhite() ? -1 : 1), phantom);
        }
    }

    public Piece copyToBoard(Board<Piece> board) {
        return new Pawn(getPosition(), isWhite()).setBoard(board);
    }
}
