package me.mattlogan.locationtracker.testdoubles;

import me.mattlogan.locationtracker.location.DeviceInfo;

public final class LocationEnabledDeviceInfo implements DeviceInfo {
    @Override public boolean isLocationEnabled() {
        return true;
    }
}
