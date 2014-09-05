package pl.hskrk.hskrklight;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class MainScreen extends ActionBarActivity {
    public final String UrlToggle = "http://light.at.hskrk.pl/api/v2/light/toggle/";
    public final String UrlGet = "http://al2.hskrk.pl/api/v2/light/get_state/all";
    private ArrayAdapter<String> LightsAdapter;
    private static ArrayList<Light> Lights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        LightsAdapter = new ArrayAdapter<String>(this,R.layout.list_element);
        Lights = new ArrayList<Light>();

    }
    @Override
    protected void onStart(){
        super.onStart();
        Boolean enable = false;
        String json;
        JSONObject obj = null;
        try {
            json = new AsyncDownloader().execute(UrlGet).get();
        }catch (Exception e){
            Log.e("Fetch", e.getMessage());
            json = "{\"Nothing\"=false}";
        }
        try{
            obj = (JSONObject) new JSONTokener(json).nextValue();
            enable =true;

        if(enable){
            Iterator<String> names = obj.keys();
            while(names.hasNext()){
                String key = names.next();
                Lights.add(new Light(key,obj.getBoolean(key)));
            }
        }
        }catch (Exception e){
            enable = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }else{
            return false;
        }
    }
}
