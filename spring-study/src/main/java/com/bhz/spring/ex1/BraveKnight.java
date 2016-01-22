package com.bhz.spring.ex1;

public class BraveKnight implements Knight {

	Quest quest;
	
	public BraveKnight(Quest quest){
		this.quest = quest;
	}
	
	@Override
	public void embarkQuest() {
		quest.embark();
	}

}
