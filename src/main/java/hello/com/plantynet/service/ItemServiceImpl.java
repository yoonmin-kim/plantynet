package hello.com.plantynet.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.com.plantynet.domain.Item;
import hello.com.plantynet.repository.ItemMapper;
import hello.com.plantynet.repository.RegionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

	// private final ItemRepository itemRepository;
	private final ItemMapper itemRepository;
	private final RegionMapper regionMapper;

	@Transactional
	public Long save(Item item) {
		Long save = itemRepository.save(item);
		if (item.getRegions() != null) {
			item.getRegions().forEach(region -> regionMapper.save(item.getId(), region));
		}
		return item.getId();
	}

	public Item findById(Long id) {
		List<String> regions = regionMapper.findById(id);
		Item findItem = itemRepository.findById(id);
		if (regions != null) {
			findItem.setRegions(regions);
		}
		return findItem;
	}

	public List<Item> findAll() {
		List<Item> itemList = itemRepository.findAll();
		itemList.forEach(item -> {
			List<String> regions = regionMapper.findById(item.getId());
			if (regions != null) {
				item.setRegions(regions);
			}
		});
		return itemList;
	}

	@Transactional
	public int deleteById(Long id) {
		regionMapper.deleteById(id);
		return itemRepository.deleteById(id);
	}

	@Transactional
	public int update(Long id, Item item) {
		item.setId(id);
		int result = itemRepository.update(item);
		regionMapper.deleteById(id);
		List<String> regions = item.getRegions();
		if (regions != null) {
			regions.forEach(region -> regionMapper.save(id, region));
		}
		return result;
	}
}
