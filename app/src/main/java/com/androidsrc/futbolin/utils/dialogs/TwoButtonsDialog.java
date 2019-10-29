package com.androidsrc.futbolin.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.utils.Defaultdata;


public class TwoButtonsDialog extends Dialog {

    TextView title;
    TextView message;
    Button cancelButton, confirmButton;
    DialogTwoButtonsClickListener dialogClickListener;
    String titleTxt;
    String messageTxt;
    int type;
    Object extra;

    public TwoButtonsDialog(Activity a, String title, String text, DialogTwoButtonsClickListener dialogClickListener, int type, Object extra){
        super(a);
        this.titleTxt = title;
        this.messageTxt = text;
        this.dialogClickListener = dialogClickListener;
        this.type = type;
        this.extra = extra;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_two_buttons);

        Log.e("TwoButtons !","is null?" + (dialogClickListener == null));

        title = findViewById(R.id.dialog_twobuttons_title);
        message = findViewById(R.id.dialog_twobuttons_text);
        cancelButton = findViewById(R.id.dialog_twobuttons_cancel_button);
        if(type == 3){// racha de entrenos
            cancelButton.setText(getContext().getResources().getString(R.string.start_streak));
            cancelButton.setTextSize(10);
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onButtonClick(false, type, extra);
            }
        });
        confirmButton = findViewById(R.id.dialog_twobuttons_confirm_button);
        if(type == 3){
            confirmButton.setText(getContext().getResources().getString(R.string.continue_streak)
                    );
            confirmButton.setTextSize(10);
        }
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onButtonClick(true, type, extra);
            }
        });
        title.setText(titleTxt);
        message.setText(Html.fromHtml(messageTxt));

    }



    public interface DialogTwoButtonsClickListener {
        void onButtonClick(boolean confirm, int type, Object extra);
    }
}
