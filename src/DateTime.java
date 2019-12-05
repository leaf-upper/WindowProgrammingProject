import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTime
{
    Date date;
    SimpleDateFormat formatter;
    Calendar calendar;
    public Calendar getCalendar() {return calendar;}

    DateTime()
    {
        date = new Date();
        calendar = new GregorianCalendar(Locale.KOREA);
        formatter = new SimpleDateFormat("yyyyMMdd");
    }

    public String getDateTime()
    {
        String time = formatter.format(date);
        return time;
    }

    public String getBeforeOneDayDateTime()
    {
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);

        return formatter.format(calendar.getTime());
    }

}
