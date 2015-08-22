package com.example.jadjaluddin.guia;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jadjaluddin on 8/5/2015.
 */
public class RVadapter extends RecyclerView.Adapter<RVadapter.CardViewHolder> {

    static List<PopularDestinations> pd;
    static List<MessageItem> mi;

    RVadapter(List<PopularDestinations> pd, List<MessageItem> mi) {
        this.pd = pd;
        this.mi = mi;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(pd != null) {
            view = inflater.inflate(R.layout.cardview, parent, false);
        }
        else if(mi != null){
            view = inflater.inflate(R.layout.fragment_message, parent, false);
        }

        CardViewHolder cvh = new CardViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
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

        return size;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        CardView cv, message_view;
        TextView location, description, message_name, message_details;
        ImageView iv, message_image;

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


        }
    }
}
