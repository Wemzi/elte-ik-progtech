package game.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

public class ResourceLoader {
    public static BufferedImage blue;
    public static BufferedImage darkness;
    public static BufferedImage[] stever;
    public static BufferedImage[]steveu;
    public static BufferedImage[]steved;
    public static BufferedImage[] stevel;
    public static BufferedImage drake;
    public static BufferedImage end;
    public static BufferedImage start;
    public static BufferedImage brick;
    public static BufferedImage edgelrd;
    public static BufferedImage edgeurl;
    public static BufferedImage edgeurd;
    public static BufferedImage edgelud;
    public static BufferedImage edgeld;
    public static BufferedImage edgeul;
    public static BufferedImage edgelr;
    public static BufferedImage edgeur;
    public static BufferedImage edgerd;
    public static BufferedImage edgeud;
    public static BufferedImage edged;
    public static BufferedImage edgeu;
    public static BufferedImage edgel;
    public static BufferedImage edger;
    public static BufferedImage grass;
    public static BufferedImage bg;
    public static BufferedImage mapbuilder;
    public static BufferedImage mymaps;
    public static BufferedImage playoffline;
    public static BufferedImage playonline;
    public static BufferedImage toplist;
    public static BufferedImage exit;



    public static void initResources() throws IOException
    {
        bg = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("bg.png"));
        mapbuilder = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("mapbuilderbutton.png"));
        mymaps = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("mymapsbutton.png"));
        playoffline = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("playofflinebutton.png"));
        playonline = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("playonlinebutton.png"));
        toplist = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("toplistbutton.png"));
        exit = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("exitgamebutton.png"));
        blue = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("blue.png"));
        darkness = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("darkness.png"))  ;
        stever = new BufferedImage[]{ImageIO.read(ResourceLoader.class.getClassLoader().getResource("boy_right_1.png")), ImageIO.read(ResourceLoader.class.getClassLoader().getResource("boy_right_2.png"))};
        steveu = new BufferedImage[]{ImageIO.read(ResourceLoader.class.getClassLoader().getResource("boy_up_1.png")), ImageIO.read(ResourceLoader.class.getClassLoader().getResource("boy_up_2.png"))};
        steved = new BufferedImage[]{ImageIO.read(ResourceLoader.class.getClassLoader().getResource("boy_down_1.png")), ImageIO.read(ResourceLoader.class.getClassLoader().getResource("boy_down_2.png"))};
        stevel = new BufferedImage[]{ImageIO.read(ResourceLoader.class.getClassLoader().getResource("boy_left_1.png")), ImageIO.read(ResourceLoader.class.getClassLoader().getResource("boy_left_2.png"))};
        drake = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("dragon.png"));
        start = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("start.png"));
        end = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("end.png"));
        brick = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("brick.png"));
        edgelrd = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgelrd.png"));
        edgeurl = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeurl.png"));
        edgeurd = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeurd.png"));
        edgelud = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgelud.png"));
        edgeld = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeld.png"));
        edgeul = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeul.png"));
        edgelr = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgelr.png"));
        edgeur = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeur.png"));
        edgerd = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgerd.png"));
        edgeud = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeud.png"));
        edged = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edged.png"));
        edgeu = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeu.png"));
        edgel = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgel.png"));
        edger = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edger.png"));
        grass = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("grass.png"));
    }
}
