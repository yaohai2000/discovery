package com.bhz.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bhz.spring.ex2.CDPlayerConfig;
import com.bhz.spring.ex2.CompactDisc;
import com.bhz.spring.ex2.MediaPlayer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CDPlayerConfig.class)
public class CDPlayerTest {
	
	@Rule
	public final SystemOutRule log = new SystemOutRule().enableLog();
	
	@Autowired
	@Qualifier("purpose")
	private CompactDisc cd;
	
	@Autowired
	private MediaPlayer player;
	
	@Test
	public void cdShouldNotBeNull(){
		Assert.assertNotNull(cd);
		cd.play();
	}
	
	@Test
	public void play(){
		player.play();
		Assert.assertEquals("Playing Sgt. Pepper's Lonely Hearts Club Band by The Beatles" + System.getProperty("line.separator"), log.getLog());
	}
	
	
}
