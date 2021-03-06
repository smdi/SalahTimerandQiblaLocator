package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.sdsmdg.tastytoast.TastyToast;

public class Bottom extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;


            boolean check = checkConnection();


            switch (item.getItemId()) {


                case R.id.City_Country :


                    fragment = new City_country();

                    break;

                case R.id.qibladir :

                    if(check) {
                        fragment = new Qibla();
                    }
                    else{
                        TastyToast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG,TastyToast.ERROR).show();

                        setVibrator();
                    }
                    break;

                case R.id.alarm :

                    fragment = new Notifier();
                    break;

                case R.id.hadees :

                    fragment = new Read();
                    break;



                case R.id.masjid_finder :

                    if(check) {

                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Bottom.this);
                        builder.setMessage("Do you want to find masjid near you ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Uri str = Uri.parse("http://maps.google.com/maps?daddr=Masjid");

                                        Intent intent = new Intent(Intent.ACTION_VIEW,str);
                                        intent.setPackage("com.google.android.apps.maps");
                                        if(intent.resolveActivity(getPackageManager())!=null)
                                        {
                                            startActivity(intent);
                                        }
                                        else {

                                            TastyToast.makeText(getApplicationContext(), "Unable to process", Toast.LENGTH_LONG,TastyToast.CONFUSING).show();

                                        }
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                               dialogInterface.cancel();
                            }
                        });
//
                        android.support.v7.app.AlertDialog alert =builder.create();
                        alert.setTitle("Masjid Finder");
                        alert.show();
                    }
                    else{
                        TastyToast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG,TastyToast.ERROR).show();
                        setVibrator();
                    }
                    break;



            }

            return loadFragment(fragment);
        }
    };

    public boolean loadFragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            ft.commit();
            return  true;
        }
        return false;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        getSupportActionBar().hide();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new City_country());
    }


    private boolean checkConnection() {


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            return true;

        } else {
            return false;
        }


    }
    private void setVibrator() {
        Vibrator v =(Vibrator)getSystemService(VIBRATOR_SERVICE);
        v.vibrate(1000);
    }


}
