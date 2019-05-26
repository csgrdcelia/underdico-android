package com.esgi.project.underdico.views.imagespinner;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.esgi.project.underdico.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageSpinnerAdapter extends BaseAdapter {

    Context context;

    private static final List<Pair<String, Integer>> flags = new ArrayList<Pair<String, Integer>>() {
        {
            add(new Pair<>("fr", R.drawable.france_flag));
            add(new Pair<>("en", R.drawable.england_flag));
        }
    };

    public ImageSpinnerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return flags.size();
    }

    @Override
    public Object getItem(int position) {
        return flags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ImageSpinnerViewHolder countryViewHolder;

        if(convertView == null) {
            itemView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.image_spinner_row, parent, false);
            countryViewHolder = new ImageSpinnerViewHolder();
            countryViewHolder.setCountryImage((ImageView)itemView.findViewById(R.id.flagImageView));
            itemView.setTag(countryViewHolder);
        } else {
            countryViewHolder = (ImageSpinnerViewHolder)itemView.getTag();
        }

        countryViewHolder.getCountryImage().setImageDrawable(context.getDrawable(flags.get(position).second));
        return itemView;
    }


}
