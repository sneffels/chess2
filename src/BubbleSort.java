public class BubbleSort implements SortingAlgorithm {
    @Override
    public void sort(ChessPiece[][] board, int stepDelay, ChessPiece[][] base) {
        int rows = board.length;
        int cols = board[0].length;
        boolean swapped;

        for (int i = 0; i < rows * cols - 1; i++) {
            swapped = false;
            for (int j = 0; j < rows * cols - i - 1; j++) {
                int[] currentCoords = getCoordinates(j, cols);
                int[] nextCoords = getCoordinates(j + 1, cols);

                ChessPiece currentElement = board[currentCoords[0]][currentCoords[1]];
                ChessPiece nextElement = board[nextCoords[0]][nextCoords[1]];

                if(nextElement == null && currentElement ==null) continue;
                if(nextElement != null){
                    if (findIndex(base, currentElement) > findIndex(base, nextElement) || currentElement == null) {

                        board[currentCoords[0]][currentCoords[1]] = nextElement;
                        board[nextCoords[0]][nextCoords[1]] = currentElement;
                        swapped = true;

                        ChessSimulator.displayBoard(board);
                        try {
                            Thread.sleep(stepDelay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    private static int[] getCoordinates(int index, int cols) {
        int row = index / cols;
        int col = index % cols;
        return new int[]{row, col};
    }

    private static int findIndex(ChessPiece[][] matrix, ChessPiece element) {
        if (element == null) {
            return -1;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != null && matrix[i][j].toString().equals(element.toString())) {
                    return i * cols + j; // Convert 2D index to 1D
                }
            }
        }
        return -1;

    }


}