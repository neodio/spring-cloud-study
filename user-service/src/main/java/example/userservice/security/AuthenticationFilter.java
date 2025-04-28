package example.userservice.security;

import example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class AuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
        UserService userService, Environment environment) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
//        throws AuthenticationException {
//        try {
//
//            RequestLogin creds = new ObjectMapper().readValue(req.getInputStream(), RequestLogin.class);
//
//            return getAuthenticationManager().authenticate(
//                new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
//        Authentication auth) throws IOException, ServletException {
//
//        String userName = ((User) auth.getPrincipal()).getUsername();
//        UserDto userDetails = userService.getUserDetailsByEmail(userName);
//
//        byte[] secretKeyBytes = Base64.getEncoder().encode(environment.getProperty("token.secret").getBytes());
//
//        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
//
//        Instant now = Instant.now();
//
//        String token = Jwts.builder()
//            .subject(userDetails.getUserId())
//            .expiration(Date.from(now.plusMillis(Long.parseLong(environment.getProperty("token.expiration_time")))))
//            .issuedAt(Date.from(now))
//            .signWith(secretKey)
//            .compact();
//
//        res.addHeader("token", token);
//        res.addHeader("userId", userDetails.getUserId());
//    }
}
