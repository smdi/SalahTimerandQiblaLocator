package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

/**
 * Created by Imran on 31-01-2018.
 */

public class HomeInitialiser {


    private String timings;
    private String timeZone;
    private String date;

    public HomeInitialiser(String timings , String  timeZone , String date) {

        this.timings = timings;
        this.date = date;
        this.timeZone = timeZone;
    }

    public String getTimings() {
        return timings;
    }
    public String getTimeZone() {
        return timeZone;
    }
    public String getDate()
    {
        return date;
    }


}
