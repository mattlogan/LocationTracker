package me.mattlogan.locationtracker.location;

public final class LocationChangeTracker {

    public interface Callbacks {
        void onLocationUnavailable();
        void onLocationChanged(Location location);
    }

    private final DeviceInfo     deviceInfo;
    private final LocationClient locationClient;
    private final Callbacks      callbacks;

    private Location lastLocation = null;

    public LocationChangeTracker(DeviceInfo deviceInfo,
                                 LocationClient locationClient,
                                 Callbacks callbacks) {
        this.deviceInfo = deviceInfo;
        this.locationClient = locationClient;
        this.callbacks = callbacks;
    }

    public void startLocationUpdates() {
        if (!deviceInfo.isLocationEnabled()) {
            callbacks.onLocationUnavailable();
            return;
        }

        locationClient.requestUpdates(new LocationClient.Callback() {
            @Override public void onLocationUpdated(Location location) {
                if (!location.equals(lastLocation)) {
                    callbacks.onLocationChanged(location);
                }
                lastLocation = location;
            }
        });
    }

    public void stopLocationUpdates() {
        locationClient.stopUpdates();
    }
}
