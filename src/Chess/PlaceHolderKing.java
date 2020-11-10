package Chess;

public class PlaceHolderKing extends Piece
{
    public PlaceHolderKing(boolean white)
    {
        super(white);
    }

    @Override
    public int isMoveLegal(Board board, Tile moveFrom, Tile moveTo)
    {
        return 0;
    }

    @Override
    public char getId()
    {
        return 'K';
    }

    @Override
    public int getValue()
    {
        return 900;
    }
}
