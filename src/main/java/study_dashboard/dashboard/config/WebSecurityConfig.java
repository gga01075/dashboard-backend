package study_dashboard.dashboard.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration // 스프링 시큐리티를 활성화
@EnableWebSecurity   // 스프링 시큐리티를 활성화
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/member/**", "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
            "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/api/v1/auth/**",
            "/auth/**", "/users"
    };

    //  최신 Spring Security에서는 SecurityFilterChain 빈을 정의하여 보안을 구성
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http
                /*
                CSRF(Cross-Site Request Forgery)는 웹 애플리케이션의 보안 취약점 중 하나로,
                사용자가 의도하지 않은 요청을 통해 악의적인 행위가 수행되도록 만드는 공격 기법

                csrf(AbstractHttpConfigurer::disable)는 HttpSecurity 객체의 csrf 설정을 비활성화하는 메서드 참조를 전달하여,
                CSRF 보호 기능을 끈다는 의미입니다.

                REST API 서버의 경우, 세션 기반 인증이 아닌 토큰 기반 인증(JWT 등)을 사용하기 때문에 CSRF 보호가 필요하지 않습니다.
                */
                .csrf(AbstractHttpConfigurer::disable)
                /*
                    securityMatcher는 Spring Security 설정에서 특정 URL 패턴에 대한 보안 구성을 지정하는 데 사용.
                    "/**"는 모든 경로를 의미하는 와일드카드 패턴
                 */
                .securityMatcher("/**")
                /*
                    SessionCreationPolicy는 세션 관리 정책을 정의하는 데 사용됩니다. 주요 옵션은 다음과 같습니다:

                    ALWAYS: 항상 세션을 생성합니다.
                    IF_REQUIRED: 필요할 때만 세션을 생성합니다(기본 값).
                    NEVER: Spring Security가 세션을 생성하지 않지만, 이미 존재하는 세션을 사용할 수는 있습니다.
                    STATELESS: Spring Security가 세션을 생성하지 않으며, 기존 세션도 사용하지 않습니다. 이 옵션은 완전히 무상태(stateless) 방식의 인증을 의미합니다.

                   세션을 사용하지 않는 상태 없는(stateless) 인증 방식은 JWT를 사용할 때 적합합니다.

                   왜 STATELESS를 사용할까?
                    - 토큰 기반 인증: JWT와 같은 토큰 기반 인증 방식을 사용할 때는 서버가 클라이언트의 상태를 유지하지 않기 때문에 세션이 필요 없습니다. 각 요청에는 독립적인 인증 토큰이 포함되므로, 서버는 요청을 처리할 때마다 토큰을 검증합니다.
                    - 확장성: 무상태 아키텍처는 서버 간의 상태 공유가 필요 없기 때문에, 애플리케이션을 수평 확장(서버 추가)에 유리하게 만듭니다.
                    - 보안: 세션 하이재킹과 같은 세션 관련 보안 문제를 방지할 수 있습니다.
                 */
                .sessionManagement(sessionManagementConfigurer
                        -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 /*
                    REST API 서버의 경우, 보통 클라이언트 애플리케이션(예: SPA, 모바일 앱)에서 인증을 처리하고, 서버는 JSON 형식의 인증 요청을 처리합니다.
                    이때 HTML 폼을 사용하지 않으므로 폼 기반 로그인이 불필요
                  */
                .formLogin(AbstractHttpConfigurer::disable)
                /*
                    authorizeHttpRequests(): HTTP 요청에 대한 접근 제어를 설정합니다.
                    AUTH_WHITELIST 엔드포인트는 모든 사용자에게 허용됩니다.
                    그 외의 모든 요청은 인증이 필요합니다.
                    */
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                );



        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}