package model;

import java.io.Serializable;
import java.util.Date;

public class Share implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long shareId;
	private String emails;
	private Date shareDate;
	private User user;   // Giữ đối tượng User
	private Video video; // Giữ đối tượng Video

	public Share() {}

	// Getter & Setter
	public Long getShareId() { return shareId; }
	public void setShareId(Long shareId) { this.shareId = shareId; }

	public String getEmails() { return emails; }
	public void setEmails(String emails) { this.emails = emails; }

	public Date getShareDate() { return shareDate; }
	public void setShareDate(Date shareDate) { this.shareDate = shareDate; }

	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }

	public Video getVideo() { return video; }
	public void setVideo(Video video) { this.video = video; }
}