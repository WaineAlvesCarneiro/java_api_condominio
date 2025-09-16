package com.wa.condominio.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtils {

    /**
     * Converte um LocalDate local para um LocalDateTime em UTC.
     * O horário é definido para o início do dia (meia-noite).
     *
     * @param localDate A data local a ser convertida.
     * @return O LocalDateTime correspondente em UTC.
     */
    public static LocalDateTime toUTC(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        // Converte o LocalDate para LocalDateTime no fuso horário do sistema
        ZonedDateTime zonedLocal = localDate.atStartOfDay(ZoneId.systemDefault());

        // Converte para o fuso horário UTC e retorna como LocalDateTime
        ZonedDateTime zonedUtc = zonedLocal.withZoneSameInstant(ZoneId.of("UTC"));
        return zonedUtc.toLocalDateTime();
    }

    /**
     * Converte um LocalDateTime em UTC para um LocalDate local.
     *
     * @param utcDateTime O LocalDateTime em UTC a ser convertido.
     * @return O LocalDate local correspondente.
     */
    public static LocalDate fromUTC(LocalDateTime utcDateTime) {
        if (utcDateTime == null) {
            return null;
        }
        ZonedDateTime zonedUtc = utcDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedLocal = zonedUtc.withZoneSameInstant(ZoneId.systemDefault());
        return zonedLocal.toLocalDate();
    }
}