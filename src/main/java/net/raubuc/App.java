package net.raubuc;

import javafx.application.Application;
import javafx.stage.Stage;
import net.raubuc.exceptions.PersistenceToFileException;
import net.raubuc.exceptions.WrongOptionException;
import net.raubuc.objects.ObjectPool;
import net.raubuc.objects.guest.GuestService;
import net.raubuc.objects.reservation.ReservationService;
import net.raubuc.objects.room.RoomService;
import net.raubuc.ui.gui.PrimaryStage;
import net.raubuc.ui.text.TextUI;
import net.raubuc.utils.Properties;

import java.io.IOException;

public class App extends Application {

    private static final TextUI textUI = new TextUI();
    private static final GuestService guestService = ObjectPool.getGuestService();
    private static final RoomService roomService = ObjectPool.getRoomService();
    private static final ReservationService reservationService = ObjectPool.getReservationService();


    public static void main(String[] args) throws WrongOptionException {

        try {
            Properties.createDataDirectory();
            System.out.println("Trwa ładowanie danych");
            guestService.readAllGuests();
            roomService.readAllRooms();
            reservationService.readAllReservations();
        } catch (IOException e) {
            throw new PersistenceToFileException(
                    Properties.DATA_DIRECTORY.toString(),
                    "create",
                    "directory"
            );
        }
        launch(args);

/*
     textUI.showSystemInfo();
     textUI.showMainMenu();
*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PrimaryStage primary = new PrimaryStage();
        primary.initialize(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Wychodzę z aplikacji. Zapisuję dane.");
        guestService.saveAllGuests();
        roomService.saveAllRooms();
        reservationService.saveAllReservations();
    }
}
