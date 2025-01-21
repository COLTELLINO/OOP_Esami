package a06.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Position> cells = new HashMap<>();
    private final Logic logic;

    
    public GUI(int width) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*width);
        
        JPanel panel = new JPanel(new GridLayout(width,width));
        this.getContentPane().add(panel);
        this.logic = new LogicImpl(width);
        
        ActionListener al = e -> {
            if (logic.isOver()) {
               
            } else {
            var jb = (JButton)e.getSource();
        	logic.hit(cells.get(jb));
            for (var entry : cells.entrySet()) {
                if (logic.getValue(entry.getValue()) == 0) {
                    entry.getKey().setText("");
                } else {
                    entry.getKey().setText(String.valueOf(logic.getValue(entry.getValue())));
                }
            }
            }
        };
                
        for (int i=0; i<width; i++){
            for (int j=0; j<width; j++){
            	var pos = new Position(i, j);
                final JButton jb = new JButton("");
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
