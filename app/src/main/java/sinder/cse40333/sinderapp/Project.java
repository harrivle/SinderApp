package sinder.cse40333.sinderapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.File;
import java.io.Serializable;

@IgnoreExtraProperties
public class Project implements Serializable {
	private String projectName;
	private String projectDate;
	private String projectDesc;
	private String contactInfo;
	private String imageLoc;
	private int numVolunteers;
	private Boolean deleted = false;

	@Exclude
	private int projectID = -1;
	@Exclude
	private String imageName;
	@Exclude
	private String ownerName;

	public Project() {
		// required for FireBase
	}

	public Project (String projectName, String projectDate, String projectDesc, String contactInfo, int numVolunteers) {
		this.projectName = projectName;
		this.projectDate = projectDate;
		this.projectDesc = projectDesc;
		this.contactInfo = contactInfo;
		this.numVolunteers = numVolunteers;
		imageLoc = "images/image_default.jpg";
		imageName = "image_default.jpg";
	}

	@Exclude
	public int getProjectID() {
		return projectID;
	}

	@Exclude
	public void setProjectID(int ID) {
		projectID = ID;
	}

	@Exclude
	public void setOwnerName(String name) {
		ownerName = name;
	}

	@Exclude
	public String getOwnerName() {
		return ownerName;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getProjectDate() {
		return projectDate;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public String getImageLoc() {
		return imageLoc;
	}

	public void setImageLoc(String imageLoc) {
		this.imageLoc = imageLoc;
		File f = new File(imageLoc);
		imageName = f.getName();
	}

	public String getImageName() {
		return imageName;
	}

	public int getNumVolunteers() {
		return numVolunteers;
	}
}
