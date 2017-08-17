package com.myapplicationdev.android.p06_taskmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

public class TaskReminderReceiver extends BroadcastReceiver {

    int notifReqCode = 123;

	@Override
	public void onReceive(Context context, Intent i) {

		int id = i.getIntExtra("id", -1);
		String name = i.getStringExtra("name");
		String desc = i.getStringExtra("desc");

		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("id", id);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

		Intent intentreply = new Intent(context,
				MainActivity.class);
		PendingIntent pendingIntentReply = PendingIntent.getActivity
				(context, 0, intentreply,
						PendingIntent.FLAG_UPDATE_CURRENT);

		RemoteInput ri = new RemoteInput.Builder("status")
				.setLabel("Status report")
				.setChoices(new String [] {"Completed", "Not yet"})
				.build();

		NotificationCompat.Action action2 = new
				NotificationCompat.Action.Builder(
				R.mipmap.ic_launcher,
				"Reply",
				pendingIntentReply)
				.addRemoteInput(ri)
				.build();

		NotificationCompat.WearableExtender extender = new
				NotificationCompat.WearableExtender();
		extender.addAction(action2);


		// build notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setContentTitle("Task");
		builder.setContentText(name + "\n" + desc);
		builder.setSmallIcon(android.R.drawable.ic_dialog_info);
		builder.setContentIntent(pIntent);
		builder.setAutoCancel(true);
		builder.extend(extender);


		Notification n = builder.build();

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// You may put an ID for the first parameter if you wish
		// to locate this notification to cancel
		notificationManager.notify(notifReqCode, n);

	}

}
