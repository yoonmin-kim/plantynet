package hello.com.plantynet.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import hello.com.plantynet.domain.Account;

@Mapper
public interface UserMapper {

	@Insert("insert into user(id,username,password,role) values(#{id},#{username},#{password},#{role})")
	@SelectKey(statement = "SELECT nextval('user') FROM DUAL", keyProperty = "id", before = true, resultType = Long.class)
	int save(Account account);

	@Select("select id,username,password,role from user where username = #{username}")
	Optional<Account> findByUsername(@Param("username") String username);
}
