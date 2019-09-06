package orthae.com.github.medicalmanagementsystem.client.aspects.settings;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import orthae.com.github.medicalmanagementsystem.client.employees.dto.EmployeeDetailsDto;

@Service
@Getter
@Setter
public class SettingsServiceImpl implements SettingsService {

    private String sessionToken;
    private EmployeeDetailsDto loggedEmployee;

}
