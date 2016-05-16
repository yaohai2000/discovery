package com.bhz.spittr.data;

import java.util.List;

import com.bhz.spittr.entity.Spittle;

public interface SpittleRepository {
	List<Spittle> findSpittles(long max, int count);
}
