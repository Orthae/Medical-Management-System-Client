package orthae.com.github.medicalmanagementsystem.client.management.employees.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeChangePasswordDto;
import orthae.com.github.medicalmanagementsystem.client.management.employees.service.EmployeesService;

@Component
public class ChangePasswordPromptWindowController {

    @FXML
    private VBox changePasswordPromptWindow;

    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField retypePasswordField;

    private EmployeesService employeesService;
    private int employeeId;


    public ChangePasswordPromptWindowController(EmployeesService employeesService) {
        this.employeesService = employeesService;
        this.employeeId = 0;
    }

    public void initialize(int employeeId) {
        this.employeeId = employeeId;
    }


    private EmployeeChangePasswordDto processForm() {
        String password = passwordField.getText();
        String retypedPassword = retypePasswordField.getText();
        return new EmployeeChangePasswordDto(password, retypedPassword);
    }

    public void changePassword() {
        try {
            EmployeeChangePasswordDto dto = processForm();
            employeesService.changePassword(employeeId, dto);
            changePasswordPromptWindow.getScene().getWindow().hide();
        } catch (Exception e) {
//  TODO add alert window
            System.out.println(e.getMessage());
            System.out.println("Error while changing password");
        }
    }

    public void close() {
        changePasswordPromptWindow.getScene().getWindow().hide();
    }

}
