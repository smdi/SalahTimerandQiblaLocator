package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

import java.util.ArrayList;

/**
 * Created by Imran on 20-11-2017.
 */

public class AndroidAdapter  {

    int mresourse;

    String head,hadees ;


    public AndroidAdapter() {
    }

    public AndroidAdapter(String head , String hadees )
    {
        this.head=head;
        this.hadees=hadees;
    }

    public int getMresourse() {
        return mresourse;
    }

    public String getHead() {
        return head;
    }

    public String getHadees() {
        return hadees;
    }
}
