package br.com.pix_service.ewallet.infrastructure.utils;

import br.com.pix_service.ewallet.infrastructure.exceptions.InvalidArgumentException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static void notNull(Object... sources) {

        try {
            for (Object data : sources) {
                if (ObjectUtils.isEmpty(data)) {
                    throw new InvalidArgumentException();
                }
            }
        } catch (Exception e) {
            throw new InvalidArgumentException();
        }
    }
}
