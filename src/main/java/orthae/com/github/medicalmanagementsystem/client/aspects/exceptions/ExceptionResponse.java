package orthae.com.github.medicalmanagementsystem.client.aspects.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {
    private long timestamp;
    private int statusCode;
    private String requestType;
    private String message;
    private String path;

    @Override
    public String toString(){
        return statusCode + " " + requestType + " " + path + "\n" + message;
    }

}
