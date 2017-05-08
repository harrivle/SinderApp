package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseAuth;
import android.widget.Button;

/**
 * Created by apple on 5/3/17.
 */

public class Welcome extends BaseActivity {
	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_v2_welcome);

		Toolbar my_tool_bar = (Toolbar) findViewById(R.id.toolbar_v2_welcome);
		setSupportActionBar(my_tool_bar);
		my_tool_bar.setTitle("Main Menu");

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

		mAuth = FirebaseAuth.getInstance();
		/*mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user == null) {
					// User is signed out
					Log.d("LOGOUT", "onAuthStateChanged:signed_out");
					Intent intent = new Intent(Welcome.this, LoginActivity.class);
					startActivity(intent);
				}
			}
		};
		*/
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
			startActivity(new Intent(Welcome.this, Forms_V.class));
		} else if (res_id == R.id.saved_projects_v2) {
			startActivity(new Intent(Welcome.this, SavedProjects_V.class));
		}	else if (res_id == R.id.logout_v2) {
			signOut();
		} else if (res_id == R.id.close_v2) {
			//no code necessary here
		}
		return true;
	}
}
