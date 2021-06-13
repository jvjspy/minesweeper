package com.chung;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
	private static final Map<Images,ImageIcon> imgMap=new HashMap<>();
	private static void lazyLoad(Images img,String name){
		if(!imgMap.containsKey(img)){
			imgMap.put(img, new ImageIcon(ImageLoader.class.getClassLoader().getResource(name)));
		}
	}
	public static ImageIcon load(Images img) throws IOException {
		switch (img){
			case BOMB:
				lazyLoad(img,"bomb.png");
				return imgMap.get(img);
			case FLAG:
				lazyLoad(img,"flag.png");
				return imgMap.get(img);
			case SAD:
				lazyLoad(img,"sad.png");
				return imgMap.get(img);
			case SMILE:
				lazyLoad(img,"smile.png");
				return imgMap.get(img);
			case HAPPY:
				lazyLoad(img,"happy.png");
				return imgMap.get(img);
		}
		return null;
	}
}
