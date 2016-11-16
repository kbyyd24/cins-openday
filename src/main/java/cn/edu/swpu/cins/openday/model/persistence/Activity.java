package cn.edu.swpu.cins.openday.model.persistence;

public class Activity {

	private Integer id;
	private String title;
	private String content;

	public Activity(int id, String title, String content) {

		this.id = id;
		this.title = title;
		this.content = content;
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

	public Activity() {}

	@Override
	public String toString() {
		return "Activity{" +
			"id=" + id +
			", title='" + title + '\'' +
			", content='" + content + '\'' +
			'}';
	}
}
