package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by apple on 3/27/17.
 */

public class PastProjects_SM extends AppCompatActivity {
	/*FirebaseDatabase db = FirebaseDatabase.getInstance();
	DatabaseReference ref = db.getReference("projects");*/
	ProjectsAdapter projectsAdapter;
	ArrayList<ArrayList<String>> projects = new ArrayList();

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_sm2_pastprojects);
		Toolbar my_tool_bar_sm2 = (Toolbar) findViewById(R.id.toolbar_sm2);
		setSupportActionBar(my_tool_bar_sm2);
		my_tool_bar_sm2.setTitle("Past Projects");

		/*ref.child("projects").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				System.out.println(dataSnapshot.getValue());
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.w("Cancelled", "loadPost:onCancelled", databaseError.toException());
			}
		});*/

		ArrayList<String> temp = new ArrayList();
		temp.add("Project 1");
		temp.add("08/18/1995");
		projects.add(temp);
		projectsAdapter = new ProjectsAdapter(this, projects);
		ListView scheduleListView = (ListView) findViewById(R.id.projectsList);
		scheduleListView.setAdapter(projectsAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_sm2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int res_id = item.getItemId();

		if (res_id == R.id.add_sm2) {
			startActivity(new Intent(PastProjects_SM.this, AddProjects_SM.class));
		} else if (res_id == R.id.delete_sm2) {
			//??
		} else if (res_id == R.id.edit_profile_sm2) {
			//??
		} else if (res_id == R.id.logout_sm2) {
			startActivity(new Intent(PastProjects_SM.this, LoginActivity.class));
		} else if (res_id == R.id.close_sm2) {
			//no code necessary here
		}
		return true;
	}

}
