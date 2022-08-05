package com.nebulacompanies.ibo.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.DashboardActivity;
import com.nebulacompanies.ibo.activities.SessionOverDialog;
import com.nebulacompanies.ibo.ecom.CustomerSupportList;
import com.nebulacompanies.ibo.ecom.MainActivity;
import com.nebulacompanies.ibo.ecom.MyCartActivity;
import com.nebulacompanies.ibo.ecom.ProductDescription;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.util.Session;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import static com.nebulacompanies.ibo.ecom.utils.Utils.callForceLogout;

/**
 * Created by Palak Mehta on 8/29/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static final String NOTIFICATION_CHANNEL_ID = "";

    String deviceId;
    String idtckt;
    Intent intent;
    String uniqueID;
    Session session;
    String token;

    /*Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            // sendNotification(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) { }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {}
    };*/

    RemoteMessage remoteMessage;

    @SuppressLint("HardwareIds")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        this.remoteMessage = remoteMessage;
        Log.d(TAG, "Notification===");
        //super.onMessageReceived(remoteMessage);
        Log.d(TAG, "FireBase123 Body: " + remoteMessage.getData().toString());
        Log.d(TAG, "FireBase123 Message: " + remoteMessage.getData().get("message"));
        Log.d(TAG, "FireBase123 Image: " + remoteMessage.getData().get("Base64String"));
        Log.d(TAG, "FireBase123 Redirect: " + remoteMessage.getData().get("Redirect"));

        //Calling method to generate notification
        //if(!Config.NOTIFICATION_OPEN) {
        //sendNotification(remoteMessage.getData().get("CreateDate"), remoteMessage.getData().get("message"), remoteMessage.getData().get("Base64String"), remoteMessage.getData().get("Redirect"));
        // sendNotification(remoteMessage.getData().get("message"), remoteMessage.getData().get("Base64String"), remoteMessage.getData().get("Redirect"));
        //}

        session = new Session(this);
        token = session.getAccessToken();

        deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        Log.d("MDEVICEID cart uniqueID", "MDEVICEIDcart uniqueID: " + uniqueID);
        if (deviceId == null || deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();
                SharedPreferences preferences = this.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid, randomID);
                editor.apply();
                deviceId = randomID;
            } else {
                deviceId = uniqueID;
            }
        }

        sendNotification();

    }

    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*private void sendNotification(String messageBody, String Image, String Redirect) {
        Config.NOTIFICATIONS = true;

        Log.d(TAG, "Redirect sendNotification1: " + messageBody);
        Log.d(TAG, "Redirect sendNotification2: " + Image);
        Log.d(TAG, "Redirect sendNotification3: " + Redirect);

    }*/

    private void sendNotification() {
        Config.NOTIFICATIONS = true;
        String messageBody = remoteMessage.getData().get("message");
        String Image = remoteMessage.getData().get("icon");
        String Redirect = remoteMessage.getData().get("Redirect");

        Log.d(TAG, "Redirect sendNotification1: " + messageBody);
        Log.d(TAG, "Redirect sendNotification2: " + Image);
        Log.d(TAG, "Redirect sendNotification3: " + Redirect);

        String strRedirect = Redirect;

      /*
        if (Redirect.equals( part1 )) {
            sendNotificationProductDecs( part1, messageBody, Image );
        }*/
        if (Redirect.equals("ForceLogOut")) {
            String Id = remoteMessage.getData().get("Id");
            callForceLogout = Integer.parseInt(Id);
            Log.d(TAG, "Redirect callForceLogout:Id  " + Id+" : "+session.getLoginID());
           /* Intent intent = new Intent(this, DashboardActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            createNotificationwithoutImage(pendingIntent, messageBody);*/
            //  new Utils().doLogout(getApplicationContext(), session);
            if (Id.equalsIgnoreCase(session.getLoginID())) {
                Intent dialogIntent = new Intent(this, SessionOverDialog.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);
            }
            // new Utils().doLogout(getApplicationContext(), session);
            //Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
           /* new Handler().postDelayed(() -> {
                Log.d(TAG, "showErrorDialog: " + messageBody);
                new Utils().showErrorDialog(getApplicationContext(), false, "", messageBody, true, session);
            }, 200);*/

        } else if (Redirect.equals("promotional")) {
            Intent intent = new Intent(this, DashboardActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            if (!Image.equals("")) {
                createNotification(pendingIntent, messageBody, Image);
            } else {
                createNotificationwithoutImage(pendingIntent, messageBody);
            }
        } else if (Redirect.equalsIgnoreCase("offer")) {
            Intent intent = new Intent(this, ProductDescription.class);
            String proId = remoteMessage.getData().get("product_id");
            String ebcId = remoteMessage.getData().get("ebc_id");
            String qnty = remoteMessage.getData().get("product_quantity");
            intent.putExtra("product_id", Integer.parseInt(proId));
            intent.putExtra("ebc_id", Integer.parseInt(ebcId));
            intent.putExtra("product_quantity", Integer.parseInt(qnty));
            intent.putExtra("notification", true);
            //intent.putExtra( "ecom_pick_up_id", Integer.parseInt("1") );
            Log.d(TAG, "product_id: " + proId);
            Log.d(TAG, "ebc_id: " + ebcId);
            Log.d(TAG, "product_quantity: " + qnty);
            //intent.putExtra( "ecom_pick_up_id", Integer.parseInt(cityID) );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            if (!Image.equals("")) {
                createNotification(pendingIntent, messageBody, Image);
            } else {
                createNotificationwithoutImage(pendingIntent, messageBody);
            }

        }
        //Event
        else if (Redirect.equalsIgnoreCase("eventcalendar")) {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra("notificationIntent", 1);
            //intent.putExtra( "Token",token);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            if (!Image.equals("")) {
                createNotification(pendingIntent, messageBody, Image);
            } else {
                createNotificationwithoutImage(pendingIntent, messageBody);
            }

        } else if (Redirect.equalsIgnoreCase("nebulacarepage")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            if (!Image.equals("")) {
                createNotification(pendingIntent, messageBody, Image);
            } else {
                createNotificationwithoutImage(pendingIntent, messageBody);
            }

        } else if (Redirect.equalsIgnoreCase("myorders")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("notificationIntent", 1);
            //intent.putExtra( "Token",token);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            if (!Image.equals("")) {
                createNotification(pendingIntent, messageBody, Image);
            } else {
                createNotificationwithoutImage(pendingIntent, messageBody);
            }

        } else if (Redirect.equals("cart")) {

            Config.NOTIFICATIONS = true;
            intent = new Intent(this, MyCartActivity.class);
            intent.putExtra("deviceid", deviceId);
            intent.putExtra("userid", session.getIboKeyId());
            intent.putExtra("pickupid", 0);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, Config.NOTIFICATION_COUNT, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            if (!Image.equals("")) {
                createNotification(pendingIntent, messageBody, Image);
            } else {
                createNotificationwithoutImage(pendingIntent, messageBody);
            }

           /*if (!Image.equals( "" )) {
               NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture( getBitmapFromURL( Image ) );
               Uri defaultSoundUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
               int color = getResources().getColor( R.color.nebula );
               // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
               NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( this,"default" )
                       .setSmallIcon( getNotificationIcon() )
                       .setColor( color )
                       .setContentTitle( "Nebula Pro" )
                       .setAutoCancel( true )
                       .setStyle( s.setSummaryText( messageBody ) )
                       //.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                       .setContentText( messageBody )
                       .setSound( defaultSoundUri )
                       .setPriority( Notification.PRIORITY_HIGH )
                       .setContentIntent( pendingIntent );


               NotificationManager mNotificationManager =
                       (NotificationManager) this.getSystemService( Context.NOTIFICATION_SERVICE );

               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   int importance = NotificationManager.IMPORTANCE_HIGH;
                   NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance );
                   notificationChannel.enableLights( true );
                   notificationChannel.setLightColor( Color.GREEN );
                   notificationChannel.enableVibration( true );
                   notificationChannel.setVibrationPattern( new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400} );
                   assert mNotificationManager != null;
                   notificationBuilder.setChannelId( NOTIFICATION_CHANNEL_ID );
                   mNotificationManager.createNotificationChannel( notificationChannel );
               }
               // Config.NOTIFICATION_COUNT++;

               assert mNotificationManager != null;
               mNotificationManager.notify( (int) System.currentTimeMillis(), notificationBuilder.build() );
               Log.i( "INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT );
           } else {
               Uri defaultSoundUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
               int color = getResources().getColor( R.color.nebula );
               // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
               NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( this,"default" )
                       .setSmallIcon( getNotificationIcon() )
                       .setColor( color )
                       .setContentTitle( "Nebula Pro" )
                       .setAutoCancel( true )
                       .setStyle( new NotificationCompat.BigTextStyle().bigText( messageBody ) )
                       .setContentText( messageBody )
                       .setSound( defaultSoundUri )
                       .setPriority( Notification.PRIORITY_HIGH )
                       .setContentIntent( pendingIntent );


               NotificationManager mNotificationManager =
                       (NotificationManager) this.getSystemService( Context.NOTIFICATION_SERVICE );

               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   int importance = NotificationManager.IMPORTANCE_HIGH;
                   NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance );
                   notificationChannel.enableLights( true );
                   notificationChannel.setLightColor( Color.GREEN );
                   notificationChannel.enableVibration( true );
                   notificationChannel.setVibrationPattern( new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400} );
                   assert mNotificationManager != null;
                   notificationBuilder.setChannelId( NOTIFICATION_CHANNEL_ID );
                   mNotificationManager.createNotificationChannel( notificationChannel );
               }
               Config.NOTIFICATION_COUNT++;

               assert mNotificationManager != null;
               mNotificationManager.notify( (int) System.currentTimeMillis(), notificationBuilder.build() );
               Log.i( "INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT );
           }*/
        } else if (Redirect.equals("Nebula_Pro")) {
            intent = new Intent(MyFirebaseMessagingService.this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, Config.NOTIFICATION_COUNT, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            if (!Image.equals("")) {
                createNotification(pendingIntent, messageBody, Image);
            } else {
                createNotificationwithoutImage(pendingIntent, messageBody);
            }

           /* if (!Image.equals("")) {
                NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromURL(Image));
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                int color = getResources().getColor(R.color.nebula);
// Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                        .setSmallIcon(getNotificationIcon())
                        .setColor(color)
                        .setContentTitle("Nebula Pro")
                        .setAutoCancel(true)
                        .setStyle(s.setSummaryText(messageBody))
//.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setContentText(messageBody)
                        .setSound(defaultSoundUri)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);


                NotificationManager mNotificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert mNotificationManager != null;
                    notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    mNotificationManager.createNotificationChannel(notificationChannel);
                }
            }*/
        } else if (Redirect.equals("ticket")) {
            idtckt = remoteMessage.getData().get("RedirectId");
            intent = new Intent(MyFirebaseMessagingService.this, CustomerSupportList.class);
            intent.putExtra("ticketId", Integer.parseInt(idtckt));
            intent.putExtra("session_back", 1);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, Config.NOTIFICATION_COUNT, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            if (!Image.equals("")) {
                createNotification(pendingIntent, messageBody, Image);
            } else {
                createNotificationwithoutImage(pendingIntent, messageBody);
            }
          /*  //    if (Image.equals("")) {
            NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromURL(Image));
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            int color = getResources().getColor(R.color.nebula);
// Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(getNotificationIcon())
                    .setColor(color)
                    .setContentTitle("Nebula Pro")
                    .setAutoCancel(true)
                    .setStyle(s.setSummaryText(messageBody))
//.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setContentText(messageBody)

                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);


            NotificationManager mNotificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }

            assert mNotificationManager != null;
            mNotificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
            Log.i("INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT);

            // }*/
        } else if (Redirect.equals(strRedirect)) {


            String part1 = strRedirect.split("\\|")[0]; // Redirect Name
            String part2 = strRedirect.split("\\|")[1];// CategoryId
            String part3 = strRedirect.split("\\|")[2]; // SubCategoryId
            String part4 = strRedirect.split("\\|")[3];// ProductId

            String strPart = part2;
            String CategoryId = strPart.split("\\=")[1];
            //  Log.e("part11", "part11:- " + CategoryId);

            String strProductId = part4;
            String productIdNotification = strProductId.split("\\=")[1];
            //  Log.e("part12", "part12:- " + productIdNotification);

            String strProductIdNew = part4;
            String productIdNotificationNew = strProductIdNew.split("\\=")[1];
            //   Log.e("part12New", "part12New:- " + productIdNotificationNew);


         /*   Log.d(TAG, "Part1: " + part1);
            Log.d(TAG, "Part2: " + part2);
            Log.d(TAG, "Part3: " + part3);
            Log.d(TAG, "Part4: " + part4);*/

           /* Intent intent = new Intent( this, DashboardActivity.class );

            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            PendingIntent pendingIntent = PendingIntent.getActivity( this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT );*/

            intent = new Intent(MyFirebaseMessagingService.this, ProductDescription.class);
            intent.putExtra("catid", part2);
           /* intent.putExtra("CategoryId_Notification", CategoryId);
            intent.putExtra("ProductId_Notification", productIdNotification);
            intent.putExtra("ProductId_Notification_ID", productIdNotificationNew);
            intent.putExtra("device_id_decs", deviceId);
            intent.putExtra("session_id_decs", session.getIboKeyId());*/

           /* intent.putExtra( "product_id",product.getProductId());
            intent.putExtra( "ebc_id",   product.getProductDetilsID()  );
            intent.putExtra( "product_quantity", product.getQuantity() );
            intent.putExtra( "ecom_pick_up_id", Integer.parseInt(cityID) );*/



         /*   Log.e("CategoryId", "CategoryId:- " + CategoryId);
            Log.e("productIdNotification", "productIdNotification:- " + productIdNotification);
            Log.e("productIdNotificationN", "productIdNotificationN:- " + productIdNotificationNew);*/
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, Config.NOTIFICATION_COUNT, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            if (!Image.equals("")) {
                createNotification(pendingIntent, messageBody, Image);
            } else {
                createNotificationwithoutImage(pendingIntent, messageBody);
            }
           /* if (!Image.equals("")) {
                NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromURL(Image));
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                int color = getResources().getColor(R.color.nebula);
                // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                        .setSmallIcon(getNotificationIcon())
                        .setColor(color)
                        .setContentTitle("Nebula Pro")
                        .setAutoCancel(true)
                        .setStyle(s.setSummaryText(messageBody))
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setContentText(messageBody)
                        .setSound(defaultSoundUri)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);


                NotificationManager mNotificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert mNotificationManager != null;
                    notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    mNotificationManager.createNotificationChannel(notificationChannel);
                }
                // Config.NOTIFICATION_COUNT++;

                assert mNotificationManager != null;
                mNotificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
                Log.i("INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT);
            } else {
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                int color = getResources().getColor(R.color.nebula);
                // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                        .setSmallIcon(getNotificationIcon())
                        .setColor(color)
                        .setContentTitle("Nebula Pro")
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setContentText(messageBody)
                        .setSound(defaultSoundUri)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);


                NotificationManager mNotificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert mNotificationManager != null;
                    notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    mNotificationManager.createNotificationChannel(notificationChannel);
                }
                Config.NOTIFICATION_COUNT++;

                assert mNotificationManager != null;
                mNotificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
                Log.i("INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT);
            }*/

        } else {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (!Image.equals("")) {
                createNotification(pendingIntent, messageBody, Image);
            } else {
                createNotificationwithoutImage(pendingIntent, messageBody);
            }
           /* if (!Image.equals("")) {
                NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromURL(Image));
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                int color = getResources().getColor(R.color.nebula);
                // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                        .setSmallIcon(getNotificationIcon())
                        .setColor(color)
                        .setContentTitle("Nebula Pro")
                        .setAutoCancel(true)
                        .setStyle(s.setSummaryText(messageBody))
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setContentText(messageBody)
                        .setSound(defaultSoundUri)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);


                NotificationManager mNotificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert mNotificationManager != null;
                    notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    mNotificationManager.createNotificationChannel(notificationChannel);
                }
                // Config.NOTIFICATION_COUNT++;

                assert mNotificationManager != null;
                mNotificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
                Log.i("INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT);
            } else {
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                int color = getResources().getColor(R.color.nebula);
                // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                        .setSmallIcon(getNotificationIcon())
                        .setColor(color)
                        .setContentTitle("Nebula Pro")
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setContentText(messageBody)
                        .setSound(defaultSoundUri)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);


                NotificationManager mNotificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert mNotificationManager != null;
                    notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    mNotificationManager.createNotificationChannel(notificationChannel);
                }
                Config.NOTIFICATION_COUNT++;

                assert mNotificationManager != null;
                mNotificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
                Log.i("INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT);
            }*/
        }

    }

    private void createNotificationwithoutImage(PendingIntent pendingIntent, String messageBody) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int color = getResources().getColor(R.color.nebula);
        // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.app_logo))
                .setColor(color)
                .setContentTitle("Nebula Pro")
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentText(messageBody)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);


        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        Config.NOTIFICATION_COUNT++;

        assert mNotificationManager != null;
        mNotificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        Log.i("INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT);
    }

    private void createNotification(PendingIntent pendingIntent, String messageBody, String Image) {
        NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromURL(Image));
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int color = getResources().getColor(R.color.nebula);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.app_logo))
                .setColor(color)
                .setContentTitle("Nebula Pro")
                .setAutoCancel(true)
                .setStyle(s.setSummaryText(messageBody))
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentText(messageBody)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);


        NotificationManager mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        // Config.NOTIFICATION_COUNT++;

        assert mNotificationManager != null;
        mNotificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        Log.i("INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT);
    }

