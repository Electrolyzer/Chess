package chess;

public class Board {
    public Piece[][] Board;

    public void move(Square start, Square end) {
        return;
    }

    public Piece getPosition(Square position){
        return Board[position.getFile()][position.getRank()];
    }

    public void setPosition(Square position, Piece piece){
        Board[position.getFile()][position.getRank()] = piece;
    }
}
