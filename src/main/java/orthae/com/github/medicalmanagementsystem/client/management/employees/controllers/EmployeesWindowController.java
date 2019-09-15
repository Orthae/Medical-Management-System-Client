package orthae.com.github.medicalmanagementsystem.client.management.employees.controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.aspects.ui.DialogService;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeDto;
import orthae.com.github.medicalmanagementsystem.client.management.employees.service.EmployeesService;
import orthae.com.github.medicalmanagementsystem.client.management.sessions.SessionsTableController;

@Component
public class EmployeesWindowController {

    @FXML
    private BorderPane employeesWindow;

    @FXML
    private TextField nameTextfield;
    @FXML
    private TextField surnameTextfield;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField emailTextfield;

    @FXML
    private TableView<EmployeeDto> tableView;
    @FXML
    private TableColumn<EmployeeDto, Number> idColumn;
    @FXML
    private TableColumn<EmployeeDto, String> nameColumn;
    @FXML
    private TableColumn<EmployeeDto, String> surnameColumn;
    @FXML
    private TableColumn<EmployeeDto, String> usernameColumn;
    @FXML
    private TableColumn<EmployeeDto, String> emailColumn;
    @FXML
    private TableColumn<EmployeeDto, Boolean> enabledColumn;
    @FXML
    private TableColumn<EmployeeDto, Boolean> activeColumn;

    private DialogService dialogService;
    private EmployeesService employeesService;

    public EmployeesWindowController(EmployeesService employeesService, DialogService dialogService) {
        this.employeesService = employeesService;
        this.dialogService = dialogService;
    }

    public void initialize() {
        contextMenuSetup();
        idColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getId()));
        idColumn.setMinWidth(35);
        idColumn.setMaxWidth(35);
        idColumn.setResizable(false);
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        nameColumn.setMinWidth(100);
        surnameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSurname()));
        surnameColumn.setMinWidth(100);
        usernameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername()));
        usernameColumn.setMinWidth(100);
        emailColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmail()));
        emailColumn.setMinWidth(200);
        enabledColumn.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().isEnabled()));
        activeColumn.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().isActive()));
    }


    public void search() {
        try {
            String name = nameTextfield.getText().trim().isEmpty() ? null : nameTextfield.getText().trim();
            String surname = surnameTextfield.getText().trim().isEmpty() ? null : surnameTextfield.getText().trim();
            String username = usernameTextfield.getText().trim().isEmpty() ? null : usernameTextfield.getText().trim();
            String email = emailTextfield.getText().trim().isEmpty() ? null : usernameTextfield.getText().trim();
            tableView.getItems().clear();
            tableView.getItems().addAll(employeesService.find(name, surname, username, email));
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }

    public void clear() {
        nameTextfield.clear();
        surnameTextfield.clear();
        usernameTextfield.clear();
        emailTextfield.clear();
    }

    public void delete() {
        try {
            EmployeeDto dto = getSelected();
            Alert alert = dialogService.warringAlert("Are you sure you want to delete selected employee?");
            if (alert.getResult() == ButtonType.YES) {
                employeesService.delete(dto.getId());
                tableView.getItems().remove(dto);
            }
            tableView.getSelectionModel().clearSelection();
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }

    public void create() {
        try {
            FXMLLoader loader = dialogService.fxmlLoader("/fxml/management/employees/addEmployeeWindow.fxml");
            Stage stage = dialogService.stageSetup(loader, employeesWindow.getScene().getWindow());
            stage.showAndWait();
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }

    public void edit() {
        try {
            EmployeeDto dto = getSelected();
            FXMLLoader loader = dialogService.fxmlLoader("/fxml/management/employees/editEmployeeWindow.fxml");
            Stage stage = dialogService.stageSetup(loader, employeesWindow.getScene().getWindow());
            EditEmployeeWindowController controller = loader.getController();
            controller.initialize(dto);
            stage.showAndWait();
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }

    public void enable() {
        try {
            EmployeeDto dto = getSelected();
            employeesService.activate(dto.getId());
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }


    public void disable() {
        try {
            EmployeeDto dto = getSelected();
            employeesService.deactivate(dto.getId());
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }

    public void changePassword(){
        try {
            EmployeeDto dto = getSelected();
            FXMLLoader loader = dialogService.fxmlLoader("/fxml/management/employees/changePasswordPromptWindow.fxml");
            Stage stage = dialogService.stageSetup(loader, employeesWindow.getScene().getWindow());
            ChangePasswordPromptWindowController controller = loader.getController();
            controller.initialize(dto.getId());
            stage.showAndWait();
        } catch (Exception e){
            dialogService.errorAlert(e.getMessage());
        }

    }

    public void sessions(){
        try{
            EmployeeDto dto = getSelected();
            FXMLLoader loader = dialogService.fxmlLoader("/fxml/management/sessions/sessionsTable.fxml");
            Stage stage = dialogService.stageSetup(loader, employeesWindow.getScene().getWindow());
            SessionsTableController controller = loader.getController();
            controller.initialize(dto);
            stage.setTitle("Sessions of " + dto.getName() + " " + dto.getSurname());
            stage.showAndWait();
        } catch (Exception e){
            dialogService.errorAlert(e.getMessage());
        }
    }

    private void contextMenuSetup(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem create = new MenuItem("Create");
        create.setOnAction( event -> create());
        MenuItem edit = new MenuItem("Edit");
        edit.setOnAction( event -> edit());
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction( event -> delete());
        MenuItem enable = new MenuItem("Enable");
        enable.setOnAction( event -> enable());
        MenuItem disable = new MenuItem("Disable");
        disable.setOnAction( event -> disable());
        MenuItem password = new MenuItem("Password");
        password.setOnAction( event -> changePassword());
        MenuItem sessions = new MenuItem("Sessions");
        sessions.setOnAction(event -> sessions());


        contextMenu.getItems().add(create);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(edit);
        contextMenu.getItems().add(delete);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(enable);
        contextMenu.getItems().add(disable);
        contextMenu.getItems().add(password);
        contextMenu.getItems().add(sessions);
        tableView.setContextMenu(contextMenu);
    }

    private EmployeeDto getSelected() {
        EmployeeDto dto = tableView.getSelectionModel().getSelectedItem();
        if (dto != null)
            return dto;
        else
            throw new RuntimeException("You didn't select any employee");
    }


}

