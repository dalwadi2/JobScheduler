package io.github.dalwadi2.jobscheduler;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Project : JobScheduler
 * Created by: Harsh Dalwadi - Senior Software Engineer
 * Created Date: 26-12-2018
 */
public class NoteSyncJob extends Job {

    public static final String TAG = "job_note_sync";

    public static void scheduleJob() {
//        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(NoteSyncJob.TAG);
//        if (!jobRequests.isEmpty()) {
//            return;
//        }
        int jobid = new JobRequest.Builder(NoteSyncJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(7))
//                .setUpdateCurrent(true) // calls cancelAllForTag(NoteSyncJob.TAG) for you
//                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .build()
                .schedule();
        Log.w(TAG, "scheduleJob: JobId : " + jobid);
        int jobid1 = new JobRequest.Builder(NoteSyncJob.TAG)
//                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .startNow()
                .build()
                .schedule();
        Log.w(TAG, "scheduleJob: jobid1 : " + jobid1);
    }

    @Override
    @NonNull
    protected Result onRunJob(@NonNull Params params) {
        Log.w(TAG, "onRunJob: " + System.currentTimeMillis());
        PendingIntent pi = PendingIntent.getActivity(getContext(), 0,
                new Intent(getContext(), MainActivity.class), 0);
        String channelId = "default_channel";

        Notification notification =
                new NotificationCompat.Builder(getContext(), channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Android Job Demo")
                        .setContentText("Notification from Android Job Demo App.")
                        .setColorized(true)
                        .setColor(getContext().getResources().getColor(R.color.colorAccent))
                        .setAutoCancel(true)
                        .setContentIntent(pi)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setShowWhen(true)
                        .setColor(Color.RED)
                        .setLocalOnly(true).build();

        NotificationManagerCompat.from(getContext())
                .notify(new Random().nextInt(), notification);
        return Job.Result.SUCCESS;
    }
}