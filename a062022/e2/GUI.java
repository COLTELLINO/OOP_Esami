package a06.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;

public class GUI extends JFrame {
    
    private final Map<JButton,Position> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        this.logic = new LogicImpl(size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        JButton go = new JButton("Go");
        main.add(BorderLayout.SOUTH, go);
        go.addActionListener(e -> { logic.hit();
            for (var entry : cells.entrySet()) {
                if (logic.getValue(entry.getValue()) == 0) {
                    entry.getKey().setText("");
                } else {
                entry.getKey().setText(String.valueOf(logic.getValue(entry.getValue())));
                }
            }
            if (logic.isOver()) {
                go.setEnabled(false);
            }
        });
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                Position pos = new Position(j, i);
                final JButton jb = new JButton(String.valueOf(logic.getValue(pos)));
                this.cells.put(jb,pos);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }    
}
