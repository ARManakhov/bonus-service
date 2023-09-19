package dev.sirosh.bonus_service.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.sirosh.bonus_service.entity.User;
import dev.sirosh.bonus_service.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;


@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();

        Optional<DecodedJWT> maybeDecodedJwt = jwtManager.verify(token);
        if (maybeDecodedJwt.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        DecodedJWT decodedJWT = maybeDecodedJwt.get();
        String username = decodedJWT.getClaim("username").as(String.class);
        Optional<User> maybeUser = userRepository.findByUsername(username);
        if (maybeUser.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        JwtTokenAuthentication authentication = new JwtTokenAuthentication(maybeUser.get());
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
