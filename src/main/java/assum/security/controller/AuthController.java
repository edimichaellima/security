package assum.security.controller;

import org.springframework.security.core.Authentication;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import assum.security.config.TokenConfig;
import assum.security.dto.request.LoginRequest;
import assum.security.dto.request.RegisterUserRequest;
import assum.security.dto.response.LoginResponse;
import assum.security.dto.response.RegisterUserResponse;
import assum.security.entity.Role;
import assum.security.entity.User;
import assum.security.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")	
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final TokenConfig tokenConfig;
	
	public AuthController(UserRepository userRepository, 
			PasswordEncoder passwordEncoder, 
			AuthenticationManager authenticationManager, 
			TokenConfig tokenConfig) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.tokenConfig = tokenConfig;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
		
		UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
		Authentication authentication = authenticationManager.authenticate(userAndPass);
		
		User user = (User) authentication.getPrincipal();
		String token = null;
		try {
			token = TokenConfig.generateToken(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(new LoginResponse(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request){
		User newUser = new User();
		
		newUser.setPassword(passwordEncoder.encode(request.password()));
		newUser.setEmail(request.email());
		newUser.setName(request.name());
		
		if (request.role() != null) {
			newUser.setRoles(Set.of(request.role()));
		} else {
			newUser.setRoles(Set.of(Role.ROLE_USER));
		}
		
		userRepository.save(newUser);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponse(newUser.getName(), newUser.getEmail()));
	}
	
	
}
