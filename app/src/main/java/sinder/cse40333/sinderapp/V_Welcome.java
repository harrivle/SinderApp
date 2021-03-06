package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;

import java.io.File;
import java.util.ArrayList;

public class V_Welcome extends BaseActivity {
	GridAdapter projectsAdapter;
	ArrayList<Project> projects;
	ArrayList<File> images;
	ArrayList<ArrayList<Object>> objects;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_v2_welcome);

		Toolbar my_tool_bar = (Toolbar) findViewById(R.id.toolbar_v2_welcome);
		setSupportActionBar(my_tool_bar);
		my_tool_bar.setTitle("Main Menu");
	}

	@Override
	public void update(DataSnapshot data) {
		projects = new ArrayList();
		images = new ArrayList();
		objects = new ArrayList();

		for (DataSnapshot postData : data.child("projects").getChildren()) {
			Iterable<DataSnapshot> tempData = data.child("users/" + baseAuth.getCurrentUser().getUid() + "/savedProjects").getChildren();
			if (!postData.getKey().equals("nextID") && !iterableContains(tempData, postData.getKey())) {
				Project project = postData.getValue(Project.class);
				project.setProjectID(Integer.parseInt(postData.getKey()));

				String UID = (String) postData.child("ownerUID").getValue();
				DataSnapshot nameData = data.child("users/" + UID + "/fullName");
				project.setOwnerName((String) nameData.getValue());

				projects.add(project);

				File file = downloadFromStorage(project);
				images.add(file);

				ArrayList<Object> temp = new ArrayList();
				temp.add(project);
				temp.add(file);
				objects.add(temp);
			}
		}

		projectsAdapter = new GridAdapter(this, objects);
		GridView projectsView = (GridView) findViewById(R.id.projects_grid);
		projectsView.setAdapter(projectsAdapter);

		//go to V_ProjectDetail if one of the items is clicked:
		AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
			//  @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent myintent = new Intent(V_Welcome.this, V_ProjectDetail.class);
				myintent.putExtra("project", projects.get(position));
				startActivity(myintent);
			}
		};

		projectsView.setOnItemClickListener(clickListener);
	}

	public Boolean iterableContains(Iterable<DataSnapshot> i, String key) {
		for (DataSnapshot data : i) {
			if (data.getKey().equals(key)) {
				return true;
			}
		}

		return false;
	}

	// Menu/Toolbar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_v2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int res_id = item.getItemId();

		if (res_id == R.id.forms_v2) {
			startActivity(new Intent(V_Welcome.this, V_Forms.class));
		} else if (res_id == R.id.saved_projects_v2) {
			startActivity(new Intent(V_Welcome.this, V_SavedProjects.class));
		} else if (res_id == R.id.logout_v2) {
			signOut();
		} else if (res_id == R.id.close_v2) {
			//no code necessary here
		}
		return true;
	}
}
