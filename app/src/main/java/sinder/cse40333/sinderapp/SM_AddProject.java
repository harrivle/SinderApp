package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SM_AddProject extends BaseActivity {
	final int CAMERA_REQUEST = 1;
	private Uri cameraURI = null;
	private Uri uploadURI;
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

				if (cameraURI != null) {
					try {
						File newImage = createImageFile(project);
						moveFile(new File(cameraURI.getPath()), newImage);
						uploadURI = Uri.fromFile(newImage);
						System.out.println(uploadURI);
						uploadToStorage(uploadURI, project);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				startActivity(new Intent(SM_AddProject.this, SM_PastProjects.class));
			}
		});

		View.OnClickListener cameraButtonListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if (cameraIntent.resolveActivity(getPackageManager()) != null) {
					// Create the File where the photo should go
					File imageFile = null;
					try {
						imageFile = createTempFile();
					} catch (IOException ex) {
						ex.printStackTrace();
					}

					if (imageFile != null) {
						cameraURI = FileProvider.getUriForFile(SM_AddProject.this, "sinder.cse40333.sinderapp.fileprovider", imageFile);
						cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraURI);
						startActivityForResult(cameraIntent, CAMERA_REQUEST);
					}
				}
			}
		};

		Button cameraButton = (Button) findViewById(R.id.camera_button);
		cameraButton.setOnClickListener(cameraButtonListener);
	}

	private File createTempFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File rootPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		String imageFileName = timeStamp + ".jpg";
		imageFileName = imageFileName.replaceAll("\\s+",""); // remove whitespace
		System.out.println(imageFileName);
		File image = new File(rootPath, imageFileName);

		return image;
	}

	private File createImageFile(Project project) throws IOException {
		File rootPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		String imageFileName = project.getProjectID() + project.getProjectName() + ".jpg";
		imageFileName = imageFileName.replaceAll("\\s+",""); // remove whitespace
		System.out.println(imageFileName);
		File image = new File(rootPath, imageFileName);

		return image;
	}

	public void moveFile(File src, File dst) throws IOException {
		FileChannel inChannel = new FileInputStream(src).getChannel();
		FileChannel outChannel = new FileOutputStream(dst).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}

		src.delete();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// set image in activity_detail
		if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
			ImageView projectImage = (ImageView) findViewById(R.id.project_image);
			projectImage.setImageURI(cameraURI);
		}
	}
}
