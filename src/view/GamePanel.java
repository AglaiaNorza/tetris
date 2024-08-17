package view;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {



    public GamePanel(BoardPanel boardPanel) {
        setPreferredSize(TetrisFrame.FRAME_SIZE);
        setLayout(new FlowLayout());
        setBackground(Color.BLACK);
        add(boardPanel);
    }
}
