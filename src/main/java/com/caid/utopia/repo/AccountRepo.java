package com.caid.utopia.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.caid.utopia.entity.Account;


@Repository
public interface AccountRepo extends JpaRepository<Account, Integer>{
	
	@Query(" FROM Account where username =:username")
	public List<Account> readAccountsByUserName(@Param("username") String username);
	
	@Query(" FROM Account where username =:username")
	public List<Account> getAccountByExactUserName(@Param("username") String username);

}