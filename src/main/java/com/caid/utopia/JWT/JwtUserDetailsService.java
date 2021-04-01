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
		List<Accounts> accounts = null;
		try {
			//should filter this down so only the one name gets passed
			 accounts= getAccountByName(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Accounts account = null;
		if(accounts!=null)
			account=accounts.get(0);
	if(account !=null) {
		return new User(account.getUsername(), account.getPassword(),
				new ArrayList<>());
	}
//	else if ("javainuse".equals(username)) {
//			return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
//					new ArrayList<>());
//		} else if("user1".equals(username)) {
//			return new User("user1", "$2a$10$KJivYEkQbd8ZHt/jkCiBeeuo9cU84PiK/Peq/tXdp5sp5AtaTvpQa", 
//					new ArrayList<>());
//		}
		else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
	
	public List<Accounts> getAccountByName(@RequestBody String username) throws SQLException { 
		
		List<Accounts> accounts = new ArrayList<>();
		accounts = accountsRepo.readAccountsByUserName(username);
		return accounts;
	}
}
