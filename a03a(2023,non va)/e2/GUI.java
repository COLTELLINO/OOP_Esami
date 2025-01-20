package a06.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logics;

    public GUI(int width, int height) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*height);

        JPanel panel = new JPanel(new GridLayout(width,height));
        this.getContentPane().add(panel);

        logics = new LogicImpl(width, height);

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

        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                var pos = new Position(j,i);
                final JButton jb = new JButton(logics.getValue(pos));
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

}