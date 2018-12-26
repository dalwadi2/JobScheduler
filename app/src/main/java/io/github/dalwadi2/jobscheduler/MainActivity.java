package io.github.dalwadi2.jobscheduler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteSyncJob.scheduleJob();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                for (JobRequest allJobRequest : JobManager.instance().getAllJobRequests()) {
                    Log.e(TAG, "onClick: " + allJobRequest.toString());
                }

            }
        });
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (JobRequest allJobRequest : JobManager.instance().getAllJobRequests()) {
                    Log.e(TAG, "onClick toString: " + allJobRequest.toString());
                    Log.e(TAG, "onClick getFailureCount: " + allJobRequest.getFailureCount());
                    Log.e(TAG, "onClick: getScheduledAt" + allJobRequest.getScheduledAt());
                    Log.e(TAG, "onClick: getScheduledAt" + allJobRequest.getEndMs());
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                String channelId = "default_channel";
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                bigTextStyle.setBigContentTitle("Android Job Demo");
                bigTextStyle.bigText("Notification from Android Job Demo App.");

                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(MainActivity.this, channelId)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setStyle(bigTextStyle)
                                .setColorized(true)
                                .setColor(getResources().getColor(R.color.colorAccent))
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId,
                            "Inspyred Updates",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setShowBadge(true);
                    notificationManager.createNotificationChannel(channel);
                }
//                notificationManager.notify(new Random().nextInt(), notificationBuilder.build());


                NotificationManagerCompat.from(MainActivity.this)
                        .notify(new Random().nextInt(), notificationBuilder.build());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
