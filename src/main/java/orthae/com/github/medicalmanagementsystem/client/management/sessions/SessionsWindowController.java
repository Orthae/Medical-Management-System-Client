package orthae.com.github.medicalmanagementsystem.client.management.sessions;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.aspects.ui.DialogService;
import orthae.com.github.medicalmanagementsystem.client.management.employees.dto.SessionDto;
import orthae.com.github.medicalmanagementsystem.client.management.sessions.service.SessionService;

import java.util.List;

@Component
@Scope("prototype")
public class SessionsWindowController {

    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField ipAddressTextfield;

    @FXML
    private DatePicker createdDatepicker;
    @FXML
    private DatePicker expiringDatepicker;

    @FXML
    private ComboBox<String> activeCombobox;

    //  bugged warning
    @SuppressWarnings("unused")
    @FXML
    private SessionsTableController sessionsTableController;

    private SessionService sessionService;
    private DialogService dialogService;

    public SessionsWindowController(SessionService sessionService, DialogService dialogService) {
        this.sessionService = sessionService;
        this.dialogService = dialogService;
    }

    private Boolean getComboBoxBool(ComboBox box) {
        switch (box.getSelectionModel().getSelectedIndex()) {
            case 1:
                return true;
            case 2:
                return false;
            default:
                return null;
        }
    }

    public void initialize() {
        activeCombobox.getItems().addAll("All", "Active", "Inactive");
        activeCombobox.getSelectionModel().select(0);
    }

    public void clear() {
        usernameTextfield.clear();
        ipAddressTextfield.clear();
        activeCombobox.getSelectionModel().select(0);
        createdDatepicker.setValue(null);
        expiringDatepicker.setValue(null);
    }

    public void search() {
        try {
            String username = usernameTextfield.getText().trim().isEmpty() ? null : usernameTextfield.getText().trim();
            String ipAddress = ipAddressTextfield.getText().trim().isEmpty() ? null : ipAddressTextfield.getText().trim();
            Boolean active = getComboBoxBool(activeCombobox);
            String created = createdDatepicker.getEditor().getText().trim().isEmpty() ? null : createdDatepicker.getValue().toString();
            String expiring = expiringDatepicker.getEditor().getText().trim().isEmpty() ? null : expiringDatepicker.getValue().toString();
            List<SessionDto> list = sessionService.find(username, ipAddress, active, created, expiring);
            sessionsTableController.setTableContent(list.toArray(new SessionDto[0]));
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }
}
