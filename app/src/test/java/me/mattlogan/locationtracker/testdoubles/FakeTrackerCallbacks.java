package me.mattlogan.locationtracker.testdoubles;

import me.mattlogan.locationtracker.location.Location;
import me.mattlogan.locationtracker.location.LocationChangeTracker;

public final class FakeTrackerCallbacks implements LocationChangeTracker.Callbacks {

    private int numLocationChangedCalls = 0;

    @Override public void onLocationUnavailable() {
        // Ignore
    }

    @Override public void onLocationChanged(final Location location) {
        numLocationChangedCalls++;
    }

    public int numLocationChangedCalls() {
        return numLocationChangedCalls;
    }
}
