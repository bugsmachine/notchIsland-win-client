// HelloApplication.java
package top.notchisland.notchislandclientwin;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import top.notchisland.notchislandclientwin.utility.GlobalMouseListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class HelloApplication extends Application {

    private Stage home;

    @Override
    public void start(Stage parent) throws IOException {
        // Initialize the parent stage
        parent.initStyle(StageStyle.UTILITY);
        parent.setOpacity(0);
        parent.show();

        // Create and configure the home stage
        home = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-windows.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        home.initStyle(StageStyle.TRANSPARENT); // Remove window decorations

        home.setWidth(600);
        home.setHeight(130);

        // Position the home stage at the middle top of the screen
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        home.setX((screenWidth - home.getWidth()) / 2);
        home.setY(3);

        home.setScene(scene);
        home.initOwner(parent);

        // Add an event filter to detect when the home stage is hidden
        home.setOnHidden(event -> {
            System.out.println("Window is hidden");
        });

        home.setOnShowing(event -> {
            simulateMouseClick();
            System.out.println("Window is showing");
        });

//        home.setOnShown(event -> {
//            simulateMouseClick();
//            System.out.println("Window is shown");
//        });

        // Track focus changes on the home stage
        home.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    System.out.println("Window lost focus");
                    Platform.runLater(() -> home.hide());
                }
            }
        });

        GlobalMouseListener.startListeningClicking(this);
        setTrayApp(home);
        home.show();
    }

    public void showHome() {
        home.show();
    }

    public static void simulateMouseClick() {

        //get current mouse position
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        double x = mouseLocation.getX();
        double y = mouseLocation.getY();

        // check if the mouse is in the home stage area, if in click if nof in, do nothing
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        if (x > (screenWidth - 600) / 2 && x < (screenWidth + 600) / 2 && y < 130) {
            Platform.runLater(() -> {
                try {
                    Robot robot = new Robot();

                    // Move the mouse to the center of the stage
                    robot.mouseMove((int) x, (int) y);

                    // Simulate mouse press and release
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                    System.out.println("Mouse click simulated on the stage.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }else {
            return;
        }




    }

    private static void setTrayApp(Stage stage) {
        if (FXTrayIcon.isSupported()) {
            System.out.println("System tray supported!");
            File image = new File("src/main/resources/top/notchisland/notchislandclientwin/images/XJTLU.png");
            URI uri = image.toURI();
            URL url = null;
            try {
                url = uri.toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            FXTrayIcon trayIcon = new FXTrayIcon(stage, url);
            MenuItem openItem = new MenuItem("Open");
            MenuItem exitItem = new MenuItem("Exit");
            trayIcon.addMenuItem(openItem);
            trayIcon.addMenuItem(exitItem);

            openItem.setOnAction(e -> {
                stage.show();
            });

            exitItem.setOnAction(e -> {
                System.exit(0);
            });

            if (!trayIcon.isShowing()) {
                trayIcon.show();
            }

        } else {
            System.out.println("System tray not supported!");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}