package view;

import javax.swing.*;
import java.awt.*;

public class LeaderboardPanel extends  JPanel{

    public LeaderboardPanel() {
        setSize(TetrisFrame.FRAME_SIZE);
        setBackground(Color.GREEN);
        add(new JButton("menu"){
            {
                addActionListener( _ -> TetrisFrame.getInstance().switchScreen(TetrisFrame.Screen.MENU) );
            }
        });
    }
}
