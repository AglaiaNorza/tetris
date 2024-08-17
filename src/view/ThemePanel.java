package view;

import javax.swing.*;
import java.awt.*;

public class ThemePanel extends JPanel{

    public ThemePanel() {
        setSize(TetrisFrame.FRAME_SIZE);
        setBackground(Color.BLUE);
        add(new JButton("menu"){
            {
                addActionListener( _ -> TetrisFrame.getInstance().switchScreen(TetrisFrame.Screen.MENU) );
            }
        });
    }
}
