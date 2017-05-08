package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by apple on 3/27/17.
 */

public class Forms_V extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_v3a_forms);

		Toolbar my_tool_bar2 = (Toolbar) findViewById(R.id.toolbar_v3a);
		setSupportActionBar(my_tool_bar2);
		my_tool_bar2.setTitle("Forms");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_v3a, menu);
		return true;
	}

	public String shareFunction() {
		StringBuilder emailStringBuilder = new StringBuilder(20);

		emailStringBuilder.append("Hello! Thank you for allowing me to participate in your community service project!" + "\n");
        emailStringBuilder.append("Attached is my service form." + "\n");
        emailStringBuilder.append("Thanks!" + "\n");
        //emailStringBuilder.append("-" + name);

		String emailString = emailStringBuilder.toString();
        return emailString;
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
			startActivity(new Intent(this, LoginActivity.class));
		} else if (res_id == R.id.share_v3a) {
			Uri uri=Uri.parse("android.resource://"+getPackageName()+"/drawable/"+"sinder_logo");

			Intent shareIntent = new Intent();
			shareIntent.setAction(android.content.Intent.ACTION_SEND);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Service Form Request");
			shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareFunction());

			shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
			shareIntent.setType("image/jpeg");

			startActivity(android.content.Intent.createChooser(shareIntent, "Share via"));

		}

		return true;
	}

}
