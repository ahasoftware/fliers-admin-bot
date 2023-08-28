package kz.aha.bot.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultResponse {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("error")
    private Object error;

    @JsonProperty("data")
    private Object data;

    public static DefaultResponse success() {
        return DefaultResponse.builder().success(true).build();
    }

    public static DefaultResponse success(Object data) {
        return DefaultResponse.builder().success(true).data(data).build();
    }

    public static DefaultResponse error() {
        return DefaultResponse.builder().success(false).build();
    }

    public static DefaultResponse error(Object type) {
        return DefaultResponse.builder().success(false).error(type).build();
    }

    public static DefaultResponse error(String message) {
        return DefaultResponse.builder().success(false).message(message).build();
    }

    public static DefaultResponse error(Object data, String message) {
        return DefaultResponse.builder().success(false).data(data).message(message).build();
    }
}
