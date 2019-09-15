package orthae.com.github.medicalmanagementsystem.client.management.employees.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SessionDto {
    private int id;
    private String username;
    private String ipAddress;
    private Date sessionCreation;
    private Date sessionExpiry;
    private boolean active;

}

