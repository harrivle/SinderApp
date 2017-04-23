package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by apple on 3/27/17.
 */

public class SavedProjects_V extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_v3b_savedprojects);
        Toolbar my_tool_bar3 = (Toolbar) findViewById(R.id.toolbar_v3a);
        setSupportActionBar(my_tool_bar3);
        my_tool_bar3.setTitle("Saved Projects");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_v3a, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();

        if (res_id == R.id.add_v3a) {
            //??

        } else if (res_id == R.id.delete_v3a) {
            //??
        } else if (res_id == R.id.edit_v3a) {
            //??
        } else if (res_id == R.id.close_v3a) {
            //no code necessary here
        } else if (res_id == R.id.returntowelcome_v3a) {
            startActivity(new Intent(SavedProjects_V.this, LoginActivity.class));
        }

        return true;
    }

}