import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Don't touch it, it's already in use!        
interface GameEngine {

    void place(char entity, int x, int y);

    void move(int fromX, int fromY, int toX, int toY);

    void undo();

    void save();

    void load();

    void reDraw();
}

// this class is finished for now, no changes to board logic please
class Board {
    private static final char EMPTY = ' ';
    private char[][] squares;

    Board(int width, int height) {
        squares = new char[height][width];
        Arrays.stream(squares).forEach(row -> Arrays.fill(row, EMPTY));
    }

    public void place(char gamePiece, int col, int row) {
        squares[row - 1][col - 1] = gamePiece;
    }

    public void move(int fromCol, int fromRow, int toCol, int toRow) {
        char gamePiece = squares[fromRow - 1][fromCol - 1];
        squares[fromRow - 1][fromCol - 1] = EMPTY;
        squares[toRow - 1][toCol - 1] = gamePiece;
    }

    @Override
    public String toString() {
        return IntStream
                .iterate(squares.length - 1, i -> i >= 0, i -> i - 1)
                .mapToObj(i -> squares[i])
                .map(Arrays::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public Snapshot getSnapshot() {
        return new Snapshot(Arrays.stream(squares)
                .map(char[]::clone)
                .toArray(char[][]::new));
    }

    public void setStateFrom(Snapshot board) {
//        this.squares = board.board1;
        for (int i = 0; i < board.board1.length; i++) {
            for (int j = 0; j < board.board1[i].length; j++) {
                this.squares[i][j] = board.board1[i][j];
            }
//            this.board = board.clone();
        }
    }

    // It's for saving board state. If you're going to change it,
    // don't forget to update the methods above
    static class Snapshot {
        private final char[][] board1;

        private Snapshot(char[][] board) {
            this.board1 = new char[board.length][board[0].length];
//            System.arraycopy(this.array, 0, array, 0, array.length);
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    this.board1[i][j] = board[i][j];
                }
//            this.board = board.clone();
            }
        }
    }
}

// Finish this class implementation. You can modify it
// as you need but don't break anything!
class BoardGameEngine implements GameEngine {
    private final Board board;

    BoardGameEngine(int boardWidth, int boardHeight) {
        board = new Board(boardWidth, boardHeight);
        history.push(board.getSnapshot());
    }

    @Override
    public void place(char gamePiece, int col, int row) {
        board.place(gamePiece, col, row);
        history.push(board.getSnapshot());
    }

    @Override
    public void move(int fromCol, int fromRow, int toCol, int toRow) {
        board.move(fromCol, fromRow, toCol, toRow);
        history.push(board.getSnapshot());
        ;
    }

    Board.Snapshot snapshot;
    private final Deque<Board.Snapshot> history = new ArrayDeque<>();

    @Override
    public void undo() {
        // TODO
        if (history.size()>1) {
            history.pop();
            if (!history.isEmpty()) {
                board.setStateFrom(history.peek());
            }
        }

    }

    @Override
    public void save() {
        // TODO
        snapshot = board.getSnapshot();
//        history.push(board.getSnapshot());

    }

    @Override
    public void load() {
        // TODO
        if (snapshot != null) {
            board.setStateFrom(snapshot);
            history.clear();
            history.push(board.getSnapshot());
        }
    }

    @Override
    public void reDraw() {
        System.out.println(board);
    }
}

//class Main {
//    public static void main(String[] args) {
////        Board board = new Board(2,5);
//        BoardGameEngine boardGameEngine = new BoardGameEngine(2, 2);
//
//        boardGameEngine.load();
//        boardGameEngine.reDraw();
//
//        boardGameEngine.place('G', 2, 2);
//        boardGameEngine.place('A', 1, 1);
//        boardGameEngine.reDraw();
//
//        System.out.println();
//
//        boardGameEngine.load();
//        boardGameEngine.reDraw();
//
//        System.out.println();
//
//        boardGameEngine.place('H', 2, 2);
//        boardGameEngine.place('J', 1, 1);
//        boardGameEngine.save();
//
//        boardGameEngine.load();
//        boardGameEngine.reDraw();
//
//        System.out.println();
//
//        boardGameEngine.place('r', 2, 2);
//        boardGameEngine.reDraw();
//        System.out.println();
//        boardGameEngine.place('v', 1, 1);
//        boardGameEngine.reDraw();
//        System.out.println();
//        boardGameEngine.move(2, 2, 1, 1);
//        boardGameEngine.reDraw();
//        System.out.println();
//
//        boardGameEngine.undo();
//        boardGameEngine.reDraw();
//
//        System.out.println();
//        boardGameEngine.undo();
//        boardGameEngine.reDraw();
//        System.out.println();
//        boardGameEngine.undo();
//        boardGameEngine.reDraw();
//        System.out.println();
//        boardGameEngine.undo();
//        boardGameEngine.reDraw();
//        System.out.println();
//        boardGameEngine.load();
//        boardGameEngine.reDraw();
//        System.out.println();
//        boardGameEngine.undo();
//        boardGameEngine.reDraw();
//        System.out.println();
//        boardGameEngine.undo();
//        boardGameEngine.reDraw();
//        System.out.println();
//        boardGameEngine.undo();
//        boardGameEngine.reDraw();
//        System.out.println();
//        boardGameEngine.place('T',2,2);
//        boardGameEngine.reDraw();
//        boardGameEngine.load();
//        boardGameEngine.reDraw();
//        boardGameEngine.load();
//        boardGameEngine.reDraw();
//        boardGameEngine.place('M',2,2);
//        boardGameEngine.save();
//        boardGameEngine.reDraw();
//        boardGameEngine.load();
//        boardGameEngine.reDraw();
//        boardGameEngine.place('T',2,2);
//        boardGameEngine.reDraw();
//        boardGameEngine.undo();
//        boardGameEngine.reDraw();
//        boardGameEngine.undo();
//        boardGameEngine.reDraw();
//        boardGameEngine.undo();
//        boardGameEngine.place('A',2,2);
//        boardGameEngine.reDraw();
//        boardGameEngine.load();
//        boardGameEngine.reDraw();
//    }
//}