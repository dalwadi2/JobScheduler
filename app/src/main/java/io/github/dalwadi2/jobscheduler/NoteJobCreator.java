package io.github.dalwadi2.jobscheduler;


import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Project : JobScheduler
 * Created by: Harsh Dalwadi - Senior Software Engineer
 * Created Date: 26-12-2018
 */
class NoteJobCreator implements JobCreator {
    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case NoteSyncJob.TAG:
                return new NoteSyncJob();
            default:
                return null;
        }
    }
}
