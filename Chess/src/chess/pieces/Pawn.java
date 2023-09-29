package chess.pieces;
import chess.Piece;
import chess.Square;

public class Pawn extends Piece {
/** Specific class for Pawns*/
    public Pawn(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public void updateValidMoves(){

        //Initialize all moves to invalid
        validMoves = new boolean[8][8]

        //Check which direction Pawn moves in
        int direction = isWhite() ? 1 : -1;
        
        //Classic Pawn movement
        validMoves[getFile()][getRank()+direction] = (null == (Board.Board[getFile()][getRank()+direction]));

        //Pawn Capture movement
        validMoves[getFile()+1][getRank()+direction] = !isSameColor(Board.Board[getFile()+1][getRank()+direction]);
        validMoves[getFile()-1][getRank()+direction] = !isSameColor(Board.Board[getFile()-1][getRank()+direction]);

        //First Pawn move option
        validMoves[getFile()][getRank()+2*direction] = !hasMoved();

        //En passant
        //TODO
    }

    //Transform into the chosen piece when the Pawn reaches the last row
    public void promotePawn(String pieceToBecome){
        //TODO
    }
    
    //If no piece is given to transform into, then transform into a queen
    public void promotePawn(){
        //TODO
    }

}
