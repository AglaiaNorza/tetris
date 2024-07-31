package view;

import model.Rotation;
import model.Tetromino;
import model.TileShape;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class GamePanel extends JPanel {
    private Tetromino tile;
    private int[][] board;

    private HashMap<TileShape, HashMap<Rotation, BufferedImage>> tetrominoMap;
    private HashMap<Integer, BufferedImage> boardMap;

    // tetrominoes spawn at -2
    private final int boardShift = -76;

    public GamePanel(Tetromino tile, int[][] board){
        setSize(new Dimension(Tetromino.TILE_SIZE*10, Tetromino.TILE_SIZE*20));
        setBackground(Color.BLUE);
        this.tile = tile;
        tetrominoMap = ImageUtil.getTetrominoMap();
        this.board = board;
        boardMap = ImageUtil.getTileMap();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBoard(g);
        paintTile(g);
    }

    public void paintBoard(Graphics g) {
        //the first two rows are hidden (per the rules)
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                g.drawImage(boardMap.get(board[i][j]), j * Tetromino.TILE_SIZE, (i - 2) * Tetromino.TILE_SIZE, null);
            }
        }
    }

    public void paintTile(Graphics g){
        Image img = tetrominoMap.get(tile.getShape()).get(tile.getRotation()).getScaledInstance(38*tile.getSquareSize(),38*tile.getSquareSize(), Image.SCALE_SMOOTH);
        g.drawImage(img, tile.getX()*38, tile.getY()*38 + boardShift, null);
    }

    public void setTile(Tetromino tile) {
        this.tile = tile;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
}
