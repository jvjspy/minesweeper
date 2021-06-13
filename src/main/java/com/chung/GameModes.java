package com.chung;

public enum GameModes {
	BEGINNER(9,9,10),INTERMEDIATE(16,16,40),EXPERT(30,16,99);
	public final int ROWS,COLS,BOMBS;
	
	GameModes(int rows, int cols, int bombs) {
		this.ROWS = rows;
		this.COLS = cols;
		this.BOMBS = bombs;
	}
	
}
