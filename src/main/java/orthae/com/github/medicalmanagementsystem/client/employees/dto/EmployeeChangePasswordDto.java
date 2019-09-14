package orthae.com.github.medicalmanagementsystem.client.employees.dto;

import lombok.Getter;

@Getter
public class EmployeeChangePasswordDto {
    private String password;
    private String retypePassword;

    public EmployeeChangePasswordDto(String password, String retypePassword) {
        this.password = password;
        this.retypePassword = retypePassword;
    }
}
