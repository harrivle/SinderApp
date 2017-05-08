package sinder.cse40333.sinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
public class LoginActivity extends BaseActivity {
	private static final String TAG = "EmailPassword";
	boolean checked = false;

	private EditText mEmailField;
	private EditText mPasswordField;
	ArrayList<ArrayList<String>> projects = new ArrayList();


	FirebaseAuth.AuthStateListener mAuthListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loginFalse();
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

		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = baseAuth.getCurrentUser();
				if (user != null) {
					// User is signed in
					Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
					Intent intent = new Intent(LoginActivity.this, LogintoMenuActivity.class);
					startActivity(intent);

				}
			}
		};
	}

	@Override
	public void onStart() {
		super.onStart();
		baseAuth.addAuthStateListener(mAuthListener);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mAuthListener != null) {
			baseAuth.removeAuthStateListener(mAuthListener);
		}
	}

	private void createAccount(String email, String password) {
		Log.d(TAG, "createAccount:" + email);
		if (!validateForm()) {
			return;
		}

		showProgressDialog();

		// [START create_user_with_email]
		baseAuth.createUserWithEmailAndPassword(email, password)
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
		baseAuth.signInWithEmailAndPassword(email, password)
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