package orthae.com.github.medicalmanagementsystem.client.aspects.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//TODO Rewrite server and client exceptions
public class Error {
    private String message;
    private String[] errors;

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder(message);
        builder.append("\n");
        for(String s : errors){
        builder.append(s);
        builder.append("\n");
        }
        return builder.toString();
    }

}
