package com.k8slearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.userdetails.ReactiveUserDetailsServiceResourceFactoryBean;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.k8slearning.service.impl.v1.UserServiceImpl;
import com.k8slearning.utils.Constants;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class K8sSecurityConfig {

	@Autowired
	private AuthEntryPoint authEntryPoint;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public K8sSecurityFilter secK8sSecurityFilter() {
		return new K8sSecurityFilter();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

		http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()).disable());
		http.cors(cors -> cors.disable());
		http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.headers(header -> header.frameOptions(frame -> frame.disable()));
		http.addFilterBefore(secK8sSecurityFilter(), UsernamePasswordAuthenticationFilter.class);
		http.authorizeHttpRequests(auth -> auth.requestMatchers(mvc.pattern(Constants.Api.WHILTELIST_SETUP_URL))
				.permitAll().requestMatchers(mvc.pattern(Constants.Api.WHITELIST_AUTH_URL)).permitAll()
				.requestMatchers(PathRequest.toH2Console()).permitAll().anyRequest().fullyAuthenticated());
		http.exceptionHandling(ex -> {
			ex.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(accessDeniedHandler());
		});
		return http.build();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandlerImpl();
	}

}
