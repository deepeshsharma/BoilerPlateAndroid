package com.deep.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class AbstractPreference {

    private static final String VERSION = "__prefs_version__";
    private final Context mContext;
    private final SharedPreferences prefs;

    public AbstractPreference(Context ctx, String prefsName, int version) {
        this.mContext = ctx.getApplicationContext();
        if (TextUtils.isEmpty(prefsName)) {
            prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        } else {
            prefs = ctx.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        }
        init(version);
    }

    protected Context getContext() {
        return mContext;
    }

    private void init(int newVersion) {
        int oldVersion = prefs.getInt(VERSION, -1);
        if (oldVersion != newVersion) {
            onUpgrade();
            saveInt(VERSION, newVersion);
        }
    }

    private void onUpgrade() {
        prefs.edit().clear().apply();
    }

    protected boolean clearPreferences() {
        return prefs.edit().clear().commit();
    }

    protected boolean readBoolean(int keyResId) {
        return readBoolean(mContext.getString(keyResId));
    }

    protected boolean readBoolean(int keyResId, int defValueResId) {
        return readBoolean(mContext.getString(keyResId), mContext.getResources().getBoolean(defValueResId));
    }

    protected boolean readBoolean(int keyResId, Boolean value) {
        return readBoolean(mContext.getString(keyResId), value);
    }

    protected boolean readBoolean(String key) {
        return readBoolean(key, Boolean.FALSE);
    }

    protected boolean readBoolean(String key, int defValueResId) {
        return readBoolean(key, mContext.getResources().getBoolean(defValueResId));
    }

    protected boolean readBoolean(String key, Boolean value) {
        return prefs.getBoolean(key, value);
    }

    protected int readInt(int keyResId) {
        return readInt(mContext.getString(keyResId));
    }

    protected int readInt(int keyResId, int defValue) {
        return readInt(mContext.getString(keyResId), defValue);
    }

    protected int readInt(String key) {
        return readInt(key, Integer.MIN_VALUE);
    }

    protected int readInt(String key, int value) {
        return prefs.getInt(key, value);
    }

    protected String readString(int keyResId) {
        return readString(mContext.getString(keyResId));
    }

    protected String readString(int keyResId, int defValueResId) {
        return readString(mContext.getString(keyResId), mContext.getResources().getString(defValueResId));
    }

    protected String readString(int keyResId, String defValue) {
        return readString(mContext.getString(keyResId), defValue);
    }

    protected String readString(String key) {
        return readString(key, null);
    }

    protected String readString(String key, int defValueResId) {
        return readString(key, mContext.getResources().getString(defValueResId));
    }

    protected String readString(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    protected long readLong(int keyResId) {
        return readLong(mContext.getString(keyResId));
    }

    protected long readLong(int keyResId, Long defValue) {
        return readLong(mContext.getString(keyResId), defValue);
    }

    protected long readLong(String key) {
        return readLong(key, Long.MIN_VALUE);
    }

    protected long readLong(String key, Long defValue) {
        return prefs.getLong(key, defValue);
    }

    protected float readFloat(int keyResId) {
        return readFloat(mContext.getString(keyResId));
    }

    protected float readFloat(int keyResId, Float defValue) {
        return readFloat(mContext.getString(keyResId), defValue);
    }

    protected float readFloat(String key) {
        return readFloat(key, Float.MIN_VALUE);
    }

    protected float readFloat(String key, Float defValue) {
        return prefs.getFloat(key, defValue);
    }

    protected void saveBoolean(int keyResId, boolean val) {
        saveBoolean(mContext.getResources().getString(keyResId), val);
    }

    protected void saveBoolean(String key, boolean val) {
        prefs.edit().putBoolean(key, val).apply();
    }

    protected void saveInt(int keyResId, int val) {
        saveInt(mContext.getResources().getString(keyResId), val);
    }

    protected void saveInt(String key, int val) {
        prefs.edit().putInt(key, val).apply();
    }

    protected void saveLong(int keyResId, long val) {
        saveLong(mContext.getResources().getString(keyResId), val);
    }

    protected void saveLong(String key, long val) {
        prefs.edit().putLong(key, val).apply();
    }

    protected void saveString(int keyResId, String val) {
        saveString(mContext.getResources().getString(keyResId), val);
    }

    protected void saveString(String key, String val) {
        prefs.edit().putString(key, val).apply();
    }

    protected void saveFloat(int keyResId, float val) {
        saveFloat(mContext.getResources().getString(keyResId), val);
    }

    protected void saveFloat(String key, float val) {
        prefs.edit().putFloat(key, val).apply();
    }

    protected void remove(String key) {
        prefs.edit().remove(key).apply();
    }
}
