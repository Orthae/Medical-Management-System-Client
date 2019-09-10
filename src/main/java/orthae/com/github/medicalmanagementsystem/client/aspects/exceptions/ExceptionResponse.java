package orthae.com.github.medicalmanagementsystem.client.aspects.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExceptionResponse {
    private int status;
    private Date timestamp;
    private String error;
    private String message;
    private String path;

    @Override
    public String toString(){
        return "Timestamp: " + timestamp.toString() + "\n"  +
                "Status code:" + status + "\n" +
                "Path: " + path + "\n" +
                "Error: " + error + "\n\n" +
                message + "\n";
    }

}
