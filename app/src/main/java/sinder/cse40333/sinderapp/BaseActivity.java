package sinder.cse40333.sinderapp;

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {
	FirebaseAuth mAuth;
	FirebaseAuth.AuthStateListener mAuthListener;
	FirebaseDatabase db = FirebaseDatabase.getInstance();
	DatabaseReference ref = db.getReference();

	@VisibleForTesting
	public ProgressDialog mProgressDialog;

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
		hideProgressDialog();
	}
}