package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;


public class AlarmClass extends BroadcastReceiver {

    int global;

    MediaPlayer mp;
    static Context ctx;
    Context apllicationContext;

    @Override
    public void onReceive(Context context, Intent intent) {

//        Vibrator v =(Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
//        v.vibrate(1000);


     try {

          mp = MediaPlayer.create(context, R.raw.message_bell_s8);
           mp.start();


     }
     catch (Exception e){

         e.printStackTrace();
     }

        TastyToast.makeText(context, "its Hadees Time", Toast.LENGTH_LONG, TastyToast.SUCCESS).show();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String title = category(context);
        String message = hadees(context);

        ctx = context;

        Intent i1 = new Intent(context, Bottom.class);
        PendingIntent pi1 = PendingIntent.getActivity(context, 1, i1, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.mosquenotif);

        NotificationCompat.BigTextStyle  bigTextStyle  = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(message);
        bigTextStyle.setBigContentTitle(title);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.mosquenotif)
                        .setColor(Color.WHITE)
                        .setLights(1,1,1)
                        .setContentText(" Slide down to read completely ")
                        .setAutoCancel(true)
                        .setTicker(title)
                        .setLargeIcon(bitmap)
                        .setStyle(bigTextStyle)
                .setContentIntent(pi1);



        NotificationManager nf = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nf.notify(0, mBuilder.build());

    }

    private String category(Context v) {

        global = load(v);

        String message = "";

        if(global>=1&&global<=65)
        {
            message =Reference.InnerReference.hadeesCategory1;
        }
        else  if(global>=66&&global<=130)
        {
            message =Reference.InnerReference.hadeesCategory2;
        }
        else  if(global>=131&&global<=195)
        {
            message =Reference.InnerReference.hadeesCategory3;
        } else if (global>=196&&global<=260) {
            message =Reference.InnerReference.hadeesCategory4;
        }
        else if(global>=261&&global<=293)
        {
            message =Reference.InnerReference.hadeesCategory5;
        }
        else if(global>=294&&global<=358){
            message =Reference.InnerReference.hadeesCategory6;
        }
        return message;
    }

    private String hadees(Context v) {

        global = load(v);

        String message = "";

        message = Reference.InnerReference.get(global);

        return message;
    }

    public int load(Context v) {

        int in;
        try {


            SharedPreferences prefs =
                    PreferenceManager.getDefaultSharedPreferences(v);

             in = prefs.getInt("var", 1);
            global = in;


        } catch (NullPointerException e) {
            e.printStackTrace();

        }
        return global;
    }

}