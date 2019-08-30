package orthae.com.github.medicalmanagementsystem.client.aspects.settings;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class SettingsServiceImpl implements SettingsService {

    private String sessionToken;
}
