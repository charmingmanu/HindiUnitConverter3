package com.urvatool.android.hindiunitconverter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


public class Adaptor extends BaseAdapter {

    ArrayList<String> result;
        Context context;
        int [] imageId;
        private static LayoutInflater inflater=null;
        public Adaptor(Context context, int[] osImages) {
            context=context;
            imageId=osImages;
            inflater = (LayoutInflater)context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return imageId.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class Holder
        {
            ImageView os_img;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder=new Holder();
            View rowView;
            rowView = inflater.inflate(R.layout.sample, null);
            holder.os_img =(ImageView) rowView.findViewById(R.id.img_view2);
            holder.os_img.setImageResource(imageId[position]);

            return rowView;
        }

    }

