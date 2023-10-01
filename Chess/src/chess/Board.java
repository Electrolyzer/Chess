package chess;

import java.util.*;

public class Board<T> {

    public List<List<T>> Board;

    public Board(){
        Board = new ArrayList<List<T>>();
        for (int i = 0; i < 8; i++)
        {
            Board.add(new ArrayList<T>());
        }
    }

    public void move(Square start, Square end) {
        return;
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
}
