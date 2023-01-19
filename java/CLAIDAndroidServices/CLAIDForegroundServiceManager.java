package CLAIDAndroidServices;

import android.content.Intent;

import androidx.core.content.ContextCompat;

import com.example.claiddemo.MainActivity;

public class CLAIDForegroundServiceManager
{
    public static void startService()
    {
        MainActivity activity = MainActivity.instance;
        Intent serviceIntent = new Intent(activity, CLAIDForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(activity, serviceIntent);
    }

}
