package hello.com.plantynet.controller;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.com.plantynet.domain.DeliveryCode;
import hello.com.plantynet.domain.Item;
import hello.com.plantynet.domain.dto.ItemDto;
import hello.com.plantynet.service.ItemService;
import hello.com.plantynet.service.utils.ResourceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/thymeleaf")
public class ThymeLeafController {

	public static final String GLOBAL_ERROR_CODE = "required";
	public static final String DEFAULT_GLOBAL_MESSAGE = "필수값을 확인해주세요";
	private final ItemService itemService;
	private final ModelMapper modelMapper;
	private final ResourceUtil resourceUtil;

	@ModelAttribute("deliveryCodes")
	public List<DeliveryCode> deliveryCodes() {
		return resourceUtil.getDeliveryCodes();
	}

	@ModelAttribute("regions")
	public Map<String, String> regions() {
		return resourceUtil.getRegions();
	}

	@GetMapping
	public String index(@RequestParam(required = false) boolean error, Model model) {
		if (error) {
			model.addAttribute("accessDenied", true);
		}
		return "thymeleaf/index";
	}

	@GetMapping("/item")
	public String addForm(Model model) {
		model.addAttribute("item", new Item());
		return "thymeleaf/addForm";
	}

	@PostMapping("/item")
	public String save(@Validated @ModelAttribute("item") ItemDto itemDto, BindingResult bindingResult) {
		if (itemDto.isGlobalError()) {
			bindingResult.reject(GLOBAL_ERROR_CODE, DEFAULT_GLOBAL_MESSAGE);
		}
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "thymeleaf/addForm";
		}
		itemService.save(modelMapper.map(itemDto, Item.class));
		return "thymeleaf/index";
	}

	@GetMapping("/list")
	public String list(Model model) {
		List<Item> items = itemService.findAll();
		model.addAttribute("items", items);
		return "thymeleaf/list";
	}

	@GetMapping("/editList")
	public String editList(Model model) {
		List<Item> items = itemService.findAll();
		model.addAttribute("items", items);
		return "thymeleaf/editList";
	}

	@GetMapping("/editForm/{itemId}")
	public String editForm(@PathVariable Long itemId, Model model) {
		Item findItem = itemService.findById(itemId);
		model.addAttribute("item", findItem);
		return "thymeleaf/editForm";
	}

	@PostMapping("/editForm/{itemId}")
	public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemDto itemDto,
		BindingResult bindingResult) {

		if (itemDto.isGlobalError()) {
			bindingResult.reject(GLOBAL_ERROR_CODE, DEFAULT_GLOBAL_MESSAGE);
		}
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "thymeleaf/editForm";
		}
		itemService.update(itemId, modelMapper.map(itemDto, Item.class));
		return "redirect:/thymeleaf/editList";
	}

	@GetMapping("/deleteList")
	public String deleteList(Model model) {
		List<Item> items = itemService.findAll();
		model.addAttribute("items", items);
		return "thymeleaf/deleteList";
	}

	@GetMapping("/deleteList/{itemId}")
	public String delete(@PathVariable Long itemId) {
		itemService.deleteById(itemId);
		return "redirect:/thymeleaf/deleteList";
	}
}
