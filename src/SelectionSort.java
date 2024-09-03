public class SelectionSort implements SortingAlgorithm {
    @Override
    public void sort(ChessPiece[][] board, int stepDelay, ChessPiece[][] base) {
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ChessPiece nextElement = base[i][j];
                int[] minIdx = findIndex(board, nextElement);

                if (nextElement != null) {
                    ChessPiece temp = board[i][j];
                    board[i][j] = board[minIdx[0]][minIdx[1]];
                    board[minIdx[0]][minIdx[1]] = temp;

                    ChessSimulator.displayBoard(board);
                    try {
                        Thread.sleep(stepDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static int[] findIndex(ChessPiece[][] board, ChessPiece element) {
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(board[i][j] == null || element ==null) {
                    continue;
                }
                if (board[i][j].toString().equals(element.toString())) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }
}