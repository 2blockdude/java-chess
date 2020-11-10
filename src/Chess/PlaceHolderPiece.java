package Chess;

public class PlaceHolderPiece extends Piece
{
    public PlaceHolderPiece(boolean white)
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
        return 0;
    }

    @Override
    public int getValue()
    {
        return 0;
    }
}
