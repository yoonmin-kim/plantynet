package hello.com.plantynet.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

import hello.com.plantynet.domain.Account;

@Mapper
public interface UserMapper {

	@Insert("insert into user(id,username,password,role) values(#{id},#{username},#{password},#{role})")
	@SelectKey(statement = "SELECT nextval('user') FROM DUAL", keyProperty = "id", before = true, resultType = Long.class)
	int save(Account account);
}
