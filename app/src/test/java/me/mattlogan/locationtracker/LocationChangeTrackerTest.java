package me.mattlogan.locationtracker;

import me.mattlogan.locationtracker.location.DeviceInfo;
import me.mattlogan.locationtracker.location.Location;
import me.mattlogan.locationtracker.location.LocationChangeTracker;
import me.mattlogan.locationtracker.location.LocationClient;
import me.mattlogan.locationtracker.testdoubles.DummyLocationClient;
import me.mattlogan.locationtracker.testdoubles.FakeLocationClient;
import me.mattlogan.locationtracker.testdoubles.FakeTrackerCallbacks;
import me.mattlogan.locationtracker.testdoubles.LocationDisabledDeviceInfo;
import me.mattlogan.locationtracker.testdoubles.LocationEnabledDeviceInfo;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LocationChangeTrackerTest {

    @Test
    public void locationUnavailableCalledWhenLocationNotEnabled() throws Exception {
        DeviceInfo locationDisabledDeviceInfo = new LocationDisabledDeviceInfo(); // Stub
        LocationClient dummyLocationClient = new DummyLocationClient(); // Dummy
        LocationChangeTracker.Callbacks callbacks = mock(LocationChangeTracker.Callbacks.class); // Spy

        LocationChangeTracker tracker = new LocationChangeTracker(locationDisabledDeviceInfo,
                                                                  dummyLocationClient,
                                                                  callbacks);

        tracker.startLocationUpdates();

        verify(callbacks).onLocationUnavailable(); // Using the spy to verify correct behavior
    }

    @Test
    public void startLocationUpdatesCallsLocationChangedCorrectNumberOfTimesWithSpy() throws Exception {
        DeviceInfo locationEnabledDeviceInfo = new LocationEnabledDeviceInfo(); // Stub
        LocationClient fakeLocationClient = new FakeLocationClient( // Fake
                                                                    new Location(0.1, -0.2),
                                                                    new Location(-1.2, 3.4),
                                                                    new Location(-5, 10),
                                                                    new Location(-5, 10) // Same location twice in a row
        );
        LocationChangeTracker.Callbacks callbacks = mock(LocationChangeTracker.Callbacks.class); // Spy

        LocationChangeTracker tracker = new LocationChangeTracker(locationEnabledDeviceInfo,
                                                                  fakeLocationClient,
                                                                  callbacks);

        tracker.startLocationUpdates();

        verify(callbacks, times(3)).onLocationChanged(isA(Location.class)); // Use the spy to verify correct behavior
    }

    @Test
    public void startLocationUpdatesCallsLocationChangedCorrectNumberOfTimesWithFake() throws Exception {
        DeviceInfo locationEnabledDeviceInfo = new LocationEnabledDeviceInfo(); // Stub
        LocationClient fakeLocationClient = new FakeLocationClient( // Fake
                                                                    new Location(0.1, -0.2),
                                                                    new Location(-1.2, 3.4),
                                                                    new Location(-5, 10),
                                                                    new Location(-5, 10) // Same location twice in a row
        );
        FakeTrackerCallbacks fakeCallbacks = new FakeTrackerCallbacks(); // Fake

        LocationChangeTracker tracker = new LocationChangeTracker(locationEnabledDeviceInfo,
                                                                  fakeLocationClient,
                                                                  fakeCallbacks);

        tracker.startLocationUpdates();

        assertEquals(3, fakeCallbacks.numLocationChangedCalls());
    }
}
