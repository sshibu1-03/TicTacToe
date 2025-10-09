package controller;

import javax.swing.JFrame;

import model.TicTacToeGame;
import model.GameHistoryManager;
import view.AppWindow;

public class App {

    public static AppWindow win = new AppWindow();
    public static TicTacToeGame game = new TicTacToeGame();
    public static GameHistoryManager history = new GameHistoryManager();


    public static void main(String[] args) {
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.init();
        win.setLocation(300, 200);

        win.pack();
        win.setVisible(true);
        
    }
    
}
