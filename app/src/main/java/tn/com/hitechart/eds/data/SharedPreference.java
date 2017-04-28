package tn.com.hitechart.eds.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class SharedPreference {

    private final static String NAME = "SharedPreferencesEDS";
    private static SharedPreference instance;
    private final SharedPreferences sharedPreferences;
    private final ObjectMapper objectMapper;

    private SharedPreference() {
        this.sharedPreferences = App.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        this.objectMapper = provideObjectMapper();
    }

    public static SharedPreference getInstance() {
        if (instance == null) {
            synchronized (SharedPreference.class) {
                instance = new SharedPreference();
            }
        }
        return instance;
    }

    public ObjectMapper provideObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        return objectMapper;
    }

    public void putBoolean(@NonNull String key, boolean value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public void putFloat(@NonNull String key, float value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putFloat(key, value);
        edit.apply();
    }

    public void putInt(@NonNull String key, int value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public void putLong(@NonNull String key, long value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(key, value);
        edit.apply();
    }

    public void putDouble(@NonNull String key, double value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, String.valueOf(value));
        edit.apply();
    }

    public void putString(@NonNull String key, String value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public <T> void putObject(@NonNull String key, T object) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String value = convertToString(object);
        edit.putString(key, value);
        edit.apply();
    }

    public void putStringSet(@NonNull String key, Set<String> value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putStringSet(key, value);
        edit.apply();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public boolean getBoolean(@NonNull String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);

    }

    public float getFloat(@NonNull String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public double getDouble(@NonNull String key, double defValue) {
        String valueStr = sharedPreferences.getString(key, String.valueOf(defValue));
        return !TextUtils.isEmpty(valueStr) ? Double.parseDouble(valueStr) : 0.0;
    }

    public int getInt(@NonNull String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);

    }

    public long getLong(@NonNull String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public String getString(@NonNull String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public Set<String> getStringSet(@NonNull String key, Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }

    public <T> T getObject(@NonNull String key, Class<T> valueType) {
        final String str = sharedPreferences.getString(key, null);
        return !TextUtils.isEmpty(str) ? convertToObject(str, valueType) : null;
    }

    public boolean contains(@NonNull String key) {
        return sharedPreferences.contains(key);
    }

    public void remove(@NonNull String key) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(key);
        edit.apply();
    }

    public void clearAll() {
        Map<String, ?> preferences = sharedPreferences.getAll();
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (Map.Entry<String, ?> me : preferences.entrySet()) {
            String key = me.getKey();
            edit.remove(key);
        }
        edit.apply();
    }

    private <T> T convertToObject(String src, Class<T> valueType) {
        T data;
        try {
            data = objectMapper.readValue(src, valueType);
        } catch (IOException error) {
            data = null;
        }
        return data;
    }

    private <T> String convertToString(T value) {
        String decoded;
        if (value != null) {
            try {
                decoded = objectMapper.writeValueAsString(value);
            } catch (IOException e) {
                decoded = null;
            }
        } else {
            decoded = null;
        }
        return decoded;
    }
}
