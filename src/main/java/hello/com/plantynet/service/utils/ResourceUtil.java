package hello.com.plantynet.service.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hello.com.plantynet.domain.DeliveryCode;
import hello.com.plantynet.domain.ItemType;

@Service
public class ResourceUtil {

	public List<DeliveryCode> getDeliveryCodes() {
		List<DeliveryCode> deliveryCodes = new ArrayList<>();
		deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
		deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
		deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
		return deliveryCodes;
	}

	public Map<String, String> getRegions() {
		Map<String, String> regions = new HashMap<>();
		regions.put("JEJU", "제주도");
		regions.put("SEOUL", "서울");
		regions.put("BUSAN", "부산");
		return regions;
	}

	public Map<ItemType, String> getItemType() {
		Map<ItemType, String> itemTypes = new HashMap<>();
		for (ItemType value : ItemType.values()) {
			itemTypes.put(value, value.getDescription());
		}
		return itemTypes;
	}

	public Map<String, String> getRoles() {
		Map<String, String> regions = new LinkedHashMap<>();
		regions.put("USER", "ROLE_USER");
		regions.put("MANAGER", "ROLE_MANAGER");
		regions.put("ADMIN", "ROLE_ADMIN");
		return regions;
	}
}
