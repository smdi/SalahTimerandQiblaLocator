package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gospelware.liquidbutton.LiquidButton;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imran on 04-02-2018.
 */

public class SalahTimer extends Fragment {


    private  String city = "";
    private  String country = "";
    private ProgressBar mProgressBar;
    public String USGS_REQUEST_URL;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private   LiquidButton liquidButton;

    private List<HomeInitialiser> listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        city = getArguments().getString("city");
        country = getArguments().getString("country");

        return inflater.inflate(R.layout.salahtimer,container ,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);



        USGS_REQUEST_URL ="http://api.aladhan.com/timingsByCity?city="+city+"&country="+country+"&method=8";


        initialiser(view);

        MyTask tk= new MyTask();
        tk.execute(USGS_REQUEST_URL);
    }

    private void initialiser(View view) {




        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        listView = new ArrayList<>();


    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, USGS_REQUEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                mProgressBar.setProgress(80);


                try {

                    JSONObject baseJsonResponse = new JSONObject(response);
                    JSONObject  data = baseJsonResponse.getJSONObject("data");
                    JSONObject  timings = data.getJSONObject("timings");
                    JSONObject  date = data.getJSONObject("date");
                    String readable = date.getString("readable");
                    JSONObject  meta = data.getJSONObject("meta");
                    String timezone = meta.getString("timezone");

                    int i=1;

                    switch(i=1)
                    {
                        case 1:

                            String fajr = timings.getString("Fajr");
                            HomeInitialiser homeInitialiser = new HomeInitialiser(" Fajr     : " +fajr ,"Date  : " +readable , "TimeZone : "+timezone);
                            listView.add(homeInitialiser);
                        case 2:
                            String sunrise = timings.getString("Sunrise");
                            HomeInitialiser homeInitialiser1 = new HomeInitialiser(" Sunrise : " +sunrise ,"Date  : "+readable ,"TimeZone : "+timezone);
                            listView.add(homeInitialiser1);
                        case 3:
                            String dhuhr = timings.getString("Dhuhr");
                            HomeInitialiser homeInitialiser2 = new HomeInitialiser(" Zohar   : " +dhuhr ,"Date  : "+readable ,"TimeZone : "+timezone);
                            listView.add(homeInitialiser2) ;

                        case 4:
                            String asrr = timings.getString("Asr");
                            HomeInitialiser homeInitialiser3 = new HomeInitialiser(" Asar    : " +asrr ,"Date  : "+readable ,"TimeZone : "+timezone);
                            listView.add(homeInitialiser3);

                        case 5:
                            String sunset = timings.getString("Sunset");
                            HomeInitialiser homeInitialiser4 = new HomeInitialiser(" SunSet  : " +sunset ,"Date  : "+readable ,"TimeZone : "+timezone);
                            listView.add(homeInitialiser4);

                        case 6:
                            String magrib = timings.getString("Maghrib");
                            HomeInitialiser homeInitialiser5 = new HomeInitialiser(" Magrib  : " +magrib ,"Date  : "+readable ,"TimeZone : "+timezone);
                            listView.add(homeInitialiser5);
                        case 7:
                            String isha = timings.getString("Isha");
                            HomeInitialiser homeInitialiser6 = new HomeInitialiser(" Isha    : " +isha ,"Date  : "+readable ,"TimeZone : "+timezone);
                            listView.add(homeInitialiser6);

                        case 8:
                            String imask = timings.getString("Imsak");
                            HomeInitialiser homeInitialiser7 = new HomeInitialiser(" Imsak   : " +imask ,"Date  : "+readable ,"TimeZone : "+timezone);
                            listView.add(homeInitialiser7);


                        case 9:
                            String midnight = timings.getString("Midnight");
                            HomeInitialiser homeInitialiser8 = new HomeInitialiser(" MidNight : " +midnight ,"Date  : "+readable ,"TimeZone : "+timezone);
                            listView.add(homeInitialiser8);
                            break;


                    }




                } catch (JSONException e) {

                    Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
                }



                adapter = new HomeAdapter(getActivity(),listView);
                    recyclerView.setAdapter(adapter);

            }
        }

                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                TastyToast.makeText(getActivity(),""+error, Toast.LENGTH_LONG ,TastyToast.ERROR).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private class MyTask extends AsyncTask<String, Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.CYAN));
            mProgressBar.setProgress(20);

        }


        @Override
        protected void onPostExecute(Void androidAdapter) {

            mProgressBar.setProgress(100);
                mProgressBar.setVisibility(View.GONE);

        }

        @Override
        protected Void doInBackground(String... strings) {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, USGS_REQUEST_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                mProgressBar.setProgress(80);


                    try {

                        JSONObject baseJsonResponse = new JSONObject(response);
                        JSONObject  data = baseJsonResponse.getJSONObject("data");
                        JSONObject  timings = data.getJSONObject("timings");
                        JSONObject  date = data.getJSONObject("date");
                        String readable = date.getString("readable");
                        JSONObject  meta = data.getJSONObject("meta");
                        String timezone = meta.getString("timezone");

                        int i=1;

                        switch(i=1)
                        {
                            case 1:

                                String fajr = timings.getString("Fajr");
                                HomeInitialiser homeInitialiser = new HomeInitialiser(" Fajr     : " +fajr ,"Date  : " +readable , "TimeZone : "+timezone);
                                listView.add(homeInitialiser);
                            case 2:
                                String sunrise = timings.getString("Sunrise");
                                HomeInitialiser homeInitialiser1 = new HomeInitialiser(" Sunrise : " +sunrise ,"Date  : "+readable ,"TimeZone : "+timezone);
                                listView.add(homeInitialiser1);
                            case 3:
                                String dhuhr = timings.getString("Dhuhr");
                                HomeInitialiser homeInitialiser2 = new HomeInitialiser(" Zohar   : " +dhuhr ,"Date  : "+readable ,"TimeZone : "+timezone);
                                listView.add(homeInitialiser2) ;

                            case 4:
                                String asrr = timings.getString("Asr");
                                HomeInitialiser homeInitialiser3 = new HomeInitialiser(" Asar    : " +asrr ,"Date  : "+readable ,"TimeZone : "+timezone);
                                listView.add(homeInitialiser3);

                            case 5:
                                String sunset = timings.getString("Sunset");
                                HomeInitialiser homeInitialiser4 = new HomeInitialiser(" SunSet  : " +sunset ,"Date  : "+readable ,"TimeZone : "+timezone);
                                listView.add(homeInitialiser4);

                            case 6:
                                String magrib = timings.getString("Maghrib");
                                HomeInitialiser homeInitialiser5 = new HomeInitialiser(" Magrib  : " +magrib ,"Date  : "+readable ,"TimeZone : "+timezone);
                                listView.add(homeInitialiser5);
                            case 7:
                                String isha = timings.getString("Isha");
                                HomeInitialiser homeInitialiser6 = new HomeInitialiser(" Isha    : " +isha ,"Date  : "+readable ,"TimeZone : "+timezone);
                                listView.add(homeInitialiser6);

                            case 8:
                                String imask = timings.getString("Imsak");
                                HomeInitialiser homeInitialiser7 = new HomeInitialiser(" Imsak   : " +imask ,"Date  : "+readable ,"TimeZone : "+timezone);
                                listView.add(homeInitialiser7);


                            case 9:
                                String midnight = timings.getString("Midnight");
                                HomeInitialiser homeInitialiser8 = new HomeInitialiser(" MidNight : " +midnight ,"Date  : "+readable ,"TimeZone : "+timezone);
                                listView.add(homeInitialiser8);
                                break;


                        }




                    } catch (JSONException e) {

                        Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
                    }






                    adapter = new HomeAdapter(getActivity(),listView);
                    recyclerView.setAdapter(adapter);

                }
            }

                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    TastyToast.makeText(getActivity(),""+error, Toast.LENGTH_LONG,TastyToast.ERROR).show();

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);



            return null;
        }



    }


}
