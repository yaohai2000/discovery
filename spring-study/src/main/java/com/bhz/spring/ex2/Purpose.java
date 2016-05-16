package com.bhz.spring.ex2;

import org.springframework.stereotype.Component;

//@Component
public class Purpose implements CompactDisc {

	private String title = "What do you mean";
	private String artist = "Justin Bieber";
	
	@Override
	public void play() {
		System.out.println("Playing " + title + " by " + artist);
	}

}
