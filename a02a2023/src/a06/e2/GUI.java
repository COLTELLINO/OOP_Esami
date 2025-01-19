package a06.e2;

import javax.swing.*;
import java.util.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logics;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        logics = new LogicImpl(size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        logics.drawAsterisks();
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            logics.hit(cells.get(jb));
            for (var entry : cells.entrySet()) {
                entry.getKey().setText(logics.getValue(entry.getValue()));               
            }
            if (logics.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton("");
                this.cells.put(jb, new Position(pos.getX(), pos.getY()));
                jb.addActionListener(al);
                panel.add(jb);
                for (var entry : cells.entrySet()) {
                    entry.getKey().setText(logics.getValue(entry.getValue()));
                }
            }
        }
        this.setVisible(true);
    }
    
}
