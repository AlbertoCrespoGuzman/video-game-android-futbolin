package com.androidsrc.futbolin.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.utils.adapters.ShieldsAdapter;
import com.androidsrc.futbolin.utils.itemsDecoration.SpacesItemDecoration;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ShieldsPickerDialog extends Dialog implements ShieldsAdapter.ItemClickListener{

    public Activity c;
    public Dialog d;
    int primaryColor;
    int secundaryColor;
    RecyclerView shieldsRV;
    ShieldsAdapter adapter;
    List<Integer> shields;
    private DialogClickListener mDialogClickListener;

    public ShieldsPickerDialog(DialogClickListener mDialogClickListener,Activity a, List<Integer> shields, int primaryColor, int secundaryColor) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.primaryColor = primaryColor;
        this.secundaryColor = secundaryColor;
        this.shields = shields;
        this.mDialogClickListener = mDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_shields_picker);
        shieldsRV = findViewById(R.id.dialog_shields_picker_rv);
        adapter = new ShieldsAdapter(this, c, shields, primaryColor, secundaryColor);
        shieldsRV.setAdapter(adapter);
//        shieldsRV.addItemDecoration(new ItemOffsetDecoration(10));shieldsRV
        final GridLayoutManager linearLayoutManager = new GridLayoutManager(c,4);
        shieldsRV.setLayoutManager(linearLayoutManager);
        shieldsRV.addItemDecoration(new SpacesItemDecoration(3));

        }

    @Override
    public void onItemClick(View view, int position) {
        mDialogClickListener.onItemClick(position + 1);

    }
    public interface DialogClickListener {
        void onItemClick(int position);
    }


}