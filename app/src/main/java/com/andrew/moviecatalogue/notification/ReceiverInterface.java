package com.andrew.moviecatalogue.notification;

import android.content.Context;

import java.util.Calendar;

public interface ReceiverInterface {

    Calendar getReminderTime();
    void setAlarm(Context context);
    void cancelAlarm(Context context);
    void showNotification(Context context, String title, String message, int notifId);
}
