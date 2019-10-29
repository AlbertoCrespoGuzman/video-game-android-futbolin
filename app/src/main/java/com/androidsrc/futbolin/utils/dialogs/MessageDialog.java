package com.androidsrc.futbolin.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.androidsrc.futbolin.R;



public class MessageDialog extends Dialog {

    TextView title;
    TextView message;
    Button closeButton;
    DialogClickListener dialogClickListener;
    String titleTxt;
    String messageTxt;

    public MessageDialog(Activity a, String title, String text, DialogClickListener dialogClickListener){
        super(a);
        this.titleTxt = title;
        this.messageTxt = text;
        this.dialogClickListener = dialogClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_message);

        title = findViewById(R.id.dialog_message_title);
        message = findViewById(R.id.dialog_message_text);
        closeButton = findViewById(R.id.dialog_message_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onMessageDialogButtonClick();
            }
        });
        title.setText(titleTxt);
        message.setText(messageTxt);

    }



    public interface DialogClickListener {
        void onMessageDialogButtonClick();
    }
}
