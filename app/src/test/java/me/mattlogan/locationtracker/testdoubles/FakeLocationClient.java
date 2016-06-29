package me.mattlogan.locationtracker.testdoubles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.mattlogan.locationtracker.location.Location;
import me.mattlogan.locationtracker.location.LocationClient;

public final class FakeLocationClient implements LocationClient {

    private final List<Location> preLoadedLocations;

    public FakeLocationClient(Location... locations) {
        preLoadedLocations = new ArrayList<>();
        Collections.addAll(preLoadedLocations, locations);
    }

    @Override public void requestUpdates(Callback callback) {
        for (Location location : preLoadedLocations) {
            callback.onLocationUpdated(location);
        }
    }

    @Override public void stopUpdates() {
        // Don't do anything
    }
}
