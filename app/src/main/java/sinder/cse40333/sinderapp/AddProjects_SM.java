package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by apple on 3/28/17.
 */

public class AddProjects_SM extends AppCompatActivity {
	Button save_project;
	EditText inputProjName;
	EditText inputDate;
	EditText inputDescrip;
	EditText inputConInfo;
	EditText inputNumVol;
	String projName;
	String date;
	String descrip;
	String conInfo;
	String numVol;
	FirebaseDatabase db = FirebaseDatabase.getInstance();
	DatabaseReference ref = db.getReference();

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_sm3_addproject);
		Toolbar my_tool_bar_sm3 = (Toolbar) findViewById(R.id.toolbar_sm3);
		setSupportActionBar(my_tool_bar_sm3);
		my_tool_bar_sm3.setTitle("Add Project");

		inputProjName = (EditText) findViewById(R.id.editProjName);
		projName = inputProjName.getText().toString();

		inputDate = (EditText) findViewById(R.id.editDate);
		date = inputDate.getText().toString();

		inputDescrip = (EditText) findViewById(R.id.editDescrip);
		descrip = inputDescrip.getText().toString();

		inputConInfo = (EditText) findViewById(R.id.editConInfo);
		conInfo = inputConInfo.getText().toString();

		inputNumVol = (EditText) findViewById(R.id.editNumVol);
		numVol = inputNumVol.getText().toString();

		save_project = (Button) findViewById(R.id.saveProj);
		save_project.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO: save info into database
				//System.out.println(projName + date + descrip + conInfo + numVol);
				startActivity(new Intent(AddProjects_SM.this, PastProjects_SM.class));
			}

		});
		Button createProjectButton = (Button) findViewById(R.id.createProjectButton);
		createProjectButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("THAT");
				writeNewDatabase("3", "05/18/2017", "Project 3");
			}
		});
	}

	public void writeNewDatabase(String ID, String date, String name) {
		System.out.println(ID + date + name);
		ref.child("projects").child(ID).child("projectDate").setValue(date);
		ref.child("projects").child(ID).child("projectName").setValue(name);
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
