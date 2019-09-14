package orthae.com.github.medicalmanagementsystem.client.employees.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDetailsDto {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private List<AuthorityDto> authorities;

    public EmployeeDetailsDto(int id, String name, String surname, String username, String email, String password, boolean enabled, List<AuthorityDto> authorities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

}
