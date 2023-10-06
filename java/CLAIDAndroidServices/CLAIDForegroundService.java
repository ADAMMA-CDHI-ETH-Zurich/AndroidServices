/***************************************************************************
* Copyright (C) 2023 ETH Zurich
* Core AI & Digital Biomarker, Acoustic and Inflammatory Biomarkers (ADAMMA)
* Centre for Digital Health Interventions (c4dhi.org)
* 
* Authors: Patrick Langer
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*         http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
***************************************************************************/

package CLAIDAndroidServices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;





import java.io.IOException;
import java.io.InputStream;

import JavaCLAID.CLAID;

public class CLAIDForegroundService extends Service
{
    static {
        System.loadLibrary("JavaCLAID");
    }
    private static final String CLASS_TAG = CLAIDForegroundService.class.getName();
    private boolean isRunning = false;
    public static final String CHANNEL_ID = "CLAIDForegroundServiceChannel";

    private String loadFileFromAssets(String fileName)
    {

        int size = 0;
        try {
            InputStream stream = getAssets().open(fileName);
            size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            String result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, CLAIDForegroundService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("CLAID Foreground Service")
                .setOngoing(true)
                .build();
        startForeground(1, notification);

        if(!this.isRunning)
        {
            this.isRunning = true;
            onServiceStarted();
        }

        return START_STICKY;
    }

    void onServiceStarted()
    {
        Log.i(CLASS_TAG, "CLAID foreground service started");
        String assetsXMLConfig = loadFileFromAssets("CLAID.xml");
        CLAID.loadFromXMLString(assetsXMLConfig);
        CLAID.setContext(this.getBaseContext());
        CLAID.startInSeparateThread();
        //CLAID.enableLoggingToFile("/storage/emulated/0/Android/media/adamma.c4dhi.claid.wearos/" + "/CLAIDLog.txt");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}