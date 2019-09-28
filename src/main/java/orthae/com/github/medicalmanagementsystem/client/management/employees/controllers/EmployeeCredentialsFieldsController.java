package orthae.com.github.medicalmanagementsystem.client.management.employees.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.AuthorityDto;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.EmployeeDetailsDto;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class EmployeeCredentialsFieldsController {

    @FXML
    private TextField nameTextfield, surnameTextfield, usernameTextfield, emailTextfield;
    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox managementCheckbox;

    private EmployeeDetailsDto detailsDto;

    @SuppressWarnings("WeakerAccess")
    public EmployeeDetailsDto processForm(){
        int id = detailsDto == null ? 0 : detailsDto.getId();
        String name = nameTextfield.getText();
        String surname = surnameTextfield.getText();
        String username = usernameTextfield.getText();
        String email = emailTextfield.getText();
        String password = passwordField.getText();
        boolean active = detailsDto == null || detailsDto.isEnabled();
        List<AuthorityDto> authorities = new ArrayList<>();
        if(managementCheckbox.isSelected()){
            authorities.add(new AuthorityDto("MANAGEMENT"));
        }
        return new EmployeeDetailsDto(id ,name, surname, username, email, password, active, authorities);
    }

    @SuppressWarnings({"WeakerAccess", "SwitchStatementWithTooFewBranches"})
    public void initializeFields(EmployeeDetailsDto detailsDto){
        this.detailsDto = detailsDto;
        nameTextfield.setText(detailsDto.getName());
        surnameTextfield.setText(detailsDto.getSurname());
        usernameTextfield.setText(detailsDto.getUsername());
        emailTextfield.setText(detailsDto.getEmail());
        for(AuthorityDto authorityDto : detailsDto.getAuthorities()){
            switch (authorityDto.getAuthority()){
                case "MANAGEMENT":
                    managementCheckbox.selectedProperty().setValue(true);
                    break;
            }
        }
    }

}
