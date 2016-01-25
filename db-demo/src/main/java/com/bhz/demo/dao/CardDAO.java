package com.bhz.demo.dao;

import java.util.List;

import com.bhz.demo.entity.Card;

public interface CardDAO {
//	public List<Card> showAllCards();
	public Card getCard(String cardNo);
	public void addCard(Card card);
//	public void delCard(Card card);
}
