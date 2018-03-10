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
        buttonresults(view);
    }

    private void buttonresults(View view) {


        bt1 = (Button) view.findViewById(R.id.get_The_Results);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startplayer(view);

                try {

                    String x = ac.getText().toString().trim();
                    String y = ac1.getText().toString().trim();



                    SQLiteDatabase db = city_country_store.getReadableDatabase();

                    String[] project = {PetsContract.PetEntry._ID, PetsContract.PetEntry.COLUMN_City, PetsContract.PetEntry.COLUMN_Country};

                    Cursor cursor = db.query(
                            PetsContract.PetEntry.TABLE_NAME,
                            project,
                            null,
                            null,
                            null,
                            null,
                            null
                    );


                    try {

                        String currenName = "";
                        String currenBreed = "";

                        int colounName = cursor.getColumnIndex(PetsContract.PetEntry.COLUMN_City);
                        int columnBreed = cursor.getColumnIndex(PetsContract.PetEntry.COLUMN_Country);

                        while (cursor.moveToNext()) {

                            currenName = cursor.getString(colounName);
                            currenBreed = cursor.getString(columnBreed);

                            if (currenName.equals(x) && currenBreed.equals(y)) {

                                dontSetup(x, y);
                                has = true;
                                break;
                            }
                            if (currenName.equals(x) && currenBreed.equals("dont")) {

                                dontSetup(x, y);
                                has = true;
                                break;
                            }
                            if (currenName.equals("dont") && currenBreed.equals(y)) {

                                dontSetup(x, y);
                                has = true;
                                break;
                            }

                        }

                        if (has == false) {

                            if (currenName.equals(x) && !currenBreed.equals(y)) {
                                setupY(x, y);
                            } else if (!currenName.equals(x) && currenBreed.equals(y)) {
                                setupX(x, y);
                            } else if(!currenName.equals(x) && !currenBreed.equals(y)){
                                goSetup(x, y);
                            }

                        }

                    }
                    finally {
                        cursor.close();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }


        });

    }

    private void startplayer(View view) {

        final MediaPlayer mp = MediaPlayer.create(getActivity() ,R.raw.tweet);
        mp.start();
    }


    private void setupX(String x, String y) {

        if (x.length()>=2&&y.length()>=2)
        {
            TastyToast.makeText(getActivity(),"Fetching Data ...",Toast.LENGTH_LONG,TastyToast.SUCCESS).show();
            insertData(x,"dont");
            gotoSalahTimer(x,y);
        }
        else {
            TastyToast.makeText(getActivity(), "Enter the City and Country", Toast.LENGTH_LONG ,TastyToast.CONFUSING).show();
            setVibrator();
        }
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

    private void setupY(String x, String y) {

        if (x.length()>=2&&y.length()>=2)
        {
            TastyToast.makeText(getActivity(),"Fetching Data ...",Toast.LENGTH_LONG,TastyToast.SUCCESS).show();
            insertData("dont",y);
            gotoSalahTimer(x,y);
        }
        else {
            TastyToast.makeText(getActivity(), "Enter the City and Country", Toast.LENGTH_LONG,TastyToast.CONFUSING).show();
           setVibrator();
        }
    }

    private void goSetup(String x, String y) {

        if (x.length()>=2&&y.length()>=2)
        {
            TastyToast.makeText(getActivity(),"Fetching Data ...",Toast.LENGTH_LONG,TastyToast.SUCCESS).show();
            insertData(x,y);
            gotoSalahTimer(x,y);
        }
        else {
            TastyToast.makeText(getActivity(), "Enter the City and Country", Toast.LENGTH_LONG,TastyToast.CONFUSING).show();
          setVibrator();
        }
    }

    private void dontSetup(String x ,String y) {

        if (x.length()>=2&&y.length()>=2)
        {
            TastyToast.makeText(getActivity(), "Fectching Data ...", Toast.LENGTH_LONG,TastyToast.SUCCESS).show();
           gotoSalahTimer(x,y);
        }
        else {
            TastyToast.makeText(getActivity(), "Enter the City and Country", Toast.LENGTH_LONG,TastyToast.CONFUSING).show();

            setVibrator();

        }
    }
    private void displayDatabaseInfo() {

        SQLiteDatabase db = city_country_store.getReadableDatabase();

        String []project = {PetsContract.PetEntry._ID, PetsContract.PetEntry.COLUMN_City, PetsContract.PetEntry.COLUMN_Country};

        Cursor cursor = db.query(
                PetsContract.PetEntry.TABLE_NAME,
                project,
                null,
                null,
                null,
                null,
                null
        );


        try {

            int colounName = cursor.getColumnIndex(PetsContract.PetEntry.COLUMN_City);
            int columnBreed = cursor.getColumnIndex(PetsContract.PetEntry.COLUMN_Country);
            while(cursor.moveToNext())
            {

                String currenName = cursor.getString(colounName);
                String currenBreed = cursor.getString(columnBreed);

                arrayList.add(currenName);
                arrayList1.add(currenBreed);
            }
        } finally {
            cursor.close();
        }

    }

    private void insertData(String x ,String y) {

        SQLiteDatabase sqldb =city_country_store.getWritableDatabase();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int i1 = preferences.getInt("int",1);


        if(i1==1) {

            ContentValues values = new ContentValues();
            values.put(PetsContract.PetEntry.COLUMN_City, "");
            values.put(PetsContract.PetEntry.COLUMN_Country, "");


            SharedPreferences prefs =
                    PreferenceManager.getDefaultSharedPreferences(getActivity());

            SharedPreferences.Editor editor = prefs.edit();

            editor.putInt("int", 2);

            editor.commit();

            long newRowId = sqldb.insert(PetsContract.PetEntry.TABLE_NAME, null, values);
            Log.v("","" +newRowId);

        }
        else
        {

            ContentValues values = new ContentValues();
            values.put(PetsContract.PetEntry.COLUMN_City, x);
            values.put(PetsContract.PetEntry.COLUMN_Country, y);

            long newRowId = sqldb.insert(PetsContract.PetEntry.TABLE_NAME, null, values);
            Log.v("","" +newRowId);

        }
    }

    private void initialise(View view) {

        fadingTextView = (FadingTextView) view.findViewById(R.id.Qtext);
        fadingTextView.setTimeout(FadingTextView.SECONDS ,2);



        city_country_store = new City_Country_store(getActivity());

        arrayList = new ArrayList<String>();
        arrayList1 = new ArrayList<String>();


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int i1 = preferences.getInt("int",1);

        if(i1 == 1)
        {

            insertData( "" ,"");
            SharedPreferences prefs =
                    PreferenceManager.getDefaultSharedPreferences(getActivity());

            SharedPreferences.Editor editor = prefs.edit();

            editor.putInt("int", 2);

            editor.commit();


        }
        else {
            displayDatabaseInfo();
        }

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
