package br.ufpb.dcx.apps4society.educapimanager.model.dto;

import java.io.Serializable;

public class NewContextDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String imageUrl;
	private String soundUrl;
	private String videoUrl;
	
	public NewContextDTO() {}

	public NewContextDTO(String name, String imageUrl, String soundUrl, String videoUrl) {
		this.name = name;
		this.imageUrl = imageUrl;
		this.soundUrl = soundUrl;
		this.videoUrl = videoUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSoundUrl() {
		return soundUrl;
	}

	public void setSoundUrl(String soundUrl) {
		this.soundUrl = soundUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
}
