///////
package com.caid.utopia.JWT;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.caid.utopia.entity.Accounts;
import com.caid.utopia.repo.AccountsRepo;
import com.caid.utopia.springsecurity.controller.SecurityCntrl;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	AccountsRepo accountsRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//SecurityCntrl sc = new SecurityCntrl();
//		List<Accounts> accounts = null;
//		try {
//			//should filter this down so only the one name gets passed
//			 accounts= getAccountByName(username);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Accounts account = null;
//		if(accounts!=null)
//			account=accounts.get(0);
		Accounts account = null;
		try {
			 account= getAccountByExactName(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	if(account !=null) {
		return new User(account.getUsername(), account.getPassword(),
				new ArrayList<>());
	}
		else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
	
	public List<Accounts> getAccountByName(@RequestBody String username) throws SQLException { 
		
		List<Accounts> accounts = new ArrayList<>();
		accounts = accountsRepo.readAccountsByUserName(username);
		return accounts;
	}
	
	public Accounts getAccountByExactName(@RequestBody String username) throws Exception { 
		///add code so only one (the right one) name gets returned
		List<Accounts> accounts = new ArrayList<>();
		accounts = accountsRepo.getAccountByExactUserName(username);
		for(int i=0; i< accounts.size(); i++ ) {
			if(accounts.get(i).getUsername().equals(username))
				return accounts.get(i);
		}
		System.err.print("Something went wrong getting account");
		return null;
	}
}
