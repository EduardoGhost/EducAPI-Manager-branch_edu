package br.ufpb.dcx.apps4society.educapimanager.model.dto;

import java.io.Serializable;

import br.ufpb.dcx.apps4society.educapimanager.model.bean.Context;

public class ContextDTO  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	private String imageUrl;
	private String soundUrl;
	private String videoUrl;
	
	public ContextDTO() {}
	
	public ContextDTO(Context obj) {
		this.id = obj.getId();
	}

	public NewContextDTO toNewContextDto(ContextDTO contextDTO){
		return new NewContextDTO(contextDTO.name, contextDTO.imageUrl, contextDTO.soundUrl, contextDTO.videoUrl);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
