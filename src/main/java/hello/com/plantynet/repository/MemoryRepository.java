package hello.com.plantynet.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import hello.com.plantynet.domain.Item;

@Repository
public class MemoryRepository implements ItemRepository {

	private static final Map<Long, Item> store = new HashMap<>();
	private static long sequence = 1L;

	public Long save(Item item) {
		item.setId(sequence++);
		store.put(item.getId(), item);
		return item.getId();
	}

	public Item findById(Long id) {
		return store.get(id);
	}

	public List<Item> findAll() {
		return new ArrayList<>(store.values());
	}

	public int deleteById(Long id) {
		store.remove(id);
		return 1;
	}

	public int update(Long id, Item updateItem) {
		Item findItem = store.get(id);
		findItem.update(updateItem);
		return 1;
	}
}
