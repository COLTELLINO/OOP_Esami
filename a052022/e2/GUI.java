package a05.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton,Position> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        this.logic = new LogicImpl(size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);

                logic.hit(position);

                for (var entry : cells.entrySet()) {
                    entry.getKey().setText(logic.getValue(entry.getValue()));
                }

                logic.isOver();

                for (var entry : cells.entrySet()) {
                    entry.getKey().setText(logic.getValue(entry.getValue()));
                }
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                Position pos = new Position(j, i);
                final JButton jb = new JButton(logic.getValue(pos));
                jb.setEnabled(!logic.isDisabled(pos));
                this.cells.put(jb,pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }    
}
