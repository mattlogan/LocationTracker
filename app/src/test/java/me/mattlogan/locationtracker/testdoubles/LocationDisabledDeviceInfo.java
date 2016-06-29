package me.mattlogan.locationtracker.testdoubles;

import me.mattlogan.locationtracker.location.DeviceInfo;

public final class LocationDisabledDeviceInfo implements DeviceInfo {
    @Override public boolean isLocationEnabled() {
        return false;
    }
}
