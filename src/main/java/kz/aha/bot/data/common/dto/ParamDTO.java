package kz.aha.bot.data.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Akhmet.Sulemenov
 * created 28.06.2023
 */
@Data
@AllArgsConstructor
public class ParamDTO {
    private String key;
    private String value;
}
