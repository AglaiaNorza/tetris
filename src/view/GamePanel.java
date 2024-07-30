package view;

import model.Rotation;
import model.Tetromino;
import model.TileShape;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {

    private Tetromino tile;
    private int[][] board;
    private HashMap<TileShape, HashMap<Rotation, BufferedImage>> tetrominoMap;

    public GamePanel(Tetromino tile){
        setSize(new Dimension(Tetromino.TILE_SIZE*10, Tetromino.TILE_SIZE*20));
        setBackground(Color.BLUE);
        this.tile = tile;
        tetrominoMap = ImageUtil.getTetrominoMap();
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

        BufferedImage img = tetrominoMap.get(tile.getShape()).get(tile.getRotation());
        g.drawImage(img, tile.getX()*48, tile.getY()*48, null);
    }

    public void setTile(Tetromino tile) { this.tile = tile; }
}
