package com.chung;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

public class GameLogic {
	private Cell[][] grid;
	private final int[][] DIRECTIONS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};
	private Game game;
	private boolean isGameFinished;
	private boolean isFirstClick;
	private GameModes mode;
	private int markCount;
	
	public GameLogic(Game game) {
		this.game = game;
		mode=GameModes.BEGINNER;
		initGame(mode);
	}
	public void initGame(GameModes mode){
		this.mode=mode;
		markCount=0;
		isFirstClick=true;
		isGameFinished=false;
		grid=new Cell[mode.ROWS][mode.COLS];
		for (int i = 0; i < mode.ROWS; ++i) {
			for (int j = 0; j < mode.COLS; ++j) {
				grid[i][j] = new Cell(new int[]{i, j});
				grid[i][j].addActionListener(this::onCellClick);
				grid[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						onRightClick(e);
					}
				});
			}
		}
	}
	private void onCellClick(ActionEvent e) {
		if (isGameFinished) return;
		if(isFirstClick){
			Cell cell= (Cell) e.getSource();
			generate(cell);
			isFirstClick=false;
		}
		openCell((Cell) e.getSource());
	}
	
	private void onRightClick(MouseEvent e) {
		if (isGameFinished) return;
		if (e.getButton() == MouseEvent.BUTTON3) {
			markCell((Cell) e.getSource());
		}
	}
	
	private int countBombOfCell(int r, int c) {
		int count = 0;
		for (int[] dir : DIRECTIONS) {
			int row = r + dir[0];
			int col = c + dir[1];
			if (isValidPos(row, col) && grid[row][col].isBomb()) {
				count++;
			}
		}
		return count;
	}
	
	private void generate(Cell firstCell) {
		int generatedBombCount=0;
		while (generatedBombCount<mode.BOMBS) {
			int r = ThreadLocalRandom.current().nextInt(mode.ROWS);
			int c = ThreadLocalRandom.current().nextInt(mode.COLS);
			if (!grid[r][c].isBomb() && r!=firstCell.getPos()[0] && c!=firstCell.getPos()[1]) {
				grid[r][c].setBomb(true);
				generatedBombCount++;
			}
		}
		for (int i = 0; i < mode.ROWS; ++i) {
			for (int j = 0; j < mode.COLS; ++j) {
				if (!grid[i][j].isBomb()) {
					grid[i][j].setBombCount(countBombOfCell(i, j));
				}
			}
		}
	}
	
	public Cell[][] getGrid() {
		return grid;
	}
	
	private boolean isValidPos(int r, int c) {
		return r >= 0 && r < mode.ROWS && c >= 0 && c < mode.COLS;
	}
	
	private void markCell(Cell cell) {
		if (cell.isOpen()) return;
		cell.setFlagged(!cell.isFlagged());
		if(cell.isFlagged()){
			markCount++;
		} else {
			markCount--;
		}
		game.setRemainBombLabel(getRemainBombs());
		cell.forceRerender();
	}
	
	private void openCell(Cell cell) {
		if (cell.isBomb()) {
			openAllCells();
			isGameFinished=true;
			game.gameOver();
			return;
		}
		openCellRecursive(cell);
		if (checkWin()) {
			isGameFinished=true;
			game.gameWin();
		}
	}
	
	private boolean checkWin() {
		for (int i = 0; i < mode.ROWS; ++i) {
			for (int j = 0; j < mode.COLS; ++j) {
				if (!grid[i][j].isOpen() && !grid[i][j].isBomb()) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void openAllCells() {
		for (int i = 0; i < mode.ROWS; ++i) {
			for (int j = 0; j < mode.COLS; ++j) {
				grid[i][j].setOpen(true);
				grid[i][j].forceRerender();
			}
		}
	}
	
	private void openCellRecursive(Cell cell) {
		if (cell.isOpen()) return;
		cell.setOpen(true);
		cell.setBorder(new LineBorder(new Color(0.8f,0.8f,0.8f,0.5f)));
		if (cell.getBombCount() == 0) {
			for (int[] dir : DIRECTIONS) {
				int row = cell.getPos()[0] + dir[0];
				int col = cell.getPos()[1] + dir[1];
				if (isValidPos(row, col)) {
					openCellRecursive(grid[row][col]);
				}
			}
		}
	}
	
	public GameModes getMode() {
		return mode;
	}
	public int getRemainBombs(){
		return mode.BOMBS-markCount;
	}
}
