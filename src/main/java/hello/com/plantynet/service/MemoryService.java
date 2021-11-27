package hello.com.plantynet.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hello.com.plantynet.domain.Item;
import hello.com.plantynet.domain.dto.ItemDto;
import hello.com.plantynet.repository.MemoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoryService {

	private final MemoryRepository memoryRepository;
	private final ModelMapper modelMapper;

	public Long save(ItemDto itemDto) {
		Item item = modelMapper.map(itemDto, Item.class);
		return memoryRepository.save(item);
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

	public void update(Long id, ItemDto itemDto) {
		Item item = modelMapper.map(itemDto, Item.class);
		memoryRepository.update(id, item);
	}
}
