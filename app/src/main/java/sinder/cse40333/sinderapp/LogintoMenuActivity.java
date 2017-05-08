package sinder.cse40333.sinderapp;
/*
Harrison Le
*/

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LogintoMenuActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		System.out.println("LogintoMenu");
		ref.child("users").child(baseAuth.getCurrentUser().getUid()).child("isSM").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if ((Boolean) dataSnapshot.getValue()) {
					Intent intent = new Intent(LogintoMenuActivity.this, PastProjects_SM.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(LogintoMenuActivity.this, Welcome.class);
					startActivity(intent);
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}
}
