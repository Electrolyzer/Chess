package chess;

public abstract class Piece {
    public static Board Board;

    private Square _position;
    private boolean _isWhite;
    private boolean _hasMoved = false;

    public Piece(Square position, boolean isWhite) {
        _position = position;
        _isWhite = isWhite;
    }

    public abstract boolean isValidMove(Square destination);

    public void move(Square destination) {
        if (isValidMove(destination))
        {
            _position = destination;
            _hasMoved = true;
        }
    }
}