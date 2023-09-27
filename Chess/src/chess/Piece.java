package chess;

/** Abstract base class for all pieces. */
public abstract class Piece {
    /** The board shared for all Pieces */
    public static Board Board = new Board();

    private Square _position;
    private boolean _isWhite;
    private boolean _hasMoved = false;

    /** Creates a new piece with the desired position and color. */
    public Piece(Square position, boolean isWhite) {
        _position = position;
        _isWhite = isWhite;
    }

    /** Returns whether or not moving the piece to the given destination would be valid. */
    public abstract boolean isValidMove(Square destination);

    /** Moves the piece to the given destination if valid. */
    public void move(Square destination) {
        if (isValidMove(destination))
        {
            _position = destination;
            _hasMoved = true;
        }
    }
}