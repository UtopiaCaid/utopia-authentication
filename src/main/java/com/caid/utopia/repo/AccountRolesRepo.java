package com.caid.utopia.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caid.utopia.entity.AccountRoles;

@Repository
public interface AccountRolesRepo extends JpaRepository<AccountRoles, Integer>{

}