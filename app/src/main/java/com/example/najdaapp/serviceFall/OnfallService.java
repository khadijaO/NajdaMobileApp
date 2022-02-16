package com.example.najdaapp.serviceFall;

import static android.Manifest.permission.CALL_PHONE;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;


import com.example.najdaapp.DBSqLite.DatabaseHelper;
import com.example.najdaapp.Message.MessageModule;
import com.example.najdaapp.R;
import com.example.najdaapp.contact.ContactModel;
import com.example.najdaapp.emergency.EmergencyModel;
import com.example.najdaapp.serviceShake.SensorService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OnfallService extends Service implements SensorEventListener {
    private static final String TAG="MainActivity";
    private SensorManager sensorManager;
    Sensor accelerometer;
    DatabaseHelper db;
    TextView xValue,yValue,zValue;
    @Override
    public void onCreate() {
        super.onCreate();
        // start the foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());


//        _______________________________________________________
        Log.d(TAG,"onCreate: Initializing Sensor Service");
        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.sensorManager.registerListener((SensorEventListener) this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG,"onCreate: Registered accelerometer Listener");


    }

    public OnfallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_MIN);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("FALL " +
                        "")
                .setContentText("We are there for you")

                // this is important, otherwise the notification will show the way
                // you want i.e. it will show some default notification
                .setSmallIcon(R.drawable.ic_launcher_foreground)

                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d(TAG,"onSensorChanged: X: " + sensorEvent.values[0]+"onSensorChanged: Y: "+ sensorEvent.values[1]+"onSensorChanged: Z: "+ sensorEvent.values[2]);


        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(OnfallService.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

//        xValue.setText("xValue: " +sensorEvent.values[0]);
//        yValue.setText("yValue: " +sensorEvent.values[1]);
//        zValue.setText("zValue: " +sensorEvent.values[2]);

        double loX = sensorEvent.values[0];
        double loY = sensorEvent.values[1];
        double loZ = sensorEvent.values[2];

        double loAccelerationReader = Math.sqrt(Math.pow(loX, 2)
                + Math.pow(loY, 2)
                + Math.pow(loZ, 2));

        DecimalFormat precision = new DecimalFormat("0.00");
        double ldAccRound = Double.parseDouble(precision.format(loAccelerationReader));

        if (ldAccRound > 0.3d && ldAccRound < 0.5d) {
            Toast.makeText(this, "FALL DETECTED!!!!!", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(Intent.ACTION_DIAL);
//             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setData(Uri.parse("119"));
//            startActivity(intent);


///send message
            // create FusedLocationProviderClient to get the user location
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

            // use the PRIORITY_BALANCED_POWER_ACCURACY
            // so that the service doesn't use unnecessary power via GPS
            // it will only use GPS at this very moment
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, new CancellationToken() {
                @Override
                public boolean isCancellationRequested() {
                    return false;
                }

                @NonNull
                @Override
                public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                    return null;
                }
            }).addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.d("Check: ", "hiiiii");
//                    Toast.makeText(getApplicationContext(), "Shake detected", Toast.LENGTH_SHORT).show();
//                    Vibrator v = (Vibrator)getSystemService(OnfallService.VIBRATOR_SERVICE);
//                    v.vibrate(1000);
                    // check if location is null
                    // for both the cases we will
                    // create different messages
                    if (location != null) {

                        // get the SMSManager
                        SmsManager smsManager = SmsManager.getDefault();

                        // get the list of all the contacts in Database
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        List<ContactModel> list = db.getAllContacts();
                        MessageModule msg=db.getMessage(true);
                        // send SMS to each contact
                        for (ContactModel c : list) {
                            String message = msg.getSalutation()+"    "+ c.getName() +"    "+ msg.getBody()+" \n " + "http://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();
                            smsManager.sendTextMessage(c.getPhoneNo(), null, message, null, null);
                        }
                    } else {
                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        MessageModule msg=db.getMessage(true);

//                                String message = "I am in DANGER, i need help. Please urgently reach me out.\n" + "GPS was turned off.Couldn't find location. Call your nearest Police Station.";
                        SmsManager smsManager = SmsManager.getDefault();
//                                DatabaseHelper db = new DatabaseHelper(SensorService.this);
                        List<ContactModel> list = db.getAllContacts();
                        for (ContactModel c : list) {
                            String message = "I am in DANGER, i need help. Please urgently reach me out.\n" + "GPS was turned off.Couldn't find location. Call your nearest Police Station.";

                            smsManager.sendTextMessage(c.getPhoneNo(), null, message, null, null);
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Check: ", "OnFailure");
                    String message = "I am in DANGER, i need help. Please urgently reach me out.\n" + "GPS was turned off.Couldn't find location. Call your nearest Police Station.";
                    SmsManager smsManager = SmsManager.getDefault();
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    List<ContactModel> list = db.getAllContacts();
                    for (ContactModel c : list) {
                        smsManager.sendTextMessage(c.getPhoneNo(), null, message, null, null);
                    }
                }
            });








            db=new DatabaseHelper(this);
            EmergencyModel r=db.getEmergency("");
            String tel=r.getPhoneNo();

            final Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:"+tel));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try{

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                Toast.makeText(getApplicationContext(), "seconds remaining: " + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
                Vibrator v = (Vibrator)getSystemService(OnfallService.VIBRATOR_SERVICE);
                v.vibrate(1000);
            }

            public void onFinish() {
                Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
            if(ContextCompat.checkSelfPermission(getApplicationContext(),CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
                startActivity(i);
//                Toast.makeText(getApplicationContext(), "ymkn", Toast.LENGTH_SHORT).show();
            }
            else {
//                Toast.makeText(getApplicationContext(), "ymknch", Toast.LENGTH_SHORT).show();

            }


            }
        }.start();



}
catch (Exception eh){
    Toast.makeText(this, "can't !!!!"+eh.getMessage(), Toast.LENGTH_LONG).show();

}

            Vibrator v = (Vibrator)getSystemService(OnfallService.VIBRATOR_SERVICE);
v.vibrate(1000);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}