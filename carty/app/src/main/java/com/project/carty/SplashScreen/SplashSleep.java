package com.project.carty.SplashScreen;

import android.app.Application;
import android.os.Build;
import android.os.SystemClock;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class SplashSleep extends Application {
    public static final String CHANNEL_1_ID = "Fruits and Vegetables Category";
    public static final String CHANNEL_2_ID = "Meat, Poultry and Fish Category";
    public static final String CHANNEL_3_ID = "Bakery and Sweets Category";
    public static final String CHANNEL_4_ID = "Cheese and Dairy Category";
    public static final String CHANNEL_5_ID = "Frozen Food Category";
    public static final String CHANNEL_6_ID = "Cleaning Needs Category";
    public static final String CHANNEL_7_ID = "Rice, Pasta and Legumes Category";
    public static final String CHANNEL_8_ID = "Drinks Category";

    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(3000);

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Recommendations for Fruits and Vegetables Category",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Fruits and Vegetables Category");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Recommendations for Meat, Poultry and Fish Category",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("Meat, Poultry and Fish Category");
            NotificationChannel channel3 = new NotificationChannel(
                    CHANNEL_3_ID,
                    "Recommendations for Bakery and Sweets Category",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel3.setDescription("Bakery and Sweets Category");
            NotificationChannel channel4 = new NotificationChannel(
                    CHANNEL_4_ID,
                    "Recommendations for Cheese and Dairy Category",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel4.setDescription("Cheese and Dairy Category");
            NotificationChannel channel5 = new NotificationChannel(
                    CHANNEL_5_ID,
                    "Recommendations for Frozen Food Category",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel5.setDescription("Frozen Food Category");
            NotificationChannel channel6 = new NotificationChannel(
                    CHANNEL_6_ID,
                    "Recommendations for Cleaning Needs Category",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel6.setDescription("Cleaning Needs Category");
            NotificationChannel channel7 = new NotificationChannel(
                    CHANNEL_7_ID,
                    "Recommendations for Rice, Pasta and Legumes Category",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel7.setDescription("Rice, Pasta and Legumes Category");
            NotificationChannel channel8 = new NotificationChannel(
                    CHANNEL_8_ID,
                    "Recommendations for Drinks Category",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel8.setDescription("Drinks Category");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
            manager.createNotificationChannel(channel3);
            manager.createNotificationChannel(channel4);
            manager.createNotificationChannel(channel5);
            manager.createNotificationChannel(channel6);
            manager.createNotificationChannel(channel7);
            manager.createNotificationChannel(channel8);
        }
    }

}
