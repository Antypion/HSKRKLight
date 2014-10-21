package pl.hskrk.hskrklight;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;


/**
 * Created by Krzysiek on 2014-10-20.
 */
public class SensorsDialog extends DialogFragment {
    String json = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        try {
            json = new AsyncDownloader().execute("http://al2.hskrk.pl/api/v2/temp/get").get();
        } catch (Exception e){
            Log.d("Fetch", e.getMessage());
            json = "0";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        try {
            builder.setMessage(new String("Temp: "+json+"\u00B0C")).setNegativeButton("Return",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });


        }catch(Exception e){

        }
        return builder.create();
    }
}

