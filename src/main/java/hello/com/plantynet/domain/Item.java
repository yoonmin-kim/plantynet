package hello.com.plantynet.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Item {

	private Long id;
	private String itemName;
	private Integer price;
	private Integer quantity;

	private Boolean open; //판매 여부
	private List<String> regions; //등록 지역
	private ItemType itemType; //상품 종류
	private String deliveryCode; //배송 방식

	public void update(Item updateItem) {
		this.itemName = updateItem.getItemName();
		this.price = updateItem.getPrice();
		this.quantity = updateItem.getQuantity();
		this.open = updateItem.getOpen();
		this.regions = updateItem.getRegions();
		this.itemType = updateItem.getItemType();
		this.deliveryCode = updateItem.getDeliveryCode();
	}
}
