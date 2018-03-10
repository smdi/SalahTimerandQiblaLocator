package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

/**
 * Created by Imran on 31-01-2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<HomeInitialiser> listitem;
    private Context context;
    private  boolean change = true;

    public HomeAdapter(Context context, List<HomeInitialiser> listitem) {
        this.listitem = listitem;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{



       public TextView t1,t2,t3;

        public ViewHolder(View itemView) {
            super(itemView);

             t1 = (TextView) itemView.findViewById(R.id.main_head);
             t2 = (TextView) itemView.findViewById(R.id.date);
             t3 = (TextView) itemView.findViewById(R.id.description);

        }
    }


    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        HomeInitialiser homeInitialiser = listitem.get(position);

        holder.t1.setText(" "+homeInitialiser.getTimings());

        holder.t2.setText(" "+homeInitialiser.getDate());

        holder.t3.setText(" "+homeInitialiser.getTimeZone());

    }


    @Override
    public int getItemCount() {
        return listitem.size();
    }
}
