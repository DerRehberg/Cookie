import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyboardListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        if(keyEvent.getKeyCode() == KeyEvent.VK_D)
        {
            Cookie.player.Direction = 1;
        }else if(keyEvent.getKeyCode() == KeyEvent.VK_A)
        {
            Cookie.player.Direction = 2;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE && !Cookie.player.Gravused )
        {
            Cookie.player.Jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_D)
        {
            Cookie.player.Direction = 0;
        }else if(keyEvent.getKeyCode() == KeyEvent.VK_A)
        {
            Cookie.player.Direction = 0;
        }
    }
}
