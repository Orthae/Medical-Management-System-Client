package orthae.com.github.medicalmanagementsystem.client.employees.dto;

import lombok.Getter;

@Getter
public class AuthorityDto {
    private String authority;

    public AuthorityDto(String authority) {
        this.authority = authority;
    }
}
