package hello.com.plantynet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.com.plantynet.domain.DeliveryCode;
import hello.com.plantynet.domain.Item;
import hello.com.plantynet.domain.dto.ItemDto;
import hello.com.plantynet.service.MemoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/jsp")
public class JspController {

	public static final String GLOBAL_ERROR_CODE = "required";
	public static final String DEFAULT_GLOBAL_MESSAGE = "필수값을 확인해주세요";
	private final MemoryService memoryService;

	@ModelAttribute("deliveryCodes")
	public List<DeliveryCode> deliveryCodes() {
		List<DeliveryCode> deliveryCodes = new ArrayList<>();
		deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
		deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
		deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
		return deliveryCodes;
	}

	@ModelAttribute("regions")
	public Map<String, String> regions() {
		Map<String, String> regions = new HashMap<>();
		regions.put("JEJU", "제주도");
		regions.put("SEOUL", "서울");
		regions.put("BUSAN", "부산");
		return regions;
	}

	@GetMapping
	public String index() {
		return "index";
	}

	@GetMapping("/item")
	public String addForm(Model model) {
		model.addAttribute("item", new Item());
		return "addForm";
	}

	@PostMapping("/item")
	public String save(@Validated @ModelAttribute("item") ItemDto itemDto, BindingResult bindingResult) {
		if (itemDto.isGlobalError()) {
			bindingResult.reject(GLOBAL_ERROR_CODE, DEFAULT_GLOBAL_MESSAGE);
		}
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "addForm";
		}
		memoryService.save(itemDto);
		return index();
	}

	@GetMapping("/list")
	public String list(Model model) {
		List<Item> items = memoryService.findAll();
		model.addAttribute("items", items);
		return "list";
	}

	@GetMapping("/editList")
	public String editList(Model model) {
		List<Item> items = memoryService.findAll();
		model.addAttribute("items", items);
		return "editList";
	}

	@GetMapping("/editForm/{itemId}")
	public String editForm(@PathVariable Long itemId, Model model) {
		Item findItem = memoryService.findById(itemId);
		model.addAttribute("item", findItem);
		return "editForm";
	}

	@PostMapping("/editForm/{itemId}")
	public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemDto itemDto,
		BindingResult bindingResult) {

		if (itemDto.isGlobalError()) {
			bindingResult.reject(GLOBAL_ERROR_CODE, DEFAULT_GLOBAL_MESSAGE);
		}
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "editForm";
		}
		memoryService.update(itemId, itemDto);
		return "redirect:/jsp/editList";
	}

	@GetMapping("/deleteList")
	public String deleteList(Model model) {
		List<Item> items = memoryService.findAll();
		model.addAttribute("items", items);
		return "deleteList";
	}

	@GetMapping("/deleteList/{itemId}")
	public String delete(@PathVariable Long itemId) {
		memoryService.deleteById(itemId);
		return "redirect:/jsp/deleteList";
	}
}
