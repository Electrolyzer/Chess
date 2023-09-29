package chess;

public class Board<T> {
    public T[][] Board;

    public Board(){
    }

    public void move(Square start, Square end) {
        return;
    }

    public T getPosition(Square position){
        return Board[position.getFile()][position.getRank()];
    }

    public T getPosition(int file, int rank){
        return Board[file][rank];
    }

    public void setPosition(Square position, T object){
        Board[position.getFile()][position.getRank()] = object;
    }

    public void setPosition(int file, int rank, T object){
        Board[file][rank] = object;
    }
}
