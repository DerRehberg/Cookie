import java.awt.*;
import java.util.ArrayList;

public class Stuff {

    public static int Grav = 2;
    public static ArrayList<Life> Controllable = new ArrayList<>();
    public static ArrayList<Rectangle> Objects = new ArrayList<>();

    public static void init()
    {
        Controllable.add(new Life("Owl","Owl.png",new Rectangle(50,50,50,50),true));
    }

}
