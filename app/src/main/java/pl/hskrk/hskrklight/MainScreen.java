package pl.hskrk.hskrklight;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class MainScreen extends ActionBarActivity {
    public final String UrlToggle = "http://light.at.hskrk.pl/api/v2/light/toggle/";
    public final String UrlGet = "http://al2.hskrk.pl/api/v2/light/get_state/all";
    private ArrayAdapter<String> LightsAdapter;
    private static ArrayList<Light> Lights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LightsAdapter = new ArrayAdapter<String>(this,R.layout.list_element);
        Lights = new ArrayList<Light>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }
    @Override
    protected void onStart(){
        super.onStart();
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
}
