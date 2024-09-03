public class InsertionSort implements SortingAlgorithm {
    @Override
    public void sort(ChessPiece[][] board, int stepDelay, ChessPiece[][] base) {
        int rows = board.length;
        int cols = board[0].length;

        // Print the initial state
        System.out.println("Initial state:");
        //printMatrix(matrix);

        for (int i = 1; i < rows * cols; i++) {
            int[] currentCoords = getCoordinates(i, cols);
            ChessPiece key = board[currentCoords[0]][currentCoords[1]];
            int j = i - 1;

            while (j >= 0) {
                int[] prevCoords = getCoordinates(j, cols);
                ChessPiece prevElement = board[prevCoords[0]][prevCoords[1]];
                if(key == null && prevElement == null) break;
                if (key != null &&
                        (findIndex(base, prevElement) > findIndex(base, key) || prevElement == null)) {

                    // Shift element one position ahead
                    board[prevCoords[0]][prevCoords[1]] = key;
                    board[currentCoords[0]][currentCoords[1]] = prevElement;

                    currentCoords = prevCoords; // Update currentCoords for next iteration
                    j--;

                    ChessSimulator.displayBoard(board);
                    try {
                        Thread.sleep(stepDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break; // Stop shifting if the correct position is found
                }
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
            return -1; // Nulls are considered to be at the end
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
