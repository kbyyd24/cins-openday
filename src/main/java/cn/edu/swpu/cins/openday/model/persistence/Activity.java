package cn.edu.swpu.cins.openday.model.persistence;

public class Activity {

	private Integer id;
	private String title;
	private String content;
	private String img;
	private Long createTime;
	private Long endTime;

	public Activity(Integer id, String title, String content, String img, Long create_time, Long end_time) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.img = img;
		createTime = create_time;
		endTime = end_time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Activity() {}

}
