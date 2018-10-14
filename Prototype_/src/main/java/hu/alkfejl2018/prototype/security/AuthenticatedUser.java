package hu.alkfejl2018.prototype.security;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import hu.alkfejl2018.prototype.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RequestScope
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {
    
	private User user;
}