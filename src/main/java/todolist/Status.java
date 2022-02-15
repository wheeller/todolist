package todolist;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Optional;

public enum Status {
    NEW, DONE;

    @JsonCreator
    public static Status getFromJson(String value) {
        return Optional.ofNullable(value)
                .map(String::toUpperCase)
                .map(Status::valueOf)
                .orElse(null);
    }
}
