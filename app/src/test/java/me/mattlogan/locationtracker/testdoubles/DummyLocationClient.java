package me.mattlogan.locationtracker.testdoubles;

import me.mattlogan.locationtracker.location.LocationClient;

public final class DummyLocationClient implements LocationClient {

    @Override public void requestUpdates(Callback callback) {
        throw new RuntimeException("This is a dummy implementation!");
    }

    @Override public void stopUpdates() {
        throw new RuntimeException("This is a dummy implementation!");
    }
}
