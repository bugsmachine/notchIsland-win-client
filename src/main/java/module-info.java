module top.notchisland.notchislandclientwin {
    requires javafx.controls;
    requires javafx.fxml;
    requires FXTrayIcon;
    requires java.desktop;
    opens top.notchisland.notchislandclientwin to javafx.fxml;
    exports top.notchisland.notchislandclientwin;
    exports top.notchisland.notchislandclientwin.controller;
    opens top.notchisland.notchislandclientwin.controller to javafx.fxml;
}