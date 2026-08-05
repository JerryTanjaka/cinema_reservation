package com.example.demo.endpoint.rest.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtTokenService jwtTokenService;

  public JwtAuthFilter(JwtTokenService jwtTokenService) {
    this.jwtTokenService = jwtTokenService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header != null && header.startsWith("Bearer ")) {
      try {
        Claims claims = jwtTokenService.parseToken(header.substring(7));
        List<String> roles = claims.get("roles", List.class);
        List<SimpleGrantedAuthority> authorities =
            roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (RuntimeException ignored) {
      }
    }
    filterChain.doFilter(request, response);
  }
}
