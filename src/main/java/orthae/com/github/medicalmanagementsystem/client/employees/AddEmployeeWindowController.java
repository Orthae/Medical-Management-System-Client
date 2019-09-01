package orthae.com.github.medicalmanagementsystem.client.employees;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.AuthorityDto;
import orthae.com.github.medicalmanagementsystem.client.employees.service.EmployeesService;

import java.util.ArrayList;
import java.util.List;

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

    @FXML
    private CheckBox managementCheckbox;


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
        List<AuthorityDto> authorities = new ArrayList<>();
        if(managementCheckbox.isSelected()){
            authorities.add(new AuthorityDto("MANAGEMENT"));
        }
        try {
            employeesService.add(name, surname, username, email, password, authorities);
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
