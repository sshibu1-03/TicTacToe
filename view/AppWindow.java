package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import controller.App;
import controller.ButtonListener;
import controller.NewGameButtonListener;
import controller.StrategyButtonListener;
import model.Marking;
import model.PlayStrategy;
import model.TicTacToeGame;
import view.AppWindow;

public class AppWindow extends JFrame {

    public static final String VsHumanAction = "Vs. Human";
    public static final String VsComputerAction = "Vs. Computer";
    public static final String VsSmartComputerAction = "Vs. Smart Computer";

    private AppCanvas canvas = new AppCanvas();
    private BoardButton[] markingButtons = new BoardButton[9];
    private JButton newGameButton = new JButton("New Game");
    private JRadioButton vsHumanButton;
    private JRadioButton vsComputerButton;
    private JRadioButton vsSmartComputerButton;
    private JButton viewHistoryButton = new JButton("View History");

    public void init() {
        var cp = getContentPane();
        cp.add(canvas, BorderLayout.NORTH);

        ButtonListener buttonListener = new ButtonListener();
        for (int i = 0; i < markingButtons.length; i++) {
            markingButtons[i] = new BoardButton(i);
            markingButtons[i].addActionListener(buttonListener);
        }

        JPanel gameBoardPanel = new JPanel();
        gameBoardPanel.setLayout(new GridLayout(3, 3));
        for (var cell : markingButtons) {
            gameBoardPanel.add(cell);
        }
        cp.add(gameBoardPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2, 1));
        cp.add(southPanel, BorderLayout.SOUTH);

        JPanel radioPanel = new JPanel();
        radioPanel.setBorder(new TitledBorder("Play Strategy"));
        vsHumanButton = new JRadioButton(VsHumanAction, App.game.getStrategy() == PlayStrategy.VsHuman);
        vsComputerButton = new JRadioButton(VsComputerAction, App.game.getStrategy() == PlayStrategy.VsComputer);
        vsSmartComputerButton = new JRadioButton(VsSmartComputerAction, App.game.getStrategy() == PlayStrategy.VsSmartComputer);
        radioPanel.add(vsHumanButton);
        radioPanel.add(vsComputerButton);
        radioPanel.add(vsSmartComputerButton);
        StrategyButtonListener strategyListener = new StrategyButtonListener();
        vsHumanButton.addActionListener(strategyListener);
        vsComputerButton.addActionListener(strategyListener);
        vsSmartComputerButton.addActionListener(strategyListener);
        ButtonGroup strategyGroup = new ButtonGroup();
        strategyGroup.add(vsHumanButton);
        strategyGroup.add(vsComputerButton);
        strategyGroup.add(vsSmartComputerButton);
        southPanel.add(radioPanel);

        JPanel actionPanel = new JPanel();
        actionPanel.setBorder(new TitledBorder("Actions"));
        actionPanel.add(newGameButton);
        actionPanel.add(viewHistoryButton);
        viewHistoryButton.addActionListener(e -> showHistoryDialog());
        newGameButton.addActionListener(new NewGameButtonListener());
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        actionPanel.add(exitButton);
        southPanel.add(actionPanel);

        updateWindow();

    }

    public void updateWindow() {
        TicTacToeGame game = App.game;
        Marking[] board = game.getBoard();
        for (int i = 0; i < board.length; i++) {
            markingButtons[i].setMark(board[i]);
        }

        switch (game.getState()) {
            case INIT:
            case OVER:
                for (var b : markingButtons) {
                    b.setEnabled(false);
                }
                newGameButton.setEnabled(true);
                vsHumanButton.setEnabled(true);
                vsComputerButton.setEnabled(true);
                vsSmartComputerButton.setEnabled(true);
                break;
            case PLAYING:
                newGameButton.setEnabled(false);
                vsHumanButton.setEnabled(false);
                vsComputerButton.setEnabled(false);
                vsSmartComputerButton.setEnabled(false);
                for (int i = 0; i < board.length; i++) {
                    markingButtons[i].setEnabled(board[i] == Marking.U);
                }
                break;

        }

        canvas.repaint();
    }
    
    private void showHistoryDialog() {
        java.util.List<String> lines = controller.App.history.readAllDescending();
        String message;
        if (lines.isEmpty()) {
            message = "No game history found.";
        } else {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line).append("\n");
            }
            message = sb.toString();
        }
        javax.swing.JTextArea area = new javax.swing.JTextArea(message, 15, 40);
        area.setEditable(false);
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(area);
        javax.swing.JOptionPane.showMessageDialog(this, scroll, "Play History", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
} 
