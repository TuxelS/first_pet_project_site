package com.example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	    @Bean
	    UserDetailsService userDetailsService(PasswordEncoder encoder)
		{
			UserDetails admin = User.builder().username("admin1").roles("ADMIN").password(encoder.encode("admin1")).build();
			return new InMemoryUserDetailsManager(admin);
			
		}

	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
		{
			return http
						.csrf(AbstractHttpConfigurer::disable)
						.authorizeHttpRequests(
								auth->
									auth
										
										.requestMatchers("/**").permitAll())
					.httpBasic(Customizer.withDefaults())
					.build();
		}

	    @Bean
	    PasswordEncoder passwordEncoder()
		{
			return new BCryptPasswordEncoder();
		}
}
