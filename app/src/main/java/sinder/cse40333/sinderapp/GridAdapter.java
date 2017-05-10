package sinder.cse40333.sinderapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<ArrayList<Object>> {
	GridAdapter(Context context, ArrayList<ArrayList<Object>> objects) {
		super(context, R.layout.image_layout, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater scheduleInflater = LayoutInflater.from(getContext());
		View projectView = scheduleInflater.inflate(R.layout.image_layout, parent, false);
		ArrayList<Object> objects = getItem(position);
		File imageFile = (File) objects.get(1);
		Uri imageURI = Uri.fromFile(imageFile);
		Project item = (Project) objects.get(0);

		TextView projectName = (TextView) projectView.findViewById(R.id.project_name);
		projectName.setText(item.getProjectName());

		ImageView projectImage = (ImageView) projectView.findViewById(R.id.project_image);
		projectImage.setImageURI(imageURI);

		return projectView;
	}
}
