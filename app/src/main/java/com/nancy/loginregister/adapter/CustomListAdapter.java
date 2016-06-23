package com.nancy.loginregister.adapter;

import com.nancy.loginregister.R;
import com.nancy.loginregister.models.Readings;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.nancy.loginregister.models.VolleyController;

/**
 * Created by Nancy on 05/25/2016.
 */
public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Readings> readings;
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Readings> readings) {
        this.activity = activity;
        this.readings = readings;
    }

    @Override
    public int getCount() {
        return readings.size();
    }

    @Override
    public Object getItem(int location) {
        return readings.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = VolleyController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView accno = (TextView) convertView.findViewById(R.id.editText_accno);
        TextView meterno = (TextView) convertView.findViewById(R.id.editText_meterno);
        TextView message = (TextView) convertView.findViewById(R.id.sv_message);

        // getting movie data for the row
        Readings m = readings.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        accno.setText(m.getAccount());

        // rating
        meterno.setText("Meter No: " + String.valueOf(m.getMeter()));

        // genre
        String genreStr = "";
        for (String str : m.getMessage()) {
            genreStr += str + ", ";
        }
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        message.setText(genreStr);



        return convertView;
    }
}
