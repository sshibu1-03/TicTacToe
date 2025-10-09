package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GameState;
import model.TicTacToeGame;
import view.BoardButton;

public class ButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
       TicTacToeGame game = App.game;
       BoardButton button = (BoardButton)e.getSource();

       game.play(button.getPos());
       
       if (game.getWinner() != null) {
        game.setState(GameState.OVER);
        System.out.println("Game Over: " + game.getWinner());

        // Append to history
        String mode;
        switch (App.game.getStrategy()) {
            case VsHuman: mode = "Human vs. Human"; break;
            case VsComputer: mode = "Human vs. Random Computer"; break;
            case VsSmartComputer: mode = "Human vs. Smart Computer"; break;
            default: mode = "Unknown"; break;
        }
        String winnerLabel;
        if (game.getWinner() == model.Marking.U) {
            winnerLabel = "Draw";
        } else if (App.game.getStrategy() == model.PlayStrategy.VsHuman) {
            // Winner is X or O; label as "Player X" to avoid saying Computer
            winnerLabel = game.getWinner().name();
        } else {
            // In vs computer modes, map mark to Human/Computer label
            winnerLabel = (game.getWinner() == model.Marking.X) ? "Human" : "Computer";
        }
        App.history.appendRecord(mode, winnerLabel);
       } else{
        game.changeTurns();
       }


       App.win.updateWindow();
    }
    
}
