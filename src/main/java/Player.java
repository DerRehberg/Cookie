import javax.swing.*;
import java.awt.*;

public class Player {

    Rectangle Collider;
    Image Texture;
    boolean Gravused = true;
    boolean Jump = false;
    int Jumped = 0;
    byte Direction = 0;

    public Player()
    {
        try {
            Texture = new ImageIcon("Textures/Geezer-walk.gif").getImage();
            Collider = new Rectangle(50,50,50,50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RenderImage(Graphics g)
    {
        g.drawImage(Texture,(int)Collider.getX(),(int)Collider.getY(),(int)Collider.getWidth(),(int)Collider.getHeight(),null);
    }

    public void Mathe()
    {
        if(Gravused)
        {
            Collider.y += Stuff.Grav;
        }

        if(Direction == 1)
        {
            Collider.x += 1;
        }else if(Direction == 2)
        {
            Collider.x -= 1;
        }
        if(Jump)
        {
            Collider.y -= Stuff.Grav;
            Jumped += Stuff.Grav*1;
            if(Jumped == 36)
            {
                Jump = false;
                Jumped = 0;
                Gravused = true;
            }
        }
    }

}
