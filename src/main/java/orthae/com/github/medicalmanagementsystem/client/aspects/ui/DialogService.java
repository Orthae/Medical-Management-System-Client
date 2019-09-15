package orthae.com.github.medicalmanagementsystem.client.aspects.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DialogService {

    private ApplicationContext context;

    public DialogService(ApplicationContext context) {
        this.context = context;
    }

    public FXMLLoader fxmlLoader(String fxmlPath){
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader;
    }

    public Stage stageSetup(FXMLLoader loader, Window owner) throws IOException {
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    public Alert errorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        return alert;
    }

    public Alert warringAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert;
    }


}
