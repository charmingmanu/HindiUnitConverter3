
package com.urvatool.android.hindiunitconverter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.urvatool.android.hindiunitconverter.api.models.Currencies;
import com.urvatool.android.hindiunitconverter.models.Conversion;
import com.urvatool.android.hindiunitconverter.models.Language;


public class Preferences {

    public static final String PREFS_THEME = "light_theme";
    public static final String PREFS_FROM_VALUE = "from_value";
    public static final String PREFS_NUMBER_OF_DECIMALS = "number_decimals";
    public static final String PREFS_DECIMAL_SEPARATOR = "decimal_separator";
    public static final String PREFS_GROUP_SEPARATOR = "group_separator";
    public static final String PREFS_HAS_RATED = "has_rated";
    public static final String PREFS_APP_OPEN_COUNT = "app_open_count";
    public static final String PREFS_SHOW_HELP = "show_help";
    private static final String PREFS_CONVERSION = "conversion";
    private static final String PREFS_CURRENCY_V2 = "currency_v2";
    public static final String PREFS_LANGUAGE = "language";

    private static Preferences mInstance;
    private SharedPreferences mPrefs;
    private Context mContext;

    public static Preferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Preferences(context.getApplicationContext());
        }

        return mInstance;
    }

    private Preferences(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mContext = context;
    }

    /**
     * Get instance of app's Shared Preferences
     *
     * @return SharedPreference instance
     */
    public SharedPreferences getPreferences() {
        return mPrefs;
    }

    public boolean isLightTheme() {
        return mPrefs.getBoolean(PREFS_THEME, false);
    }

    @SuppressWarnings("ResourceType")
    public
    @Conversion.id
    int getLastConversion() {
        return mPrefs.getInt(PREFS_CONVERSION, Conversion.AREA);
    }

    public void setLastConversion(@Conversion.id int conversionId) {
        mPrefs.edit().putInt(PREFS_CONVERSION, conversionId).apply();
    }

    public String getLastValue() {
        return mPrefs.getString(PREFS_FROM_VALUE, "1.0");
    }

    public void setLastValue(String value) {
        mPrefs.edit().putString(PREFS_FROM_VALUE, value).apply();
    }

    public int getNumberDecimals() {
        return Integer.parseInt(mPrefs.getString(PREFS_NUMBER_OF_DECIMALS, mContext.getString(R.string.default_number_decimals)));
    }

    public String getDecimalSeparator() {
        return mPrefs.getString(PREFS_DECIMAL_SEPARATOR, mContext.getString(R.string.default_decimal_separator));
    }

    public String getGroupSeparator() {
        return mPrefs.getString(PREFS_GROUP_SEPARATOR, mContext.getString(R.string.default_group_separator));
    }

    public void setShowHelp(boolean showHelp) {
        mPrefs.edit().putBoolean(PREFS_SHOW_HELP, showHelp).apply();
    }

    public boolean showHelp() {
        return mPrefs.getBoolean(PREFS_SHOW_HELP, true);
    }

    public boolean hasLatestCurrency() {
        return mPrefs.contains(PREFS_CURRENCY_V2);
    }

    public void saveLatestCurrency(Currencies currencies) {
        mPrefs.edit().putString(PREFS_CURRENCY_V2, new Gson().toJson(currencies)).apply();
    }

    public Currencies getLatestCurrency() {
        if (mPrefs.contains(PREFS_CURRENCY_V2)) {
            String currencies = mPrefs.getString(PREFS_CURRENCY_V2, null);
            return new Gson().fromJson(currencies, Currencies.class);
        }
        else {
            return null;
        }
    }

    public void setLanguage(final Language language) {
        mPrefs.edit().putString(PREFS_LANGUAGE, language.getId()).apply();
    }

    @NonNull
    public Language getLanguage() {
        final String id = mPrefs.getString(PREFS_LANGUAGE, Language.DEFAULT.getId());
        return Language.fromId(id);
    }
}
