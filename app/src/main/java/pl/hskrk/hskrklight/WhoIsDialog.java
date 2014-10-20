package pl.hskrk.hskrklight;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Krzysiek on 2014-10-20.
 */
public class WhoIsDialog extends DialogFragment {
    String json = null;
    JSONObject obj = null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        try {
            json = new AsyncDownloader().execute("http://whois.hskrk.pl/whois").get();
        } catch (Exception e){
            Log.d("Fetch", e.getMessage());
            json = "{\"date\": 1413806595, \"total_devices_count\": 0, \"users\": [], \"unknown_devices_count\": 0}";
        }
        try {
            obj = (JSONObject) new JSONTokener(json).nextValue();
            Log.d("[JsonTokener]",obj.toString());
        } catch (JSONException e) {
            Log.d("[JsonTokener]","failure");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        try {
            builder.setMessage(new String("Devices: " + obj.getInt("total_devices_count")
                 + "\n" + "People:" + obj.getJSONArray("users").toString().replace('[',' ').replace(']',' '))).setNegativeButton("Return",
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

