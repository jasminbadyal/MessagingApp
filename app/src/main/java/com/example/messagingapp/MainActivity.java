package com.example.messagingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver BroadReciever;
    TextView displayStatus;
    String message;
    String number;
    Runnable runnable;
    TextView Message;
    String themessage;
    private static final int MY_PERMISSIONS_REQUEST_RECIEVE_SMS = 2;
   private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final int MY_PERMISSIONS_READ_PHONE_STATE = 3;
   private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 4;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_RECIEVE_SMS: {
                // permission is granted
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("BROTHER", "send sms granted");
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();

                } else {
                    Log.d("BROTHER", "send me permission granted");
                    Toast.makeText(MainActivity.this, "Permission failure", Toast.LENGTH_LONG).show();


                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // permission is granted
                        if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(number, null, message, null, null);
                    Log.d("BROTHER", "send sms granted");
                } else {
                    Log.d("BROTHER", "send me permission granted");
                    Toast.makeText(MainActivity.this, "Permission failure", Toast.LENGTH_LONG).show();

                        }
                return;
            }

            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                // permission is granted
                        if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(number, null, message, null, null);
                            Toast.makeText(getApplicationContext(), "SMS sent.",
                                    Toast.LENGTH_LONG).show();
                    Log.d("BROTHER", "send sms granted");



                } else {
                    Log.d("BROTHER", "send me permission granted");
                    Toast.makeText(MainActivity.this, "Permission failure", Toast.LENGTH_LONG).show();

                        }
                return;

            }



            case MY_PERMISSIONS_READ_PHONE_STATE: {
                // permission is granted
                        if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("BROTHER", "send sms granted");
                            sendSMS(number,  message);

                        } else {
                    Log.d("BROTHER", "send me permission granted");
                    Toast.makeText(MainActivity.this, "Permission failure", Toast.LENGTH_LONG).show();



                }
                return;
            }


        }
    }

    //Recieve Messages
  //  @Override
//  protected void onResume() {
     //   super.onResume();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Message = findViewById(R.id.Message);

        checkForSMSPermission();

        BroadReciever = new BroadcastReceiver() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceive(Context context, Intent intent) {

                themessage=" ";
                Bundle bundle = new Bundle();
                // intent = getIntent();

                bundle = intent.getExtras();

                Object pdus[] = (Object[]) bundle.get("pdus");


                SmsMessage[] smsMessages = new SmsMessage[pdus.length];

                for (int i = 0; i < pdus.length; i++) {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], bundle.getString("pdus"));

                    themessage+=smsMessages[i].getOriginatingAddress();
                    themessage+=smsMessages[i].getDisplayMessageBody();
                    Log.d("BROTHER", "onReceive: " + themessage);
                    Toast.makeText(context, themessage, Toast.LENGTH_LONG).show();




                }


                IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");


                context.registerReceiver(BroadReciever, intentFilter);


                //Send Messages

               // SmsManager smsManager = SmsManager.getDefault();
               // smsManager.sendTextMessage(number, null, message, null, null);

               // Handler handler = new Handler();
              //  handler.postDelayed(runnable, 10);


            }


        };


        };

    private void checkForSMSPermission() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("BROTHER", "Jasmin");
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
                Log.d("BROTHER", "Jasmin2");

            } else {
                Log.d("BROTHER", "PERMISSION ALREADY GRANTED HAHA!");
            sendSMS(number,  message);

        }


        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("BROTHER", "Jasmin");
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_REQUEST_READ_SMS);
            Log.d("BROTHER", "Jasmin2");

        } else {
            Log.d("BROTHER", "PERMISSION ALREADY GRANTED HAHA!");
            sendSMS(number,  message);
        }


        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("BROTHER", "Jasmin");
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    MY_PERMISSIONS_REQUEST_RECIEVE_SMS);
            Log.d("BROTHER", "Jasmin2");

        } else {
            Log.d("BROTHER", "PERMISSION ALREADY GRANTED HAHA!");
            sendSMS(number,  message);

        }


        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("BROTHER", "Jasmin");
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_READ_PHONE_STATE);
            Log.d("BROTHER", "Jasmin2");

        } else {
            Log.d("BROTHER", "PERMISSION ALREADY GRANTED HAHA!");
            sendSMS(number,  message);


        }
        }

        private void sendSMS(String phoneNumber, String message)
        {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
        }


    };








