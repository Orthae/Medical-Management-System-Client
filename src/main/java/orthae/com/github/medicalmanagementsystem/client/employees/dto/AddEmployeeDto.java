package orthae.com.github.medicalmanagementsystem.client.employees.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddEmployeeDto {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private List<AuthorityDto> authorities;


    public AddEmployeeDto(String name, String surname, String username, String email, String password, List<AuthorityDto> authorities) {
        this(0, name, surname, username, email ,password, authorities);
    }

    public AddEmployeeDto(int id, String name, String surname, String username, String email, String password, List<AuthorityDto> authorities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
}
