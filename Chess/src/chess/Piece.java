package chess;

/** Abstract base class for all pieces. */
public abstract class Piece {
    /** The board shared for all Pieces */
    public static Board<Piece> Board = new Board<Piece>();

    private Square _position;
    private boolean _isWhite;
    private boolean _hasMoved = false;

    public static enum moveType {INVALID, VALID, CASTLE, ENPASSANT, PROMOTE}
    protected Board<moveType> validMoves;

    /** Creates a new piece with the desired position and color. */
    public Piece(Square position, boolean isWhite) {
        _position = position;
        _isWhite = isWhite;
    }

    /** Returns whether or not moving the piece to the given destination would be valid. 
    */
    public moveType isValidMove(Square destination){
        return validMoves.getPosition(destination);
    }

    /** Generates the array of which moves are valid for this piece at current boardstate */
    public abstract void updateValidMoves();

    /** Returns whether the two pieces being compared are of the same color */
    protected boolean isSameColor(Piece other){ return isWhite() ^ other.isWhite(); }

    /** Returns whether the piece has moved yet this game */
    protected boolean hasMoved(){ return _hasMoved; }

    /** Sets have moved to true. Prevents weird castling bugs with pawns on the e-file promoting to rooks. */
    public Piece promoteSetup() { 
        _hasMoved = true; 
        return this;
    }

    /** Returns the position of the piece. */
    protected Square getPosition() { return _position; }

    /** Returns whether or not the piece is white. */
    public boolean isWhite() { return _isWhite; }

    public int getRank() { return _position.getRank(); }
    public int getFile() { return _position.getFile(); }
}