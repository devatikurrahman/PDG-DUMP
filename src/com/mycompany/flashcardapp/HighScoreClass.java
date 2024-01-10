package com.mycompany.flashcardapp;

public class HighScoreClass {

	String TestName = "", TestTakenTime = "", HighScore = "";

	public String getTestName() {
		return TestName;
	}

	public void setTestName(String TestName) {
		this.TestName = TestName;
	}

	public String getTestTakenTime() {
		return TestTakenTime;
	}

	public void setTestTakenTime(String TestTakenTime) {
		this.TestTakenTime = TestTakenTime;
	}

	public void setHighScore(String HighScore) {
		this.HighScore = HighScore;
	}

	public String getHighScore() {
		return HighScore;
	}
}
