package todolist.exception;

import lombok.Data;

@Data
class ExceptionResponseBody {
    public String error;
    public String description;

    ExceptionResponseBody(String error, String text) {
        this.error = error;
        this.description = text;
    }
}
