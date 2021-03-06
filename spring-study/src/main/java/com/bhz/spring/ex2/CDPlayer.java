package com.bhz.spring.ex2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
public class CDPlayer implements MediaPlayer {
	
	CompactDisc cd;
	
	@Autowired
	public CDPlayer(CompactDisc cd){
		this.cd = cd;
	}

	@Override
	public void play() {
		cd.play();
	}
	
}
