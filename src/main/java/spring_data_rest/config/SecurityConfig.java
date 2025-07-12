package spring_data_rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration // Класс конфигурации Spring Security
public class SecurityConfig {

    @Bean // Бин, настраивающий цепочку безопасности
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // <--- Включаем CORS
                // Настройка правил авторизации запросов
                .authorizeHttpRequests(auth -> auth
                        // Swagger UI и OpenAPI эндпоинты разрешаем без аутентификации
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        // Эндпоинты Actuator также разрешаем без аутентификации (если нужно)
                        .requestMatchers("/actuator/**").permitAll()
                        // Разрешаем регистрацию и логин без авторизации
                        .requestMatchers("/api/auth/**").permitAll()
                        // Все остальные /api/** требуют аутентификации
                        .requestMatchers("/api/**").authenticated()
                        // Остальные запросы разрешены всем
                        .anyRequest().permitAll()
                )

                // Отключаем CSRF (обычно для REST API сессии не нужны)
                .csrf(csrf -> csrf.disable());

        // Возвращаем настроенную цепочку фильтров
        return http.build();
    }

    @Bean // Бин CORS-настроек: откуда можно слать запросы, с какими методами и заголовками
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500")); // Фронт локально
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Разрешённые методы
        config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Разрешённые заголовки
        config.setAllowCredentials(true); // если нужен cookie / авторизация

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // применить ко всем путям
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}