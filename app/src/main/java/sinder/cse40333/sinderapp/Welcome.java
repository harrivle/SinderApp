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

/**
 * Created by apple on 5/3/17.
 */

public class Welcome extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_v2_welcome);

		Toolbar my_tool_bar = (Toolbar) findViewById(R.id.toolbar_v2_welcome);
		setSupportActionBar(my_tool_bar);
		my_tool_bar.setTitle("Main Menu");


		//Forms Button (transition to Forms_V Activity)
		Button FormsButton = (Button) findViewById(R.id.button_forms);
		FormsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//go to Forms_V Activity!
				startActivity(new Intent(Welcome.this, Forms_V.class));
			}
		});

		//Saved Projects Button (transition to SavedProjects_V Activity)
		Button SavedProjects = (Button) findViewById(R.id.button_saved_projects);
		SavedProjects.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//go to SavedProjects_V Activity!
				startActivity(new Intent(Welcome.this, SavedProjects_V.class));
			}
		});


		//go to Welcome_Detail if one of the items is clicked:
		AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
			//  @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent myintent = new Intent(Welcome.this, Welcome_Detail.class);
				//  myintent.putExtra("team", teams.get(position));
				//  System.out.println("" + teams.get(position).getTeamName());
				startActivity(myintent);

			}

		};
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

		if (res_id == R.id.edit_profile_v2) {
			//??

		} else if (res_id == R.id.logout_v2) {
			startActivity(new Intent(Welcome.this, LoginActivity.class));
		} else if (res_id == R.id.close_v2) {
			//no code necessary here
		}
		return true;
	}
}
