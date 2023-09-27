package chess;

public class Square {
    private int _file;
    private int _rank;

    public Square(int file, int rank) {
        _file = file;
        _rank = rank;
    }

    public int getFile() { return _file; }
    public int getRank() { return _rank; }
}