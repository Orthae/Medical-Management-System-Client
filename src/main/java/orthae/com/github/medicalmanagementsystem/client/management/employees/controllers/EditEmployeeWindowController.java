package orthae.com.github.medicalmanagementsystem.client.management.employees.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeDetailsDto;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeDto;
import orthae.com.github.medicalmanagementsystem.client.management.employees.service.EmployeesService;

@Component
public class EditEmployeeWindowController {

    @FXML
    private VBox editEmployeeWindow;

    private EmployeesService employeesService;
    private EmployeeCredentialsController credentialsFields;

    public EditEmployeeWindowController(EmployeesService employeesService, EmployeeCredentialsController credentialsFields){
        this.employeesService = employeesService;
        this.credentialsFields = credentialsFields;
    }

    public void initialize(EmployeeDto dto){
        EmployeeDetailsDto detailsDto =  employeesService.find(dto.getId());
        credentialsFields.initializeFields(detailsDto);
    }

    public void edit(){
        try {
            EmployeeDetailsDto detailsDto = credentialsFields.processForm();
            employeesService.update(detailsDto);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Updated employee data.");
            alert.showAndWait();
            close();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void close (){
        editEmployeeWindow.getScene().getWindow().hide();
    }
}
