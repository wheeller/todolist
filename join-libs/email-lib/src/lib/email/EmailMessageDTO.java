package lib.email;

import lombok.Data;

@Data
public class EmailMessageDTO {
    private String to;
    private String subject;
    private String text;

    public EmailMessageDTO(String to, String subject, String text){
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
}
