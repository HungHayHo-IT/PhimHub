package model;

import java.io.Serializable;

public class Video implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String videoId;
	private String title;
	private String poster;
	private int views;
	private String description;
	private boolean active = true;
	private String href; // <--- THÊM MỚI
	private double price;
	
	public Video() {}

	// Constructor cập nhật đầy đủ (nếu bạn có dùng constructor này thì nhớ sửa lại code gọi nó)
	public Video(String videoId, String title, String poster, int views, String description, boolean active, String href) {
		this.videoId = videoId;
		this.title = title;
		this.poster = poster;
		this.views = views;
		this.description = description;
		this.active = active;
		this.href = href;
	}

	// Getter & Setter cũ giữ nguyên...
	public String getVideoId() { return videoId; }
	public void setVideoId(String videoId) { this.videoId = videoId; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getPoster() { return poster; }
	public void setPoster(String poster) { this.poster = poster; }
	public int getViews() { return views; }
	public void setViews(int views) { this.views = views; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// Getter & Setter MỚI cho Href
	public String getHref() { return href; }
	public void setHref(String href) { this.href = href; }
}