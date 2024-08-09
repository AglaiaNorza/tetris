package view;

import model.Rotation;
import model.Tetromino;
import model.TileShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

public class GamePanel extends JPanel {
    private Tetromino tile;
    private int[][] board;

    private AnimationPanel animationPanel;

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
        animationPanel = new AnimationPanel();
        add(animationPanel);
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
        BufferedImage img = tetrominoMap.get(tile.getShape()).get(tile.getRotation());
        g.drawImage(img, tile.getX()*38, tile.getY()*38 + boardShift, null);
    }

    public void setTile(Tetromino tile) {
        this.tile = tile;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public AnimationPanel getAnimationPanel() { return animationPanel; }

    public class AnimationPanel extends JPanel {
        private Timer animationTimer;
        private BufferedImage curFrame;
        private Optional<ArrayList<Integer>> rows = Optional.empty();
        Iterator<BufferedImage> animation = ImageUtil.getAnimationArray().iterator();

        public AnimationPanel() {
            setPreferredSize(new Dimension(Tetromino.TILE_SIZE * 10, Tetromino.TILE_SIZE * 20));
            setOpaque(false);

            animationTimer = new Timer(24, _ -> {
                curFrame = animation.next();
                repaint();
                if(!animation.hasNext()){
                    //resetting the animation
                    animation = ImageUtil.getAnimationArray().iterator();
                    setVisible(false);
                    animationTimer.stop();
                }
            });
        }

        public void rowCleared(ArrayList<Integer> r) {
            rows = Optional.of(r);
            setVisible(true);
            animationTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("repainting");

            if(rows.isPresent()){
                ArrayList<Integer> r = rows.get();
                for(int i = 0; i < r.size(); i++){
                    int j = r.getFirst() - i;
                    g.drawImage(curFrame, 0, (j - 2) * Tetromino.TILE_SIZE, null);
                }
            }

        }
    }

}


