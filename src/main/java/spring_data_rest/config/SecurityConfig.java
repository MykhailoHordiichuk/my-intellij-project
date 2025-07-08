package spring_data_rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // Класс конфигурации Spring Security
public class SecurityConfig {

    @Bean // Бин, настраивающий цепочку безопасности
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Настройка правил авторизации запросов
                .authorizeHttpRequests(auth -> auth
                        // Swagger UI и OpenAPI эндпоинты разрешаем без аутентификации
                        .requestMatchers(
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                new AntPathRequestMatcher("/swagger-ui.html")
                        ).permitAll()
                        // Эндпоинты Actuator также разрешаем без аутентификации (если нужно)
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                        // Все запросы на /api/** требуют аутентификации
                        .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated()
                        // Остальные запросы разрешены всем
                        .anyRequest().permitAll()
                )
                // Включаем HTTP Basic аутентификацию (логин/пароль через браузер)
                .httpBasic(Customizer.withDefaults())
                // Отключаем CSRF (обычно для REST API сессии не нужны)
                .csrf(csrf -> csrf.disable());

        // Возвращаем настроенную цепочку фильтров
        return http.build();
    }
}