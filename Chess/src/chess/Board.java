package chess;

import java.util.*;
import chess.pieces.*;

public class Board<T> implements Iterable<T> {

    public List<List<T>> Board;

    public Board(){
        Board = new ArrayList<List<T>>();
        for (int i = 0; i < 8; i++)
        {
            List<T> list = new ArrayList<T>();
            for (int j = 0; j < 8; j++)
            {
                list.add(null);
            }
            Board.add(list);
        }
    }

    public T getPosition(Square position){
        return Board.get(position.getFile()).get(position.getRank());
    }

    public T getPosition(int file, int rank){
        return Board.get(file).get(rank);
    }

    public void setPosition(Square position, T object){
        Board.get(position.getFile()).set(position.getRank(), object);
    }

    public void setPosition(int file, int rank, T object){
        Board.get(file).set(rank, object);
    }

    public Iterator<T> iterator()
    {
        return new BoardIterator();
    }

    private class BoardIterator implements Iterator<T>
    {
        private Square _curr = new Square(0, 0);
        private boolean _isDone = false;

        public boolean hasNext() {
            return !_isDone;
        }

        public T next() {
            T value = getPosition(_curr);
            _curr = _curr.getNextSquare();
            if (_curr.equals(new Square(0, 0)))
                _isDone = true;
            return value;
        }
    }
    
    /** Creates copy of a given board. Mainly for use in checking move results without 
     * affecting the main board.
     */
    public static Board<Piece> deepCopy (Board<Piece> boardToCopy){
        Board<Piece> newBoard = new Board<Piece>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard.setPosition(i, j, 
                    boardToCopy.getPosition(i, j) == null
                    ? null
                    : boardToCopy.getPosition(i, j).copyToBoard(newBoard)
                );
            }
        }
        return newBoard;
    }

    /** Returns whether or not the king of the specified color is in check. <p>
      * If there is no king of the specified color, the method returns false.
      */
    public static boolean isInCheck(Board<Piece> board, boolean isWhite) {
        King king = null;
        for (Piece piece : board) {
            if (piece != null && piece instanceof King && piece.isWhite() == isWhite) {
                king = (King) piece;
                break;
            }
        }
        if (king == null) return false;
        int startFile = king.getFile();
        int startRank = king.getRank();

        // Loop over the 8 directions.
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                boolean ortho = i == 0 || j == 0;
                boolean isForward = isWhite ? j == 1 : j == -1;
                int file = startFile + i;
                int rank = startRank + j;
                Square currentSquare = new Square(0, 0);
                Piece pieceAtSquare = board.getPosition(currentSquare);
                boolean kingsMoveAway = true;
                boolean blockedByEdge = true;
                while (file >= 0 && file < 8 && rank >= 0 && rank < 8) {
                    pieceAtSquare = board.getPosition(file, rank);
                    if (!(pieceAtSquare == null || pieceAtSquare instanceof PhantomPawn))
                    {
                        blockedByEdge = false;
                        break;
                    }
                    file += i;
                    rank += j;
                    kingsMoveAway = false;
                }
                if (blockedByEdge) continue;
                if (!pieceAtSquare.isSameColor(king) && (
                    pieceAtSquare instanceof Queen
                    || (ortho && pieceAtSquare instanceof Rook)
                    || (!ortho && pieceAtSquare instanceof Bishop)
                    || (!ortho && kingsMoveAway && isForward && pieceAtSquare instanceof Pawn)
                    || (kingsMoveAway && pieceAtSquare instanceof King)
                )) { return true; }
            }
        }

        // We have checked all the pieces with the exception of the knight; lets check the knight now.
        //The two for loops iterate the 8 possible directions
        for (int up = -2; up <= 2; up ++) {
            for (int right = -2; right <= 2; right ++) {

                //Only check for valid moves if the taxicab distance is 3
                if((Math.abs(right)+Math.abs(up)!=3)){ continue; }

                //Ensures knight move is not out of bounds
                int file = startFile + up;
                int rank = startRank + right;
                if(file<0 || file>7 || rank<0 || rank>7){ continue; }

                //Checks whether the space is occupied by an enemy knight.
                Piece pieceAtSquare = board.getPosition(file, rank);
                if (pieceAtSquare != null && !pieceAtSquare.isSameColor(king) && pieceAtSquare instanceof Knight)
                    return true; 
            }
        }

        return false;
    }

    public static boolean anyValidMoves(List<Board<Piece.moveType>> validMoveLists) {
        for (Board<Piece.moveType> board : validMoveLists) {
            for (Piece.moveType move : board) {
                if (move != Piece.moveType.INVALID) return true;
            }
        }
        return false;
    }
}