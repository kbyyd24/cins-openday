package cn.edu.swpu.cins.openday.model.http;

public class PostActivity {
	private String title;
	private String content;
	private String img;
	private Long startTime;
	private Long endTime;

	public PostActivity() {}

	public PostActivity(String title, String content, String img, Long startTime, Long endTime) {
		this.title = title;
		this.content = content;
		this.img = img;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
}
