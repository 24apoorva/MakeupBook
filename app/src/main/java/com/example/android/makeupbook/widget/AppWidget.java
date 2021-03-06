package com.example.android.makeupbook.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.makeupbook.R;
import com.example.android.makeupbook.savedlistsui.MyListsMainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.app_widget);

        Intent intent = new Intent(context,MyListsMainActivity.class);
        intent.putExtra(MyListsMainActivity.LISTTYPE,2);

        Intent intenthave = new Intent(context,MyListsMainActivity.class);
        intenthave.putExtra(MyListsMainActivity.LISTTYPE,1);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        PendingIntent pendingIntenthave = PendingIntent.getActivity(context,1,intenthave,0);

        views.setOnClickPendingIntent(R.id.wantImage_widget,pendingIntent);
        views.setOnClickPendingIntent(R.id.haveImage_widget,pendingIntenthave);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

