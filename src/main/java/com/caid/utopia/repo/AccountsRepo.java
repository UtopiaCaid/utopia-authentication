package com.caid.utopia.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.caid.utopia.entity.Accounts;


@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Integer>{
	
	@Query(" FROM Accounts where username =:username")
	public List<Accounts> readAccountsByUserName(@Param("username") String username);
	
	@Query(" FROM Accounts where username =:username")
	public List<Accounts> getAccountByExactUserName(@Param("username") String username);

}