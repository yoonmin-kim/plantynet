package hello.com.plantynet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegionMapper {

	Long save(@Param("itemId") Long itemId, @Param("region") String region);

	List<String> findById(@Param("itemId") Long itemId);

	void deleteById(@Param("itemId") Long itemId);
}
