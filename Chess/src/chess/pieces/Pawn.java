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
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                validMoves.setPosition(i, j, moveType.INVALID);
            }    
        }
        //Check which direction Pawn moves in
        int direction = isWhite() ? 1 : -1;
        
        //Classic Pawn movement
        if((Board.Board[getFile()][getRank()+direction]) == null){
            validMoves.setPosition(getFile(), getRank()+direction, moveType.VALID);
        }

        //Pawn Capture movement
        if (!isSameColor(Board.Board[getFile() + 1][getRank() + direction])) {
            validMoves.setPosition(getFile() + 1, getRank() + direction, moveType.VALID);
        }
        if (!isSameColor(Board.Board[getFile() - 1][getRank() + direction])) {
            validMoves.setPosition(getFile() - 1, getRank() + direction, moveType.VALID);
        }

        // First Pawn move option
        if (!hasMoved() && (null == (Board.Board[getFile()][getRank() + direction])) &&
                (null == (Board.Board[getFile()][getRank() + 2 * direction]))) {
            validMoves.setPosition(getFile(), getRank() + 2 * direction, moveType.VALID);
        }

        //En passant
        //TODO
    }

    //Transform into the chosen piece when the Pawn reaches the last row
    public void promotePawn(char pieceToBecome){
        switch(pieceToBecome){
            case 'N':
                Board.Board[getFile()][getRank()] = new Knight(getPosition(), isWhite());
                break;
            case 'B':
                Board.Board[getFile()][getRank()] = new Bishop(getPosition(), isWhite());
                break;
            case 'R':
                Board.Board[getFile()][getRank()] = new Rook(getPosition(), isWhite());
                break;
            case 'Q':
                Board.Board[getFile()][getRank()] = new Queen(getPosition(), isWhite());
                break;
        }
    }
    
    //If no piece is given to transform into, then transform into a queen
    public void promotePawn(){
        Board.Board[getFile()][getRank()] = new Queen(getPosition(), isWhite());
    }

}
