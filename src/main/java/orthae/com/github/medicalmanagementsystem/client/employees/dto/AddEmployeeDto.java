package orthae.com.github.medicalmanagementsystem.client.employees.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddEmployeeDto {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;

    public AddEmployeeDto(String name, String surname, String username, String email, String password) {
        this(0, name, surname, username, email ,password);
    }

    public AddEmployeeDto(int id, String name, String surname, String username, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
