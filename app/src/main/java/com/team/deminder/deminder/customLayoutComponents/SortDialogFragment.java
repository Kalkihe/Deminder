package com.team.deminder.deminder.customLayoutComponents;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

public class SortDialogFragment extends DialogFragment {

    public static String[] sortDeadlinesOptions = new String[]{
            "Alphabet",
            "Datum"
    };

    /** Interface, to invoke a callback function in the implementing class */
    AlertPositiveListener alertPositiveListener;

    /** Interface for "OK" button click listener */
    public interface AlertPositiveListener {
        void onPositiveClick(int position);
    }

    /** Ensures that the hosting activity implements AlertPositiveListener **/
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            alertPositiveListener = (AlertPositiveListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement AlertPositiveListener");
        }
    }

    /** OK button listener for the alert dialog **/
    DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog)dialog;
            int position = alert.getListView().getCheckedItemPosition();
            alertPositiveListener.onPositiveClick(position);
        }
    };

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** Getting the arguments */
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");

        /** Creating a builder for the alert dialog window */
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        b.setTitle("Sortieren nach");

        /** Setting items to the alert dialog */
        b.setSingleChoiceItems(sortDeadlinesOptions, position, null);

        /** Setting a positive button and its listener */
        b.setPositiveButton("OK",positiveListener);

        /** Setting a cancel button and its listener */
        b.setNegativeButton("Cancel", null);

        /** Creating the alert dialog window using the builder class */
        AlertDialog d = b.create();
        
        return d;
    }
}
