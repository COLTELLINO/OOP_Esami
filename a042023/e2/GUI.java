package a06.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Position> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int width) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*width);
        logic = new LogicImpl(width);

        JPanel panel = new JPanel(new GridLayout(width,width));
        this.getContentPane().add(panel);
        
        logic.start();

        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            logic.hit(cells.get(jb));
            jb.setText(logic.getValue(cells.get(jb)));
            if (logic.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i=0; i<width; i++){
            for (int j=0; j<width; j++){
            	var pos = new Position(i, j);
                final JButton jb = new JButton(logic.getValue(pos));
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
