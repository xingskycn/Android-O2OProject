package com.gez.grill.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gez.grill.entity.CaipBasic;
import com.gez.grill.entity.CantBasic;
import com.gez.grill.entity.ViewsAndVisits;
import com.gez.grill.service.LiulfwService;

@Component
public class CacheViewsAndVisits {

	private final static Logger logger = Logger.getLogger(CantController.class);

	private final static int limit = 30;

	private final static int frequence = 3000;

	private final static int halfday = 1000 * 60 * 60 * 12;

	@Autowired
	private LiulfwService liulfwService;

	/* 餐厅浏览量 */
	private ConcurrentHashMap<String, ViewsAndVisits> cantlll = new ConcurrentHashMap<String, ViewsAndVisits>();

	/* 餐厅访问量 */
	private ConcurrentHashMap<String, ViewsAndVisits> cantfwl = new ConcurrentHashMap<String, ViewsAndVisits>();

	/* 菜品浏览量 */
	private ConcurrentHashMap<String, ViewsAndVisits> caiplll = new ConcurrentHashMap<String, ViewsAndVisits>();

	/* 菜品访问量 */
	private ConcurrentHashMap<String, ViewsAndVisits> caipfwl = new ConcurrentHashMap<String, ViewsAndVisits>();

	/* 餐厅浏览量队列 */
	private ConcurrentLinkedQueue<ViewsAndVisits> workLineForRestView = new ConcurrentLinkedQueue<ViewsAndVisits>();

	/* 餐厅访问量队列 */
	private ConcurrentLinkedQueue<ViewsAndVisits> workLineForRestVisit = new ConcurrentLinkedQueue<ViewsAndVisits>();

	/* 菜品浏览量队列 */
	private ConcurrentLinkedQueue<ViewsAndVisits> workLineForDishView = new ConcurrentLinkedQueue<ViewsAndVisits>();

	/* 菜品访问量队列 */
	private ConcurrentLinkedQueue<ViewsAndVisits> workLineForDishVisit = new ConcurrentLinkedQueue<ViewsAndVisits>();

	public void addRestaurantView(ArrayList<CantBasic> cantList) {
		int tesCount = cantList.size();

		for (int i = 0; i < tesCount; i++) {
			String cantId = cantList.get(i).getId();

			if (cantlll.containsKey(cantId)) {
				ViewsAndVisits record = cantlll.get(cantId);
				record.setVav(record.getVav() + 1);

				if (record.getVav() > limit) {
					workLineForRestView.add(record);

					cantlll.remove(cantId);
				} else {
					cantlll.put(cantId, record);
				}
			} else {
				ViewsAndVisits newCommer = new ViewsAndVisits();
				newCommer.setId(cantId);
				newCommer.setVav(1);

				cantlll.put(cantId, newCommer);
			}
		}
	}

	public void addRestaurantVisit(String cantId) {
		if (cantfwl.containsKey(cantId)) {
			ViewsAndVisits record = cantfwl.get(cantId);
			record.setVav(record.getVav() + 1);

			if (record.getVav() > limit) {
				workLineForRestVisit.add(record);

				cantfwl.remove(cantId);
			} else {
				cantfwl.put(cantId, record);
			}
		} else {
			ViewsAndVisits newCommer = new ViewsAndVisits();
			newCommer.setId(cantId);
			newCommer.setVav(1);

			cantfwl.put(cantId, newCommer);
		}
	}

	public void addDishView(ArrayList<CaipBasic> caipList) {
		int tesCount = caipList.size();

		for (int j = 0; j < tesCount; j++) {
			String caipId = caipList.get(j).getId();

			if (caiplll.containsKey(caipId)) {
				ViewsAndVisits record = caiplll.get(caipId);
				record.setVav(record.getVav() + 1);

				if (record.getVav() > limit) {
					workLineForDishView.add(record);

					caiplll.remove(caipId);
				} else {
					caiplll.put(caipId, record);
				}
			} else {
				ViewsAndVisits newCommer = new ViewsAndVisits();
				newCommer.setId(caipId);
				newCommer.setVav(1);

				caiplll.put(caipId, newCommer);
			}
		}
	}

	public void addDishVisit(String caipId) {
		if (caipfwl.containsKey(caipId)) {
			ViewsAndVisits record = caipfwl.get(caipId);
			record.setVav(record.getVav() + 1);

			if (record.getVav() > limit) {
				workLineForDishVisit.add(record);

				caipfwl.remove(caipId);
			} else {
				caipfwl.put(caipId, record);
			}
		} else {
			ViewsAndVisits newCommer = new ViewsAndVisits();
			newCommer.setId(caipId);
			newCommer.setVav(1);

			caipfwl.put(caipId, newCommer);
		}
	}

	public void clearThePool() {
		String key = "";

		Set<String> keySet = cantlll.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			key = iter.next();

			workLineForRestView.add(cantlll.get(key));
			cantlll.remove(key);
		}

		keySet = cantfwl.keySet();
		iter = keySet.iterator();
		while (iter.hasNext()) {
			key = iter.next();

			workLineForRestVisit.add(cantfwl.get(key));
			cantfwl.remove(key);
		}

		keySet = caiplll.keySet();
		iter = keySet.iterator();
		while (iter.hasNext()) {
			key = iter.next();

			workLineForDishView.add(caiplll.get(key));
			caiplll.remove(key);
		}

		keySet = caipfwl.keySet();
		iter = keySet.iterator();
		while (iter.hasNext()) {
			key = iter.next();

			workLineForDishVisit.add(caipfwl.get(key));
			caipfwl.remove(key);
		}
	}

	@PostConstruct
	public void init() {
		new Thread(new Observer()).start();

		new Thread(new clearOnTime()).start();
	}

	class Observer implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					while (!workLineForRestView.isEmpty()) {
						liulfwService.updateRestaurantView(workLineForRestView.poll());
					}

					while (!workLineForRestVisit.isEmpty()) {
						liulfwService.updateRestaurantVisit(workLineForRestVisit.poll());
					}

					while (!workLineForDishView.isEmpty()) {
						liulfwService.updateDishView(workLineForDishView.poll());
					}

					while (!workLineForDishVisit.isEmpty()) {
						liulfwService.updateDishVisit(workLineForDishVisit.poll());
					}

					Thread.sleep(frequence);
				}
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
	}

	class clearOnTime implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					clearThePool();

					Thread.sleep(halfday);
				}
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
	}
}