package me.mattlogan.locationtracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;
import me.mattlogan.locationtracker.location.DeviceInfo;
import me.mattlogan.locationtracker.location.Location;
import me.mattlogan.locationtracker.location.LocationChangeTracker;
import me.mattlogan.locationtracker.location.LocationClient;
import me.mattlogan.locationtracker.location.RealDeviceInfo;
import me.mattlogan.locationtracker.location.RealLocationClient;

public class MainActivity extends AppCompatActivity implements LocationChangeTracker.Callbacks {

    private View     rootView;
    private TextView latitudeText;
    private TextView longitudeText;

    private LocationChangeTracker locationChangeTracker;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = findViewById(R.id.root_view);
        latitudeText = (TextView) findViewById(R.id.latitude_text);
        longitudeText = (TextView) findViewById(R.id.longitude_text);
        setupLocationTracker();
    }

    private void setupLocationTracker() {
        DeviceInfo deviceInfo = new RealDeviceInfo(getContentResolver());
        LocationClient locationClient = new RealLocationClient(this);
        locationChangeTracker = new LocationChangeTracker(deviceInfo, locationClient, this);
    }

    @Override public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationChangeTracker.startLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationChangeTracker.startLocationUpdates();
        }
    }

    @Override public void onPause() {
        super.onPause();
        locationChangeTracker.stopLocationUpdates();
    }

    @Override public void onLocationUnavailable() {
        Snackbar
            .make(rootView, "Location is not enabled!", Snackbar.LENGTH_LONG)
            .show();
    }

    @Override public void onLocationChanged(Location location) {
        latitudeText.setText(String.format(Locale.US, "Latitude: %f", location.latitude()));
        longitudeText.setText(String.format(Locale.US, "Longitude: %f", location.longitude()));
    }
}
