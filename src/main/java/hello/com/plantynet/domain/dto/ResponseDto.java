package hello.com.plantynet.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {
	private String result;
	private String message;
	private String token;
}
