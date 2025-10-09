package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

import model.Marking;

public class BoardButton  extends JButton{
    
    private int pos; // index/pos in the game board

    public BoardButton(int pos) {
        this.pos = pos;
        setFont(new Font ("Courier New", Font.BOLD, 84));
        setForeground(Color.BLUE);
        setMark(Marking.U);
    }

    public int getPos() {
        return pos;
    }

    public void setMark(Marking mark) {
        String label = mark.name();
        if (mark == Marking.U) label = ".";
        setText(label);
        
    }
}
