package CLAIDAndroidServices;

import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;


public class CLAIDForegroundServiceManager
{
    public static void startService(Context context)
    {
        Intent serviceIntent = new Intent(context, CLAIDForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(context, serviceIntent);
    }

}
