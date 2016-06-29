package me.mattlogan.locationtracker.location;

import android.content.ContentResolver;
import android.provider.Settings;
import android.util.Log;

public final class RealDeviceInfo implements DeviceInfo {

    private final ContentResolver contentResolver;

    public RealDeviceInfo(final ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override public boolean isLocationEnabled() {
        try {
            return Settings.Secure.getInt(contentResolver, Settings.Secure.LOCATION_MODE) != Settings.Secure.LOCATION_MODE_OFF;
        } catch (Settings.SettingNotFoundException e) {
            Log.e("RealDeviceInfo", "Unable to check if location is enabled", e);
            return false;
        }
    }
}
