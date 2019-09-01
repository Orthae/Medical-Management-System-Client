package orthae.com.github.medicalmanagementsystem.client.employees.dto;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
    private int id;
    private SimpleStringProperty name;
    private SimpleStringProperty surname;
    private SimpleStringProperty username;
    private SimpleStringProperty email;

}
