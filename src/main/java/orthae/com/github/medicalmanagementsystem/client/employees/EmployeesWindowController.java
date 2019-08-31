package orthae.com.github.medicalmanagementsystem.client.employees;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeesWindowController {

    @FXML
    TextField nameTextfield;
    @FXML
    TextField surnameTextfield;
    @FXML
    TextField usernameTextfield;
    @FXML
    TextField emailTextfield;


    @FXML
    TableView<EmployeeDto> tableView;
    @FXML
    TableColumn<EmployeeDto, String> nameColumn;
    @FXML
    TableColumn<EmployeeDto, String> surnameColumn;
    @FXML
    TableColumn<EmployeeDto, String> usernameColumn;
    @FXML
    TableColumn<EmployeeDto, String> emailColumn;

    private EmployeesService employeesService;

    public EmployeesWindowController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    public void initialize() {
        nameColumn.setCellValueFactory(param -> param.getValue().getName());
        nameColumn.setMinWidth(100);
        surnameColumn.setCellValueFactory(param -> param.getValue().getSurname());
        surnameColumn.setMinWidth(100);
        usernameColumn.setCellValueFactory(param -> param.getValue().getUsername());
        usernameColumn.setMinWidth(100);
        emailColumn.setCellValueFactory(param -> param.getValue().getEmail());
        emailColumn.setMinWidth(200);
    }


    public void search() {
        String name = nameTextfield.getText().trim().isEmpty() ? null : nameTextfield.getText().trim();
        String surname = surnameTextfield.getText().trim().isEmpty() ? null : surnameTextfield.getText().trim();
        String username = usernameTextfield.getText().trim().isEmpty() ? null : usernameTextfield.getText().trim();
        String email = emailTextfield.getText().trim().isEmpty() ? null : usernameTextfield.getText().trim();
        tableView.getItems().clear();
        tableView.getItems().addAll(employeesService.find(name, surname, username, email));
    }

    public void clear(){
        nameTextfield.clear();
        surnameTextfield.clear();
        usernameTextfield.clear();
        emailTextfield.clear();
    }

    public void delete(){
        EmployeeDto dto = tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected employee");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            tableView.getItems().remove(dto);
            employeesService.delete(dto.getId());
        }



    }


}
