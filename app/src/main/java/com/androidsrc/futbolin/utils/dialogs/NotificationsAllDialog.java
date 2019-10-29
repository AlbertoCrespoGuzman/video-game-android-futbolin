package com.androidsrc.futbolin.utils.dialogs;


        import android.app.Activity;
        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.os.Message;
        import android.provider.Settings;
        import android.util.Log;
        import android.view.View;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.androidsrc.futbolin.R;
        import com.androidsrc.futbolin.activities.MainActivity;
        import com.androidsrc.futbolin.communications.Svc;
        import com.androidsrc.futbolin.communications.SvcApi;
        import com.androidsrc.futbolin.communications.http.auth.get.getNotification;
        import com.androidsrc.futbolin.communications.http.auth.get.getUser;
        import com.androidsrc.futbolin.database.models.SystemNotification;
        import com.androidsrc.futbolin.database.models.SystemSubNotifications;

import java.util.List;

import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;


public class NotificationsAllDialog extends Dialog implements MessagePlayerAnchorTagDialog.DialogPlayerAnchorTagClickListener{

    TextView title;
    TableLayout table;
    Button closeButton;
    DialogClickListener dialogClickListener;
    List<SystemSubNotifications> notifications;
    Activity a;
    ProgressDialog dialog;
    MessagePlayerAnchorTagDialog messageDialog;
    MessagePlayerAnchorTagDialog.DialogPlayerAnchorTagClickListener dialogMessageClickListener;

    public NotificationsAllDialog(Activity a, List<SystemSubNotifications> notifications, DialogClickListener dialogClickListener){
        super(a);
        this.notifications = notifications;
        this.a  = a;
        this.dialogClickListener = dialogClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notifications_all);




        dialogMessageClickListener = this;

        title = findViewById(R.id.dialog_notifications_title);
        table = findViewById(R.id.dialog_notifications_table);
        closeButton = findViewById(R.id.dialog_notifications_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onDialogNotificationsAllButtonClick(notifications);
            }
        });


        loadFragment();
    }
    void loadFragment(){
        table.removeAllViews();
        title.setText(getContext().getResources().getString(R.string.notifications));
        if(notifications.size() > 0) {
            TableRow row1 = new TableRow(getContext());

            row1.setPadding(10, 10, 10, 10);


            TextView textView1 = new TextView(getContext());
            textView1.setText(getContext().getResources().getString(R.string.notifications));
            textView1.setTextSize(12);

            row1.addView(textView1);
            table.addView(row1);
        }
        for(final SystemSubNotifications notification : notifications){
            TableRow row = new TableRow(getContext());

            row.setPadding(10,10,10,10);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getNotification(notification.getId());

                }
            });

            TextView textView = new TextView(getContext());
            textView.setText(notification.getTitle() + " - " + notification.getPublished());

            if(notification.isUnread()) {
                row.setBackgroundColor(Color.parseColor("#ffabab"));
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            }else{
                row.setBackgroundColor(Color.parseColor("#e0e0e0"));
            }
            textView.setText(notification.getTitle() + " - " + notification.getPublished());
            textView.setTextSize(12);

            row.addView(textView);
            table.addView(row);
        }


    }


    @Override
    public void onButtonPlayerAnchorTagClick() {
        if(messageDialog!= null && messageDialog.isShowing()){
            messageDialog.dismiss();
        }
    }

    public interface DialogClickListener {
        void onDialogNotificationsAllButtonClick(List<SystemSubNotifications> notifications);
    }


    public void getNotification(long id){
        final long idf = id;
        SvcApi svc = Svc.initAuth(getContext());
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = ProgressDialog.show(getContext(), "",
                getContext().getResources().getString(R.string.loading_notification)
                , true);

        Call<getNotification> call = svc.getNotification(id, Settings.Secure.getString(a.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getNotification>() {
            @Override
            public void onResponse(Call<getNotification> call, Response<getNotification> response) {
                final getNotification notification = response.body();
                final Response<getNotification> responsefinal = response;

                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("getNotification","Success, getNotification = " + responsefinal);
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                messageDialog = new MessagePlayerAnchorTagDialog(a, notification.getTitle(),
                                        notification.getMessage(), dialogMessageClickListener);
                                messageDialog.show();
                                SystemSubNotifications not = null;
                                for(int i=0; i < notifications.size();i++){
                                    if(notifications.get(i).getId() == idf){
                                        notifications.get(i).setUnread(false);
                                    }
                                }
                                loadFragment();

                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            ((MainActivity)a).changeToLiveFragment();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getContext(),
                                getContext().getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getNotification> call, Throwable t) {
                t.printStackTrace();
                Log.e("onFailure!", "onFailure!");
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });


    }



}
