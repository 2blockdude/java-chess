package Chess;

public class Board
{
    private int boardSizeX;
    private int boardSizeY;
    private Tile[][] board;
    private final int pawnValue = 1;
    private final int horseValue = 3;
    private final int bishopValue = 3;
    private final int rookValue = 5;
    private final int queenValue = 9;
    private final int kingValue = 900;

    public Board(int boardSizeX, int boardSizeY)
    {
        this.boardSizeX = boardSizeX;
        this.boardSizeY = boardSizeY;
        board = new Tile[boardSizeX][boardSizeY];
        this.setBoard();
    }

    public Tile getTile(int x, int y)
    {
        if(x >= 0 && x < boardSizeX && y >= 0 && y < boardSizeY)
        {
            return board[x][y];
        }

        throw new IllegalArgumentException();
    }

    public int getBoardSizeX()
    {
        return boardSizeX;
    }

    public int getBoardSizeY()
    {
        return boardSizeY;
    }

    private void setBoard()
    {
        for (int x = 0; x < boardSizeX; x++)
        {
            for (int y = 0; y < boardSizeY; y++)
            {
                board[x][y] = new Tile(x, y, null);
            }
        }

        // SET BOARD
        // white
        board[0][1].setPiece(new Pawn(true, pawnValue));
        board[1][1].setPiece(new Pawn(true, pawnValue));
        board[2][1].setPiece(new Pawn(true, pawnValue));
        board[3][1].setPiece(new Pawn(true, pawnValue));
        board[4][1].setPiece(new Pawn(true, pawnValue));
        board[5][1].setPiece(new Pawn(true, pawnValue));
        board[6][1].setPiece(new Pawn(true, pawnValue));
        board[7][1].setPiece(new Pawn(true, pawnValue));
        board[0][0].setPiece(new Rook(true, pawnValue));
        board[1][0].setPiece(new Horse(true, horseValue));
        board[2][0].setPiece(new Bishop(true, bishopValue));
        board[3][0].setPiece(new Queen(true, rookValue));
        board[4][0].setPiece(new King(true, kingValue));
        board[5][0].setPiece(new Bishop(true, bishopValue));
        board[6][0].setPiece(new Horse(true, horseValue));
        board[7][0].setPiece(new Rook(true, rookValue));
        // black
        board[0][6].setPiece(new Pawn(false, pawnValue));
        board[1][6].setPiece(new Pawn(false, pawnValue));
        board[2][6].setPiece(new Pawn(false, pawnValue));
        board[3][6].setPiece(new Pawn(false, pawnValue));
        board[4][6].setPiece(new Pawn(false, pawnValue));
        board[5][6].setPiece(new Pawn(false, pawnValue));
        board[6][6].setPiece(new Pawn(false, pawnValue));
        board[7][6].setPiece(new Pawn(false, pawnValue));
        board[0][7].setPiece(new Rook(false, pawnValue));
        board[1][7].setPiece(new Horse(false, horseValue));
        board[2][7].setPiece(new Bishop(false, bishopValue));
        board[3][7].setPiece(new Queen(false, rookValue));
        board[4][7].setPiece(new King(false, kingValue));
        board[5][7].setPiece(new Bishop(false, bishopValue));
        board[6][7].setPiece(new Horse(false, horseValue));
        board[7][7].setPiece(new Rook(false, rookValue));


        // Pawn test
        // en passant
//        board[0][3].setPiece(new Pawn(true, 10));
//        board[0][3].getPiece().increaseMoves();
//        board[1][3].setPiece(new Pawn(false, 10));
        // blocking
//        board[0][0].setPiece(new Pawn(true, 10));
//        board[0][3].setPiece(new Pawn(false, 10));

        // Rook test
//        board[3][3].setPiece((new Rook(true, 20)));
//        board[3][5].setPiece((new Rook(true, 20)));
//        board[3][0].setPiece((new Rook(false, 20)));
//        board[0][7].setPiece((new Rook(false, 20)));
//        board[3][1].setPiece((new Rook(false, 20)));

        // Bishop test
//        board[3][3].setPiece((new Bishop(true, 15)));
//        board[5][5].setPiece((new Bishop(true, 15)));
//        board[1][1].setPiece((new Bishop(false, 15)));
//        board[7][7].setPiece((new Bishop(false, 15)));
//        board[2][4].setPiece((new Bishop(false, 15)));

        // Queen test
//        board[3][3].setPiece((new Queen(true, 30)));
//        board[6][3].setPiece((new Queen(false, 30)));
//        board[3][6].setPiece((new Queen(true, 30)));
//        board[5][5].setPiece((new Queen(true, 30)));
//        board[7][7].setPiece((new Queen(false, 30)));
//        board[1][1].setPiece((new Queen(false, 30)));
//        board[2][3].setPiece((new Queen(true, 30)));
//        board[3][2].setPiece((new Queen(true, 30)));
//        board[4][2].setPiece((new Rook(false, 30)));

        // Horse test
//        board[3][3].setPiece((new Horse(true, 20)));
//        board[5][2].setPiece((new Horse(true, 20)));
//        board[1][4].setPiece((new Horse(false, 20)));

        // King test
        // castle
//        board[4][0].setPiece((new King(true, 900)));
//        board[5][2].setPiece(new Horse(false, 10));
//        board[4][7].setPiece(new Rook(false, 10));
//        board[0][1].setPiece(new Rook(true, 10));
//        board[0][3].getPiece().increaseMoves();
//        board[4][1].setPiece((new Queen(false, 20)));
//        board[4][5].setPiece((new Queen(true, 30)));
//        board[7][7].setPiece(new King(false, 10));
//        board[2][1].setPiece(new Pawn(true, 10));
//        board[3][1].setPiece(new Pawn(true, 10));
    }
}
