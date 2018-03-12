package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.Calendar;

/**
 * Created by Imran on 04-02-2018.
 */

public class Notifier extends Fragment {


    TimePicker tp;
    int idName;
    public static final  long one_minute = 3000;
    Button b1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notifier,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        initialiser(view);

    }

    private void initialiser(View view) {

        tp = (TimePicker) view.findViewById(R.id.timePicker);
        b1 = (Button) view.findViewById(R.id.butonnsetNotification);

        b1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                getMedia();
                setVibrator();

                Calendar cl = Calendar.getInstance();
                   if(Build.VERSION.SDK_INT >= 23) {
                    cl.set(
                            cl.get(Calendar.YEAR),
                            cl.get(Calendar.MONTH),
                            cl.get(Calendar.DAY_OF_MONTH),
                            tp.getHour(),
                            tp.getMinute(),
                            0

                    );
                }
                else
                {

                    cl.set(
                            cl.get(Calendar.YEAR),
                            cl.get(Calendar.MONTH),
                            cl.get(Calendar.DAY_OF_MONTH),
                            tp.getCurrentHour(),
                            tp.getCurrentMinute(),
                            0

                    );

                }
                setAlarm(cl.getTimeInMillis() ,view);
            }
        });
    }


    private void setAlarm(long timeInMillis , View view) {



        AlarmManager al = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(getActivity(), AlarmClass.class);

        PendingIntent pi = PendingIntent.getBroadcast(getActivity() ,0 ,i ,0);


        al.setRepeating(AlarmManager.RTC_WAKEUP ,timeInMillis, /*86400000*/ 2000, pi);

        TastyToast.makeText(getActivity(),"Notification has set",Toast.LENGTH_LONG,TastyToast.SUCCESS).show();
    }


    public void getMedia() {
        final MediaPlayer mp = MediaPlayer.create(getActivity() ,R.raw.tweet);
        mp.start();
    }
    private void setVibrator() {
        Vibrator v =(Vibrator)getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
        v.vibrate(1000);
    }


}
