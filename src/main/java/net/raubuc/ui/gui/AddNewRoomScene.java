package net.raubuc.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.raubuc.objects.ObjectPool;
import net.raubuc.objects.room.RoomService;
import net.raubuc.objects.room.dto.RoomDTO;

import java.util.ArrayList;
import java.util.List;

public class AddNewRoomScene {
    private final Scene mainScene;
    private final List<ComboBox<String>> comboBoxes = new ArrayList<>();
    private final RoomService roomService = ObjectPool.getRoomService();

    public AddNewRoomScene(Stage stage, TableView<RoomDTO> tableView) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setAlignment(Pos.CENTER);

        Label roomNumberLabel = new Label("Numer pokoju:");
        TextField roomNumberField = new TextField();

        roomNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                roomNumberField.setText(oldValue);
            }
        });

        gridPane.add(roomNumberLabel, 0, 0);
        gridPane.add(roomNumberField, 1, 0);

        Label bedTypeLabel = new Label("Typy łóżek:");
        Button addNewBedButton = getButton();

        gridPane.add(bedTypeLabel, 0, 1);
        gridPane.add(addNewBedButton, 1, 1);

        VBox bedsVerticalLayout = new VBox(getComboBox());

        addNewBedButton.setOnAction(actionEvent -> {
            bedsVerticalLayout.getChildren().add(getComboBox());
        });

        Button addNewRoomButton = getButton(stage, tableView, roomNumberField);
        addNewRoomButton.setPadding(new Insets(5, 5, 5, 5));

        gridPane.add(bedsVerticalLayout, 1, 2);
        gridPane.add(addNewRoomButton, 0, 3);

        this.mainScene = new Scene(gridPane, 640, 480);
        this.mainScene.getStylesheets().add(getClass().getClassLoader().getResource("hotelReservation.css").toExternalForm());
    }

    private Button getButton() {
        Button addNewBedButton = new Button();

        Image icon = new Image(getClass().getClassLoader().getResourceAsStream("add.jpg"));
        ImageView imgView = new ImageView(icon);

        imgView.setFitWidth(16);
        imgView.setFitHeight(16);

        addNewBedButton.setGraphic(imgView);
        addNewBedButton.setPadding(Insets.EMPTY);
        return addNewBedButton;
    }

    private Button getButton(Stage stage, TableView<RoomDTO> tableView, TextField roomNumberField) {
        Button addNewRoomButton = new Button("Dodaj nowy pokój");

        addNewRoomButton.setOnAction(actionEvent -> {
            int newRoomNumber = Integer.parseInt(roomNumberField.getText());
            List<String> bedTypes = new ArrayList<>();

            this.comboBoxes.forEach(comboBox -> {
                bedTypes.add(comboBox.getValue());
            });

            this.roomService.createNewRoom(newRoomNumber, bedTypes);
            tableView.getItems().clear();

            List<RoomDTO> allAsDTO = roomService.getAllRoomsDTO();

            tableView.getItems().addAll(allAsDTO);
            stage.close();
        });
        return addNewRoomButton;
    }

    private ComboBox<String> getComboBox() {
        ComboBox<String> bedTypeField = new ComboBox<>();

        bedTypeField.getItems().addAll("Pojedyncze", "Podwójne", "Królewskie");
        bedTypeField.setValue("Pojedyncze");
        this.comboBoxes.add(bedTypeField);

        return bedTypeField;
    }


    public Scene getMainScene() {
        return mainScene;
    }
}
