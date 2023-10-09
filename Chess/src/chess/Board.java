package chess;

import java.util.*;

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

    /** Creates copy of a given board. Mainly for use in checking move results without 
     * affecting the main board.
     */
    public Board(Board<T> boardToCopy){
        Board = new ArrayList<List<T>>();
        for (int i = 0; i < 8; i++)
        {
            List<T> list = new ArrayList<T>();
            for (int j = 0; j < 8; j++)
            {
                list.add(boardToCopy.getPosition(i, j));
            }
            Board.add(list);
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
}