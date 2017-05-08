package sinder.cse40333.sinderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by apple on 5/4/17.
 */

public class Welcome_Detail extends Activity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_v2_welcome_detail);

        Button gallery_detailButton = (Button) findViewById(R.id.saved_projects_v2b);
        gallery_detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent back_to_welcomeIntent = new Intent(Welcome_Detail.this, Welcome.class);
                //galleryIntent.putExtra("id", Integer.parseInt(stringInfo.getTeamID()));
                startActivity(back_to_welcomeIntent);

             /*

            !!!!INCLUDE WAY TO SAVE THIS PROJECT AND HAVE IT APPEAR IN THE SAVEDPROJECTS_V ACTIVITY!!!
            */
            }
        });
    }
}
