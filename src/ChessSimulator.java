import java.util.Random;


public class ChessSimulator {
    private static final int BOARD_SIZE = 8;
    private static final ChessPiece[][] board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
    private static final ChessPiece[][] basedBoard = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
    private static final Random random = new Random();

    // Código de colores en ANSI
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private static String consoleColor = ANSI_RESET;


    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: java ChessSimulator a t c r s");
            System.out.println("a: sorting algorithm (S/B/I/M/Q)");
            System.out.println("t: list type (n/c)");
            System.out.println("c: piece color (b/w)");
            System.out.println("r: number of pieces (1-16)");
            System.out.println("s: step delay in ms (100-1000)");
            return;
        }

        char algorithm = args[0].toUpperCase().charAt(0);
        char listType = args[1].toLowerCase().charAt(0);
        char color = args[2].toLowerCase().charAt(0);
        int numPieces;
        int stepDelay;

        try {
            numPieces = Integer.parseInt(args[3]);
            stepDelay = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            System.out.println("Error: Formato de número no válido para piezas");
            return;
        }


        // Validación de parámetros del input
        if (!isValidAlgorithm(algorithm) || !isValidListType(listType) || !isValidColor(color) ||
                !isValidNumPieces(numPieces) || !isValidStepDelay(stepDelay)) {
            System.out.println("Parámetros inválidos. Ingresa los parámetros de nuevo.");
            return;
        }

        // Set console color based on piece color
        setConsoleColor(color);

        chessBasedBoard(listType, color, basedBoard);
        //displayBoard(basedBoard);
        // Inicializa el tablero
        initializeBoard(listType, color, numPieces);

        // Tablero inicial
        displayBoard(board);

        // Ejecutar al algoritmo de ordenamiento
        long startTime = System.currentTimeMillis();
        sortBoard(algorithm, stepDelay);
        long endTime = System.currentTimeMillis();

        // Disposición final del tablero
        displayBoard(board);

        // Impresión de la ejecución
        printSummary(algorithm, listType, color, numPieces, stepDelay, endTime - startTime);

        // Reiniciar consola
        System.out.print(ANSI_RESET);
    }


    private static void initializeBoard(char listType, char color, int numPieces) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = null;
            }
        }

        for (int i = 0; i < numPieces; i++) {
            ChessPiece piece = ChessPiece.createPiece(listType, i, color);
            int row, col;
            do {
                row = random.nextInt(BOARD_SIZE);
                col = random.nextInt(BOARD_SIZE);
            } while (board[row][col] != null);
            board[row][col] = piece;
        }
    }


    private static void chessBasedBoard(char listType, char color, ChessPiece[][] board) {

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = null;
            }
        }

        board[0][0] = ChessPiece.createPiece(listType, 2, color);
        board[0][1] = ChessPiece.createPiece(listType, 6, color);
        board[0][2] = ChessPiece.createPiece(listType, 4, color);
        board[0][3] = ChessPiece.createPiece(listType, 0, color);
        board[0][4] = ChessPiece.createPiece(listType, 1, color);
        board[0][5] = ChessPiece.createPiece(listType, 5, color);
        board[0][6] = ChessPiece.createPiece(listType, 7, color);
        board[0][7] = ChessPiece.createPiece(listType, 3, color);

        board[1][0] = ChessPiece.createPiece(listType, 8, color);
        board[1][1] = ChessPiece.createPiece(listType, 9, color);
        board[1][2] = ChessPiece.createPiece(listType, 10, color);
        board[1][3] = ChessPiece.createPiece(listType, 11, color);
        board[1][4] = ChessPiece.createPiece(listType, 12, color);
        board[1][5] = ChessPiece.createPiece(listType, 13, color);
        board[1][6] = ChessPiece.createPiece(listType, 14, color);
        board[1][7] = ChessPiece.createPiece(listType, 15, color);

   }

    public static void displayBoard(ChessPiece[][] board) {
        System.out.println(consoleColor + "  +----+----+----+----+----+----+----+----+");
        for (int i = BOARD_SIZE - 1; i >= 0; i--) {
            System.out.print(consoleColor + (i + 1) + " |");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != null) {
                    System.out.printf("%4s", board[i][j]);
                } else {
                    System.out.print("    ");
                }
                System.out.print("|");
            }
            System.out.println("\n" + consoleColor + "  +----+----+----+----+----+----+----+----+");
        }
        System.out.println(consoleColor + "    A    B    C    D    E    F    G    H");
    }

    private static void sortBoard(char algorithm, int stepDelay) {
        SortingAlgorithm sortingAlgorithm;
        switch (algorithm) {
            case 'B':
                sortingAlgorithm = new BubbleSort();
                break;
            case 'S':
                sortingAlgorithm = new SelectionSort();
                break;
            case 'I':
                sortingAlgorithm = new InsertionSort();
                break;
            default:
                System.out.println("Algoritmo de ordenamiento no implementado.");
                return;
        }
        sortingAlgorithm.sort(board, stepDelay, basedBoard);
    }

    private static void setConsoleColor(char color) {
        if (color == 'b') {
            consoleColor = ANSI_WHITE + ANSI_BLACK_BACKGROUND;
        } else {
            consoleColor = ANSI_BLACK + ANSI_WHITE_BACKGROUND;
        }
        System.out.print(consoleColor);
    }

    private static void printSummary(char algorithm, char listType, char color, int numPieces, int stepDelay, long executionTime) {
        System.out.println(consoleColor + "\nResumen de la ejecución:");
        System.out.println("Algoritmo: " + getAlgorithmName(algorithm));
        System.out.println("Tipo de lista: " + (listType == 'n' ? "Numérica" : "Caracteres"));
        System.out.println("Color de las piezas: " + (color == 'b' ? "Negras" : "Blancas"));
        System.out.println("Número de piezas: " + numPieces);
        System.out.println("Retraso entre pasos: " + stepDelay + " ms");
        System.out.println("Tiempo de ejecución: " + executionTime + " ms");
    }

    private static String getAlgorithmName(char algorithm) {
        switch (algorithm) {
            case 'B': return "Bubble Sort";
            case 'S': return "Selection Sort";
            case 'I': return "Insertion Sort";
            default: return "Desconocido";
        }
    }

    // Validation methods
    private static boolean isValidAlgorithm(char algorithm) {
        return "SBIMQ".indexOf(algorithm) != -1;
    }

    private static boolean isValidListType(char listType) {
        return listType == 'n' || listType == 'c';
    }

    private static boolean isValidColor(char color) {
        return color == 'b' || color == 'w';
    }

    private static boolean isValidNumPieces(int numPieces) {
        return numPieces >= 1 && numPieces <= 16;
    }

    private static boolean isValidStepDelay(int stepDelay) {
        return stepDelay >= 100 && stepDelay <= 1000;
    }
}