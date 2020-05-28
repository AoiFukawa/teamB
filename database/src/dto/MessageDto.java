package dto;

/**
 * メッセージ情報をまとめて保持するクラス<br>
 * データ検索時に使用され、以下のフィールドを持つ<br>
 * ・投稿ID<br>
 * ・投稿内容<br>
 * ・投稿日時<br>
 * @author user
 *
 */
public class MessageDto { //MessageDtoが一行分のデータを持っている
	
	/**
	 * 投稿ID
	 */
	int id;
	
	int mention;
	
	String username;
	
	/**
	 * 投稿内容
	 */
	String content;
	
	/**
	 * 投稿日時
	 */
	String date;

	boolean favorite;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMention() {
		return mention;
	}

	public void setMention(int mention) {
		this.mention = mention;
	}
	
	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public boolean getFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
}
