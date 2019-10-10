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
import orthae.com.github.medicalmanagementsystem.client.management.sessions.service.SessionService;
import orthae.com.github.medicalmanagementsystem.client.management.workdays.AddWorkdaysWindowController;

@Component
public class EmployeesWindowController {

    @FXML
    private BorderPane employeesWindow;

    @FXML
    private TextField nameTextfield, surnameTextfield, usernameTextfield, emailTextfield;

    @FXML
    private ComboBox<String> enabledComboBox, activeComboBox;

    @FXML
    private TableView<EmployeeDto> tableView;
    @FXML
    private TableColumn<EmployeeDto, Number> idColumn;
    @FXML
    private TableColumn<EmployeeDto, String> nameColumn, surnameColumn, usernameColumn, emailColumn;
    @FXML
    private TableColumn<EmployeeDto, Boolean> enabledColumn, activeColumn;

    private DialogService dialogService;
    private EmployeesService employeesService;
    private SessionService sessionService;

    public EmployeesWindowController(DialogService dialogService, EmployeesService employeesService, SessionService sessionService) {
        this.dialogService = dialogService;
        this.employeesService = employeesService;
        this.sessionService = sessionService;
    }

    public void initialize() {
        contextMenuSetup();
        comboBoxSetup();
        tableSetup();
    }

    private void comboBoxSetup(){
        enabledComboBox.getItems().addAll("All", "Enabled", "Disabled");
        enabledComboBox.getSelectionModel().select(0);
        activeComboBox.getItems().addAll("All", "Active", "Inactive");
        activeComboBox.getSelectionModel().select(0);
    }

    private void tableSetup(){
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
        enabledColumn.setCellFactory(callback -> {
                TableCell<EmployeeDto, Boolean> cell = new TableCell<EmployeeDto, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Label label = new Label();
                            if(item){
                                label.setText("Enabled");
                             } else{
                                label.setText("Disabled");
                            }
                            setGraphic(label);
                        }
                    }
                };
                cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                return cell;
            });
        activeColumn.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().isActive()));
        activeColumn.setCellFactory(callback -> {
            TableCell<EmployeeDto, Boolean> cell = new TableCell<EmployeeDto, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Label label = new Label();
                        if(item){
                            label.setText("Active");
                        } else{
                            label.setText("Inactive");
                        }
                        setGraphic(label);
                    }
                }
            };
            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            return cell;
        });
    }

    private Boolean getComboBoxBool(ComboBox comboBox){
        switch (comboBox.getSelectionModel().getSelectedIndex()){
            case 1:
                return true;
            case 2:
                return false;
            default:
                return null;
        }
    }



    public void search() {
        try {
            String name = nameTextfield.getText().trim().isEmpty() ? null : nameTextfield.getText().trim();
            String surname = surnameTextfield.getText().trim().isEmpty() ? null : surnameTextfield.getText().trim();
            String username = usernameTextfield.getText().trim().isEmpty() ? null : usernameTextfield.getText().trim();
            String email = emailTextfield.getText().trim().isEmpty() ? null : usernameTextfield.getText().trim();
            Boolean active = getComboBoxBool(activeComboBox);
            Boolean enabled = getComboBoxBool(enabledComboBox);
            tableView.getItems().clear();
            tableView.getItems().addAll(employeesService.find(name, surname, username, email, active ,enabled));
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }


    public void clear() {
        nameTextfield.clear();
        surnameTextfield.clear();
        usernameTextfield.clear();
        emailTextfield.clear();
        activeComboBox.getSelectionModel().select(0);
        enabledComboBox.getSelectionModel().select(0);
    }

    private void delete() {
        try {
            EmployeeDto dto = getSelected();
            Alert alert = dialogService.warringAlert("Are you sure you want to delete selected employee?");
            if (alert.getResult() == ButtonType.YES) {
                employeesService.delete(dto.getId());
                dialogService.infoAlert("Employee deleted.");
                tableView.getItems().remove(dto);
            }
            tableView.getSelectionModel().clearSelection();
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }

    private void create() {
        try {
            FXMLLoader loader = dialogService.fxmlLoader("/fxml/management/employees/addEmployeeWindow.fxml");
            Stage stage = dialogService.stageSetup(loader, employeesWindow.getScene().getWindow());
            stage.showAndWait();
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }

    private void edit() {
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

    private void enable() {
        try {
            EmployeeDto dto = getSelected();
            employeesService.activate(dto.getId());
            dialogService.infoAlert("Employee enabled.");
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }


    private void disable() {
        try {
            EmployeeDto dto = getSelected();
            employeesService.deactivate(dto.getId());
            dialogService.infoAlert("Employee disabled.");
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }

    private void changePassword(){
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

    private void employeeSessions(){
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

    private void invalidateSessions(){
        try {
            EmployeeDto dto = getSelected();
            Alert alert = dialogService.warringAlert("Do you want to invalidate all employee sessions?");
            if(alert.getResult() == ButtonType.YES){
            sessionService.invalidateEmployeeSessions(dto.getId());
                dialogService.infoAlert("All employee sessions has been invalidated.");
            }
        } catch (Exception e){
            dialogService.errorAlert(e.getMessage());
        }
    }

    private void createWorkdays(){
        try{
            EmployeeDto dto = getSelected();
            FXMLLoader loader = dialogService.fxmlLoader("/fxml/management/workdays/addWorkdayWindow.fxml");
            Stage stage = dialogService.stageSetup(loader, employeesWindow.getScene().getWindow());
            AddWorkdaysWindowController controller = loader.getController();
            controller.initialize(dto.getId());
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
        MenuItem password = new MenuItem("Change password");
        password.setOnAction( event -> changePassword());
        MenuItem viewSessions = new MenuItem("View sessions");
        viewSessions.setOnAction(event -> employeeSessions());
        MenuItem invalidateSessions = new MenuItem("Invalidate sessions");
        invalidateSessions.setOnAction(event -> invalidateSessions());
        MenuItem createWorkdays = new MenuItem("Create workdays");
        createWorkdays.setOnAction(event -> createWorkdays());

        contextMenu.getItems().addAll(create, new SeparatorMenuItem(), edit, delete, new SeparatorMenuItem(), enable, disable, password,
                new SeparatorMenuItem(), viewSessions, invalidateSessions, createWorkdays);
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

