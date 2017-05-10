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

public class SM_PastProjects extends BaseActivity {
	ProjectsAdapter projectsAdapter;
	ArrayList<Project> projects;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_sm2_pastprojects);
		Toolbar my_tool_bar_sm2 = (Toolbar) findViewById(R.id.toolbar_sm2);
		setSupportActionBar(my_tool_bar_sm2);
		my_tool_bar_sm2.setTitle("Past Projects");
	}

	@Override
	public void update(DataSnapshot data) {
		projects = new ArrayList();

		for (DataSnapshot postData : data.child("projects").getChildren()) {

			if (!postData.getKey().equals("nextID")) {
				String UID = (String) postData.child("ownerUID").getValue();

				if (UID != null && UID.equals(baseAuth.getCurrentUser().getUid())) {
					Project project = postData.getValue(Project.class);
					project.setProjectID(Integer.parseInt(postData.getKey()));

					DataSnapshot nameData = data.child("users/" + UID + "/fullName");
					project.setOwnerName((String) nameData.getValue());

					projects.add(project);
				}
			}
		}

		projectsAdapter = new ProjectsAdapter(this, (ArrayList) projects);
		ListView scheduleListView = (ListView) findViewById(R.id.projectsList);
		scheduleListView.setAdapter(projectsAdapter);

		AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
			//  @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent myintent = new Intent(SM_PastProjects.this, V_ProjectDetail.class);
				myintent.putExtra("project", projects.get(position));
				startActivity(myintent);
			}
		};

		scheduleListView.setOnItemClickListener(clickListener);
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
			startActivity(new Intent(SM_PastProjects.this, SM_AddProject.class));
		} else if (res_id == R.id.delete_sm2) {
			//??
		} else if (res_id == R.id.edit_profile_sm2) {
			//??
		} else if (res_id == R.id.logout_sm2) {
			signOut();
		} else if (res_id == R.id.close_sm2) {
			//no code necessary here
		}
		return true;
	}

}
