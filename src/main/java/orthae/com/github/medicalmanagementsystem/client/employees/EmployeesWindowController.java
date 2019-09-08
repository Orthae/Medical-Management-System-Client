package orthae.com.github.medicalmanagementsystem.client.employees;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDto;
import orthae.com.github.medicalmanagementsystem.client.employees.service.EmployeesService;

import java.io.IOException;

@Component
public class EmployeesWindowController {

    @FXML
    BorderPane employeesWindow;

    @FXML
    TextField nameTextfield;
    @FXML
    TextField surnameTextfield;
    @FXML
    TextField usernameTextfield;
    @FXML
    TextField emailTextfield;

    @FXML
    TableView<EmployeeDto> tableView;
    @FXML
    TableColumn<EmployeeDto, String> nameColumn;
    @FXML
    TableColumn<EmployeeDto, String> surnameColumn;
    @FXML
    TableColumn<EmployeeDto, String> usernameColumn;
    @FXML
    TableColumn<EmployeeDto, String> emailColumn;

    private EmployeesService employeesService;
    private ApplicationContext context;

    public EmployeesWindowController(EmployeesService employeesService, ApplicationContext context) {
        this.employeesService = employeesService;
        this.context = context;
    }

    public void initialize() {
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        nameColumn.setMinWidth(100);
        surnameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSurname()));
        surnameColumn.setMinWidth(100);
        usernameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername()));
        usernameColumn.setMinWidth(100);
        emailColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmail()));
        emailColumn.setMinWidth(200);
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
            errorAlert(e.getMessage());
        }
    }

    public void clear() {
        nameTextfield.clear();
        surnameTextfield.clear();
        usernameTextfield.clear();
        emailTextfield.clear();
    }

    public void delete() {
        EmployeeDto dto = tableView.getSelectionModel().getSelectedItem();
        if (dto == null) {
            noEmployeeSelectedAlert();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete selected employee");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    employeesService.delete(dto.getId());
                    tableView.getItems().remove(dto);
                    tableView.getSelectionModel().clearSelection();
                } catch (Exception e) {
                    errorAlert(e.getMessage());
                }
            }
        }
    }

    public void add() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/employees/addEmployeeWindow.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initOwner(employeesWindow.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            errorAlert(e.getMessage());
        }
    }

    public void edit() {
        EmployeeDto dto = tableView.getSelectionModel().getSelectedItem();
        if (dto == null) {
            noEmployeeSelectedAlert();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/employees/editEmployeeWindow.fxml"));
                loader.setControllerFactory(context::getBean);
                Parent root = loader.load();
                EditEmployeeWindowController controller = loader.getController();
                controller.initialize(tableView.getSelectionModel().getSelectedItem());
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initOwner(employeesWindow.getScene().getWindow());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                errorAlert(e.getMessage());
            }
        }
    }


    private void noEmployeeSelectedAlert() {
        errorAlert("You didn't select any employee");
    }

    private void errorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}

