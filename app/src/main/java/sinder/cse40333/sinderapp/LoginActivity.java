package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
public class LoginActivity extends BaseActivity {
	private static final String TAG = "EmailPassword";
	boolean checked = false;

	private TextView mStatusTextView;
	private TextView mDetailTextView;
	private EditText mEmailField;
	private EditText mPasswordField;
	ArrayList<ArrayList<String>> projects = new ArrayList();

	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;
	FirebaseDatabase db = FirebaseDatabase.getInstance();
	DatabaseReference ref = db.getReference();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Views
		mEmailField = (EditText) findViewById(R.id.email);
		mPasswordField = (EditText) findViewById(R.id.password);

		// Buttons
		findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
			}
		});
		findViewById(R.id.email_create_account_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
			}
		});

		ref.child("projects").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot firstSnapshot : dataSnapshot.getChildren()) {
					ArrayList<String> temp = new ArrayList();
					temp.add(((HashMap<String, String>) firstSnapshot.getValue()).get("projectName"));
					temp.add(((HashMap<String, String>) firstSnapshot.getValue()).get("projectDate"));
					projects.add(temp);
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.w("Cancelled", "loadPost:onCancelled", databaseError.toException());
			}
		});

		mAuth = FirebaseAuth.getInstance();
		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user != null) {
					// User is signed in
					Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
					if (checked) {
						Intent intent = new Intent(LoginActivity.this, PastProjects_SM.class);
						startActivity(intent);
					}
					else {
						Intent intent = new Intent(LoginActivity.this, Welcome.class);
						startActivity(intent);
					}
				} else {
					// User is signed out
					Log.d(TAG, "onAuthStateChanged:signed_out");
				}
			}
		};
	}

	@Override
	public void onStart() {
		super.onStart();
		mAuth.addAuthStateListener(mAuthListener);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mAuthListener != null) {
			mAuth.removeAuthStateListener(mAuthListener);
		}
	}

	private void createAccount(String email, String password) {
		Log.d(TAG, "createAccount:" + email);
		if (!validateForm()) {
			return;
		}

		showProgressDialog();

		// [START create_user_with_email]
		mAuth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

						// If sign in fails, display a message to the user. If sign in succeeds
						// the auth state listener will be notified and logic to handle the
						// signed in user can be handled in the listener.
						if (!task.isSuccessful()) {
							Toast.makeText(LoginActivity.this, R.string.auth_failed,
									Toast.LENGTH_SHORT).show();
						}

						// [START_EXCLUDE]
						hideProgressDialog();
						// [END_EXCLUDE]
					}
				});
		// [END create_user_with_email]
	}

	private void signIn(String email, String password) {
		Log.d(TAG, "signIn:" + email);
		if (!validateForm()) {
			return;
		}

		showProgressDialog();

		// [START sign_in_with_email]
		mAuth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

						// If sign in fails, display a message to the user. If sign in succeeds
						// the auth state listener will be notified and logic to handle the
						// signed in user can be handled in the listener.
						if (!task.isSuccessful()) {
							Log.w(TAG, "signInWithEmail:failed", task.getException());
							Toast.makeText(LoginActivity.this, R.string.auth_failed,
									Toast.LENGTH_SHORT).show();
						}

						// [START_EXCLUDE]
					//	if (!task.isSuccessful()) {
					//		mStatusTextView.setText(R.string.auth_failed);
					//	}
					//	hideProgressDialog();
						// [END_EXCLUDE]
					}
				});
		// [END sign_in_with_email]
	}

	private void signOut() {
		mAuth.signOut();
	}

	private boolean validateForm() {
		boolean valid = true;

		String email = mEmailField.getText().toString();
		if (TextUtils.isEmpty(email)) {
			mEmailField.setError("Required.");
			valid = false;
		} else {
			mEmailField.setError(null);
		}

		String password = mPasswordField.getText().toString();
		if (TextUtils.isEmpty(password)) {
			mPasswordField.setError("Required.");
			valid = false;
		} else {
			mPasswordField.setError(null);
		}

		return valid;
	}

	public void itemClicked(View v) {
		if(((CheckBox) v).isChecked()) {
			checked = true;
		}
		else {
			checked = false;
		}
	}
}