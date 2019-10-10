package orthae.com.github.medicalmanagementsystem.client.management.employees.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.aspects.ui.DialogService;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeChangePasswordDto;
import orthae.com.github.medicalmanagementsystem.client.management.employees.service.EmployeesService;

@Component
public class ChangePasswordPromptWindowController {

    @FXML
    private VBox changePasswordPromptWindow;

    @FXML
    private PasswordField passwordField, retypePasswordField;

    private DialogService dialogService;
    private EmployeesService employeesService;
    private int employeeId;

    public ChangePasswordPromptWindowController(DialogService dialogService, EmployeesService employeesService) {
        this.dialogService = dialogService;
        this.employeesService = employeesService;
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
            dialogService.infoAlert("Password changed");
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }

    public void close() {
        changePasswordPromptWindow.getScene().getWindow().hide();
    }

}
