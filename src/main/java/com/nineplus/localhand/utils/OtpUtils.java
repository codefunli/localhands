package com.nineplus.localhand.utils;

import com.google.firebase.database.DatabaseReference;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OtpUtils {
    public static void startDataDeletionTimer(DatabaseReference databaseReference) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            databaseReference.removeValueAsync();
            System.out.println("Data deleted from Firebase Realtime Database.");
            scheduler.shutdown();
        }, 300, TimeUnit.SECONDS);
    }
    public static String generateRandomNumber() {
        return String.format("%04d", new SecureRandom().nextInt(9999));
    }
}
