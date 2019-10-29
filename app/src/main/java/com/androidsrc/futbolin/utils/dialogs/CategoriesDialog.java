package com.androidsrc.futbolin.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.http.auth.get.TournamentCategories;
import com.androidsrc.futbolin.communications.http.auth.get.getTournament;


public class CategoriesDialog extends Dialog {

    TextView title;
    Button closeButton;
    DialogClickListener dialogClickListener;
    String titleTxt;
    String messageTxt;
    getTournament tournament;
    TableLayout categoriesTable;
    Activity a;

    public CategoriesDialog(Activity a, getTournament tournament, DialogClickListener dialogClickListener){
        super(a);
        this.tournament = tournament;
        this.dialogClickListener = dialogClickListener;
        this.a = a;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_categories);

        categoriesTable = findViewById(R.id.dialog_categories_table);


        for(final TournamentCategories tournamentCategories :tournament.getCategories()){

            TableRow row = new TableRow(getContext());

            Button cat = new Button(getContext());
            cat.setTextSize(12);
            cat.setText(tournamentCategories.getName());
            cat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogClickListener.onButtonCategoriesClick(tournamentCategories.getId());
                }
            });
            row.addView(cat);
            categoriesTable.addView(row);
        }

        closeButton = findViewById(R.id.dialog_categories_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onButtonCategoriesClick(-1);
            }
        });


    }



    public interface DialogClickListener {
        void onButtonCategoriesClick(long category_id);
    }
}
