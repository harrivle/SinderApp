package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class V_SavedProjects extends BaseActivity {
	ProjectsAdapter projectsAdapter;
	ArrayList<Project> projects;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_v3b_savedprojects);
		Toolbar my_tool_bar3 = (Toolbar) findViewById(R.id.toolbar_v3a);
		setSupportActionBar(my_tool_bar3);
		my_tool_bar3.setTitle("Saved Projects");
	}

	@Override
	public void update(DataSnapshot data) {
		projects = new ArrayList();

		for (DataSnapshot postData : data.child("users/" + baseAuth.getCurrentUser().getUid() + "/savedProjects").getChildren()) {
			DataSnapshot tempData = data.child("projects/" + postData.getKey());
			if ((Boolean) postData.getValue()) {
				Project project = tempData.getValue(Project.class);
				project.setProjectID(Integer.parseInt(tempData.getKey()));

				String UID = (String) postData.child("ownerUID").getValue();
				DataSnapshot nameData = data.child("users/" + UID + "/fullName");
				project.setOwnerName((String) nameData.getValue());


				projects.add(project);
			}
		}

		projectsAdapter = new ProjectsAdapter(this, (ArrayList) projects);
		ListView scheduleListView = (ListView) findViewById(R.id.projectsList);
		scheduleListView.setAdapter(projectsAdapter);

		AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
			//  @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent myintent = new Intent(V_SavedProjects.this, V_ProjectDetail.class);
				myintent.putExtra("project", projects.get(position));
				startActivity(myintent);
			}
		};

		scheduleListView.setOnItemClickListener(clickListener);
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
			signOut();
		}

		return true;
	}

}