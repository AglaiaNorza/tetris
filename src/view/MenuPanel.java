package view;

import model.Tetromino;

import javax.swing.*;
import java.awt.*;

// TODO
public class MenuPanel extends JPanel {

    MenuPanel() {
        setSize(new Dimension(Tetromino.TILE_SIZE*10, Tetromino.TILE_SIZE*20));
        setBackground(Color.RED);
        add(new JButton(){
            {
                addActionListener( _ -> TetrisFrame.getInstance().gameStartClicked() );
            }
        });
    }
}
