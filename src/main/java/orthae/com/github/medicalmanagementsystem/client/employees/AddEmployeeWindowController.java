package orthae.com.github.medicalmanagementsystem.client.employees;

import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDetailsDto;
import orthae.com.github.medicalmanagementsystem.client.employees.service.EmployeesService;

@Component
@Getter
@Setter
public class AddEmployeeWindowController extends AbstractAddEditEmployeeWindowController {

    private EmployeesService employeesService;

    public AddEmployeeWindowController(EmployeesService employeesService){
        this.employeesService = employeesService;
    }

    public void add() {
        EmployeeDetailsDto dto = processForm();
        try {
            employeesService.add(dto);
            getAddEditEmployeeWindow().getScene().getWindow().hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
