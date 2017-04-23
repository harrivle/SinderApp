package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by apple on 3/28/17.
 */

public class AddProjects_SM extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sm3_addproject);
        Toolbar my_tool_bar_sm3 = (Toolbar) findViewById(R.id.toolbar_sm3);
        setSupportActionBar(my_tool_bar_sm3);
        my_tool_bar_sm3.setTitle("Add Project");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_sm3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();

        if (res_id == R.id.close_sm3) {
            startActivity(new Intent(AddProjects_SM.this, PastProjects_SM.class));

        }
        return true;
    }

}
