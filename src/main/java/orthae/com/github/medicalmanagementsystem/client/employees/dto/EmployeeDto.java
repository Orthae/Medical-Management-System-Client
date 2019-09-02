package orthae.com.github.medicalmanagementsystem.client.employees.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String email;
}
