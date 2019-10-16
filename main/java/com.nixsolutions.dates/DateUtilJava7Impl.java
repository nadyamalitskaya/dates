package com.nixsolutions.dates;

import com.nixsolutions.ppp.dates.DateUtilJava7;

import java.text.DateFormat;
import java.util.*;

public class DateUtilJava7Impl implements DateUtilJava7 {

    public static void main(String[] args) {
        DateUtilJava7Impl date = new DateUtilJava7Impl();

        // Between two dates
        Date date1 = new Date(
                GregorianCalendar.getInstance().getTimeInMillis());
        Calendar calendar2 = new GregorianCalendar();
        calendar2.set(Calendar.YEAR, 2025);
        calendar2.set(Calendar.MONTH, Calendar.JULY);
        calendar2.set(Calendar.DATE, 18);
        Date date2 = new Date(calendar2.getTimeInMillis());

        System.out.println(date.between(date1, date2));

        // Days in month
        for (int i : date.daysInMonth(2016)) {
            System.out.println(i);
        }

        // Mondays in month
        System.out.println(Arrays.toString(date.mondays(0, 2018)));

        // Friday 13
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DATE, 13);
        Date date13 = new Date(calendar.getTimeInMillis());
        System.out.println(date.idFridays13(date13));

        calendar.set(2018, Calendar.JANUARY, 1);

        // Format full
        System.out.println(
                date.formatFull(new Date(calendar.getTimeInMillis()), "en"));
        System.out.println(
                date.formatFull(new Date(calendar.getTimeInMillis()), "ru"));
    }

    // DONE
    @Override public String between(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        int years, months, days;

        if (date1.before(date2)) {
            cal1.setTime(date1);
            cal2.setTime(date2);
        } else {
            cal1.setTime(date2);
            cal2.setTime(date1);
        }

        years = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);

        if (cal2.get(Calendar.MONTH) < cal1.get(Calendar.MONTH)) {
            years -= 1;
            months = cal2.get(Calendar.MONTH) + 12 - cal1.get(Calendar.MONTH);
        } else {
            months = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
        }

        int cal1DaysAmount = cal1.get(Calendar.DAY_OF_MONTH);
        int cal2DaysAmount = cal2.get(Calendar.DAY_OF_MONTH);

        if (cal2DaysAmount < cal1DaysAmount) {
            if (months == 0) {
                months = 12;
                years -= 1;
            } else {
                months -= 1;
            }
            days = cal2.getActualMaximum(Calendar.DAY_OF_MONTH) - cal1DaysAmount
                    + cal2DaysAmount;
        } else {
            days = cal2DaysAmount - cal1DaysAmount;
        }

        StringBuilder result = new StringBuilder();

        if (years > 0) {
            result.append(years).append(" year");

            if (years > 1)
                result.append("s ");
            else
                result.append(" ");
        }
        if (months > 0) {
            result.append(months).append(" month");

            if (months > 1)
                result.append("s ");
            else
                result.append(" ");
        }
        if (days > 0) {
            result.append(days).append(" day");

            if (days > 1)
                result.append("s ");
            else
                result.append(" ");
        }
        return result.toString();
    }

    // DONE
    public int[] daysInMonth(int year) {
        int[] daysOfMonths = new int[12];
        Calendar calendar = new GregorianCalendar();
        for (int i = 0; i < 12; i++) {
            calendar.set(year, i, 1);
            daysOfMonths[i] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        return daysOfMonths;
    }

    // DONE
    public Date[] mondays(int month, int year) {
        ArrayList<Date> mondaysInMonth = new ArrayList<>();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year, month, 1);
        int amountOfDatesInMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < amountOfDatesInMonth; i++) {
            calendar.set(year, month, i);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                mondaysInMonth.add(new Date(calendar.getTimeInMillis()));
            }
        }
        return mondaysInMonth.toArray(new Date[0]);
    }

    // DONE
    public boolean idFridays13(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
        if (calendar.get(GregorianCalendar.DAY_OF_MONTH) != 13)
            return false;
        return calendar.get(GregorianCalendar.DAY_OF_WEEK)
                == GregorianCalendar.FRIDAY;
    }

    // DONE
    public String formatFull(Date date, String country) {
        DateFormat df = DateFormat
                .getDateInstance(DateFormat.LONG, new Locale(country));
        return df.format(date);
    }
}
