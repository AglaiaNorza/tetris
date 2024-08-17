package view;

import model.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MenuPanel extends JPanel {

    HashMap<String, BufferedImage> assets;

    MenuPanel() {
        setSize(TetrisFrame.FRAME_SIZE);
        setBackground(Color.RED);
        setLayout(new GridBagLayout());

        assets = ImageUtil.getThemeAssets();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.insets = new Insets(220,280,0,0);

        add(new JPanel(){
            {
                setOpaque(false);
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                add(new JButton(){
                    {
                        setIcon(new ImageIcon(assets.get("play")));
                        setBorder(BorderFactory.createEmptyBorder());
                        setContentAreaFilled(false);
                        addActionListener( _ -> TetrisFrame.getInstance().gameStartClicked() );
                    }
                });
                add(new JButton(){
                    {
                        setIcon(new ImageIcon(assets.get("games")));
                        setBorder(BorderFactory.createEmptyBorder());
                        setContentAreaFilled(false);
                        addActionListener( _ -> TetrisFrame.getInstance().switchScreen(TetrisFrame.Screen.LEADERBOARD));
                    }
                });

                add(new JButton(){
                    {
                        setIcon(new ImageIcon(assets.get("rules")));
                        setBorder(BorderFactory.createEmptyBorder());
                        setContentAreaFilled(false);
                        addActionListener( _ -> TetrisFrame.getInstance().switchScreen(TetrisFrame.Screen.RULES));
                    }
                });
            }

        }, gbc);

        gbc.gridy = 1;
        gbc.gridx = 2;
        gbc.insets = new Insets(0,110,0,0);

        add(new JButton(){
            {
                setIcon(new ImageIcon(assets.get("themes")));
                setBorder(BorderFactory.createEmptyBorder());
                setContentAreaFilled(false);
                addActionListener( _ -> TetrisFrame.getInstance().switchScreen(TetrisFrame.Screen.THEMES) );
            }
        }, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(assets.get("menupanel"), 0,0, null);
    }
}
