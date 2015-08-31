package com.example.jadjaluddin.guia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Stephanie on 8/6/2015.
 */
public class MessageFragment extends Fragment {
    ArrayList<MessageItem> mList = new ArrayList<MessageItem>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoggedInGuide.toolbar.setTitle("Messages");
        String message = "Hooyyy asa naman tawn ka ui, pagdali kay ikaw nalay gihuwat";
        String message_part;
        if(message.length()>25) message_part = message.substring(0, 24) + "...";
        else message_part = message;

        mList.clear();
        mList.add(new MessageItem(R.drawable.profile, "Claire Magz", message_part, message));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.cardList);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        RVadapter adapter = new RVadapter(null, mList);
        rv.setAdapter(adapter);
        return view;
    }
}
