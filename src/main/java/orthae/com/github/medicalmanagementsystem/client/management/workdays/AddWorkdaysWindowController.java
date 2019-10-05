package orthae.com.github.medicalmanagementsystem.client.management.workdays;


import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.management.workdays.dto.WorkdayDto;
import orthae.com.github.medicalmanagementsystem.client.management.workdays.service.WorkdayService;

import java.time.LocalTime;
import java.util.List;

@Component
@Scope("prototype")
public class AddWorkdaysWindowController {

    private WorkdayService workdayService;
    private int employeeId;

    private final int LOWEST_START_HOUR = 8;
    private final int HIGHEST_START_HOUR = 16;
    private final int LOWEST_END_HOUR = 12;
    private final int HIGHEST_END_HOUR = 20;

    @FXML
    private WorkdaysTableController workdaysTableController;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Spinner<Integer> startHourSpinner, endHourSpinner;

    public void initialize(){
        datePicker.setEditable(false);
        setupSpinners();
    }

    private void setupSpinners(){
        startHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(LOWEST_START_HOUR, HIGHEST_START_HOUR));
        endHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(LOWEST_END_HOUR, HIGHEST_END_HOUR));
    }

    public void initialize(int employeeId){
        this.employeeId = employeeId;
        refreshTable();
    }

    public AddWorkdaysWindowController(WorkdayService workdayService) {
        this.workdayService = workdayService;
    }


    public void add(){
        WorkdayDto dto = new WorkdayDto();
        if(datePicker.getValue() == null)
            throw new RuntimeException("IS NULL");
        dto.setDate(datePicker.getValue());
        dto.setStartHour(LocalTime.of(startHourSpinner.getValue(), 0));
        dto.setEndHour(LocalTime.of(endHourSpinner.getValue(), 0));
        workdayService.createWorkday(employeeId, dto);
        refreshTable();
    }


    private void refreshTable(){
        List<WorkdayDto> list = workdayService.getByEmployeeId(employeeId);
        workdaysTableController.setItems(list.toArray(new WorkdayDto[0]));
    }



}
