package com.capgemini.larseknu.playingwithservices;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WorkerService extends Service {
    Worker mWorker;
    ExecutorService mExecutorService;
    ScheduledExecutorService mScheduledService;

    public WorkerService() {
    }

    @Override
    public void onCreate() {
        mWorker = new Worker(this);
        mWorker.MonitorGpsInBackground();
        mExecutorService = Executors.newSingleThreadExecutor();
        mScheduledService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ServiceRunnable runnable = new ServiceRunnable(this, startId);

        mExecutorService.execute(runnable);

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mWorker.stopGpsMonitoring();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class ServiceRunnable implements Runnable {
        WorkerService mWorkerService;
        int mStartId;

        public ServiceRunnable(WorkerService workerService, int startId) {
            mWorkerService = workerService;
            mStartId = startId;
        }

        @Override
        public void run() {
            LogHelper.ProcessAndThreadId("WorkerService.onStartCommand");

            Location location = mWorker.getLocation();

            String address = mWorker.reverseGeocode(location);

            mWorker.save(location, address, "WorkerService.out");

            DelayedStopRequest stopRequest = new DelayedStopRequest(mWorkerService, mStartId);

            mWorkerService.mScheduledService.schedule(stopRequest, 5, TimeUnit.MINUTES);
        }
    }

    class DelayedStopRequest implements Runnable {
        WorkerService mWorkerService;
        int mStartId;

        public DelayedStopRequest(WorkerService workerService, int startId) {
            mWorkerService = workerService;
            mStartId = startId;
        }


        @Override
        public void run() {
            mWorkerService.stopSelfResult(mStartId);
        }
    }
}
