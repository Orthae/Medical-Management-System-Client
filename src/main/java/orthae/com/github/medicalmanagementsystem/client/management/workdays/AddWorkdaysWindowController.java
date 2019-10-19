package orthae.com.github.medicalmanagementsystem.client.management.workdays;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.aspects.ui.DialogService;
import orthae.com.github.medicalmanagementsystem.client.management.workdays.dto.WorkdayDto;
import orthae.com.github.medicalmanagementsystem.client.management.workdays.service.WorkdayService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

@Component
@Scope("prototype")
public class AddWorkdaysWindowController {

    private int employeeId;
    private WorkdayService workdayService;
    private DialogService dialogService;

//  TODO externalize to database ?

    @Value("${open-hour}")
    private int openHour;

    @Value("${close-hour}")
    private int closeHour;

    @FXML
    private WorkdaysTableController workdaysTableController;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Spinner<Integer> startHourSpinner, endHourSpinner, yearSpinner;

    @FXML
    private ComboBox<String> monthCombobox;

    public void initialize() {
        setupCalendar();
        setupCombobox();
        setupSpinners();
        setupDate();
        setupActionListener();
    }

    public AddWorkdaysWindowController(WorkdayService workdayService, DialogService dialogService) {
        this.workdayService = workdayService;
        this.dialogService = dialogService;
    }

    private void setupSpinners() {
        startHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(openHour, closeHour));
        endHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(openHour, closeHour));
        yearSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, 2035, Calendar.getInstance().get(Calendar.YEAR)));
    }

    public void initialize(int employeeId) {
        this.employeeId = employeeId;
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
        } catch (Exception e) {
            dialogService.errorAlert(e.getMessage());
        }
    }

    private void refreshTable() {
        int month = monthCombobox.getSelectionModel().getSelectedIndex() + 1;
        int year = yearSpinner.getValue();
        List<WorkdayDto> list = workdayService.getByEmployeeIdAndMonth(employeeId, month, year);
        workdaysTableController.setItems(list.toArray(new WorkdayDto[0]));
    }

    private void setupCombobox() {
        monthCombobox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    }

    private void setupDate() {
        yearSpinner.getEditor().setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        monthCombobox.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH));
    }

    private void setupActionListener(){
        yearSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!oldValue.equals(newValue))
                refreshTable();
        });
        monthCombobox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(!oldValue.equals(newValue))
                refreshTable();
        });
    }

    private void setupCalendar(){
        datePicker.setEditable(false);
        datePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if(item.getMonth().getValue() != monthCombobox.getSelectionModel().getSelectedIndex() + 1 || item.getYear() != yearSpinner.getValue())
                    setDisable(true);
            }
        });
    }
}
