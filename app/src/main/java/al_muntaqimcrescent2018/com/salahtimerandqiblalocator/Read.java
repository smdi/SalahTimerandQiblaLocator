package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class Read extends Fragment {


   private ListView listView;
   private ArrayList<AndroidAdapter> al;
    private AndroidAdapter and;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.read,container,false);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        al = new ArrayList<>();
try {
    for (int i = 1; i <= 358; i++) {
        al.add(new AndroidAdapter(category(i), Reference.InnerReference.get(i)));
    }
}
catch (Exception e)
{
    e.printStackTrace();
}
        final AndroidFlavours an  = new AndroidFlavours(getActivity() ,al);

        ListView lv = (ListView) view.findViewById(R.id.listview);
        lv.setAdapter(an);

      lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            and = al.get(i);

              String text =    and.getHadees();

              TastyToast.makeText(getActivity()," bookmarked ",TastyToast.LENGTH_LONG,TastyToast.SUCCESS).show();

              SharedPreferences preferences = getActivity().getSharedPreferences("BOOKMARK", Context.MODE_PRIVATE);
              SharedPreferences.Editor editor = preferences.edit();
              editor.putString("head",text);
              editor.commit();

          }
      });

    }

    private String category(int i) {

       int global= i;

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

}
