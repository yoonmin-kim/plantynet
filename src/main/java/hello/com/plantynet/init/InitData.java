package hello.com.plantynet.init;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import hello.com.plantynet.domain.Item;
import hello.com.plantynet.domain.ItemType;
import hello.com.plantynet.service.MemoryService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitData {

	private final MemoryService memoryService;

	/**
	 * 시연용 초기 데이터
	 */
	@PostConstruct
	public void init() {
		Item item = new Item();
		item.setItemName("이불");
		item.setPrice(10000);
		item.setQuantity(100);
		item.setDeliveryCode("NORMAL");
		item.setOpen(true);
		item.setRegions(Arrays.asList("JEJU","SEOUL"));
		item.setItemType(ItemType.ETC);
		memoryService.save(item);

		Item item2 = new Item();
		item2.setItemName("침대");
		item2.setPrice(900000);
		item2.setQuantity(50);
		item2.setDeliveryCode("SLOW");
		item2.setOpen(true);
		item2.setRegions(Arrays.asList("BUSAN"));
		item2.setItemType(ItemType.ETC);
		memoryService.save(item2);

		Item item3 = new Item();
		item3.setItemName("떡볶이");
		item3.setPrice(12000);
		item3.setQuantity(1000);
		item3.setDeliveryCode("FAST");
		item3.setOpen(true);
		item3.setRegions(Arrays.asList("JEJU", "SEOUL", "BUSAN"));
		item3.setItemType(ItemType.FOOD);
		memoryService.save(item3);

	}
}
