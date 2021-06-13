package com.chung;

import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {
	private boolean isBomb;
	private boolean isOpen;
	private boolean isFlagged;
	private int bombCount;
	private int[] pos;
	public Cell(int[] pos){
		this.pos=pos;
		setForeground(Color.BLUE);
		setMargin(new Insets(0,0,0,0));
		setPreferredSize(new Dimension(16,16));
		setFont(getFont().deriveFont(Font.BOLD,12));
	}
	
	@Override
	public String getText() {
		if(isOpen){
			if(bombCount==0)  return "";
			return String.valueOf(bombCount);
		}
		return "";
	}
	
	@Override
	public Icon getIcon() {
		try {
			if(isOpen && isBomb)  return ImageLoader.load(Images.BOMB);
			if(!isOpen && isFlagged) return ImageLoader.load(Images.FLAG);
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public void forceRerender(){
		repaint();
	}
	public boolean isBomb() {
		return isBomb;
	}
	
	public void setBomb(boolean bomb) {
		isBomb = bomb;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public void setOpen(boolean open) {
		isOpen = open;
	}
	
	public boolean isFlagged() {
		return isFlagged;
	}
	
	public void setFlagged(boolean flagged) {
		isFlagged = flagged;
	}
	
	public int getBombCount() {
		return bombCount;
	}
	
	public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
	}
	
	public int[] getPos() {
		return pos;
	}
	
	public void setPos(int[] pos) {
		this.pos = pos;
	}
}
