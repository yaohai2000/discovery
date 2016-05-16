package com.bhz.spittr.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bhz.spittr.entity.Spittle;

@Service
public class SpittleRepositoryImpl implements SpittleRepository {

	@Override
	public List<Spittle> findSpittles(long max, int count) {
		List<Spittle> spittles = new ArrayList<Spittle>();
		for(int i=0;i<count;i++){
			Spittle s = new Spittle(("Spittle " + i),new Date());
			spittles.add(s);
		}
		return spittles;
	}

}
