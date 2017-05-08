package sinder.cse40333.sinderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {
	boolean onLogin;
	FirebaseAuth baseAuth;
	FirebaseAuth.AuthStateListener baseAuthListener;
	FirebaseDatabase db = FirebaseDatabase.getInstance();
	DatabaseReference ref = db.getReference();

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		onLogin = true;
		baseAuth = FirebaseAuth.getInstance();
		baseAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user == null) {
					// User is signed out
					Log.d("AuthState", "onAuthStateChanged:signed_out");
					if (onLogin) {
						Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
						startActivity(intent);
					}
				}
			}
		};
	}

	@Override public void onStart() {
		super.onStart();
		baseAuth.addAuthStateListener(baseAuthListener);
	}

	@VisibleForTesting
	public ProgressDialog mProgressDialog;

	public void loginFalse() {
		onLogin = false;
	}

	public void signOut() {
		baseAuth.signOut();
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

	@Override
	public void onStop() {
		super.onStop();
		if (baseAuthListener != null) {
			baseAuth.removeAuthStateListener(baseAuthListener);
		}
		hideProgressDialog();
	}
}