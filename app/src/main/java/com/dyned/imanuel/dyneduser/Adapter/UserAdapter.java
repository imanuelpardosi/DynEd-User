package com.dyned.imanuel.dyneduser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyned.imanuel.dyneduser.Model.User;
import com.dyned.imanuel.dyneduser.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by nuel4 on 09/09/2016.
 */
public class UserAdapter extends ArrayAdapter<User> {

    private LayoutInflater mInflater;
    public List<User> userList;
    ArrayList<User> arraylist;
    public Context context;

    public UserAdapter(Context context, int textViewResourceId, List<User> objects) {
        super(context, textViewResourceId, objects);
        this.userList = objects;
        this.context = context;
        arraylist = new ArrayList<User>();
        arraylist.addAll(userList);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        Holder holder;
        holder = new Holder();
        if (view == null) {
            // View doesn't exist so create it and create the holder
            view = mInflater.inflate(R.layout.custom_user, parent, false);


            holder.photo_profile = (TextView) view.findViewById(R.id.photo_profile);
            holder.username = (TextView) view.findViewById(R.id.username);

            view.setTag(holder);
        } else {
            // Just get our existing holder
            holder = (Holder) view.getTag();
        }
        if (position % 2 == 0) {
            holder.photo_profile.setBackgroundResource(R.drawable.blue1);
        } else {
            holder.photo_profile.setBackgroundResource(R.drawable.blue2);
        }

        // Populate via the holder for speed
        User stream = getItem(position);
        holder.photo_profile.setText(stream.getUsername().substring(0, 1));
        holder.username.setText(stream.getUsername());
        return view;
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        userList.clear();
        if (charText.length() == 0) {
            userList.addAll(arraylist);

        } else {
            for (User userDetail : arraylist) {
                if (charText.length() != 0 && userDetail.getUsername().toLowerCase(Locale.getDefault()).contains(charText)) {
                    userList.add(userDetail);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Holder class used to efficiently recycle view positions
    private static final class Holder {
        public TextView photo_profile;
        public TextView username;
    }
}