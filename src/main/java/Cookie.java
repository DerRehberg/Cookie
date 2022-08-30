
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.vulkan.KHRSurface.*;
import static org.lwjgl.vulkan.KHRWin32Surface.*;
import static org.lwjgl.vulkan.KHRXlibSurface.*;
import static org.lwjgl.vulkan.VK10.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Platform;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.awt.AWTVKCanvas;
import org.lwjgl.vulkan.awt.VKData;
import java.util.ArrayList;


public class Cookie {

    static JFrame frame;
    static Graphics g;
    static BufferStrategy bufferStrategy;
    public static Player player;
    public static Rectangle Ground;

    private static VkInstance createInstance() {
        VkApplicationInfo appInfo = VkApplicationInfo.calloc()
                .sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
                .pApplicationName(memUTF8("AWT Vulkan Demo"))
                .pEngineName(memUTF8(""))
                .apiVersion(VK_MAKE_VERSION(1, 0, 2));
        ByteBuffer VK_KHR_SURFACE_EXTENSION = memUTF8(VK_KHR_SURFACE_EXTENSION_NAME);
        ByteBuffer VK_KHR_OS_SURFACE_EXTENSION;
        if (Platform.get() == Platform.WINDOWS)
            VK_KHR_OS_SURFACE_EXTENSION = memUTF8(VK_KHR_WIN32_SURFACE_EXTENSION_NAME);
        else
            VK_KHR_OS_SURFACE_EXTENSION = memUTF8(VK_KHR_XLIB_SURFACE_EXTENSION_NAME);
        PointerBuffer ppEnabledExtensionNames = memAllocPointer(2);
        ppEnabledExtensionNames.put(VK_KHR_SURFACE_EXTENSION);
        ppEnabledExtensionNames.put(VK_KHR_OS_SURFACE_EXTENSION);
        ppEnabledExtensionNames.flip();
        VkInstanceCreateInfo pCreateInfo = VkInstanceCreateInfo.calloc()
                .sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
                .pNext(0L)
                .pApplicationInfo(appInfo);
        if (ppEnabledExtensionNames.remaining() > 0) {
            pCreateInfo.ppEnabledExtensionNames(ppEnabledExtensionNames);
        }
        PointerBuffer pInstance = MemoryUtil.memAllocPointer(1);
        int err = vkCreateInstance(pCreateInfo, null, pInstance);
        if (err != VK_SUCCESS) {
            throw new RuntimeException("Failed to create VkInstance!");
        }
        long instance = pInstance.get(0);
        memFree(pInstance);
        VkInstance ret = new VkInstance(instance, pCreateInfo);
        memFree(ppEnabledExtensionNames);
        memFree(VK_KHR_OS_SURFACE_EXTENSION);
        memFree(VK_KHR_SURFACE_EXTENSION);
        appInfo.free();
        return ret;
    }

    public static void main(String[] args) {
        // Create the Vulkan instance
        VkInstance instance = createInstance();
        VKData data = new VKData();
        data.instance = instance; // <- set Vulkan instance
        frame = new JFrame("AWT test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600,600);
        frame.setVisible(true);

        AWTVKCanvas canvas = new AWTVKCanvas(data) {
            public void initVK() {
                @SuppressWarnings("unused")
                long surface = this.surface;
                // Do something with surface...
            }
            public void paintVK() {

            }
        };
        canvas.setSize(frame.getWidth(), frame.getHeight());
        canvas.setVisible(true);
        canvas.setFocusable(false);

        frame.add(canvas, BorderLayout.CENTER);
        frame.addKeyListener(new KeyboardListener());

        canvas.createBufferStrategy(2);

        frame.pack();


        bufferStrategy = canvas.getBufferStrategy();
        g = bufferStrategy.getDrawGraphics();

        //init
        player = new Player();
        Ground = new Rectangle(0,400,600,50);

        Stuff.init();
        render();

    }


    static void math()
    {
        Stuff.Controllable.get(0).Mathe();
        player.Mathe();
        for(Rectangle rect : Stuff.Objects)
        {
            for(Life life : Stuff.Controllable)
            {
                life.Mathe();
            }
        }
        if(player.Collider.intersects(Ground))
        {
            player.Gravused = false;
            player.Collider.y = (int)Ground.getY()-(int)Ground.getHeight();
        }
    }

    static void render()
    {
        while(true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            g.setColor(Color.WHITE);
            g.clearRect(0,0,frame.getWidth(),frame.getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(Ground.x,Ground.y,(int)Ground.getWidth(),(int)Ground.getHeight());
            Stuff.Controllable.get(0).RenderImage(g);
            player.RenderImage(g);
            bufferStrategy.show();
            math();
        }
    }

}
