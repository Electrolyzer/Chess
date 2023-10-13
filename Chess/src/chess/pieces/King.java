package chess.pieces;
import chess.Piece;
import chess.Square;

public class King extends Piece {

    public King(Square position, boolean isWhite){
        super(position, isWhite);
    }

    public String getType(){ return "K"; }

    public void updateValidMoves(){

        //Initialize all moves to invalid
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                validMoves.setPosition(i, j, moveType.INVALID);
            }    
        }

        int file;
        int rank;

        // The two for loops iterate the 8 possible directions
        for (int up = -1; up <= 1; up++) {
            for (int right = -1; right <= 1; right++) {

                // Don't check for valid move when there is no direction
                if (((right == 0) && (up == 0))) {
                    continue;
                }

                //Ensures king does not move out of bounds
                file = getFile() + up;
                rank = getRank() + right;
                if(file<0 || file>7 || rank<0 || rank>7){ continue; }

                // Checks each direction for valid movement
                Piece piece = Board.getPosition(getFile() + up, getRank() + right);
                if (null == piece || piece instanceof PhantomPawn) {
                    validMoves.setPosition(getFile() + up, getRank() + right, moveType.VALID);
                    continue;
                } else if (!isSameColor(Board.getPosition(getFile() + up, getRank() + right))) {
                    validMoves.setPosition(getFile() + up, getRank() + right, moveType.VALID);
                }

            }
        }

        // Handle castling.
        if (!hasMoved())
        {
            for (Piece piece : Board)
            {
                //Make sure the other piece is a rook of the same color that hasn't moved
                if (!(piece instanceof Rook)) 
                    continue;
                if (!isSameColor(piece))
                    continue;
                if(piece.hasMoved())
                    continue;

                //Check which direction the castling is happening in
                if (piece.getFile() < getFile() 
                    && isValidMove(new Square(getFile() - 1, getRank())) != moveType.INVALID  //Make sure the king is not in check in the next square
                    && Board.getPosition(getFile() - 1, getRank()) == null  //Make sure the next three squares are empty for LH castle
                    && Board.getPosition(getFile() - 2, getRank()) == null
                    && Board.getPosition(getFile() - 3, getRank()) == null)
                    validMoves.setPosition(getFile() - 2, getRank(), moveType.CASTLE);
                else if (piece.getFile() > getFile() 
                    && isValidMove(new Square(getFile() + 1, getRank())) != moveType.INVALID
                    && Board.getPosition(getFile() + 1, getRank()) == null  //Make sure the next two squares are empty for RH castle
                    && Board.getPosition(getFile() - 2, getRank()) == null)
                    validMoves.setPosition(getFile() + 2, getRank(), moveType.CASTLE);
            }
        }

    }
}