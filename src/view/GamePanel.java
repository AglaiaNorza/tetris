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

    private boolean updateBoard;

    public GamePanel(){
        setSize(1280, 600);
        updateBoard = true;
        setBackground(Color.BLUE);
        System.out.println("creating panel");
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //if(updateBoard){
        //    paintBoard(g);
        //}
        //paintTile(g);
    }

    public void paintBoard(Graphics g) {


        updateBoard = false;
    }

    public void paintTile(Graphics g){
        Image img = null;
        try{
            img = ImageIO.read(new File("~/Downloads/tetromino.png"));
        } catch (IOException e) { e.printStackTrace(); }

        g.drawImage(img, tile.getX(), tile.getY(), null);
    }

    public void boardUpdate() {
        updateBoard = true;
        repaint();
    }
}
