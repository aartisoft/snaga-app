package com.sportswear.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;

public class Dialog extends AppCompatDialogFragment {

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
       builder.setTitle("Home Delivery ")
               .setMessage("Home Delivery")
               .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Intent in = new Intent(getContext(), ShowCaert.class);
                       startActivity(in);
                   }
               });
       return builder.create();

    }
}
