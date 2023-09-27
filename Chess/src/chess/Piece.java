package chess;

public abstract class Piece {
    private Square _square;

    public Piece(Square square) {
        _square = square;
    }

    public abstract boolean isValidMove(Square destination);
}