package br.com.pix_service.ewallet.infrastructure.utils;

import br.com.pix_service.ewallet.infrastructure.exceptions.InvalidArgumentException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import static org.apache.commons.lang3.StringUtils.EMPTY;

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

    public static String cleanCPFAndPhone(String data) {
        if (data == null) {
            return EMPTY;
        }
        var numbers = data.replaceAll("\\D", "");
        if (numbers.length() != 11) {
            throw new InvalidArgumentException("Invalid CPF or phone number length");
        }
        return numbers;
    }

    public static String formatAndMaskCpf(String cpf) {
        if (cpf == null) {
            return EMPTY;
        }
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11) {
            throw new InvalidArgumentException("Invalid CPF length");
        }
        return cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "***.$2.$3-**");
    }


}
