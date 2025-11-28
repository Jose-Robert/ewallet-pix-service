package br.com.pix_service.ewallet.infrastructure.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndToEndIdGeneratorUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static String generateEndToEndId() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        var uuid = UUID.randomUUID().toString();
        var data = uuid.substring(0, 8);
        return "E2E" + timestamp + data.toUpperCase(Locale.ROOT);
    }
}
