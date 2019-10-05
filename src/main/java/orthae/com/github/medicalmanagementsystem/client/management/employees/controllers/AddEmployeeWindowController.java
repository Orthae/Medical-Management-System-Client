package orthae.com.github.medicalmanagementsystem.client.management.employees.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeDetailsDto;
import orthae.com.github.medicalmanagementsystem.client.management.employees.service.EmployeesService;

@Component
@Scope("prototype")
public class AddEmployeeWindowController {

    @FXML
    private VBox addEmployeeWindow;

    @FXML
    private EmployeeCredentialsFieldsController employeeCredentialsFieldsController;

    private EmployeesService employeesService;


    public AddEmployeeWindowController(EmployeesService employeesService){
        this.employeesService = employeesService;
    }

    public void add() {
        try {

            EmployeeDetailsDto detailsDto = employeeCredentialsFieldsController.processForm();
            employeesService.add(detailsDto);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("New employee created.");
            alert.showAndWait();
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
