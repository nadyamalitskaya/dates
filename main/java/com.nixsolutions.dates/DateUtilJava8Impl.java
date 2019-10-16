package com.nixsolutions.dates;

import com.nixsolutions.ppp.dates.DateUtilJava8;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class DateUtilJava8Impl implements DateUtilJava8 {

    public static void main(String[] args) {

        DateUtilJava8Impl date = new DateUtilJava8Impl();

        // Between two dates
        System.out.println(
                date.between(LocalDate.of(2020, 10, 3), LocalDate.now()));

        // Friday 13
        System.out.println(
                date.idFridays13(LocalDate.of(2019, Month.SEPTEMBER, 13)));

        // Full Format
        System.out.println(
                date.formatFullJava8(LocalDateTime.of(2018, 1, 1, 12, 10),
                        "en"));
        System.out.println(
                date.formatFullJava8(LocalDateTime.of(2018, 1, 1, 12, 10),
                        "ru"));

        // Mondays in month
        LocalDate local = LocalDate.of(2018, 1, 1);
        Instant in = local.atStartOfDay().toInstant(ZoneOffset.MAX);
        System.out.println(Arrays.toString(date.mondays(in)));
    }

    // DONE
    @Override public String between(LocalDate date1, LocalDate date2) {
        if (date1.isAfter(date2)) {
            LocalDate temp = date1;
            date1 = date2;
            date2 = temp;
        }
        Period period = Period.between(date1, date2);
        StringBuilder sb = new StringBuilder();
        int days = period.getDays();
        int months = period.getMonths();
        int years = period.getYears();
        if (years > 0) {
            sb.append(years).append(" year");

            if (years > 1)
                sb.append("s ");
            else
                sb.append(" ");
        }
        if (days > 0) {
            sb.append(days).append(" day");

            if (days > 1)
                sb.append("s ");
            else
                sb.append(" ");
        }
        if (months > 0) {
            sb.append(months).append(" month");

            if (months > 1)
                sb.append("s ");
            else
                sb.append(" ");
        }
        return sb.toString();
    }

    // DONE
    @Override public LocalDate[] mondays(Instant instant) {
        LocalDateTime ldt = LocalDateTime
                .ofInstant(instant, ZoneId.systemDefault());
        ArrayList<LocalDate> mondaysInMonth = new ArrayList<>();
        LocalDate ld = LocalDate.of(ldt.getYear(), ldt.getMonth(), 1);
        int amountOfDatesInMonth = ld.getMonth()
                .length(ldt.getYear() % 4 == 0 && ldt.getYear() % 100 != 0
                        || ldt.getYear() % 400 == 0);
        for (int i = 1; i < amountOfDatesInMonth; i++) {
            ld = LocalDate.of(ld.getYear(), ld.getMonth(), i);
            if (ld.getDayOfWeek() == DayOfWeek.MONDAY) {
                mondaysInMonth.add(ld);
            }
        }
        return mondaysInMonth.toArray(new LocalDate[0]);
    }

    // DONE
    @Override public boolean idFridays13(LocalDate date) {
        if (date.getDayOfMonth() != 13)
            return false;
        return date.getDayOfWeek().getValue() == 5;
    }

    // DONE
    @Override public String formatFullJava8(LocalDateTime date,
            String country) {
        if (country.equals("ru")) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                    .ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.LONG)
                    .withLocale(new Locale(country))
                    .withZone(ZoneId.systemDefault());
            return date.format(dateTimeFormatter);
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.LONG)
                .withLocale(new Locale(country))
                .withZone(ZoneId.systemDefault());
        return date.format(dateTimeFormatter);
    }

}
