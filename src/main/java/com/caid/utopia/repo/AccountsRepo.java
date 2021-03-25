package com.caid.utopia.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caid.utopia.entity.Accounts;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Integer>{

}