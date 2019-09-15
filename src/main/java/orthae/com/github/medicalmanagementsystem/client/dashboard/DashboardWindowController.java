package orthae.com.github.medicalmanagementsystem.client.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.aspects.settings.SettingsService;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeDetailsDto;

@Component
public class DashboardWindowController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;

    private SettingsService settingsService;

    public DashboardWindowController(SettingsService settingsService){
        this.settingsService = settingsService;
    }


    public void initialize(){
        EmployeeDetailsDto loggedEmployee = settingsService.getLoggedEmployee();
        nameLabel.setText("Welcome, " + loggedEmployee.getName() + " " + loggedEmployee.getSurname());
        surnameLabel.setText(loggedEmployee.getSurname());
    }



}
