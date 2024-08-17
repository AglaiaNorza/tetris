package view;

import model.Rotation;
import model.Tetromino;
import model.TileShape;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

public class BoardPanel extends JPanel {
    private Tetromino tile;
    private int[][] board;

    private AnimationPanel animationPanel;

    private HashMap<TileShape, HashMap<Rotation, BufferedImage>> tetrominoMap;
    private HashMap<Integer, BufferedImage> boardMap;

    // tetrominoes spawn at -2
    private final int boardShift = -76;

    public static final Dimension BOARD_SIZE = new Dimension(Tetromino.TILE_SIZE*10, Tetromino.TILE_SIZE*20);

    public BoardPanel(Tetromino tile, int[][] board) {
        setPreferredSize(BOARD_SIZE);
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

    public void paintTile(Graphics g) {
        BufferedImage img = tetrominoMap.get(tile.getShape()).get(tile.getRotation());
        g.drawImage(img, tile.getX()*Tetromino.TILE_SIZE, tile.getY()*Tetromino.TILE_SIZE + boardShift, null);

        // transparent preview of where the tile would drop
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
        Graphics2D g2 = (Graphics2D)g;
        g2.setComposite(ac);
        g2.drawImage(img, tile.getX()*Tetromino.TILE_SIZE,  tile.getDropPoint()*Tetromino.TILE_SIZE + boardShift + 4, null);
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
            setPreferredSize(BOARD_SIZE);
            setOpaque(false);

            animationTimer = new Timer(4, _ -> {
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

            if(rows.isPresent()){
                ArrayList<Integer> r = rows.get();
                for(int i = 0; i < r.size(); i++){
                    int j = r.getFirst() - i;
                    g.drawImage(curFrame, 0, (j - 2) * Tetromino.TILE_SIZE -6, null);
                }
            }

        }
    }

}


