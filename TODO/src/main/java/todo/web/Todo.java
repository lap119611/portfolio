package todo.web;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Todo implements Serializable {
	private int id;
	private String title;
	private String task;
	private Timestamp limitdate;
	private Timestamp lastupdate;
	private String userid;
	private int status;
	private String label;
	private String inputLimitdate;
	private String filename = null;
	private List<String> errorMessages;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Timestamp getLimitdate() {
		return limitdate;
	}

	public void setLimitdate(Timestamp limitdate) {
		this.limitdate = limitdate;
	}

	public Timestamp getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Timestamp lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getInputLimitdate() {
		return inputLimitdate;
	}

	public void setInputLimitdate(String inputLimitdate) {
		this.inputLimitdate = inputLimitdate;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public boolean valueCheck() {
		errorMessages = new ArrayList<String>();
		if (id < 0) {
			errorMessages.add("不正なタスク番号入力を検出しました：" + id);
		}
		if (title == null || title.isEmpty()) {
			errorMessages.add("入力したタイトルが空です。");
		} else if (title.length() > 256) {
			errorMessages.add("入力したタイトルが長すぎます：" + title.length());
		}
		if (task == null || task.isEmpty()) {
			errorMessages.add("入力したタスクが空です。");
		} else if (task.length() > 512) {
			errorMessages.add("入力したタスクが長すぎます：" + task.length());
		}
		if (inputLimitdate == null || inputLimitdate.isEmpty()) {
			errorMessages.add("入力したタスク期限が空です。");
		} else if (!inputLimitdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
			errorMessages.add("入力したタスク期限のフォーマットが違います：" + inputLimitdate);
		}
		if (userid == null || userid.isEmpty()) {
			errorMessages.add("入力したユーザーIDが空です。");
		} else if (userid.length() > 64) {
			errorMessages.add("入力したユーザーIDが長すぎます：" + userid);
		}
		if (status < 0 || status > 3) {
			errorMessages.add("入力したタスク状況の値が不正です");
		}

		// エラーリストが0件であればtrue, そうでなければfalseを返す
		// falseの場合、errorMessagesにエラーが入っている
		return (errorMessages.size() == 0);
	}

}
