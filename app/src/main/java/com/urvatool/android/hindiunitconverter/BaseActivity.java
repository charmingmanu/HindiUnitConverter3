
package com.urvatool.android.hindiunitconverter;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set theme, must be called before onCreate()
        if (Preferences.getInstance(this).isLightTheme()) {
            setTheme(R.style.AppThemeLight);
        }

        super.onCreate(savedInstanceState);
    }

    /**
     * Set toolbar title
     *
     * @param resId resource id of string
     */
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

    /**
     * Set backward navigation in toolbar
     *
     * @param upAsHome if back button should close activity
     */
    protected void setToolbarHomeNavigation(boolean upAsHome) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(upAsHome);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
