package com.gez.grill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gez.grill.entity.ViewsAndVisits;
import com.gez.grill.mapper.LiulfwMapper;

@Service
public class LiulfwService {

	@Autowired
	private LiulfwMapper liulfwInstance;

	@Transactional
	public void updateRestaurantView(ViewsAndVisits record) {
		liulfwInstance.updateRestaurantView(record);
	}

	@Transactional
	public void updateRestaurantVisit(ViewsAndVisits record) {
		liulfwInstance.updateRestaurantVisit(record);
	}

	@Transactional
	public void updateDishView(ViewsAndVisits record) {
		liulfwInstance.updateDishView(record);
	}

	@Transactional
	public void updateDishVisit(ViewsAndVisits record) {
		liulfwInstance.updateDishVisit(record);
	}
}