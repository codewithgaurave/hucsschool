package team.errors.hucs.network;

import android.app.Application;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        // Subscribe to a topic for receiving notifications
        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Successfully subscribed
                    }
                });
    }
}