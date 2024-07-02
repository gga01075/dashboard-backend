package study_dashboard.dashboard.config.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import study_dashboard.dashboard.Dto.UserDto;
//import sumichan.sumichan.config.message.MessageComponent;

//import sumichan.sumichan.dto.client.MessageCodeAndResDto;
import javax.crypto.SecretKey;
/*
javax.crypto.spec.SecretKeySpec클래스는
Java의 암호화 API에서 사용되는 중요한 클래스입니다.

1. 비밀 키 생성: 바이트 배열로부터 비밀 키를 생성합니다.
2. 알고리즘 지정: 생성된 키와 함께 사용할 암호화 알고리즘을 지정합니다.
3. 키 재사용: 생성된 키 객체를 여러 암호화 또는 복호화 작업에 재사용할 수 있습니다.
4. 다양한 알고리즘 지원: AES, DES, TripleDES 등 다양한 대칭 키 알고리즘을 지원합니다.
5. javax.crypto.SecretKey 인터페이스 구현: 이 인터페이스를 구현하여 다른 암호화 관련 클래스와 호환됩니다.
*/
import javax.crypto.spec.SecretKeySpec;
/*
java.time.ZonedDateTime클래스는 날짜, 시간, 시간대 정보를 모두 포함하는 클래스입니다.
사용 목적: 특정 시간대의 날짜와 시간을 다룰 때 사용됩니다.
주요 특징:
시간대 정보 포함
불변 객체 (스레드 안전)
ISO-8601 캘린더 시스템 사용
 */
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Base64;
@Slf4j
@Component
public class JwtUtil {
    private final SecretKey key;
    private final long accessTokenExpTime;
    private final long refreshTokenExpTime;

    public JwtUtil(@Value("${jwt.private_secret_key}") String secretKey,
                   @Value("${jwt.expiration_time_ten_min}") long accessTokenExpTime,
                   @Value("${jwt.expiration_time_one_year}") long refreshTokenExpTime)
    {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        key = new SecretKeySpec(keyBytes, "HmacSHA256");
        this.accessTokenExpTime = accessTokenExpTime;
        this.refreshTokenExpTime = refreshTokenExpTime;
    }
    /**
     * Access Token 생성
     * @param userDto
     * @return Access Token String
     */
    public String createAccessToken(UserDto userDto)
    {
        return createToken(userDto, accessTokenExpTime);
    }


    public String createRefreshToken(UserDto userDto)
    {
        return createToken(userDto, refreshTokenExpTime);
    }


    /**
     * JWT 생성
     * @param userDto
     * @param expireTime
     * @return JWT String
     */
    private String createToken(UserDto userDto, long expireTime)
    {
        ZonedDateTime now = ZonedDateTime.now();  // 현재시간
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime); // 현재시간 + 만료시간
        return Jwts.builder() // JWT 빌더를 사용한 토큰 생성
                .signWith(key, SignatureAlgorithm.HS256) // key라는 비밀키와 HS256 알고리즘을 사용하여 토큰에 서명
                .setSubject(userDto.getUserID()) // 토큰의 주제를 사용자 ID로 설정
                .setExpiration(Date.from(tokenValidity.toInstant())) // 만료 시간 설정
                .setIssuedAt(Date.from(now.toInstant())) // 발행 시간 설정
                .compact(); // 토큰 생성 및 반환
    }
    /**
     * Token에서 User ID 추출
     * @param token
     * @return User ID
     */
    public String getUserId(String token)
    {
        return parseSub(token).get("sub", String.class);
    }
    /**
     * JWT 검증
     * @param token
     * @return IsValidate
     */
    public String validateToken(String token)
    {
        try {
            Jws<Claims> header = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
//            Date exp = header.getPayload().getExpiration();
//            Date iat = header.getPayload().getIssuedAt();
//            if (exp.compareTo(iat) == -1)
//            {
//
//            }
            System.out.println(Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token));
            return "성공";
        }
        catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e)
        {
            log.info("Invalid JWT Token", e);
            throw new RuntimeException("Invalid JWT Token");
        }
        catch (ExpiredJwtException e)
        {
            log.info("Expired JWT Token", e);
            throw new RuntimeException("Expired JWT Token");
        }
        catch (UnsupportedJwtException e)
        {
            log.info("Unsupported JWT Token", e);
            throw new RuntimeException("Unsupported JWT Token");
        }
        catch (IllegalArgumentException e)
        {
            log.info("JWT claims string is empty.", e);
            throw new RuntimeException("JWT claims string is empty.");
        }
    }
    /**
     * JWT Claims 추출
     * @param accessToken
     * @return JWT Claims
     */
    public Claims parseSub(String accessToken)
    {
        try {
            return Jwts.parser().
                    setSigningKey(key).
                    build().
                    parseClaimsJws(accessToken).
                    getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}