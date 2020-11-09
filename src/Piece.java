public abstract class Piece
{
    private boolean captured;
    private boolean white;
    protected boolean movesMinusOne = false;
    private int movesTaken = 0;
    private int value;

    public Piece(boolean white, int value)
    {
        this.white = white;
        this.value = value;
    }

    public boolean isCaptured()
    {
        return this.captured;
    }

    public void setCaptured(boolean captured)
    {
        this.captured = captured;
    }

    public boolean isWhite()
    {
        return this.white;
    }

    public void setWhite(boolean white)
    {
        this.white = white;
    }

    public int getMoves()
    {
        return movesTaken;
    }

    public void setMoves(int moves)
    {
        this.movesTaken = moves;
    }

    public void increaseMoves()
    {
        movesTaken++;
    }

    // isLegalMove notes
    // 0: illegal move
    // 1: legal move
    // 2: en passant
    // 3: promotion
    // 4: castle king side
    // 5: castle queen side
    public abstract int isLegalMove(Board board, Tile moveFrom, Tile moveTo);
    public abstract char getId();

}
