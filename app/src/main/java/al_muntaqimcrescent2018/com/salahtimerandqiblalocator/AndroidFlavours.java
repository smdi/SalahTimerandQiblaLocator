package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class AndroidFlavours extends ArrayAdapter<AndroidAdapter> {

   public static final String LOG_TAG = AndroidFlavours.class.getSimpleName();

   private Context actcontext;

   private ArrayList<AndroidAdapter> andAdap;

   public AndroidFlavours(Activity context , ArrayList<AndroidAdapter> androidAdapters)
   {
          super(context , 0 ,androidAdapters);
       actcontext = context;
       andAdap = androidAdapters;
   }
   @SuppressLint("ResourceType")
   @Override
   public View getView(int position , View convert , ViewGroup parent)
   {
       View listItemView = convert;
       if(listItemView == null) {
           listItemView = LayoutInflater.from(getContext()).inflate(
                   R.layout.listview, parent, false);
       }
        AndroidAdapter   curr = getItem(position);

       TextView t1 = (TextView) listItemView.findViewById(R.id.Heading);
       t1.setText(curr.head);


       TextView t2 = (TextView) listItemView.findViewById(R.id.Hadees);
       t2.setText(curr.hadees);

//       ImageView imageView = (ImageView) listItemView.findViewById(R.id.tag);
//       imageView.setImageResource(curr.mresourse);


        LottieAnimationView lottieAnimationView = (LottieAnimationView) listItemView.findViewById(R.id.tag);
       getBookmark(lottieAnimationView,curr);

       return listItemView;
   }

    private void getBookmark(LottieAnimationView imageView, AndroidAdapter curr) {

        SharedPreferences preferences = actcontext.getSharedPreferences("BOOKMARK",Context.MODE_PRIVATE);
        String text = preferences.getString("head","null");

        if((text.equals(curr.hadees)))
        {
            imageView.setAnimation("bookmark_animation.json");
            imageView.loop(true);
            imageView.setVisibility(View.VISIBLE);
            imageView.playAnimation();
        }
        else {

            imageView.setVisibility(View.GONE);
            imageView.cancelAnimation();
        }

   }

    @Override
    public int getCount() {
        return andAdap.size();
    }
}
