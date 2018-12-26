package io.github.dalwadi2.jobscheduler;


import android.app.Application;

import com.evernote.android.job.JobManager;

/**
 * Project : JobScheduler
 * Created by: Harsh Dalwadi - Senior Software Engineer
 * Created Date: 26-12-2018
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new NoteJobCreator());
    }
}