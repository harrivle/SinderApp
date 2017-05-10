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
	Project project;
	Uri imageURI;

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

			Button signUpButton = (Button) findViewById(R.id.sign_up_button);
			signUpButton.setOnClickListener(signUpButtonListener);
		}
	}

	private File createImageFile() throws IOException {
		File rootPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		String imageFileName = project.getProjectID() + project.getProjectName() + ".jpg";
		imageFileName = imageFileName.replaceAll("\\s+",""); // remove whitespace
		System.out.println(imageFileName);
		File image = new File(rootPath, imageFileName);

		return image;
	}

	@Override
	public void update(DataSnapshot data) {
		File imageFile = downloadFromStorage(project);
		imageURI = Uri.fromFile(imageFile);
		ImageView projectImage = (ImageView) findViewById(R.id.project_image);
		projectImage.setImageURI(imageURI);
	}
}