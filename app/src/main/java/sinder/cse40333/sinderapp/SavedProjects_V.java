package sinder.cse40333.sinderapp;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

		/*
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
		*/
		//ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getApplicationContext(), teams);
		ListView saved_projectsListView = (ListView) findViewById(R.id.saved_projectsListView);
		//saved_projectsListView.setAdapter(scheduleAdapter);
		AdapterView.OnItemClickListener clickListener_saved_projects = new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout_v3b);
				Snackbar snackbar = Snackbar.make(coordinatorLayout, "Delete Project? (Hit yes or wait for snackbar to close)", Snackbar.LENGTH_LONG);
				snackbar.setAction("Yes", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Snackbar.make(coordinatorLayout, "Project removed successfully!", Snackbar.LENGTH_LONG).show();

					}

				});
				snackbar.show();



				/*
					//add code to delete project here!!!
				 */



			}

		};
		saved_projectsListView.setOnItemClickListener(clickListener_saved_projects);

		return true;
	}

}