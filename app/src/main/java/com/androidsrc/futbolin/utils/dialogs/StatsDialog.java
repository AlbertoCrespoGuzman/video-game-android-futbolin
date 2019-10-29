package com.androidsrc.futbolin.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.LastMatch;
import com.androidsrc.futbolin.communications.http.auth.get.MatchStats;
import com.androidsrc.futbolin.communications.http.auth.get.Matches;
import com.androidsrc.futbolin.communications.http.auth.get.TeamWithFormationId;
import com.androidsrc.futbolin.communications.http.auth.get.getMatch;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsDialog  extends Dialog implements MatchDialog.DialogClickListener{


    Button closeButton;
    StatsDialog.DialogClickListener dialogClickListener;
    TeamWithFormationId team;
    TableLayout againstTable, lastMatchesTable;
    TextView againstLabel;
    ProgressDialog postingMatchDialog;
    Activity a;
    MatchDialog matchDialog;
    public StatsDialog(Activity a, TeamWithFormationId team, StatsDialog.DialogClickListener dialogClickListener){
        super(a);
        this.a = a;
        this.team = team;
        this.dialogClickListener = dialogClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_stats);

        againstLabel = findViewById(R.id.dialog_stats_agaisnt_label);
        againstTable = findViewById(R.id.dialog_stats_against_table);
        lastMatchesTable = findViewById(R.id.dialog_stats_last_matches_table);
        closeButton = findViewById(R.id.dialog_stats_close_button);

        againstLabel.setText(getContext().getResources().getString(R.string.stats_against)+ " " + team.getName());

        TableRow rowVisit = new TableRow(getContext());
        rowVisit.setBackgroundResource(R.drawable.borders_table_grey);
        rowVisit.setPadding(15,15,15,15);
        MatchStats Visit = team.getMatches_versus().getVisit();

        TextView VisitLabel = new TextView(getContext());
        VisitLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        VisitLabel.setText(getContext().getResources().getString(R.string.local));
        VisitLabel.setTypeface(Typeface.DEFAULT_BOLD);
        VisitLabel.setTextSize(11);
        rowVisit.addView(VisitLabel);

        TextView VisitJug = new TextView(getContext());
        VisitJug.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        VisitJug.setText(String.valueOf(Visit.getTotal()));
        VisitJug.setTextSize(11);
        rowVisit.addView(VisitJug);

        TextView VisitGan = new TextView(getContext());
        VisitGan.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        VisitGan.setText(String.valueOf(Visit.getLost()));
        VisitGan.setTextSize(11);
        rowVisit.addView(VisitGan);

        TextView VisitEmp = new TextView(getContext());
        VisitEmp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        VisitEmp.setText(String.valueOf(Visit.getTied()));
        VisitEmp.setTextSize(11);
        rowVisit.addView(VisitEmp);

        TextView VisitPer = new TextView(getContext());
        VisitPer.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        VisitPer.setText(String.valueOf(Visit.getWon()));
        VisitPer.setTextSize(11);
        rowVisit.addView(VisitPer);

        TextView VisitGf = new TextView(getContext());
        VisitGf.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        VisitGf.setText(String.valueOf(Visit.getGoals_against()));
        VisitGf.setTextSize(11);
        rowVisit.addView(VisitGf);

        TextView VisitGc = new TextView(getContext());
        VisitGc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        VisitGc.setText(String.valueOf(Visit.getGoals_favor()));
        VisitGc.setTextSize(11);
        rowVisit.addView(VisitGc);

        TextView VisitGg = new TextView(getContext());
        VisitGg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        VisitGg.setText(String.valueOf(- Visit.getGoals_difference()));
        VisitGg.setTextSize(11);
        rowVisit.addView(VisitGg);

        againstTable.addView(rowVisit);

        TableRow rowLocal = new TableRow(getContext());
        rowLocal.setPadding(15,15,15,15);
        rowLocal.setBackgroundResource(R.drawable.borders_table_grey);
        MatchStats local = team.getMatches_versus().getLocal();

        TextView localLabel = new TextView(getContext());
        localLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        localLabel.setText(getContext().getResources().getString(R.string.visiting_team));
        localLabel.setTypeface(Typeface.DEFAULT_BOLD);
        localLabel.setTextSize(11);
        rowLocal.addView(localLabel);

        TextView localJug = new TextView(getContext());
        localJug.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localJug.setText(String.valueOf(local.getTotal()));
        localJug.setTextSize(11);
        rowLocal.addView(localJug);

        TextView localGan = new TextView(getContext());
        localGan.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localGan.setText(String.valueOf(local.getLost()));
        localGan.setTextSize(11);
        rowLocal.addView(localGan);

        TextView localEmp = new TextView(getContext());
        localEmp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localEmp.setText(String.valueOf(local.getTied()));
        localEmp.setTextSize(11);
        rowLocal.addView(localEmp);

        TextView localPer = new TextView(getContext());
        localPer.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localPer.setText(String.valueOf(local.getWon()));
        localPer.setTextSize(11);
        rowLocal.addView(localPer);

        TextView localGf = new TextView(getContext());
        localGf.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localGf.setText(String.valueOf(local.getGoals_against()));
        localGf.setTextSize(11);
        rowLocal.addView(localGf);

        TextView localGc = new TextView(getContext());
        localGc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localGc.setText(String.valueOf(local.getGoals_favor()));
        localGc.setTextSize(11);
        rowLocal.addView(localGc);

        TextView localGg = new TextView(getContext());
        localGg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localGg.setText(String.valueOf( - local.getGoals_difference()));
        localGg.setTextSize(11);
        rowLocal.addView(localGg);

        againstTable.addView(rowLocal);




        TableRow rowtotal = new TableRow(getContext());
        rowtotal.setPadding(15,15,15,15);
        rowtotal.setBackgroundResource(R.drawable.borders_table_grey);


        TextView totalLabel = new TextView(getContext());
        totalLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        totalLabel.setTypeface(Typeface.DEFAULT_BOLD);
        totalLabel.setText(getContext().getResources().getString(R.string.total));
        totalLabel.setTextSize(11);
        rowtotal.addView(totalLabel);

        TextView totalJug = new TextView(getContext());
        totalJug.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalJug.setText(String.valueOf(local.getTotal() + Visit.getTotal()));
        totalJug.setTypeface(Typeface.DEFAULT_BOLD);
        totalJug.setTextSize(11);
        rowtotal.addView(totalJug);

        TextView totalGan = new TextView(getContext());
        totalGan.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalGan.setText(String.valueOf(local.getLost() + Visit.getLost()));
        totalGan.setTypeface(Typeface.DEFAULT_BOLD);
        totalGan.setTextSize(11);
        rowtotal.addView(totalGan);

        TextView totalEmp = new TextView(getContext());
        totalEmp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalEmp.setText(String.valueOf(local.getTied() + Visit.getTied()));
        totalEmp.setTypeface(Typeface.DEFAULT_BOLD);
        totalEmp.setTextSize(11);
        rowtotal.addView(totalEmp);

        TextView totalPer = new TextView(getContext());
        totalPer.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalPer.setText(String.valueOf(local.getWon() + Visit.getWon()));
        totalPer.setTypeface(Typeface.DEFAULT_BOLD);
        totalPer.setTextSize(11);
        rowtotal.addView(totalPer);

        TextView totalGf = new TextView(getContext());
        totalGf.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalGf.setText(String.valueOf(local.getGoals_against() + Visit.getGoals_against()));
        totalGf.setTypeface(Typeface.DEFAULT_BOLD);
        totalGf.setTextSize(11);
        rowtotal.addView(totalGf);

        TextView totalGc = new TextView(getContext());
        totalGc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalGc.setTypeface(Typeface.DEFAULT_BOLD);
        totalGc.setText(String.valueOf(local.getGoals_favor() + Visit.getGoals_favor()));
        totalGc.setTextSize(11);
        rowtotal.addView(totalGc);

        TextView totalGg = new TextView(getContext());
        totalGg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalGg.setTypeface(Typeface.DEFAULT_BOLD);
        totalGg.setText(String.valueOf(-(local.getGoals_difference() + Visit.getGoals_difference())));
        totalGg.setTextSize(11);
        rowtotal.addView(totalGg);

        againstTable.addView(rowtotal);

        for(LastMatch lm : team.getLast_matches_versus()){
            TableRow row = new TableRow(getContext());
            row.setPadding(15,15,15,15);
            row.setBackgroundResource(R.drawable.borders_table_grey);

            TextView date = new TextView(getContext());
            date.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            date.setText(lm.getDate());
            date.setTextSize(11);
            row.addView(date);

            TextView cond = new TextView(getContext());
            cond.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cond.setTextSize(11);
            if(team.getId() == lm.getLocal_id()){
                cond.setText(getContext().getResources().getString(R.string.visiting_team));
            }else{
                cond.setText(getContext().getResources().getString(R.string.local));
            }

            row.addView(cond);
            Log.e("ids - >" , "team_id" + team.getId() + "|local_id = " + lm.getLocal_id()+ "|visit_id = " + lm.getVisit_id());
            TextView score = new TextView(getContext());
            score.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            score.setText(lm.getLocal_goals() + " - " + lm.getVisit_goals());
            score.setTextSize(11);
            if(!lm.isWon()){
                score.setTypeface(Typeface.DEFAULT_BOLD);
            }
            row.addView(score);

            ImageView seeImage =  new ImageView(getContext());
            seeImage.requestLayout();

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_search, seeImage);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(a.getResources().getColor(R.color.futbolinAzul));

            seeImage.setTag(lm.getLog_file());
            seeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("Look the match!" + view.getTag(), "onclick");
                    getMatch(view.getTag() + "");
                }
            });
            row.addView(seeImage);
            lastMatchesTable.addView(row);
        }


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onButtonClick();
            }
        });

        for(int i=0; i< lastMatchesTable.getChildCount(); i++){
            TableRow row = (TableRow) lastMatchesTable.getChildAt(i);
            for(int j=0; j< row.getChildCount(); j++){
                View view = row.getChildAt(j);
                if(view instanceof ImageView){
                    view.requestLayout();
                    view.getLayoutParams().width = 42;
                    view.getLayoutParams().height = 42;
                }
            }
        }

    }

    @Override
    public void onButtonClick() {
        if(matchDialog != null && matchDialog.isShowing()){
            matchDialog.dismiss();
        }
    }


    public interface DialogClickListener {
        void onButtonClick();
    }

    public void getMatch(String file_log){

        SvcApi svc;
        svc = Svc.initAuth(getContext());
        Call<getMatch> call = svc.getMatch(Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID), file_log);
        call.enqueue(new Callback<getMatch>() {
            @Override
            public void onResponse(Call<getMatch> call, Response<getMatch> response) {
                if(postingMatchDialog != null && postingMatchDialog.isShowing()){
                    postingMatchDialog.dismiss();
                }

                final getMatch teams = response.body();
                final Response<getMatch> responsefinal = response;
                if (response.isSuccessful()) {
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, getMatch = " + responsefinal);
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){

                                loadMatchDialog(responsefinal.body());


                            }
                        }
                    });
                } else {
                    if(postingMatchDialog != null && postingMatchDialog.isShowing()){
                        postingMatchDialog.dismiss();
                    }
                    try {
                        Log.e("error code :",response.toString() + "");
                        Log.e("error code :",response.code() + "");
                        Log.e("error:",response.message());
                        Log.v("Error ", response.errorBody().string());
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
            public void onFailure(Call<getMatch> call, Throwable t) {
                t.printStackTrace();
                if(postingMatchDialog != null && postingMatchDialog.isShowing()){
                    postingMatchDialog.dismiss();
                }
            }
        });

    }
    private void loadMatchDialog(getMatch match){
        Log.e("postMachResponse", match.toString());
        matchDialog = new MatchDialog(a, match, this);
        matchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        matchDialog.show();
    }
}

