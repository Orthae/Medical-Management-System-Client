package orthae.com.github.medicalmanagementsystem.client.employees;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.employees.service.EmployeesService;

@Component
public class AddEmployeeWindowController {

    @FXML
    private VBox addEmployeeWindow;

    private EmployeesService employeesService;
    private EmployeeCredentialsController credentialsFields;

    public AddEmployeeWindowController(EmployeesService employeesService, EmployeeCredentialsController credentialsFields ){
        this.employeesService = employeesService;
        this.credentialsFields = credentialsFields;
    }

    public void add() {
        try {
            employeesService.add(credentialsFields.processForm());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Created new employee");
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
