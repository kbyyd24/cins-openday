package cn.edu.swpu.cins.openday.model.http;

public class PostActivity {
	private String title;
	private String content;

	public PostActivity() {}

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

	@Override
	public String toString() {
		return "PostActivity{" +
			"title='" + title + '\'' +
			", content='" + content + '\'' +
			'}';
	}
}
