package orthae.com.github.medicalmanagementsystem.client.employees;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDetailsDto;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDto;
import orthae.com.github.medicalmanagementsystem.client.employees.service.EmployeesService;

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
        employeesService.update(credentialsFields.processForm());
    }

    public void close (){
        editEmployeeWindow.getScene().getWindow().hide();
    }
}
