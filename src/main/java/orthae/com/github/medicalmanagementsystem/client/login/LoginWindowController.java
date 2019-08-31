package orthae.com.github.medicalmanagementsystem.client.login;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;

@Component
public class LoginWindowController {

    @FXML
    private ImageView loginImage;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    private LoginService loginService;

    public LoginWindowController(LoginService loginService) {
        this.loginService = loginService;
    }

    public void initialize() {
        Image image = new Image("user.png");
        loginImage.setImage(image);
    }

    public void login() {
        loginButton.disableProperty().setValue(true);
        new Thread(() -> {
            try {
                loginService.login(username.getText(), password.getText());
                System.out.println("LOGGED IN CHANING UI");
            } catch (Exception exc) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Exception: " + exc.getMessage());
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    loginButton.disableProperty().setValue(false);
                });
            }
        }).start();
    }

    public void closeApp() {
        Platform.exit();
    }
}
