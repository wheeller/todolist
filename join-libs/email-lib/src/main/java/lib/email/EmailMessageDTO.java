package lib.email;

import lombok.Data;

@Data
public class EmailMessageDTO {
    private String to;
    private String subject;
    private String text;

    // Caused by: com.fasterxml.jackson.databind.exc.InvalidDefinitionException:
    // Cannot construct instance of `lib.email.EmailMessageDTO` (no Creators, like default constructor, exist):
    // cannot deserialize from Object value (no delegate- or property-based Creator)
    public EmailMessageDTO(){};

    public EmailMessageDTO(String to, String subject, String text){
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
}