/*
    private void sendNotificationProductDecs(String project_name, String messageBody, String Image) {
        Config.NOTIFICATIONS = true;

        intent = new Intent(this, ProductDescription.class);
        intent.putExtra("Product_Name", project_name);
        intent.putExtra("Notification_Click", true);
        intent.putExtra("MonthInText", month);

        intent.putExtra("Year", year);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, Config.NOTIFICATION_COUNT, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Bitmap bitmap_image = BitmapFactory.decodeResource(this.getResources(), R.drawable.nebula_logo);

        if (!Image.equals("")) {
            NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromURL(Image));
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            int color = getResources().getColor(R.color.nebula);
            // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(getNotificationIcon())
                    .setColor(color)
                    .setContentTitle("Nebula Pro")
                    .setAutoCancel(true)
                    .setStyle(s.setSummaryText(messageBody))
                    //.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);


            NotificationManager mNotificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            // Config.NOTIFICATION_COUNT++;

            assert mNotificationManager != null;
            mNotificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
            Log.i("INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT);
        } else {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            int color = getResources().getColor(R.color.nebula);
            // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(getNotificationIcon())
                    .setColor(color)
                    .setContentTitle("Nebula Pro")
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);


            NotificationManager mNotificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            Config.NOTIFICATION_COUNT++;

            assert mNotificationManager != null;
            mNotificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
            Log.i("INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT);
        }
    }*/

