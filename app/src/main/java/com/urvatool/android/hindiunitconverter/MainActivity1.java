package com.urvatool.android.hindiunitconverter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridView;
import com.anjlab.android.iab.v3.BillingProcessor;

import com.anjlab.android.iab.v3.PurchaseInfo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity1 extends BaseActivity implements BillingProcessor.IBillingHandler {

    public static int[] osImages = {
            R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4,
            R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8,
            R.drawable.a9, R.drawable.a10,
            R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14,
            R.drawable.a15, R.drawable.a16, R.drawable.a17, R.drawable.a18,
            R.drawable.a19, R.drawable.a20, R.drawable.a21};

    GridView gridView;
    GridLayoutManager gridLayoutManager;
    InterstitialAd interstitial;
    AdView mAdView;
    public static BillingProcessor bp;
    Thread splashthread;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    GridAdaptor customAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private FrameLayout adContainerView;
    private AdView adView;
    int pos;

    String[] category={"Area","Cooking","Storage","Energy","Fuel","Length","Mass","Power","Pressure","Speed","Tempreture"
    ,"Time","Torque","Volume","Current","Image","Resistance"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main1);

        setupToolbar();
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        adContainerView = findViewById(R.id.adView_container);
        adView = new AdView(this);
        adContainerView.addView(adView);
        adView.setAdUnitId(getResources().getString(R.string.banner));

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getResources().getString(R.string.interstial));

        bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjnZiWR0pR7jjfn4lHNiu5um4oOVe4L/cwdilM4VyzdK5GmEssSyKaVItSZrowGAfLxhvW9wrpbfZ4c+37uNjGlZM90Ah4w13qor8rHfBtw5gsdQtFBiLXX+i6qu3SbnUCLuo0Sbt4nEifuPBNC0qnW4mSd1sm+aHfA3DZojVwQNAB8CcQt63GGDqEul/AikSMUKZoy9sUgSHiKn382ycUtphXvCQTkNJ5rtaGYNtUqU2rpkXs+c0XaebHlpXsM4ISEIcmI5WfIeVfPGcrD8OCawWqSsq90XtxKiQwRWTubBMqqTe8hbiN6Nx+pYNxrHspQDVYv93X8uOLtbbagK5XwIDAQAB", this);
        bp.initialize();

        splashthread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(2000);
                    }
                } catch (InterruptedException e) {
                }

                if (bp.isPurchased("noads.hindiunitconverter")) {
                    runOnUiThread(new Runnable() {

                        public void run() {


                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {

                        public void run() {

                            loadBanner();
                            interstitial.loadAd(new com.google.android.gms.ads.AdRequest.Builder().addTestDevice(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR).build());
                        }
                    });

                }

            }
        };
        splashthread.start();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        gridLayoutManager=new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);
        /*gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                return (position%3==0?2: 1);
            }
        });*/
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        customAdapter = new GridAdaptor(this,osImages);
        recyclerView.setAdapter(customAdapter);

    }

    private void loadBanner() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        AdSize adSize = getAdSize();
        // Set the adaptive ad size to the ad view.
        adView.setAdSize(adSize);

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
    }
    private AdSize getAdSize() {
        //Determine the screen width to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        //you can also pass your selected width here in dp
        int adWidth = (int) (widthPixels / density);

        //return the optimal size depends on your orientation (landscape or portrait)
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){


        if(bp.isPurchased("noads.hindiunitconverter"))
        {
            menu.add("").setIcon(R.drawable.share)
                    .setOnMenuItemClickListener(share)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        else
        {

            menu.add("").setIcon(R.drawable.noads)
                    .setOnMenuItemClickListener(noads)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            menu.add("").setIcon(R.drawable.share)
                    .setOnMenuItemClickListener(share)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }


        return super.onCreateOptionsMenu(menu);
    }


    MenuItem.OnMenuItemClickListener noads = new MenuItem.OnMenuItemClickListener() {

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            bp.purchase(MainActivity1.this,"noads.hindiunitconverter");
            item.setVisible(false);
            return true;
        }
    };

    MenuItem.OnMenuItemClickListener share = new MenuItem.OnMenuItemClickListener() {

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hindi Unit Converter:https://play.google.com/store/apps/details?id=com.urvatool.android.hindiunitconverter");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }
    };


    protected void setToolbarTitle(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(resId);
        }
    }

    /**
     * Set toolbar as app's action bar
     */
    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public void onBackPressed() {
        pos++;
        if(pos==3){
            if (interstitial.isLoaded())
            {
                interstitial.show();
                pos=0;
            } else {

                new AlertDialog.Builder(MainActivity1.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("मराठी युनिट कन्व्हर्टर")
                        .setMessage(getResources().getString(R.string.closemsg))
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null).show();
            }

            interstitial.setAdListener(new AdListener()
            {
                @Override
                public void onAdClosed() {

                    new AlertDialog.Builder(MainActivity1.this)
                            .setIcon(R.mipmap.ic_launcher)
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(getResources().getString(R.string.closemsg))
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.no, null).show();
                    interstitial.loadAd(new com.google.android.gms.ads.AdRequest.Builder().addTestDevice(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR).build());

                }
            });

        }else{
            new AlertDialog.Builder(MainActivity1.this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(getResources().getString(R.string.closemsg))
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.no, null).show();
        }

    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable PurchaseInfo details) {
        finish();
        startActivity(new Intent(this,MainActivity1.class));

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }


    public void onDestroy() {


        if (bp != null) {
            bp.release();
        }

        super.onDestroy();
    }



}