package hello.com.plantynet.service;

import java.util.List;

import hello.com.plantynet.domain.Item;

public interface ItemService {

	Long save(Item item);

	Item findById(Long id);

	List<Item> findAll();

	int deleteById(Long id);

	int update(Long id, Item item);
}
