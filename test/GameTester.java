package test;

import model.TicTacToeGame;

public class GameTester {

    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();
        System.out.println(game);

        game.play(0);
        game.changeTurns();
        System.out.println(game);


        game.play(1);
        game.changeTurns();
        System.out.println(game);

        game.play(3);
        game.changeTurns();
        System.out.println(game);

        game.play(4);
        game.changeTurns();
        System.out.println(game);

        game.play(8);
        game.changeTurns();
        System.out.println(game);

        game.play(7);
        game.changeTurns();
        System.out.println(game);
    }

}
