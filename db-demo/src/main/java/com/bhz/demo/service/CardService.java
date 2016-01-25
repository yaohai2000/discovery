package com.bhz.demo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhz.demo.dao.CardDAO;
import com.bhz.demo.entity.Card;

import org.springframework.transaction.annotation.Propagation;

@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class CardService {
	@Autowired
	private CardDAO dao;
	
	@CacheEvict(value={"cards"},allEntries=true)
	public void addCard(String cardNo,int type,String amount){
		Card c = new Card();
		c.setAmount(new BigDecimal(amount));
		c.setCardNo(cardNo);
		c.setType(type);
		dao.addCard(c);
	}
	
	@Cacheable("cards")
	public Card getCard(String cardNo){
		return dao.getCard(cardNo);
	}
}
