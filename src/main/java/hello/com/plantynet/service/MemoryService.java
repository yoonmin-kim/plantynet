package hello.com.plantynet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import hello.com.plantynet.domain.Item;
import hello.com.plantynet.repository.MemoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoryService {

	private final MemoryRepository memoryRepository;

	public void save(Item item) {
		memoryRepository.save(item);
	}

	public Item findById(Long id) {
		return memoryRepository.findById(id);
	}

	public List<Item> findAll() {
		return memoryRepository.findAll();
	}

	public void deleteById(Long id) {
		memoryRepository.deleteById(id);
	}

	public void update(Long id, Item updateItem) {
		memoryRepository.update(id, updateItem);
	}
}
