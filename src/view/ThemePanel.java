package view;

import javax.swing.*;
import java.awt.*;

// TODO
public class ThemePanel extends JPanel{

    public ThemePanel() {
        setSize(TetrisFrame.FRAME_SIZE);
        setBackground(Color.BLUE);
        add(new JButton("menu"){
            {
                addActionListener( _ -> TetrisFrame.getInstance().switchScreen(TetrisFrame.Screen.MENU) );
            }
        });
        add(new JButton("gruvbox"){
            {
                addActionListener( _ -> {
                    ImageUtil.setTheme(Theme.GRUVBOX);
                    TetrisFrame.getInstance().onThemeChange();
                } );
            }
        });
        add(new JButton("tokyo night"){
            {
                addActionListener( _ -> {
                    ImageUtil.setTheme(Theme.TOKYO_NIGHT);
                    TetrisFrame.getInstance().onThemeChange();
                });
            }
        });
        add(new JButton("classic"){
            {
                addActionListener( _ -> {
                    ImageUtil.setTheme(Theme.CLASSIC);
                    TetrisFrame.getInstance().onThemeChange();
                } );
            }
        });
    }
}
