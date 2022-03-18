package todolist.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;
import todolist.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    private final String secret;

    public TokenService(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String generateToken(UserDTO userDTO) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(30)
                .atZone(ZoneId.systemDefault()).toInstant());

        String token = Jwts.builder()
                    .setId(id)
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(exp)
                    .claim("userid", userDTO.getId())
                    .claim("username", userDTO.getName())
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        return "Bearer " + token;
    }

    public UserDTO parseToken(String token) {

        Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

//        String username = jwsClaims.getBody()
//                .getSubject();
        String username = jwsClaims.getBody()
                .get("username", String.class);
        Integer userId = jwsClaims.getBody()
                .get("userid", Integer.class);

        return new UserDTO(userId, username);
    }

    public CsrfToken loadToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }
}
