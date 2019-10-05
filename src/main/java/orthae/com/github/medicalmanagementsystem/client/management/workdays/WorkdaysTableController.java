package orthae.com.github.medicalmanagementsystem.client.management.workdays;


import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.management.workdays.dto.WorkdayDto;

import java.time.LocalDate;
import java.time.LocalTime;


@Component
@Scope("prototype")
public class WorkdaysTableController {

    @FXML
    private TableView<WorkdayDto> workdaysTable;

    @FXML
    private TableColumn<WorkdayDto, LocalDate> dateColumn;
    @FXML
    private TableColumn<WorkdayDto, LocalTime> startHourColumn, endHourColumn;

    private void tableSetup(){
        dateColumn.setCellValueFactory(param -> new ObjectBinding<LocalDate>() {
            @Override
            protected LocalDate computeValue() {
                return param.getValue().getDate();
            }
        });
    }

    public void initialize(){
        tableSetup();
    }

    public void setItems(WorkdayDto... items){
        workdaysTable.getItems().clear();
        workdaysTable.getItems().addAll(items);
    }

}
