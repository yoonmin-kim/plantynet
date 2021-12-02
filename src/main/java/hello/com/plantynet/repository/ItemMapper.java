package hello.com.plantynet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import hello.com.plantynet.domain.Item;

@Mapper
public interface ItemMapper {

	Long save(Item item);

	Item findById(@Param("id") Long id);

	List<Item> findAll();

	int deleteById(@Param("id") Long id);

	int update(Item updateItem);
}
