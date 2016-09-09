package com.dyned.imanuel.dyneduser;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    public UserAdapter(Context context, int textViewResourceId, List<User> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        mInflater = (LayoutInflater)context.getSystemService(Context. LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        Holder holder;
        if (view == null) {
            // View doesn't exist so create it and create the holder
            view = mInflater.inflate(R.layout.custom_user, parent,false);

            holder = new Holder();
            holder.photo_profile = (ImageView) view.findViewById(R.id.photo_profile);
            holder.username = (TextView) view.findViewById(R.id.username);
            view.setTag( holder);
        } else {
        // Just get our existing holder
            holder = (Holder) view.getTag();
        }

        if (position % 2 == 0) {
            holder.photo_profile.setBackgroundResource(R.drawable.ic_menu_camera);
            //holder.photo_profile.setTextAlignment("aa");

        } else {
            holder.photo_profile.setBackgroundResource(R.drawable.ic_menu_gallery);
        }

        // Populate via the holder for speed
        User stream = getItem(position);

        //holder.ivCurrency.setBackgroundResource(view.getResources().getIdentifier(stream.getMataUang().toLowerCase(), "drawable",view.getContext().getPackageName()));

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
                if (charText.length() != 0 && userDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    userList.add(userDetail);
                } else if (charText.length() != 0 && userDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    userList.add(userDetail);
                }
            }
        }
        notifyDataSetChanged();
    }


    // Holder class used to efficiently recycle view positions
    private static final class Holder {
        public ImageView photo_profile;
        public TextView username;
    }
}