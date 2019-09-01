package orthae.com.github.medicalmanagementsystem.client.employees;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.employees.service.EmployeesService;

@Component
public class AddEmployeeWindowController {

    @FXML
    private GridPane addEmployeeWindow;

    @FXML
    private TextField nameTextfield;
    @FXML
    private TextField surnameTextfield;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField emailTextfield;
    @FXML
    private PasswordField passwordField;


    private EmployeesService employeesService;

    public AddEmployeeWindowController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    public void add() {
        String name = nameTextfield.getText();
        String surname = surnameTextfield.getText();
        String username = usernameTextfield.getText();
        String email = emailTextfield.getText();
        String password = passwordField.getText();
        try {
            employeesService.add(name, surname, username, email, password);
            addEmployeeWindow.getScene().getWindow().hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void close() {
        addEmployeeWindow.getScene().getWindow().hide();
    }
}
