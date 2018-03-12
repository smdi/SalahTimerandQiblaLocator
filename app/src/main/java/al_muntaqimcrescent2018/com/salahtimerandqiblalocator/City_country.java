package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.tomer.fadingtextview.FadingTextView;

import java.util.ArrayList;

/**
 * Created by Imran on 04-02-2018.
 */

public class City_country extends Fragment {

    City_Country_store city_country_store;
    boolean has = false;
    EditText ed1 ,ed2;
    Button bt1;

    public AutoCompleteTextView ac,ac1;

    ArrayList<String> arrayList ,arrayList1;
    ArrayAdapter<String> arrayAdapter;
    FadingTextView fadingTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_country,container ,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        initialise(view);
        displayDatabaseInfo();
        buttonresults(view);
    }

    private void buttonresults(View view) {


        bt1 = (Button) view.findViewById(R.id.get_The_Results);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    startplayer(view);

                    String x = ac.getText().toString().trim();
                    String y = ac1.getText().toString().trim();

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("Email", getActivity().MODE_PRIVATE).edit();
                editor.putString("country", x);
                editor.putString("city",y);
                editor.apply();
                    gotoSalahTimer(x,y);

            }


        });

    }

    private void startplayer(View view) {

        final MediaPlayer mp = MediaPlayer.create(getActivity() ,R.raw.tweet);
        mp.start();
    }

    private void setVibrator() {
        Vibrator v =(Vibrator)getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
        v.vibrate(1000);
    }


    private void gotoSalahTimer(String x,String y){




        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            Bundle bundle = new Bundle();
            bundle.putString("city",x);
            bundle.putString("country",y);
            Fragment fragment = new SalahTimer();
            fragment.setArguments(bundle);
            android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.citycountry,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();



        }
        else {
            TastyToast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG,TastyToast.ERROR).show();
           setVibrator();
        }



    }


    private void displayDatabaseInfo() {

        SharedPreferences getShare = getActivity().getSharedPreferences("Email",getActivity().MODE_PRIVATE);
        String country = getShare.getString("country","Country");
        String city    = getShare.getString("city","city");
                arrayList.add(country);
                arrayList1.add(city);
    }

    private void initialise(View view) {

        fadingTextView = (FadingTextView) view.findViewById(R.id.Qtext);
        fadingTextView.setTimeout(FadingTextView.SECONDS ,2);



        arrayList = new ArrayList<String>();
        arrayList1 = new ArrayList<String>();


        int i =1 ;

        if(i==1)
        {
            i=2;
            arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,arrayList);
            final AutoCompleteTextView actv= (AutoCompleteTextView) view.findViewById(R.id.edit_x);
            actv.setThreshold(1);
            actv.setAdapter(arrayAdapter);
            actv.setTextColor(Color.RED);
            ac = actv;
        }
        if(i==2)
        {
            i=1;
            arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,arrayList1);
            final AutoCompleteTextView actv1= (AutoCompleteTextView) view.findViewById(R.id.edit_y);
            actv1.setThreshold(1);//will start working from first character
            actv1.setAdapter(arrayAdapter);//setting the adapter data into the AutoCompleteTextView
            actv1.setTextColor(Color.RED);
            ac1= actv1;
        }



    }

}
