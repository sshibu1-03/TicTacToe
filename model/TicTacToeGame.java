package model;

public class TicTacToeGame {

    private Marking[] board = new Marking[9];
    private Marking turn = Marking.X; // X starts first
    private int moves = 0;
    private Marking winner = null; // O or X or U (draw)
    private GameState state = GameState.INIT;
    private PlayStrategy strategy = PlayStrategy.VsHuman;

    public TicTacToeGame() {
        reset();
    }

    public void reset() {
        for (int i = 0; i < board.length; i++) {
            board[i] = Marking.U;

        }
        turn = Marking.X;
        moves = 0;
        winner = null;
    }

    public void play(int position) {
        if (strategy == PlayStrategy.VsHuman) {
            humanPlayer(position);
            setWinner();
        } else if (strategy == PlayStrategy.VsComputer) {

            humanPlayer(position);
            setWinner();
            if (getWinner() != null)
                return;
            changeTurns();
            computerPlayer();
            setWinner();

        
        } else if (strategy == PlayStrategy.VsSmartComputer) {
            humanPlayer(position);
            setWinner();
            if (getWinner() != null) return;
            changeTurns();
            int pos = smartPick();
            assert pos >= 0 : "invalid position from smartPick()";
            board[pos] = turn; // AI plays
            ++moves;
            setWinner();
        }
    }

    public Marking getWinner() {
        return winner;
    }

    private void computerPlayer() {
        int pos = computerPick();
        board[pos] = turn;
        ++moves;
    }

    private int computerPick() {
        int pos = -1;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == Marking.U) {
                pos = i;
                break;
            }
        }

         assert pos >=0 : " invalid position from computerPick()";
         return pos;
    }

    public void changeTurns() {
        turn = (turn == Marking.X) ? Marking.O : Marking.X;
    }

    public Marking getTurn() {
        return turn;
    }

    private void humanPlayer(int pos) {
        board[pos] = turn;
        ++moves;
    }

    public void setWinner() {
        for (int i = 0; i < 3; i++) {
            winner = checkCol(i);
            if (winner != null) {
                return;
            }
            winner = checkRow(i);
            if (winner != null) {
                return;
            }
        }
        winner = checkDiag1();
        if (winner != null)
            return;

        winner = checkDiag2();
        if (winner != null)
            return;

        if (moves == 9) {
            winner = Marking.U; // draw
            return;
        }
        winner = null;
    }

    private Marking checkRow(int n) {
        int r = n * 3;
        if (board[r] != Marking.U &&
                board[r] == board[r + 1] &&
                board[r] == board[r + 2]) {
            return board[r];
        } else {
            return null; // no winner
        }
    }

    private Marking checkCol(int n) {
        if (board[n] != Marking.U &&
                board[n] == board[n + 3] &&
                board[n] == board[n + 6]) {
            return board[n];
        } else {
            return null; // no winner
        }
    }

    private Marking checkDiag1() {
        if (board[0] != Marking.U && board[0] == board[4]
                && board[0] == board[8]) {
            return board[0];
        } else {
            return null; // no winner
        }

    }

    private Marking checkDiag2() {
        if (board[2] != Marking.U && board[2] == board[4]
                && board[2] == board[6]) {
            return board[2];
        } else {
            return null;
        }
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public PlayStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(PlayStrategy strategy) {
        this.strategy = strategy;
    }

    public Marking[] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        var r1 = String.format("%s %s %s\n", board[0], board[1], board[2]);
        var r2 = String.format("%s %s %s\n", board[3], board[4], board[5]);
        var r3 = String.format("%s %s %s\n", board[6], board[7], board[8]);
        var r4 = String.format("Winner: %s (moves: %d)\n", winner, moves);
        return r1 + r2 + r3 + r4;
    }
    
    //  Smart AI helpers 
    private int smartPick() {
        // AI is the opposite of current human turn after changeTurns() in controller, but in this class
        // we call computerPlayer within play() without changing turn yet, so AI mark is O when human is X.
        Marking ai = Marking.O;
        Marking human = Marking.X;

        // 1) Try to win in one move
        Integer winPos = findWinningMove(ai);
        if (winPos != null) return winPos;

        // 2) Block human's immediate win
        Integer blockPos = findWinningMove(human);
        if (blockPos != null) return blockPos;

        // 3) Take center
        if (board[4] == Marking.U) return 4;

        // 4) Take a corner
        int[] corners = {0,2,6,8};
        for (int c : corners) if (board[c] == Marking.U) return c;

        // 5) Take an edge
        int[] edges = {1,3,5,7};
        for (int e : edges) if (board[e] == Marking.U) return e;

        // fallback
        for (int i = 0; i < board.length; i++) if (board[i] == Marking.U) return i;
        return -1;
    }

    /** returns index if 'mark' can win in one move, otherwise null */
    private Integer findWinningMove(Marking mark) {
        for (int i = 0; i < 9; i++) {
            if (board[i] == Marking.U) {
                board[i] = mark;
                boolean wins = isWinner(mark);
                board[i] = Marking.U;
                if (wins) return i;
            }
        }
        return null;
    }

    private boolean isWinner(Marking m) {
        return
            (board[0]==m && board[1]==m && board[2]==m) ||
            (board[3]==m && board[4]==m && board[5]==m) ||
            (board[6]==m && board[7]==m && board[8]==m) ||
            (board[0]==m && board[3]==m && board[6]==m) ||
            (board[1]==m && board[4]==m && board[7]==m) ||
            (board[2]==m && board[5]==m && board[8]==m) ||
            (board[0]==m && board[4]==m && board[8]==m) ||
            (board[2]==m && board[4]==m && board[6]==m);
    }
    
    
}

