package orthae.com.github.medicalmanagementsystem.client.management.workdays;


import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.aspects.ui.DialogService;
import orthae.com.github.medicalmanagementsystem.client.management.workdays.dto.WorkdayDto;
import orthae.com.github.medicalmanagementsystem.client.management.workdays.service.WorkdayService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@Scope("prototype")
public class WorkdaysWindowController {

    private int employeeId;
    private WorkdayService workdayService;
    private DialogService dialogService;

//  TODO externalize to database ?

    @Value("${open-hour}")
    private int openHour;

    @Value("${close-hour}")
    private int closeHour;

//  bugged warning
    @SuppressWarnings("unused")
    @FXML
    private WorkdaysTableController workdaysTableController;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Spinner<Integer> startHourSpinner, endHourSpinner;

    public void initialize() {
        setupCalendar();
        setupSpinners();
        setupActionListener();
    }

    public WorkdaysWindowController(WorkdayService workdayService, DialogService dialogService) {
        this.workdayService = workdayService;
        this.dialogService = dialogService;
    }

    private void setupSpinners() {
        startHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(openHour, closeHour));
        endHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(openHour, closeHour));
    }

    public void initialize(int employeeId) {
        this.employeeId = employeeId;
        datePicker.setValue(LocalDate.now());
        refreshTable();
    }

    public void add() {
        try {
            WorkdayDto dto = new WorkdayDto();
            dto.setDate(datePicker.getValue());
            dto.setStartHour(LocalTime.of(startHourSpinner.getValue(), 0));
            dto.setEndHour(LocalTime.of(endHourSpinner.getValue(), 0));
            workdayService.createWorkday(employeeId, dto);
            refreshTable();
            dialogService.infoAlert("Workday created.");
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }

    private void refreshTable() {
        int month = datePicker.getValue().getMonth().getValue();
        int year = datePicker.getValue().getYear();
        List<WorkdayDto> list = workdayService.getByEmployeeIdAndMonth(employeeId, month, year);
        workdaysTableController.setItems(list.toArray(new WorkdayDto[0]));
    }


    private void setupActionListener(){
        datePicker.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if(oldValue.getYear() != newValue.getYear() || oldValue.getMonth().getValue() != newValue.getMonth().getValue())
                refreshTable();
        }));
    }

    private void setupCalendar(){
        datePicker.setEditable(false);
        datePicker.setValue(LocalDate.now());
    }
}
