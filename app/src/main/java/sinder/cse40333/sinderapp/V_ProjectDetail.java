package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.io.File;
import java.io.IOException;

public class V_ProjectDetail extends BaseActivity {
	final int CAMERA_REQUEST = 1;
	private Uri imageURI;
	private Uri cameraURI;
	Project project;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_v2_welcome_detail);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			project = (Project) extras.getSerializable("project");

			TextView projectName = (TextView) findViewById(R.id.project_name);
			projectName.setText(project.getProjectName());
			TextView projectDate = (TextView) findViewById(R.id.project_date);
			projectDate.setText(project.getProjectDate());
			TextView projectDesc = (TextView) findViewById(R.id.project_desc);
			projectDesc.setText(project.getProjectDesc());
			TextView contactInfo = (TextView) findViewById(R.id.contact_info);
			contactInfo.setText(project.getContactInfo());
			TextView numVol = (TextView) findViewById(R.id.num_vol);
			numVol.setText(((Integer) project.getNumVolunteers()).toString());

			View.OnClickListener signUpButtonListener = new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					saveProject(project);
				}
			};

			View.OnClickListener cameraButtonListener = new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					if (cameraIntent.resolveActivity(getPackageManager()) != null) {
						// Create the File where the photo should go
						File imageFile = null;
						try {
							imageFile = createImageFile();
						} catch (IOException ex) {
							ex.printStackTrace();
						}

						if (imageFile != null) {
							cameraURI = FileProvider.getUriForFile(V_ProjectDetail.this, "sinder.cse40333.sinderapp.fileprovider", imageFile);
							cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraURI);
							startActivityForResult(cameraIntent, CAMERA_REQUEST);
						}
					}
				}
			};

			Button signUpButton = (Button) findViewById(R.id.sign_up_button);
			signUpButton.setOnClickListener(signUpButtonListener);
			Button cameraButton = (Button) findViewById(R.id.camera_button);
			cameraButton.setOnClickListener(cameraButtonListener);
		}
	}

	@Override
	public void update(DataSnapshot data) {
		imageURI = downloadFromStorage(project);
		ImageView projectImage = (ImageView) findViewById(R.id.project_image);
		projectImage.setImageURI(imageURI);
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String imageFileName = project.getProjectID() + project.getProjectName();
		imageFileName.replaceAll("\\s+",""); // remove whitespace
		File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);

		return image;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// set image in activity_detail
		if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
			uploadToStorage(cameraURI, project);
		}
	}
}