package entity.report;

import java.text.DecimalFormat;

public class TimeHelper {
    public static double transformToSecond(double timeStamp) {
        double newTime = timeStamp / 1000000000;
        DecimalFormat newFormat = new DecimalFormat("#.##");
        return Double.valueOf(newFormat.format(newTime));
    }

    public static double transformToMinute(double timeStamp) {
        double newTime = transformToSecond(timeStamp) / 60;
        DecimalFormat newFormat = new DecimalFormat("#.##");
        return Double.valueOf(newFormat.format(newTime));
    }

    public static String transformToSecondFormat(double timeStamp) {
        return String.valueOf(transformToSecond(timeStamp));
    }

    public static String transformToMinuteFormat(double timeStamp) {
        return String.valueOf(transformToMinute(timeStamp));
    }
}
