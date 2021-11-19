package hello.com.plantynet.domain.dto;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import hello.com.plantynet.domain.ItemType;
import lombok.Data;

@Data
public class ItemDto {

	private Long id;

	@NotBlank(message = "상품명은 필수로 입력해야합니다")
	private String itemName;

	@NotNull(message = "가격은 필수로 입력해야합니다")
	@Min(value = 500, message = "가격은 최소 500원까지만 설정할 수 있습니다")
	private Integer price;

	@NotNull(message = "수량은 필수로 입력해야합니다")
	@Max(value = 1000, message = "수량은 최대 1000을 넘어갈 수 없습니다")
	private Integer quantity;

	private Boolean open; //판매 여부
	private List<String> regions; //등록 지역
	private ItemType itemType; //상품 종류
	private String deliveryCode; //배송 방식

	public boolean isGlobalError() {
		if (getPrice() == null || getQuantity() == null) {
			return true;
		}

		if (!(getPrice() * getQuantity() >= 500 * 500)) {
			return true;
		}

		return false;
	}
}
