package orthae.com.github.medicalmanagementsystem.client.management.workdays.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class WorkdayDto {
    private int id;
    private LocalDate date;
    private LocalTime startHour;
    private LocalTime endHour;
}
