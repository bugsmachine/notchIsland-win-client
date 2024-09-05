// GlobalMouseListener.java
package top.notchisland.notchislandclientwin.utility;

import javafx.application.Platform;
import javafx.stage.Screen;
import top.notchisland.notchislandclientwin.HelloApplication;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class GlobalMouseListener {

    private static boolean isListening = false;
    private static HelloApplication appInstance;

    public static void startListeningClicking(HelloApplication app) {
        if (isListening) {
            System.out.println("GlobalMouseListener is already listening.");
            return;
        }

        System.out.println("GlobalMouseListener.startListeningClicking - Method called");

        appInstance = app;
        isListening = true;

        // Optionally, add a separate thread to continuously check mouse position (for debugging or additional functionality)
        new Thread(() -> {
            while (true) {
                try {
                    Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
//                    System.out.println("Current mouse position: " + mouseLocation.x + ", " + mouseLocation.y);
                    Thread.sleep(100); // Check every second
                    // if mouse on certain range of position, call home stage show
                    double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
                    // if mouse is in the home stage area, show the home stage
                    if (mouseLocation.x > (screenWidth - 600) / 2 && mouseLocation.x < (screenWidth + 600) / 2 && mouseLocation.y < 130) {
                        Platform.runLater(() -> appInstance.showHome());
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}