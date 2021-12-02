package hello.com.plantynet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.com.plantynet.domain.DeliveryCode;
import hello.com.plantynet.domain.Item;
import hello.com.plantynet.domain.ItemType;
import hello.com.plantynet.domain.dto.ItemDto;
import hello.com.plantynet.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReactController {

	public static final String GLOBAL_ERROR_CODE = "required";
	public static final String DEFAULT_GLOBAL_MESSAGE = "가격 * 수량 을 250,000 이상 맞춰주세요";
	private final ItemService itemService;
	private final ModelMapper modelMapper;

	@GetMapping("/deliveryCodes")
	public List<DeliveryCode> deliveryCodes() {
		List<DeliveryCode> deliveryCodes = new ArrayList<>();
		deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
		deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
		deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
		return deliveryCodes;
	}

	@GetMapping("/regions")
	public Map<String, String> regions() {
		Map<String, String> regions = new HashMap<>();
		regions.put("JEJU", "제주도");
		regions.put("SEOUL", "서울");
		regions.put("BUSAN", "부산");
		return regions;
	}

	@GetMapping("/itemTypes")
	public Map<ItemType, String> itemType() {
		Map<ItemType, String> itemTypes = new HashMap<>();
		for (ItemType value : ItemType.values()) {
			itemTypes.put(value, value.getDescription());
		}
		return itemTypes;
	}

	@PostMapping("/item")
	public Object save(@Validated @RequestBody ItemDto itemDto, BindingResult bindingResult) {
		if (itemDto.isGlobalError()) {
			bindingResult.reject(GLOBAL_ERROR_CODE, DEFAULT_GLOBAL_MESSAGE);
		}
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return bindingResult.getAllErrors();
		}

		return new ResponseEntity(itemService.save(modelMapper.map(itemDto, Item.class)), HttpStatus.CREATED);
	}

	@GetMapping("/list")
	public List<Item> list() {
		return itemService.findAll();
	}

	@GetMapping("/editForm/{itemId}")
	public Item editForm(@PathVariable Long itemId) {
		Item findItem = itemService.findById(itemId);
		return findItem;
	}

	@PutMapping("/editForm")
	public Object edit(@Validated @RequestBody ItemDto itemDto,
		BindingResult bindingResult) {

		if (itemDto.isGlobalError()) {
			bindingResult.reject(GLOBAL_ERROR_CODE, DEFAULT_GLOBAL_MESSAGE);
		}
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return bindingResult.getAllErrors();
		}
		itemService.update(itemDto.getId(), modelMapper.map(itemDto, Item.class));
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@DeleteMapping("/deleteList/{itemId}")
	public ResponseEntity delete(@PathVariable Long itemId) {
		itemService.deleteById(itemId);
		List<Item> list = list();
		return new ResponseEntity(list, HttpStatus.OK);
	}
}
