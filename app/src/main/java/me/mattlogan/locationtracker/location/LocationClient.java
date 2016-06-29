package me.mattlogan.locationtracker.location;

public interface LocationClient {
    void requestUpdates(Callback callback);
    void stopUpdates();

    interface Callback {
        void onLocationUpdated(Location location);
    }
}
