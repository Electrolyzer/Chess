package chess;

public class Square {
    private int _file;
    private int _rank;

    public Square(int file, int rank) {
        if (file < 0 || file > 7 || rank < 0 || rank > 7)
            throw new IllegalArgumentException("Both rank and file must be between 0 and 7, exclusive.");

        _file = file;
        _rank = rank;
    }

    public int getFile() { return _file; }
    public int getRank() { return _rank; }

    public Square getNextSquare() {
        if (_rank < 7) {
            return new Square(_file, _rank + 1);
        }
        if (_file < 7) {
            return new Square(_file + 1, 0);
        }
        return new Square(0, 0);
    }

    public boolean equals(Object ob)
    {
        if (ob == null || !(ob instanceof Square))
            return false;
        Square sq = (Square) ob;
        return _file == sq._file && _rank == sq._rank;
    }
}