package orthae.com.github.medicalmanagementsystem.client.management.employees.dto;

import lombok.Getter;

@Getter
public class EmployeeChangePasswordDto {
    private String password;
    private String rePassword;

    public EmployeeChangePasswordDto(String password, String retypePassword) {
        this.password = password;
        this.rePassword = retypePassword;
    }
}
