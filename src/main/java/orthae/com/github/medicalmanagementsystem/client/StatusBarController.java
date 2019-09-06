package orthae.com.github.medicalmanagementsystem.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.aspects.settings.SettingsService;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDetailsDto;

@Component
public class StatusBarController {

private SettingsService settingsService;

    @FXML
    private Label loggedAsLabel;

    public StatusBarController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public void initialize(){
        EmployeeDetailsDto loggedEmployee = settingsService.getLoggedEmployee();
        loggedAsLabel.setText("Logged as: " + loggedEmployee.getName() + " " + loggedEmployee.getSurname());
    }






}
