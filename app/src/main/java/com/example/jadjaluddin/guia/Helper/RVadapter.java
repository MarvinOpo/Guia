package com.example.jadjaluddin.guia.Helper;

import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jadjaluddin.guia.Model.Course;
import com.example.jadjaluddin.guia.Model.MessageItem;
import com.example.jadjaluddin.guia.Model.PopularDestinations;
import com.example.jadjaluddin.guia.Model.User;
import com.example.jadjaluddin.guia.R;
import com.example.jadjaluddin.guia.Traveler.FragmentChooseGuide;
import com.example.jadjaluddin.guia.Traveler.FragmentCourseBooking;

import java.util.List;

/**
 * Created by jadjaluddin on 8/5/2015.
 */
public class RVadapter extends RecyclerView.Adapter<RVadapter.CardViewHolder> {

    static List<PopularDestinations> pd;
    static List<MessageItem> mi;
    static List<User> user;
    static ViewGroup p;

    public RVadapter(List<PopularDestinations> pd, List<MessageItem> mi, List<User> user) {
        this.pd = pd;
        this.mi = mi;
        this.user = user;
    }

    @Override
    public CardViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        p=parent;
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(pd != null) {
            view = inflater.inflate(R.layout.cardview, parent, false);
        }
        else if(mi != null){
            view = inflater.inflate(R.layout.fragment_message, parent, false);
        }
        else if(user != null){
            view = inflater.inflate(R.layout.cardview_guide, parent, false);
        }

        CardViewHolder cvh = new CardViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, final int position) {
        if(pd != null) {
            holder.location.setText(pd.get(position).location);
            holder.description.setText(pd.get(position).description);
            holder.iv.setImageResource(pd.get(position).icon);
        }
        else if(mi != null) {
            holder.message_name.setText(mi.get(position).name);
            holder.message_details.setText(mi.get(position).message_part);
            holder.message_image.setImageResource(mi.get(position).image);
        }
        else  if(user != null){
            holder.guide_name.setText(user.get(position).name);
            holder.guide_gendr_age.setText(user.get(position).gender.substring(0,1).toUpperCase()+
                    user.get(position).gender.substring(1)+", "+user.get(position).age);
            holder.guide_tours.setText(String.valueOf(user.get(position).tourCount));
            holder.rb.setRating(user.get(position).rating);

            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User u = user.get(position);
                    Toast.makeText(p.getContext(), "User: "+u.name, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        int size = 0;

        if(pd != null) size = pd.size();
        else if(mi != null) size = mi.size();
        else if(user != null) size = user.size();

        return size;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        CardView cv, message_view;
        TextView location, description, message_name, message_details, guide_name, guide_gendr_age, guide_tours;
        ImageView iv, message_image;
        RatingBar rb;

        CardViewHolder(View itemView) {
            super(itemView);

            if(RVadapter.pd != null){
                cv = (CardView) itemView.findViewById(R.id.card_view);
                location = (TextView) itemView.findViewById(R.id.txtLoc);
                description = (TextView) itemView.findViewById(R.id.txtDesc);
                iv = (ImageView) itemView.findViewById(R.id.imageView);
            }
            else if(RVadapter.mi != null) {
                message_view = (CardView) itemView.findViewById(R.id.card_message);
                message_image = (ImageView) itemView.findViewById(R.id.message_image);
                message_name = (TextView) itemView.findViewById(R.id.messenger);
                message_details = (TextView) itemView.findViewById(R.id.details);
            }
            else if(RVadapter.user != null){
                cv = (CardView) itemView.findViewById(R.id.card_view);
                guide_name = (TextView) itemView.findViewById(R.id.new_travel_name);
                guide_gendr_age = (TextView) itemView.findViewById(R.id.new_travel_gender_age);
                guide_tours = (TextView) itemView.findViewById(R.id.new_travel_tour);
                rb = (RatingBar) itemView.findViewById(R.id.new_travel_rb);
            }

        }
    }
}
