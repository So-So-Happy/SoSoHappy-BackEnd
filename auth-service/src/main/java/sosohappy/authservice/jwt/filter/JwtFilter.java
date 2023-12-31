package sosohappy.authservice.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sosohappy.authservice.model.entity.User;
import sosohappy.authservice.jwt.service.JwtService;
import sosohappy.authservice.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private static String signIn = "/signIn";
    private static String getAuthorizeCode = "/getAuthorizeCode";
    private static String actuator = "/actuator";
    private static String oauth2 = "/oauth2";
    private static String reIssueToken = "/reIssueToken";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean isSignIn = request.getRequestURI().contains(signIn);
        boolean isActuator = request.getRequestURI().contains(actuator);
        boolean isOAuth2 = request.getRequestURI().contains(oauth2);
        boolean isReIssueToken = request.getRequestURI().contains(reIssueToken);
        boolean isGetAuthorizeCode = request.getRequestURI().contains(getAuthorizeCode);

        if (isSignIn || isActuator || isOAuth2 || isGetAuthorizeCode) {
            filterChain.doFilter(request, response);
            return;
        }

        if (isReIssueToken){
            System.out.println(
                    "email : " + request.getHeader("email") + "\n" +
                            "Authorization : " + request.getHeader("Authorization") + "\n" +
                            "Authorization-refresh : " + request.getHeader("Authorization-refresh")

            );

            String headerEmail = jwtService.extractHeaderEmail(request);

            String tokenEmail = jwtService.extractTokenEmail(jwtService.extractRefreshToken(request).orElse(null))
                    .orElse(null);

            String refreshToken = jwtService.extractRefreshToken(request)
                    .filter(token -> jwtService.isTokenValid(token, headerEmail))
                    .orElse(null);

            if (headerEmail.equals(tokenEmail) && refreshToken != null) {
                reIssueToken(response, refreshToken);
                return;
            }

            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        verifyAccessToken(request, response, filterChain);
    }

    public void reIssueToken(HttpServletResponse response, String refreshToken) {
        userRepository.findByRefreshToken(refreshToken)
                .ifPresentOrElse(user -> {
                            jwtService.setAccessTokenOnHeader(response, jwtService.createAccessToken(user.getEmail()));
                            jwtService.setRefreshTokenOnHeader(response, reIssuedRefreshToken(user));
                        },
                        () -> {
                            try {
                                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }

    public String reIssuedRefreshToken(User user) {
        String reIssuedRefreshToken = jwtService.createRefreshToken(user.getEmail());
        user.updateRefreshToken(reIssuedRefreshToken);
        userRepository.save(user);
        return reIssuedRefreshToken;
    }

    @SneakyThrows
    public void verifyAccessToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        Optional<User> user = jwtService.extractAccessToken(request)
                .filter(token -> jwtService.isTokenValid(token, jwtService.extractHeaderEmail(request)))
                .flatMap(jwtService::extractTokenEmail)
                .flatMap(userRepository::findByEmail);

        if(user.isEmpty()){
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(request, response);
    }

}
