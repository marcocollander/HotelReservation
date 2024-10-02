package net.raubuc.ui.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import net.raubuc.utils.Properties;

public class PrimaryStage {
    public void initialize(Stage primaryStage) {
        String hotelName = Properties.HOTEL_NAME;
        int systemVersion = Properties.SYSTEM_VERSION;
        MainTabView mainTabView = new MainTabView(primaryStage);

        Scene scene = new Scene(mainTabView.getTabPane(), 800, 600);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("hotelReservation.css").toExternalForm());
        String title = String.format("System rezerwacji hotelu %s (%d)", hotelName, systemVersion);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
