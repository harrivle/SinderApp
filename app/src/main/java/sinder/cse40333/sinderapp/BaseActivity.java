package sinder.cse40333.sinderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class BaseActivity extends AppCompatActivity {
	public ProgressDialog mProgressDialog;
	boolean onLoginScreen;

	FirebaseAuth baseAuth;
	FirebaseAuth.AuthStateListener baseAuthListener;
	FirebaseDatabase db = FirebaseDatabase.getInstance();
	DatabaseReference dbRef = db.getReference();

	FirebaseStorage storage = FirebaseStorage.getInstance();
	StorageReference storageRef = storage.getReference();

	String currentUID;
	int projectIndex;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		onLoginScreen = true;
		baseAuth = FirebaseAuth.getInstance();
		baseAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user == null) {
					currentUID = null;
					// User is signed out
					Log.d("AuthState", "onAuthStateChanged:signed_out");
					if (onLoginScreen) {
						Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
						startActivity(intent);
					}
				}
				else {
					currentUID = baseAuth.getCurrentUser().getUid();
				}
			}
		};

		dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				projectIndex = ((Long) dataSnapshot.child("projects/nextID").getValue()).intValue();
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				System.out.println("The read failed: " + databaseError.getCode());
			}
		});

		dbRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				update(dataSnapshot);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				System.out.println("The read failed: " + databaseError.getCode());
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		baseAuth.addAuthStateListener(baseAuthListener);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (baseAuthListener != null) {
			baseAuth.removeAuthStateListener(baseAuthListener);
		}
		hideProgressDialog();
	}

	public void showProgressDialog() {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(true);
		}

		mProgressDialog.show();
	}

	public void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	public void loginFalse() {
		onLoginScreen = false;
	}

	public void signOut() {
		baseAuth.signOut();
	}

	public void update(DataSnapshot data) {
		//Override
	}

	public void addNewProject(Project project) {
		project.setProjectID(projectIndex);
		dbRef.child("projects/" + projectIndex).setValue(project);
		dbRef.child("projects").child("nextID").setValue(projectIndex + 1);
	}

	public void saveProject(Project project) {
		dbRef.child("users/" + currentUID + "/savedProjects/" + project.getProjectID()).setValue(true);
	}

	public void updateProject(Project project) {
		dbRef.child("projects/" + project.getProjectID()).setValue(project);
	}

	public void uploadToStorage(Uri fileURI, final Project project) {
		final String imageLoc = "images/" + fileURI.getLastPathSegment();
		UploadTask uploadTask = storageRef.child(imageLoc).putFile(fileURI);

		uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
				@SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
				System.out.println("Upload is " + progress + "% done");
			}
		}).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
				System.out.println("Upload is paused");
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception exception) {
				exception.printStackTrace();
			}
		}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
				project.setImageLoc(imageLoc);
				updateProject(project);
			}
		});
	}

	public File downloadFromStorage(Project project) {
		File rootPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		StorageReference islandRef = storageRef.child(project.getImageLoc());
		final File localFile = new File(rootPath, project.getImageName());

		islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
				Log.e("firebase ","local file created" + localFile.toString());
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception exception) {
				Log.e("firebase ","local file not created " + exception.toString());
			}
		});

		return localFile;

		/*try {
			String[] strarr = project.getImageName().split("\\.");
			System.out.println(strarr[0]+strarr[1]);
			localFile = File.createTempFile(strarr[0], "." + strarr[1], getExternalFilesDir(Environment.DIRECTORY_PICTURES));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (localFile != null) {
			storageRef.child(project.getImageLoc()).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
				@Override
				public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
					// success
				}
			}).addOnFailureListener(new OnFailureListener() {
				@Override
				public void onFailure(@NonNull Exception exception) {
					exception.printStackTrace();
				}
			});

			return FileProvider.getUriForFile(this, "sinder.cse40333.sinderapp.fileprovider", localFile);
		}

		return null;*/
	}
}