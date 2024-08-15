package view;

import javax.swing.*;
import java.awt.*;

// TODO
public class PausePanel extends JPanel {

    public PausePanel() {
        setSize(TetrisFrame.FRAME_SIZE);
        setBackground(Color.GREEN);
        add(new JButton("play"){
            {
                addActionListener( _ -> TetrisFrame.getInstance().endPauseClicked() );
            }
        });
    }
}
