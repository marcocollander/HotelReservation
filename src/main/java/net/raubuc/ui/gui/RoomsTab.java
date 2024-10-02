package net.raubuc.ui.gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.raubuc.objects.ObjectPool;
import net.raubuc.objects.room.RoomService;
import net.raubuc.objects.room.dto.RoomDTO;

import java.util.List;

public class RoomsTab {
    private final Tab roomTab;
    private final RoomService roomService = ObjectPool.getRoomService();

    public RoomsTab(Stage primaryStage) {
        TableView<RoomDTO> tableView = getRoomDTOTableView();
        this.roomTab = new Tab("Pokoje", getVBox(primaryStage, tableView));
        this.roomTab.setClosable(false);
    }

    private TableView<RoomDTO> getRoomDTOTableView() {
        TableView<RoomDTO> tableView = new TableView<>();

        TableColumn<RoomDTO, Integer> numberColumn = new TableColumn<>("Numer");
        numberColumn.setStyle("-fx-alignment: center");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<RoomDTO, String> bedsColumn = new TableColumn<>("Typy łóżek");
        bedsColumn.setStyle("-fx-alignment: center");
        bedsColumn.setCellValueFactory(new PropertyValueFactory<>("beds"));

        TableColumn<RoomDTO, Integer> bedsCountColumn = new TableColumn<>("Ilość łóżek");
        bedsCountColumn.setStyle("-fx-alignment: center");
        bedsCountColumn.setCellValueFactory(new PropertyValueFactory<>("bedsCount"));

        TableColumn<RoomDTO, Integer> roomSizeColumn = new TableColumn<>("Rozmiar pokoju");
        roomSizeColumn.setStyle("-fx-alignment: center");
        roomSizeColumn.setCellValueFactory(new PropertyValueFactory<>("roomSize"));

        TableColumn<RoomDTO, RoomDTO> deleteColumn = new TableColumn<>("Usuń");
        deleteColumn.setStyle("-fx-alignment: center");

        deleteColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper(value.getValue()));

        deleteColumn.setCellFactory(param -> new TableCell<>() {
            Button deleteButton = new Button("Usuń");

            @Override
            protected void updateItem(RoomDTO value, boolean empty) {
                super.updateItem(value, empty);
                if (value == null) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(actionEvent -> {
                        roomService.removeRoom(value.getId());
                        tableView.getItems().remove(value);
                    });
                }
            }
        });

        //noinspection unchecked
        tableView.getColumns().addAll(numberColumn, roomSizeColumn, bedsCountColumn, bedsColumn, deleteColumn);
        List<RoomDTO> roomDTOS = roomService.getAllRoomsDTO();
        tableView.getItems().addAll(roomDTOS);
        return tableView;
    }

    private static VBox getVBox(Stage primaryStage, TableView<RoomDTO> tableView) {
        Button btn = new Button("Dodaj nowy");

        btn.setOnAction(e -> {
            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(primaryStage);
            dialog.setScene(new AddNewRoomScene(dialog, tableView).getMainScene());
            dialog.setTitle("Dodawanie nowego pokoju");

            dialog.showAndWait();
        });
        return new VBox(btn, tableView);
    }

    Tab getRoomTab() {
        return roomTab;
    }
}
