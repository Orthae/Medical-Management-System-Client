package orthae.com.github.medicalmanagementsystem.client.employees;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import orthae.com.github.medicalmanagementsystem.client.employees.dto.AuthorityDto;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDetailsDto;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public abstract class AbstractAddEditEmployeeWindowController {

    @FXML
    private VBox addEditEmployeeWindow;

    @FXML
    private TextField nameTextfield;
    @FXML
    private TextField surnameTextfield;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField emailTextfield;
    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox managementCheckbox;


    public EmployeeDetailsDto processForm(){
        String name = getNameTextfield().getText();
        String surname = getSurnameTextfield().getText();
        String username = getUsernameTextfield().getText();
        String email = getEmailTextfield().getText();
        String password = getPasswordField().getText();
        List<AuthorityDto> authorities = new ArrayList<>();
        if(getManagementCheckbox().isSelected()){
            authorities.add(new AuthorityDto("MANAGEMENT"));
        }
        return new EmployeeDetailsDto(name, surname, username, email, password, authorities);
    }

    public void close() {
        addEditEmployeeWindow.getScene().getWindow().hide();
    }
}
