package net.raubuc.ui.gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.raubuc.objects.ObjectPool;
import net.raubuc.objects.guest.GuestService;
import net.raubuc.objects.guest.dto.GuestDTO;

public class GuestsTab {
    private final Tab guestTab;
    private final GuestService guestService = ObjectPool.getGuestService();

    public GuestsTab(Stage primaryStage) {

        TableView<GuestDTO> tableView = getGuestDTOTableView();

        Button btn = new Button("Dodaj nowego gościa");

        btn.setOnAction(e -> {
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setScene(new AddNewGuestScene(stage, tableView).getMainScene());
            stage.setTitle("Dodaj nowego gościa");

            stage.showAndWait();
        });

        VBox layout = new VBox(btn, tableView);
        this.guestTab = new Tab("Goście", layout);
        this.guestTab.setClosable(false);
    }

    private TableView<GuestDTO> getGuestDTOTableView() {
        TableView<GuestDTO> tableView = new TableView<>();

        TableColumn<GuestDTO, String> firstNameColumn = getTableColumn("Imię", "firstName");
        TableColumn<GuestDTO, String> lastNameColumn = getTableColumn("nazwisko", "lastName");
        TableColumn<GuestDTO, Integer> ageColumn = getTableColumnInteger("Wiek", "age");
        TableColumn<GuestDTO, String> genderColumn = getTableColumn("Płeć", "gender");
        TableColumn<GuestDTO, GuestDTO> deleteColumn = new TableColumn<>("Usuń");

        deleteColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper(value.getValue()));

        deleteColumn.setCellFactory(param -> new TableCell<>() {
            Button deleteButton = new Button("Usuń");

            @Override
            protected void updateItem(GuestDTO value, boolean empty) {
                super.updateItem(value, empty);

                if (value == null) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(actionEvent -> {
                        guestService.removeGuest(value.getId());
                        tableView.getItems().remove(value);
                    });
                }
            }
        });

        //noinspection unchecked
        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, ageColumn, genderColumn, deleteColumn);
        tableView.getItems().addAll(guestService.getGuestsAsDTO());
        return tableView;
    }

    private static TableColumn<GuestDTO, String> getTableColumn(String nameColumn, String stringColumn) {
        TableColumn<GuestDTO, String> tableColumn = new TableColumn<>(nameColumn);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(stringColumn));
        return tableColumn;
    }

    private static TableColumn<GuestDTO, Integer> getTableColumnInteger(String nameColumn, String stringColumn) {
        TableColumn<GuestDTO, Integer> tableColumn = new TableColumn<>(nameColumn);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(stringColumn));
        return tableColumn;
    }


    public Tab getGuestTab() {
        return guestTab;
    }
}
