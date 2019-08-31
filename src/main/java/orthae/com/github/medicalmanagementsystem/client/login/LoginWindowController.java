package orthae.com.github.medicalmanagementsystem.client.login;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    private ApplicationContext context;

    public LoginWindowController(LoginService loginService, ApplicationContext context) {
        this.loginService = loginService;
        this.context = context;
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
                Platform.runLater(() ->{
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setControllerFactory(context::getBean);
                        loader.setLocation(getClass().getResource("/mainWindow.fxml"));
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        loginButton.getScene().getWindow().hide();
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
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
