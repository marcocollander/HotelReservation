package net.raubuc.ui.gui;

import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainTabView {
    private final TabPane tabPane;

    public MainTabView(Stage primaryStage) {
        this.tabPane = new TabPane();

        RoomsTab roomsTab = new RoomsTab(primaryStage);
        ReservationsTab reservationsTab = new ReservationsTab(primaryStage);
        GuestsTab guestsTab = new GuestsTab(primaryStage);

        this.tabPane.getTabs().addAll(
                reservationsTab.getReservationTab(),
                guestsTab.getGuestTab(),
                roomsTab.getRoomTab()
        );
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}
