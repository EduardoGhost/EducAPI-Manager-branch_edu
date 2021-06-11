package br.ufpb.dcx.apps4society.educapimanager.model.dto;

import java.io.Serializable;

public class NewChallengeDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String word;
	
	private String soundUrl;
	private String videoUrl;
	private String imageUrl;
	
	public NewChallengeDTO() {}

	public NewChallengeDTO(String word, String soundUrl, String videoUrl, String imageUrl) {
		this.word = word;
		this.soundUrl = soundUrl;
		this.videoUrl = videoUrl;
		this.imageUrl = imageUrl;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
