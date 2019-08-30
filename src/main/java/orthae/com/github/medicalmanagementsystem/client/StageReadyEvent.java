package orthae.com.github.medicalmanagementsystem.client;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

class StageReadyEvent extends ApplicationEvent {

    Stage getStage(){
        return (Stage) getSource();
    }

    StageReadyEvent(Object source) {
        super(source);
    }
}
