import javax.swing.*;
import java.awt.*;

public class Life {

    String Name;
    Rectangle Collider;
    Image Texture;
    byte Direction = 0;
    boolean Gravused = true;
    boolean Collides = false;

    public Life(String name, String TextureName, Rectangle rect,Boolean collides)
    {
        try {
            Texture = new ImageIcon("Textures/"+TextureName).getImage();
            Collider = rect;
            Name = name;
	    Collides = collides;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Mathe()
    {
            if(Direction == 1)
            {
                Collider.x += 2;
            }
            if(Gravused)
            {
                Collider.y += Stuff.Grav;
            }
            if(Collider.intersects(Cookie.Ground))
            {
                Gravused = false;
                Collider.y = (int)(Cookie.Ground.getMinY()-Collider.getHeight());
            }
    }

    public void RenderImage(Graphics g)
    {
        g.drawImage(Texture,(int)Collider.getX(),(int)Collider.getY(),(int)Collider.getWidth(),(int)Collider.getHeight(),null);
    }

}
