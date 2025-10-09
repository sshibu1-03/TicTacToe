package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.PlayStrategy;
import view.AppWindow;

public class StrategyButtonListener  implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
     var actionCommand = e.getActionCommand();
     switch (actionCommand) {
        case AppWindow.VsHumanAction:
            App.game.setStrategy(PlayStrategy.VsHuman);
            break;
        case AppWindow.VsComputerAction:
            App.game.setStrategy(PlayStrategy.VsComputer);
            break;
        case AppWindow.VsSmartComputerAction:
            App.game.setStrategy(PlayStrategy.VsSmartComputer);
            break;
        }
    }
    
}
