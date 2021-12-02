package hello.com.plantynet.init;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import hello.com.plantynet.domain.Item;
import hello.com.plantynet.domain.ItemType;
import hello.com.plantynet.domain.dto.ItemDto;
import hello.com.plantynet.service.ItemServiceImpl;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitData {

	private final ItemServiceImpl itemService;
	private final ModelMapper modelMapper;

	/**
	 * 시연용 초기 데이터
	 */
	// @PostConstruct
	public void init() {
		ItemDto item = new ItemDto();
		item.setItemName("이불");
		item.setPrice(10000);
		item.setQuantity(100);
		item.setDeliveryCode("NORMAL");
		item.setOpen(true);
		item.setRegions(Arrays.asList("JEJU", "SEOUL"));
		item.setItemType(ItemType.ETC);
		itemService.save(modelMapper.map(item, Item.class));

		ItemDto item2 = new ItemDto();
		item2.setItemName("침대");
		item2.setPrice(900000);
		item2.setQuantity(50);
		item2.setDeliveryCode("SLOW");
		item2.setOpen(false);
		item2.setRegions(Arrays.asList("BUSAN"));
		item2.setItemType(ItemType.ETC);
		itemService.save(modelMapper.map(item2, Item.class));

		ItemDto item3 = new ItemDto();
		item3.setItemName("떡볶이");
		item3.setPrice(12000);
		item3.setQuantity(1000);
		item3.setDeliveryCode("FAST");
		item3.setOpen(true);
		item3.setRegions(Arrays.asList("JEJU", "SEOUL", "BUSAN"));
		item3.setItemType(ItemType.FOOD);
		itemService.save(modelMapper.map(item3, Item.class));

	}
}
