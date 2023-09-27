package chess;

public class Board {
    public Piece[][] Board;

    public void move(Square start, Square end) {
        return;
    }

    public Piece getPosition(Square position)
    {
        return Board[position.getFile()][position.getRank()];
    }
}
