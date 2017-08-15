package com.midea.fridgesettings.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.midea.fridgesettings.R;
import com.midea.fridgesettings.SettingsActivity;

/**
 * Created by Administrator on 2016/11/15.
 */
public class SettingWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, SettingsActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_common_1x1);
            views.setOnClickPendingIntent(R.id.root_view, pendingIntent);
            views.setInt(R.id.root_view, "setBackgroundResource", R.drawable.desktop_widget_bg_1);
            views.setTextViewText(R.id.widget_name, context.getResources().getString(R.string.app_name));
            views.setImageViewResource(R.id.widget_icon, R.mipmap.desktop_widget_setting);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
