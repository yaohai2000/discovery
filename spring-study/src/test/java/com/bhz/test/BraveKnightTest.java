package com.bhz.test;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;

import com.bhz.spring.ex1.BraveKnight;
import com.bhz.spring.ex1.Quest;

@ContextConfiguration()
public class BraveKnightTest {

	@Test
	public void knightEmbarkOnQuest(){
		Quest mockQuest = Mockito.mock(Quest.class);
		BraveKnight knight = new BraveKnight(mockQuest);
		knight.embarkQuest();
		Mockito.verify(mockQuest, Mockito.times(1)).embark();
	}
}
