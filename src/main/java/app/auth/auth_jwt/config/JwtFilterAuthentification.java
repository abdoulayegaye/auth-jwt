package app.auth.auth_jwt.config;

import app.auth.auth_jwt.account.annotations.AuthAction;
import app.auth.auth_jwt.account.services.PermissionService;
import app.auth.auth_jwt.shared.dto.http.ResponseApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @apiNote Is called if account try to access on protected ressource
 * Get token on header
 * Test signature if ok continue to next filter
 * Test signature else  return Forbidden response
 */
@Component
@RequiredArgsConstructor
public class JwtFilterAuthentification extends OncePerRequestFilter {
    final JwtService jwtService;
    final UserDetailsService userDetailsService;
    final PermissionService permissionService;
    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String jwtToken= this.jwtService.getToken(request);
        if(jwtToken == null || !this.jwtService.isBearer(request)){
            filterChain.doFilter(request, response);
        }else {
            String username= this.jwtService.getUserName(jwtToken);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if(this.jwtService.isTokenValid(jwtToken,userDetails)){
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities()) ;
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    AuthAction authAction = this.permissionService.getAction(request);
                    if (authAction!= null){
                        if(! this.jwtService.can(authAction,username)){
                            String jsonErrorResponse = new ObjectMapper()
                                    .writeValueAsString(ResponseApi
                                            .builder()
                                            .error(true)
                                            .resultCode("403")
                                            .resultMessage("Vous essayez d'accéder à des ressources non autorisées")
                                            .build()
                                    );
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");
                            response.getWriter().write(jsonErrorResponse);
                            return;
                        }
                    }
                }
            }
            filterChain.doFilter(request,response);
        }
    }
}
