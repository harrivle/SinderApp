package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by apple on 5/3/17.
 */

public class Welcome extends AppCompatActivity {
	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;
	GridView gridview;
	static final int CAMERA_REQUEST = 1;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_v2_welcome);

		Toolbar my_tool_bar = (Toolbar) findViewById(R.id.toolbar_v2_welcome);
		setSupportActionBar(my_tool_bar);
		my_tool_bar.setTitle("Main Menu");

		//go to Welcome_Detail if one of the items is clicked:
		/*gridview.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
			//  @Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent myintent = new Intent(Welcome.this, Welcome_Detail.class);
				startActivity(myintent);
			}

		};
		*/

		mAuth = FirebaseAuth.getInstance();

		gridview = (GridView) findViewById(R.id.gridview);
		populateGridView();

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
			mAuth.signOut();
			Intent intent = new Intent(Welcome.this, LoginActivity.class);
			startActivity(intent);
		} else if (res_id == R.id.close_v2) {
			//no code necessary here
		}
		return true;
	}

	//for grid view/gallery:
	private void populateGridView() {
		String[] fields = new String[]{DatabaseHelper.COL_IMAGE_ID, DatabaseHelper.IMAGE, DatabaseHelper.IMAGE_NAME};
		String where = " team_id = ?";
		String[] args = new String[]{Integer.toString(team_id)};
		int[] items = new int[] {R.id.project_image, R.id.project_name_and_date};//R.id.col_id,


		Cursor cursor = dbHelper.getSelectEntries(DatabaseHelper.IMAGES_TABLE_NAME, fields, where, args, DatabaseHelper.COL_IMAGE_ID + " DESC");
		SimpleCursorAdapter galleryCursorAdapter = new SimpleCursorAdapter (this, R.layout.image_layout, cursor, fields, items, 0);

		galleryCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
			@Override
			public boolean setViewValue (View view, Cursor cursor, int columnIndex){
				if (view.getId() == R.id.project_image) {
					ImageView imageView=(ImageView) view;
					byte[] imageArray = cursor.getBlob(1);
					Bitmap bitmap = BitmapFactory.decodeByteArray(imageArray,0,imageArray.length);
					imageView.setImageBitmap(bitmap);
					return true;
				}

				return false;
			}});

		gridview.setAdapter(galleryCursorAdapter);
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent myintent = new Intent(Welcome.this, Welcome_Detail.class);
				//myintent.putExtra("team", teams.get(position));
				startActivity(myintent);

			}
		});
	}
}
