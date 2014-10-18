package pl.hskrk.hskrklight;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class MainScreen extends Activity {
    public final String UrlToggle = "http://al2.hskrk.pl/api/v2/light/toggle/";
    public final String UrlGet = "http://al2.hskrk.pl/api/v2/light/get_state/all";
    private ArrayAdapter<String> LightsAdapter;
    //private static ArrayList<Light> Lights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        LightsAdapter = new ArrayAdapter<String>(this,R.layout.list_element);
        ///Lights = new ArrayList<Light>();
        ListView LightsList = (ListView) findViewById(R.id.listView);
        LightsList.setAdapter(LightsAdapter);
        LightsList.setOnItemClickListener(toggleListener);

    }
    @Override
    protected void onStart(){
        super.onStart();
        updateView();
    }

    private void updateView() {

            Boolean enable = true;
            String json = null;
            JSONObject obj = null;

            try {
                json = new AsyncDownloader().execute(UrlGet).get();
            }catch (Exception e){
                Log.d("Fetch", e.getMessage());
                json = "{\"Nothing\"=false}";
            }
            try{
                obj = (JSONObject) new JSONTokener(json).nextValue();
                Log.d("[JsonTokener]",obj.toString());
               /// enable =true;

            if(enable){
                Iterator<String> names = obj.keys();
                LightsAdapter.clear();
                while(names.hasNext()){
                    String key = names.next();
                    Log.d("[Light]",key+" "+obj.getBoolean(key));
                    LightsAdapter.add(new Light(key,obj.getBoolean(key)).toString());
                }
            }
            }catch (Exception e){
                enable = false;
                Log.d("[JsonTokener]","failure");
            }

    }
    private AdapterView.OnItemClickListener toggleListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String info = ((TextView) view).getText().toString();
            String name = info.split(" ")[0];
            Log.d("[name]",name);
            if(isConnectedToWifi()){
                new AsyncDownloader().execute(UrlToggle+name);
            } else {
                Toast.makeText(getApplicationContext(),"Sorry, you aren't connected to wifi",Toast.LENGTH_SHORT).show();
            }
            for(i=0;i<3;i++)
            updateView();
        }
    };

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

    private boolean isConnectedToWifi(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }else{
            return false;
        }
    }
    /*private boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager);
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }else{
            return false;
        }
    }*/
}
