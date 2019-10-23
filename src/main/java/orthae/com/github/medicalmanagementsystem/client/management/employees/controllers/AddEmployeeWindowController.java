package orthae.com.github.medicalmanagementsystem.client.management.employees.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.aspects.ui.DialogService;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeDetailsDto;
import orthae.com.github.medicalmanagementsystem.client.management.employees.service.EmployeesService;

@Component
@Scope("prototype")
public class AddEmployeeWindowController {

    @FXML
    private VBox addEmployeeWindow;

    //  bugged warning
    @SuppressWarnings("unused")
    @FXML
    private EmployeeCredentialsFieldsController employeeCredentialsFieldsController;

    private EmployeesService employeesService;
    private DialogService dialogService;


    public AddEmployeeWindowController(EmployeesService employeesService, DialogService dialogService) {
        this.employeesService = employeesService;
        this.dialogService = dialogService;
    }

    public void add() {
        try {

            EmployeeDetailsDto detailsDto = employeeCredentialsFieldsController.processForm();
            employeesService.add(detailsDto);
            dialogService.infoAlert("New employee created.");
            close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void close(){
        addEmployeeWindow.getScene().getWindow().hide();
    }

}
