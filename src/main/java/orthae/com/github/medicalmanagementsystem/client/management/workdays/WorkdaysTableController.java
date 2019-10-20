package orthae.com.github.medicalmanagementsystem.client.management.workdays;


import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import orthae.com.github.medicalmanagementsystem.client.aspects.ui.DialogService;
import orthae.com.github.medicalmanagementsystem.client.management.workdays.dto.WorkdayDto;
import orthae.com.github.medicalmanagementsystem.client.management.workdays.service.WorkdayService;

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
    @FXML
    private TableColumn<WorkdayDto, Number> buttonColumn;

    @Value("${open-hour}")
    private int openHour;

    @Value("${close-hour}")
    private int closeHour;

    private WorkdayService workdayService;
    private DialogService dialogService;

    public WorkdaysTableController(WorkdayService workdayService, DialogService dialogService) {
        this.workdayService = workdayService;
        this.dialogService = dialogService;
    }

    public void initialize(){
        tableSetup();
    }

    public void setItems(WorkdayDto... items){
        workdaysTable.getItems().clear();
        workdaysTable.getItems().addAll(items);
    }

    private void tableSetup(){
        dateColumn.setCellValueFactory(param -> new ObjectBinding<LocalDate>() {
            @Override
            protected LocalDate computeValue() {
                return param.getValue().getDate();
            }
        });

        startHourColumn.setCellValueFactory( param -> new ObjectBinding<LocalTime>() {
            @Override
            protected LocalTime computeValue() {
                return param.getValue().getStartHour();
            }
        });
        setupSpinnerColumn(startHourColumn);

        endHourColumn.setCellValueFactory( param -> new ObjectBinding<LocalTime>() {
            @Override
            protected LocalTime computeValue() {
                return param.getValue().getEndHour();
            }
        });
        setupSpinnerColumn(endHourColumn);

        buttonColumn.setCellValueFactory(param -> new ObjectBinding<Number>() {
            @Override
            protected Number computeValue() {
                return param.getValue().getId();
            }
        });
        buttonColumn.setCellFactory(param -> new TableCell<WorkdayDto, Number>(){
            @Override
            protected void updateItem(Number item, boolean empty) {
                if(empty || getTableRow() == null){
                    setGraphic(null);
                }
                else {
                    WorkdayDto dto = ((WorkdayDto) getTableRow().getItem());
                    Button updateButton = new Button("Update");
                    updateButton.setOnAction(event -> updateButtonAction(dto));
                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(event -> deleteButtonAction(dto));
                    setGraphic(new HBox(deleteButton, updateButton));
                }
            }
        });
    }

    private void setupSpinnerColumn(TableColumn<WorkdayDto, LocalTime> column){
        column.setCellFactory(param -> {
            Spinner<Integer> spinner = new Spinner<>();
            return new TableCell<WorkdayDto, LocalTime>() {
                @Override
                protected void updateItem(LocalTime item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(openHour, closeHour, item.getHour()));
                        setGraphic(spinner);
                    }
                }
            };
        });
    }

    private void deleteButtonAction(WorkdayDto dto){
        try{
            Alert alert = dialogService.warringAlert("Are you sure you want to delete selected workday?");
            if(alert.getResult() == ButtonType.YES) {
                workdayService.deleteWorkday(dto.getId());
                workdaysTable.getItems().remove(dto);
                dialogService.infoAlert("Workday deleted");
            }
        } catch (Exception e){
            dialogService.errorAlert(e.getMessage());
        }
    }

    private void updateButtonAction(WorkdayDto dto){

    }



}
