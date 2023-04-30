package ru.agr.backend.looksliketests.utils.databuilder;

import lombok.experimental.UtilityClass;
import ru.agr.backend.looksliketests.db.entity.main.Option;

/**
 * @author Arslan Rabadanov
 */
@UtilityClass
public class OptionTestDataUtils {

    public static Option option() {
        return Option.builder()
                .build();
    }
}
