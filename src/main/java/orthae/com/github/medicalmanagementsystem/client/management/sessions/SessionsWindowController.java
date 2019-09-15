package orthae.com.github.medicalmanagementsystem.client.management.sessions;

import javafx.fxml.FXML;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.SessionDto;
import orthae.com.github.medicalmanagementsystem.client.management.sessions.service.SessionService;

import java.util.List;

@Component
@Scope("prototype")
public class SessionsWindowController {


    @SuppressWarnings("unused")
    @FXML
    private SessionsTableController sessionsTableController;

    private SessionService sessionService;

    public SessionsWindowController(SessionService sessionService) {
          this.sessionService = sessionService;
    }

    public void search(){
        List<SessionDto> list = sessionService.find();
        sessionsTableController.setTableContent(list.toArray(new SessionDto[0]));
    }
}
