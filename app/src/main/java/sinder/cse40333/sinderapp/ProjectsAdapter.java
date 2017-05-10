package sinder.cse40333.sinderapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProjectsAdapter extends ArrayAdapter<Project> {
	ProjectsAdapter(Context context, ArrayList<Project> projects) {
		super(context, R.layout.project_row, projects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater scheduleInflater = LayoutInflater.from(getContext());
		View projectView = scheduleInflater.inflate(R.layout.project_row, parent, false);
		Project item = getItem(position);

		TextView teamName = (TextView) projectView.findViewById(R.id.projectName);
		teamName.setText(item.getProjectName());

		TextView gameDate = (TextView) projectView.findViewById(R.id.projectDate);
		gameDate.setText(item.getProjectDate());

		return projectView;
	}
}
