package chess;

import chess.pieces.PhantomPawn;

/** Abstract base class for all pieces. */
public abstract class Piece {
    /** The board shared for all Pieces */
    private Board<Piece> _board; 
    public static final Board<Piece> DefaultBoard = new Board<Piece>();

    private Square _position;
    private boolean _isWhite;
    private boolean _hasMoved = false;

    public static enum moveType {INVALID, VALID, CASTLE, ENPASSANT, PROMOTE}
    protected Board<moveType> validMoves;

    /** Creates a new piece with the desired position and color. */
    public Piece(Square position, boolean isWhite) {
        _position = position;
        _isWhite = isWhite;
        _board = DefaultBoard;
        validMoves = new Board<moveType>();
    }

    /** Creates a copy of the piece with the same properties but on a different Board */
    public abstract Piece copyToBoard(Board<Piece> board);

    /** Returns whether or not moving the piece to the given destination would be valid. 
    */
    public moveType isValidMove(Square destination){
        
        return validMoves.getPosition(destination);
    }

    public void move(Square destination){
        _board.setPosition(destination, this);
        _board.setPosition(_position, null);
        _hasMoved = true;
        _position = destination;
        for (Piece piece : _board) {
            if (piece instanceof PhantomPawn) {
                _board.setPosition(piece.getFile(), piece.getRank(), null);
            }
        }
    }

    /** Generates the array of which moves are valid for this piece at current boardstate */
    public abstract void updateValidMoves();

    /** Returns whether the two pieces being compared are of the same color */
    protected boolean isSameColor(Piece other){ return !(isWhite() ^ other.isWhite()); }

    /** Returns whether the piece has moved yet this game */
    public boolean hasMoved(){ return _hasMoved; }

    /** Sets have moved to true. Prevents weird castling bugs with pawns on the e-file promoting to rooks. */
    public Piece promoteSetup() { 
        _hasMoved = true; 
        return this;
    }

    /** Returns the position of the piece. */
    protected Square getPosition() { return _position; }

    /** Returns the board the piece is on. */
    public Board<Piece> getBoard() { return _board; }

    /** Sets the board the piece is using. Returns the piece. */
    protected Piece setBoard(Board<Piece> board) { _board = board; return this; }

    /** Returns whether or not the piece is white. */
    public boolean isWhite() { return _isWhite; }

    public int getRank() { return _position.getRank(); }
    public int getFile() { return _position.getFile(); }

    public abstract String getType();

    private boolean isInCheckAfterMove(Square destination) {
        Board<Piece> copy = Board.deepCopy(_board);
        
        copy.setPosition(destination, this);
        copy.setPosition(_position, null);
        return Board.isInCheck(copy, isWhite()); // Warning message won't go away bc we named both the type and a variable Board.
    }

    protected void updateValidMovesCheck() {
        Square s = new Square(0, 0);
        while (true) {
            if (validMoves.getPosition(s) != moveType.INVALID && isInCheckAfterMove(s))
                validMoves.setPosition(s, moveType.INVALID);
            s = s.getNextSquare();
            if (s.equals(new Square(0, 0)))
                break;
        }
    }
}