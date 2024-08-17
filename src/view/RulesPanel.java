package view;

import javax.swing.*;
import java.awt.*;

public class RulesPanel extends JPanel {

    public RulesPanel() {
        setSize(TetrisFrame.FRAME_SIZE);
        setBackground(Color.GREEN);
        add(new JButton("menu"){
            {
                addActionListener( _ -> TetrisFrame.getInstance().switchScreen(TetrisFrame.Screen.MENU) );
            }
        });
    }

}
