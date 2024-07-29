package view;

import model.Tetromino;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {

    private Tetromino tile;
    private int[][] board;

    public GamePanel(Tetromino tile){
        setSize(new Dimension(Tetromino.TILE_SIZE*10, Tetromino.TILE_SIZE*20));
        setBackground(Color.BLUE);
        this.tile = tile;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBoard(g);
        paintTile(g);
    }

    public void paintBoard(Graphics g) {

    }

    public void paintTile(Graphics g){
        Image img = null;
        try{
            img = ImageIO.read(new File("/Downloads/tetromino.png"));
        } catch (IOException e) { e.printStackTrace(); }

        g.drawImage(img, tile.getX(), tile.getY(), null);
    }

    public void setTile(Tetromino tile) { this.tile = tile; }
}
