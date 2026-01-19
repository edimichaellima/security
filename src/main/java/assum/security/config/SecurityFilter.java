package assum.security.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	private final TokenConfig tokenConfig;

	public SecurityFilter(TokenConfig tokenConfig) {
		this.tokenConfig = tokenConfig;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);

			Optional<JWTUserData> optUser = tokenConfig.validateToken(token);

			if (optUser.isPresent()) {
				JWTUserData userData = optUser.get();

				List<SimpleGrantedAuthority> authorities = userData.roles().stream().map(SimpleGrantedAuthority::new)
						.toList();

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userData,
						null, authorities);

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		filterChain.doFilter(request, response);

	}

}
