package model;

import java.io.Serializable;
import java.util.Date;

public class Favorite implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long favoriteId;
	private User user;   // Giữ nguyên object để tiện truy xuất thông tin user
	private Video video; // Giữ nguyên object để tiện truy xuất thông tin video
	private Date likeDate;

	public Favorite() {
	}

	public Favorite(Long favoriteId, User user, Video video, Date likeDate) {
		super();
		this.favoriteId = favoriteId;
		this.user = user;
		this.video = video;
		this.likeDate = likeDate;
	}

	// Getter & Setter
	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Date getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(Date likeDate) {
		this.likeDate = likeDate;
	}
}