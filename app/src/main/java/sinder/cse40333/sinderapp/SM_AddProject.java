package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;

public class SM_AddProject extends BaseActivity {
	Button save_project;
	EditText inputProjName;
	EditText inputDate;
	EditText inputDescrip;
	EditText inputConInfo;
	EditText inputNumVol;
	String projName = null;
	String date = null;
	String descrip = null;
	String conInfo = null;
	String numVol = null;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_sm3_addproject);
		Toolbar my_tool_bar_sm3 = (Toolbar) findViewById(R.id.toolbar_sm3);
		setSupportActionBar(my_tool_bar_sm3);
		my_tool_bar_sm3.setTitle("Add Project");

		inputProjName = (EditText) findViewById(R.id.editProjName);
		inputDate = (EditText) findViewById(R.id.editDate);
		inputDescrip = (EditText) findViewById(R.id.editDescrip);
		inputConInfo = (EditText) findViewById(R.id.editConInfo);
		inputNumVol = (EditText) findViewById(R.id.editNumVol);

		save_project = (Button) findViewById(R.id.saveProj);
		save_project.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				projName = inputProjName.getText().toString();
				date = inputDate.getText().toString();
				descrip = inputDescrip.getText().toString();
				conInfo = inputConInfo.getText().toString();
				numVol = inputNumVol.getText().toString();
				Project project = new Project(projName, date, descrip, conInfo, Integer.parseInt(numVol));
				addNewProject(project);
				startActivity(new Intent(SM_AddProject.this, SM_PastProjects.class));
			}
		});
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
			startActivity(new Intent(SM_AddProject.this, SM_PastProjects.class));

		}
		return true;
	}

}
