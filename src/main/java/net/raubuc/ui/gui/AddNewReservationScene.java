package net.raubuc.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.raubuc.objects.ObjectPool;
import net.raubuc.objects.guest.GuestService;
import net.raubuc.objects.reservation.ReservationService;
import net.raubuc.objects.reservation.dto.ReservationDTO;
import net.raubuc.objects.room.RoomService;
import net.raubuc.objects.room.dto.RoomDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddNewReservationScene {
    private final Scene mainScene;
    private final RoomService roomService = ObjectPool.getRoomService();
    private final GuestService guestService = ObjectPool.getGuestService();
    private final ReservationService reservationService = ObjectPool.getReservationService();


    public AddNewReservationScene(Stage modalStage, TableView<ReservationDTO> tableView) {

        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);

        Label fromDateLabel = new Label("Data rozpoczęcia rezerwacji:");

        DatePicker fromDateField = new DatePicker();

        gridPane.add(fromDateLabel, 0, 0);
        gridPane.add(fromDateField, 1, 0);

        Label toDateLabel = new Label("Data zakończenia rezerwacji:");

        DatePicker toDateField = new DatePicker();

        gridPane.add(toDateLabel, 0, 1);
        gridPane.add(toDateField, 1, 1);

        List<RoomDTO> allAsDTO = this.roomService.getAllRoomsDTO();
        List<RoomSelectionItem> roomSelectionItems = new ArrayList<>();

        allAsDTO.forEach(dto -> {
            roomSelectionItems.add(
                    new RoomSelectionItem(dto.getNumber(), dto.getId()));
        });

        List<GuestSelectionItem> guestSelectionItems = new ArrayList<>();

        this.guestService.getGuestsAsDTO().forEach(dto -> {
            guestSelectionItems.add(
                    new GuestSelectionItem(
                            dto.getFirstName(), dto.getLastName(), dto.getId()));
        });

        Label roomLabel = new Label("Pokój:");
        ComboBox<RoomSelectionItem> roomField = new ComboBox<>();
        roomField.getItems().addAll(roomSelectionItems);

        gridPane.add(roomLabel, 0, 2);
        gridPane.add(roomField, 1, 2);

        Label guestLabel = new Label("Rezerwujący:");
        ComboBox<GuestSelectionItem> guestField = new ComboBox<>();
        guestField.getItems().addAll(guestSelectionItems);

        gridPane.add(guestLabel, 0, 3);
        gridPane.add(guestField, 1, 3);

        Button btn = new Button("Utwórz rezerwację");

        btn.setOnAction(actionEvent -> {
            LocalDate from = fromDateField.getValue();
            LocalDate to = toDateField.getValue();
            int guestId = guestField.getValue().getId();
            int roomId = roomField.getValue().getId();

            try {
                this.reservationService.createNewReservation(from, to, roomId, guestId);
                tableView.getItems().clear();
                tableView.getItems().addAll(this.reservationService.getReservationsAsDTO());
                modalStage.close();
            } catch (IllegalArgumentException ex) {
                Label error = new Label("Niepoprawne daty rezerwacji");
                error.setTextFill(Color.RED);
                gridPane.add(error, 0, 5);
            }
        });

        gridPane.add(btn, 1, 4);

        this.mainScene = new Scene(gridPane, 640, 480);
        this.mainScene.getStylesheets().add(getClass().getClassLoader()
                .getResource("hotelReservation.css").toExternalForm());
    }

    public Scene getMainScene() {
        return this.mainScene;
    }
}
