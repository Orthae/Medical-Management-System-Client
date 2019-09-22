package orthae.com.github.medicalmanagementsystem.client.management.sessions;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.aspects.ui.DialogService;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeDto;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.SessionDto;
import orthae.com.github.medicalmanagementsystem.client.management.sessions.service.SessionService;

import java.util.Date;
import java.util.List;

@Component
@Scope("prototype")
public class SessionsTableController {

    @FXML
    private TableView<SessionDto> tableView;

    @FXML
    private TableColumn<SessionDto, Number> idColumn;
    @FXML
    private TableColumn<SessionDto, Boolean> activeColumn;
    @FXML
    private TableColumn<SessionDto, Number> expiryDateColumn;
    @FXML
    private TableColumn<SessionDto, Number> creationDateColumn;
    @FXML
    private TableColumn<SessionDto, String> ipAddressColumn;
    @FXML
    private TableColumn<SessionDto, String> usernameColumn;

    private SessionService sessionService;
    private DialogService dialogService;

    public SessionsTableController(SessionService sessionService, DialogService dialogService) {
        this.sessionService = sessionService;
        this.dialogService = dialogService;
    }

    public void initialize() {
        idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId()));
        idColumn.setMinWidth(35);
        idColumn.setMaxWidth(35);
        activeColumn.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().isActive()));
        usernameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername()));
        expiryDateColumn.setCellValueFactory(param -> new SimpleLongProperty(param.getValue().getSessionExpiry().getTime()));
        expiryDateColumn.setMinWidth(200);
        setDateColumn(expiryDateColumn);
        creationDateColumn.setCellValueFactory(param -> new SimpleLongProperty(param.getValue().getSessionCreation().getTime()));
        creationDateColumn.setMinWidth(200);
        setDateColumn(creationDateColumn);
        ipAddressColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getIpAddress()));
        contextMenuSetup();
    }

    // TODO  Change name for something more informative
    public void initialize(EmployeeDto dto) {
        List<SessionDto> list;
        usernameColumn.setVisible(false);
        list = sessionService.find(dto.getId());
        tableView.getItems().addAll(list);
    }

    public void setTableContent(SessionDto... content) {
        tableView.getItems().clear();
        tableView.getItems().addAll(content);
    }

    private void contextMenuSetup(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem copyIpAddress = new MenuItem("Copy IP");
        copyIpAddress.setOnAction(event -> copyIpAddress());
        MenuItem invalidateSession = new MenuItem("Invalidate session");
        invalidateSession.setOnAction(event -> endSession());
        contextMenu.getItems().add(copyIpAddress);
        contextMenu.getItems().add(invalidateSession);
        tableView.setContextMenu(contextMenu);
    }

    private void copyIpAddress(){
        try{
            SessionDto sessionDto = getSelectedItem();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(sessionDto.getIpAddress());
            clipboard.setContent(content);
        } catch (Exception e){
            dialogService.errorAlert(e.getMessage());
        }
       }

       private void endSession(){
        try {
            SessionDto sessionDto = getSelectedItem();
            sessionService.invalidateSession(sessionDto.getId());
        } catch (Exception e ){
            dialogService.errorAlert(e.getMessage());
        }
       }

    private void setDateColumn(TableColumn<SessionDto, Number> column){
        column.setCellFactory(dateColumn -> {
            TableCell<SessionDto, Number> cell = new TableCell<SessionDto, Number>() {
                @Override
                protected void updateItem(Number item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Date date = new Date(item.longValue());
                        Label label = new Label(date.toString());
                        setGraphic(label);
                    }
                }
            };
            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            return cell;
        });
    }

    private SessionDto getSelectedItem(){
        SessionDto sessionDto = tableView.getSelectionModel().getSelectedItem();
        if(sessionDto != null){
            return tableView.getSelectionModel().getSelectedItem();
        } else
            throw new RuntimeException("You didn't select any item");

    }
}
