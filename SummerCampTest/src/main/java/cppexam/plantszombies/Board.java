package cppexam.plantszombies;

public class Board {
    
    public static final int ROW = 5;
    public static final int COL = 10;
    
    private static final Grid[][] grids;
    
    private Board(){}
    
    static {
        grids = new Grid[ROW][COL];
        for (int i = 0; i < ROW; i ++) {
            for (int j = 0; j < COL; j ++) {
                grids[i][j] = new Grid(i, j);
            }
        }
    }
    
    public static Grid getGrid(int row, int col) {
        validatePos(row, col);
        return grids[row][col];
    }
    
    public static void validatePos(int row, int col) {
        if( !(0 <= row && row < ROW) || !(0 <= col && col < COL) ) throw new IndexOutOfBoundsException();
    }
}
