package view;

import model.Rotation;
import model.TileShape;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ImageUtil {

    private static Theme theme = Theme.TOKYO_NIGHT;

    /**
     * Returns a HashMap containing all the images necessary to display tetrominoes
     * (and their rotated versions). Considers the current theme.
     * (why did i do this through a stream and not for loops? no idea. i wanted to have fun. i like streams)
     * @return a HashMap of the tetrominoes' representation
     */
    public static HashMap<TileShape, HashMap<Rotation, BufferedImage>> getTetrominoMap() {
        return Arrays.stream(TileShape.values())
                .collect(Collectors.toMap(
                        t -> t, // TileShape
                        t -> Arrays.stream(Rotation.values())
                                // second HashMap
                                .collect(Collectors.toMap(
                                        r -> r, // Rotation
                                        r -> getImage(String.format("../resources/tetrominoes/%s/%s_%s.png",
                                                theme.toString().toLowerCase(), t.toString(), r.toString())), // image
                                        (v1, _) -> v1, // merge function
                                        HashMap::new // mapFactory
                                )),
                        (v1, _) -> v1,  //merge function
                        HashMap::new //mapFactory
                ));
    }

    public static HashMap<Integer, BufferedImage> getTileMap() {
        HashMap<Integer, BufferedImage> tileMap = new HashMap<>();
        for(int i = 1; i<8; i++){
            tileMap.put(i, getImage("../resources/blocks/" + theme.toString().toLowerCase() +"/"+ i +".png"));
        }
        return tileMap;
    }

    public static ArrayList<BufferedImage> getAnimationArray() {
        ArrayList<BufferedImage> animationArray = new ArrayList<>();
        for(int i = 1; i<16; i++){
            animationArray.add(getImage("../resources/animation/"+ i +".png"));
        }
        return animationArray;
    }

    /**
     * Given a relative path to an image it returns the BufferedImage of it
     * @param path the path to an image
     * @return a single BufferedImage
     */
    public static BufferedImage getImage(String path) {
        BufferedImage image = null;
        try{
            image = ImageIO.read(ImageUtil.class.getResource(path));
        } catch(IOException e){ e.printStackTrace(); }
        return image;
    }

    /**
     * Given a relative path to an image it returns the BufferedImage of it
     * @param path the path to an image
     * @return a single BufferedImage
     */
    public static HashMap<String, BufferedImage> getThemeAssets() {

        String[] titles = new String[] { "themes", "games", "menupanel", "play", "rules" };

        return Arrays.stream(titles)
                .collect(Collectors.toMap(
                        t -> t, // "title"
                        t -> getImage(String.format("../resources/panels/%s/%s.png",
                                theme.toString().toLowerCase(), t)), //image
                        (v1, _) -> v1,  //merge function
                        HashMap::new //mapFactory
                ));
    }



    public static void setTheme(Theme newTheme) { theme = newTheme; }
}
