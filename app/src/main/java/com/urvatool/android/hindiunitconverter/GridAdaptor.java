package com.urvatool.android.hindiunitconverter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;

public class GridAdaptor extends RecyclerView.Adapter<GridAdaptor.MyViewHolder> {

    int[] images;
    Context context;
    static InterstitialAd interstitial;

    public GridAdaptor(Context context,int[] images) {
        this.images=images;
        this.context=context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageViewIcon;
        InterstitialAd interstitial;

        Context context;
        int pos;


        public MyViewHolder(Context context,View itemView) {
            super(itemView);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.img_view2);
            itemView.setOnClickListener(this);
            this.context=context;

            if (MainActivity1.bp.isPurchased("noads.unitconverter")) {

            }else {
                loadad();
                }

        }

        private void loadad() {
            interstitial = new InterstitialAd(context);
            interstitial.setAdUnitId(context.getResources().getString(R.string.interstial));
            interstitial.loadAd(new com.google.android.gms.ads.AdRequest.Builder().addTestDevice(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR).build());
        }

        @Override
        public void onClick(View v) {




            if(getAdapterPosition()!=0 && getAdapterPosition()%3==0) {
                if (interstitial.isLoaded()) {
                    interstitial.show();

                } else {


                    Intent intent = new Intent(context, MainActivity.class);


                    //intent.putExtra("name",category[i]);
                    intent.putExtra("pos", getAdapterPosition());
                    context.startActivity(intent);
                }

                interstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {


                        Intent intent = new Intent(context, MainActivity.class);


                        //intent.putExtra("name",category[i]);
                        intent.putExtra("pos", getAdapterPosition());
                        context.startActivity(intent);
                        interstitial.loadAd(new com.google.android.gms.ads.AdRequest.Builder().addTestDevice(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR).build());

                    }
                });
            }else{
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("pos",getAdapterPosition());
                context.startActivity(intent);
            }


        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample, parent, false);

      //  view.setOnClickListener(MainActivity.myOnClickListener);



        MyViewHolder myViewHolder = new MyViewHolder(context,view);

        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        ImageView imageView = holder.imageViewIcon;
        imageView.setImageResource(images[listPosition]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
}