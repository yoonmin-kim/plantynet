package hello.com.plantynet.repository;

import java.util.List;

import hello.com.plantynet.domain.Item;

public interface ItemRepository {

	Long save(Item item);

	Item findById(Long id);

	List<Item> findAll();

	int deleteById(Long id);

	int update(Long id, Item updateItem);
}
