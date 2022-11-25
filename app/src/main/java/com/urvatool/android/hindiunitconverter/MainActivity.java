/*
 * Copyright 2017 Phil Shadlyn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.urvatool.android.hindiunitconverter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.urvatool.android.hindiunitconverter.fragments.ConversionFragment;
import com.urvatool.android.hindiunitconverter.models.Conversion;
import com.urvatool.android.hindiunitconverter.presenters.MainActivityPresenter;
import com.urvatool.android.hindiunitconverter.presenters.MainActivityView;
import com.urvatool.android.hindiunitconverter.util.Conversions;

/**
 * Main activity
 * Created by Phizz on 15-07-28.
 */
public class MainActivity extends BaseActivity implements MainActivityView, SharedPreferences.OnSharedPreferenceChangeListener {


    private DrawerLayout mDrawerLayout;
    private Conversions mConversions;
    private MainActivityPresenter mPresenter;
    int a;
    String name;
    private FrameLayout adContainerView;
    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // TODO - replace with Dagger2 injection
        //mPresenter = new MainActivityPresenter(this, this, Preferences.getInstance(this));

        PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
        Preferences.getInstance(this).getPreferences().registerOnSharedPreferenceChangeListener(this);
        mConversions = Conversions.getInstance();
        //  setup language
//        mPresenter.setLanguageToDisplay();





        Bundle b=getIntent().getExtras();
        a = b.getInt("pos");
        name = b.getString("name");

        setContentView(R.layout.activity_main);
        setupToolbar();

        adContainerView = findViewById(R.id.adView_container);
        adView = new AdView(this);
        adContainerView.addView(adView);
        adView.setAdUnitId(getResources().getString(R.string.banner));




        int conversion = Preferences.getInstance(this).getLastConversion();
        if (MainActivity1.bp.isPurchased("noads.hindiunitconverter")) {

        }else{
            loadBanner();
        }


        // setToolbarTitle(mConversions.getById(a).getLabelResource());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        // setupDrawer(getMenuPositionOfConversion(conversion));
        setupDrawer(a);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ConversionFragment.newInstance(a))
                    .commit();
        }
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



    /* @Override
     protected void onStart() {
         super.onStart();

         // Show help dialog if never seen before
         /*if (Preferences.getInstance(this).showHelp()) {
             HelpDialogFragment.newInstance().show(getSupportFragmentManager(), HelpDialogFragment.TAG);
         }*/
    //}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Preferences.getInstance(this).getPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
    /**
     * Setup navigation drawer
     *
     * @param state index of item to be selected initially
     */
    private void setupDrawer(int state) {
        //NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        // navigationView.getMenu().getItem(state).setChecked(true);
        // navigationView.setItemBackgroundResource(Preferences.getInstance(this).isLightTheme() ?
        // R.drawable.navigation_item_background_light : R.drawable.navigation_item_background);

        int conversion = getConversionFromDrawer(state);
        setToolbarTitle(mConversions.getById(state).getLabelResource());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ConversionFragment.newInstance(conversion))
                .commit();

      /* navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                 //  case R.id.drawer_settings:
                        //PreferencesActivity.start(MainActivity.this);
                       // return true;
                    default:
                        menuItem.setChecked(true);
                        int conversion = getConversionFromDrawer(menuItem.getItemId());
                        setToolbarTitle(mConversions.getById(conversion).getLabelResource());
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, ConversionFragment.newInstance(conversion))
                                .commit();
                        return true;
                }
            }
        });*/
    }


    @Conversion.id
    private int getConversionFromDrawer(int itemId) {
        switch (itemId) {
            case R.id.drawer_area:
                return Conversion.AREA;

            case R.id.drawer_cooking:
                return Conversion.COOKING;

           /* case R.id.drawer_currency:
                return Conversion.CURRENCY;*/

            case R.id.drawer_storage:
                return Conversion.STORAGE;

            case R.id.drawer_energy:
                return Conversion.ENERGY;

            /*case R.id.drawer_fuel:
                return Conversion.FUEL;*/

            case R.id.drawer_length:
                return Conversion.LENGTH;

            case R.id.drawer_mass:
                return Conversion.MASS;

            case R.id.drawer_power:
                return Conversion.POWER;

            case R.id.drawer_pressure:
                return Conversion.PRESSURE;

            /* case R.id.drawer_speed:
                return Conversion.SPEED;*/

            case R.id.drawer_temperature:
                return Conversion.TEMPERATURE;

            /*case R.id.drawer_time:
                return Conversion.TIME;

            case R.id.drawer_torque:
                return Conversion.TORQUE;

            case R.id.drawer_volume:
                return Conversion.VOLUME;

            case R.id.drawer_current:
                return Conversion.CURRENT;

            case R.id.drawer_image:
                return Conversion.IMAGE;

            case R.id.drawer_force:
                return Conversion.FORCE;*/

           /* case R.id.drawer_angle:
                return Conversion.ANGLE;*/
        }

        return Conversion.TEMPERATURE;
    }

    private int getMenuPositionOfConversion(@Conversion.id final int conversion) {
        switch (conversion) {
            case Conversion.AREA:
                return 0;
            case Conversion.COOKING:
                return 1;
            case Conversion.CURRENCY:
                return 2;
            case Conversion.STORAGE:
                return 3;
            case Conversion.ENERGY:
                return 4;
            case Conversion.FUEL:
                return 5;
            case Conversion.LENGTH:
                return 6;
            case Conversion.MASS:
                return 7;
            case Conversion.POWER:
                return 8;
            case Conversion.PRESSURE:
                return 9;
            case Conversion                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     .SPEED:
                return 10;
            case Conversion.TEMPERATURE:
                return 11;
            case Conversion.TIME:
                return 12;
            case Conversion.TORQUE:
                return 13;
            case Conversion.VOLUME:
                return 14;
          /*  case Conversion.CURRENT:
                return 15;
            case Conversion.IMAGE:
                return 16;
            case Conversion.RESISTANCE:
                return 17;
            case Conversion.FORCE:
                return 18;
            case Conversion.ANGLE:
                return 19;*/
            default:
                return 0;
        }
    }

    private void hideKeyboard() {
        View v = getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Preferences.PREFS_THEME)) {
            recreate();
        }
        else if (key.equals(Preferences.PREFS_LANGUAGE)) {
          //  mPresenter.onLanguageChanged();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //   mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void restartApp() {
        // startActivity(IntentFactory.getRestartAppIntent(this));
    }
    // endregion
}