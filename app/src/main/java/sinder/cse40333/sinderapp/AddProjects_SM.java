package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	ImageView imageView;
	static final int REQUEST_IMAGE_CAPTURE = 1;


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

	public void cameraClick(View v) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File

			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				Uri photoURI = FileProvider.getUriForFile(this, "com.cs60333.nalvare1.theservebook.android.fileprovider",photoFile);
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			}
		}

    /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }*/
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			//  Bundle extras = data.getExtras();
			//Bitmap imageBitmap = (Bitmap) extras.get("data");
			// imageView.setImageBitmap(imageBitmap);

			int targetW = imageView.getWidth();
			int targetH = imageView.getHeight();

			// Get the dimensions of the bitmap
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
			int photoW = bmOptions.outWidth;
			int photoH = bmOptions.outHeight;

			// Determine how much to scale down the image
			int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

			// Decode the image file into a Bitmap sized to fill the View
			bmOptions.inJustDecodeBounds = false;
			bmOptions.inSampleSize = scaleFactor;
			bmOptions.inPurgeable = true;

			Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
			imageView.setImageBitmap(bitmap);
		}
	}
	String mCurrentPhotoPath;

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = image.getAbsolutePath();
		return image;
	}

}
