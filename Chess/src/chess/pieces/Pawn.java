package chess.pieces;
import chess.Piece;
import chess.Square;

public class Pawn extends Piece {
/** Specific class for Pawns*/

    public boolean Enpessantable = false; 

    public Pawn(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public void updateValidMoves(){

        //Initialize all moves to invalid
        validMoves = new boolean[8][8];

        //Check which direction Pawn moves in
        int direction = isWhite() ? 1 : -1;
        
        //Classic Pawn movement
        validMoves[getFile()][getRank()+direction] = (null == (Board.Board[getFile()][getRank()+direction]));

        //Pawn Capture movement
        validMoves[getFile()+1][getRank()+direction] = !isSameColor(Board.Board[getFile()+1][getRank()+direction]);
        validMoves[getFile()-1][getRank()+direction] = !isSameColor(Board.Board[getFile()-1][getRank()+direction]);

        //First Pawn move option
        validMoves[getFile()][getRank()+2*direction] = !hasMoved() && 
                                                        (null == (Board.Board[getFile()][getRank()+direction])) &&
                                                        (null == (Board.Board[getFile()][getRank()+2*direction]));

        //En passant
        //TODO
    }

    //Transform into the chosen piece when the Pawn reaches the last row
    public void promotePawn(String pieceToBecome){
        switch(pieceToBecome){
            case "Q":
                Board.Board[getFile()][getRank()] = new Queen(getPosition(), isWhite());
                break;
            case "N":
                Board.Board[getFile()][getRank()] = new Knight(getPosition(), isWhite());
                break;
            case "B":
                Board.Board[getFile()][getRank()] = new Bishop(getPosition(), isWhite());
                break;
            case "R":
                Board.Board[getFile()][getRank()] = new Rook(getPosition(), isWhite());
                break;            
        }
    }
    
    //If no piece is given to transform into, then transform into a queen
    public void promotePawn(){
        Board.Board[getFile()][getRank()] = new Queen(getPosition(), isWhite());
    }

}
