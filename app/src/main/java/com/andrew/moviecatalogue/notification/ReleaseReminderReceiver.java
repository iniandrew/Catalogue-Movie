package com.andrew.moviecatalogue.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.model.movie.MovieItems;
import com.andrew.moviecatalogue.model.movie.MovieResponse;
import com.andrew.moviecatalogue.network.ApiConfiguration;
import com.andrew.moviecatalogue.ui.MainActivity;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReleaseReminderReceiver extends BroadcastReceiver implements ReceiverInterface {

    int idReminder = 101;
    int reminderHour = 8;

    @Override
    public void onReceive(final Context context, Intent intent) {
        AndroidNetworking.get(ApiConfiguration.TMDB_RELEASE)
                .addPathParameter("today_date", getCurrentDate())
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(MovieResponse.class, new ParsedRequestListener<MovieResponse>() {
                    @Override
                    public void onResponse(MovieResponse response) {
                        ArrayList<MovieItems> movieItemsList = response.getResults();

                        for (MovieItems movie: movieItemsList) {
                            if (movie.getReleaseDate().equals(getCurrentDate())) {
                                showNotification(context, movie.getTitle(), movie.getTitle() + " " + context.getString(R.string.release_reminder_message), movie.getId());
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, R.string.something_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    @Override
    public Calendar getReminderTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        if (calendar.get(Calendar.HOUR_OF_DAY) > reminderHour) {
            calendar.add(Calendar.DATE, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, reminderHour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }

    @Override
    public void setAlarm(Context context) {
        Intent intent = new Intent(context, ReleaseReminderReceiver.class);
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, idReminder, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    getReminderTime().getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }

        Toast.makeText(context, R.string.release_reminder_enable, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelAlarm(Context context) {
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idReminder, intent, 0);

        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);

        Toast.makeText(context, R.string.release_reminder_disable, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000,1000,1000,1000,1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(notifId, notification);
        }
    }
}
