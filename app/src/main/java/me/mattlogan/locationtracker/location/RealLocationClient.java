package me.mattlogan.locationtracker.location;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public final class RealLocationClient implements LocationClient {

    private final GoogleApiClient          googleApiClient;
    private final LocationRequest          locationRequest;
    private final FusedLocationProviderApi locationProviderApi;

    private GoogleApiClient.ConnectionCallbacks connectionCallbacks;

    public RealLocationClient(Context context) {
        this.googleApiClient = new GoogleApiClient.Builder(context)
                                   .addApi(LocationServices.API)
                                   .build();
        this.locationRequest = LocationRequest.create()
                                              .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                              .setInterval(1000); // milliseconds
        this.locationProviderApi = LocationServices.FusedLocationApi;
    }

    @Override public void requestUpdates(final Callback callback) {
        connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
            @Override public void onConnected(@Nullable final Bundle bundle) {
                Log.d("RealLocationClient", "onConnected");
                try {
                    locationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, new LocationListener() {
                        @Override public void onLocationChanged(final android.location.Location location) {
                            Log.d("RealLocationClient", "onLocationChanged: " + location);
                            callback.onLocationUpdated(new Location(location.getLatitude(), location.getLongitude()));
                        }
                    });
                } catch (SecurityException e) {
                    throw new RuntimeException("Need to request permission first!", e);
                }
            }

            @Override public void onConnectionSuspended(final int i) {
                Log.d("RealLocationClient", "onConnectionSuspended");
            }
        };
        googleApiClient.registerConnectionCallbacks(connectionCallbacks);
        googleApiClient.connect();
    }

    @Override public void stopUpdates() {
        if (connectionCallbacks != null) {
            googleApiClient.unregisterConnectionCallbacks(connectionCallbacks);
        }
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }
}
