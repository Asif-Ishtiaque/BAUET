package com.bauet.bauet;

import android.app.Activity;
import android.content.Context;
import android.icu.util.ChineseCalendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bauet.bauet.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import javax.crypto.AEADBadTagException;

public class bChatListAdapter extends BaseAdapter {


    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mDataSnapshotList;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            mDataSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };



    public  bChatListAdapter(Activity activity,DatabaseReference ref, String name)
    {
         mActivity = activity;
        mDisplayName = name;
         mDatabaseReference = ref.child("bchat");
     mDatabaseReference.addChildEventListener(mListener);
         mDataSnapshotList = new ArrayList<>();
    }

 ///Inner Class For Chat View Holder

    static  class bChatViewHolder
    {
        TextView userName;
        TextView userText;
        TextView chat_time;
        ViewGroup.LayoutParams mParams;
    }






    @Override
    public int getCount() {

        return mDataSnapshotList.size();
    }

    @Override
    public bChat getItem(int position) {

        DataSnapshot snapshot  = mDataSnapshotList.get(position);
        return snapshot.getValue(bChat.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

     if(convertView == null)
    {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.chat_msg_row,parent,false);
        final  bChatViewHolder  holder =  new bChatViewHolder();
        holder.chat_time  = (TextView)  convertView.findViewById(R.id.chat_time);
        holder.userName  = (TextView)  convertView.findViewById(R.id.author);
        holder.userText  = (TextView)  convertView.findViewById(R.id.message);
        holder.mParams = (LinearLayout.LayoutParams) holder.userName.getLayoutParams();
        convertView.setTag(holder);
    }

      final bChat chat = getItem(position);
     final bChatViewHolder holder =  (bChatViewHolder) convertView.getTag();



     String time = chat.getTime();
     holder.chat_time.setText(time);


     String user = chat.getAuthor();
     holder.userName.setText(user);


     String chat_meassge = chat.getMeassge();
     holder.userText.setText(chat_meassge);

        return convertView;
    }


    public  void washUP()
    {
        mDatabaseReference.removeEventListener(mListener);
    }



}
