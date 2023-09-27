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

    /** Returns whether or not moving the piece to the given destination would be valid. 
     * This should be overriden by all base classes.
    */
    public boolean isValidMove(Square destination)
    {
        Piece piece = Board.getPosition(destination);
        return piece == null || (piece.isWhite() != _isWhite);
    }

    /** Moves the piece to the given destination if valid. */
    public void move(Square destination) {
        if (isValidMove(destination))
        {
            _position = destination;
            _hasMoved = true;
        }
    }

    /** Returns the position of the piece. */
    protected Square getPosition() { return _position; }

    /** Returns whether or not the piece is white. */
    public boolean isWhite() { return _isWhite; }
}