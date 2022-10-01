package us.congressionalappchallenge.scheduler.service.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
class AuthFilter extends OncePerRequestFilter {
  private static final Log log = LogFactory.getLog(AuthFilter.class);

  private final FirebaseAuth firebaseAuth;

  public AuthFilter(FirebaseAuth firebaseAuth) {
    this.firebaseAuth = firebaseAuth;
  }

  @SneakyThrows
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
    String bearerToken = request.getHeader("Authorization");
    if (!Objects.isNull(bearerToken)) {
      String token = bearerToken.split("Bearer ")[1];
      FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(token);
      log.info("Extracted token with user id: " + firebaseToken.getUid());
      PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(firebaseToken, token);
      authentication.setAuthenticated(true);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }
}
