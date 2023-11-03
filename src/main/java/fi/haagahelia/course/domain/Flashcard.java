package fi.haagahelia.course.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Flashcard {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String front;
	private String back;
	private boolean showBack;
	
	public Flashcard() {}
	
	
	public Flashcard(String front, String back) {
		super();
		this.front = front;
		this.back = back;
		this.showBack = true;
	}



	public Long getId() {
		return id;
	}
	public void setFlascardId(Long id) {
		this.id = id;
	}
	public String getFront() {
		return front;
	}
	public void setFront(String front) {
		this.front = front;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}


	public boolean getShowBack() {
		return showBack;
	}


	public void setShowBack(boolean showBack) {
		this.showBack = showBack;
	}
	
	
}