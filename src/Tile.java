public class Tile
{
    private Piece piece;
    private int x;
    private int y;

    public Tile(int x, int y, Piece piece)
    {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(int x)
    {
        if (x < 8 && x >= 0)
        {
            this.x = x;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(int y)
    {
        if (y < 8 && y >= 0)
        {
            this.y = y;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public Piece getPiece()
    {
        return this.piece;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }
}