/*
    private void sendNotificationCart(String project_name, String messageBody, String Image) {
        session = new Session(this);
        deviceId = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        Log.d("MDEVICEID cart uniqueID", "MDEVICEIDcart uniqueID: " + uniqueID);
        if (deviceId == null || deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = this.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid, randomID);
                editor.apply();
                deviceId = randomID;
            } else {
                deviceId = uniqueID;
            }
        }

        Config.NOTIFICATIONS = true;

        intent = new Intent(this, MyCartActivity.class);
        intent.putExtra("device_id", deviceId);
        intent.putExtra("session_id", session.getIboKeyId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, Config.NOTIFICATION_COUNT, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Bitmap bitmap_image = BitmapFactory.decodeResource(this.getResources(), R.drawable.nebula_logo);

        if (!Image.equals("")) {
            NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromURL(Image));
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            int color = getResources().getColor(R.color.nebula);
            // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(getNotificationIcon())
                    .setColor(color)
                    .setContentTitle("Nebula Pro")
                    .setAutoCancel(true)
                    .setStyle(s.setSummaryText(messageBody))
                    //.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);


            NotificationManager mNotificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            // Config.NOTIFICATION_COUNT++;

            assert mNotificationManager != null;
            mNotificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
            Log.i("INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT);
        } else {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            int color = getResources().getColor(R.color.nebula);
            // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(getNotificationIcon())
                    .setColor(color)
                    .setContentTitle("Nebula Pro")
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);


            NotificationManager mNotificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            Config.NOTIFICATION_COUNT++;

            assert mNotificationManager != null;
            mNotificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
            Log.i("INFO", "NOTIFICATION_COUNT : " + Config.NOTIFICATION_COUNT);
        }
    }


    private void sendIBONotification(String Redirect, String messageBody, String Image) {
        //try {
        Intent intent = new Intent(this, IBONotifications.class);
        //intent.putExtra("Notification_Click", true);
        intent.putExtra("REDIRECT", Redirect);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Bitmap bitmap_image = BitmapFactory.decodeResource(this.getResources(), R.drawable.nebula_logo);

        if (!Image.equals("")) {
            NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromURL(Image));
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            int color = getResources().getColor(R.color.nebula);
            // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(getNotificationIcon())
                    .setColor(color)
                    .setContentTitle("IBOBackOffice Notification")
                    .setAutoCancel(true)
                    .setStyle(s.setSummaryText(messageBody))
                    //.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        } else {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            int color = getResources().getColor(R.color.nebula);
            // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.nebula_launcher);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(getNotificationIcon())
                    .setColor(color)
                    .setContentTitle("IBOBackOffice Notification")
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }

        *//*} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*//*
    }*/

    /* private int getNotificationIcon() {
         boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
         return useWhiteIcon ? R.mipmap.app_logo_white : R.mipmap.notification_icon;
     }
 */


    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        //  return useWhiteIcon ? R.mipmap.notification_icon : R.mipmap.nebula_launcher;
        return useWhiteIcon ? R.mipmap.notification_icon : R.mipmap.app_logo;

    }

}
