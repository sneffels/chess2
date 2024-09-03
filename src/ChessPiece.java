public class ChessPiece {
    private char type;
    private char color;
    private String identifier;

    public ChessPiece(char type, char color, String identifier) {
        this.type = type;
        this.color = color;
        this.identifier = identifier;
    }

    public char getType() {
        return type;
    }

    public char getColor() {
        return color;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return identifier + type;
    }

    public static ChessPiece createPiece(char listType, int index, char color) {
        char type;
        String identifier;

        if (listType == 'n') {
            identifier = String.valueOf(index + 1);
        } else if (listType == 'c') {
            identifier = String.valueOf((char)('a' + index));
        } else {
            throw new IllegalArgumentException("Tipo de lista inválido: " + listType);
        }

        switch (index) {
            case 0: type = 'K'; break; // Rey
            case 1: type = 'Q'; break; // Reina
            case 2:
            case 3: type = 'T'; break; // Torres
            case 4:
            case 5: type = 'B'; break; // Alfiles
            case 6:
            case 7: type = 'H'; break; // Caballos
            default:
                if (index >= 8 && index < 16) {
                    type = 'P'; // Peones
                } else {
                    throw new IllegalArgumentException("Índice de piezas inválido: " + index);
                }
        }

        return new ChessPiece(type, color, identifier);
    }
}