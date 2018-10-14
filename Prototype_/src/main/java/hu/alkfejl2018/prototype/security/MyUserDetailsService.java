package hu.alkfejl2018.prototype.security;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.alkfejl2018.prototype.entities.User;
import hu.alkfejl2018.prototype.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private AuthenticatedUser authenticatedUser;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
    	Optional<User> oUser = userRepository.findByEmail(email);
        if (!oUser.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        
        User user = oUser.get();
        authenticatedUser.setUser(user);
        
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), grantedAuthorities);
    }
}
