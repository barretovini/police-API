package com.gft.crime.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gft.crime.api.filter.FiltroAutenticacao;
import com.gft.crime.api.services.AutenticacaoService;
import com.gft.crime.api.services.UsuarioService;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	
		super.configure(web);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
			http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth").permitAll()
			
			/*
			.antMatchers(HttpMethod.GET, "/starters").permitAll()
			.antMatchers(HttpMethod.POST, "/starters").permitAll()
			.antMatchers(HttpMethod.PUT, "/starters").permitAll()
			.antMatchers(HttpMethod.DELETE, "/starters").permitAll()
			
			.antMatchers(HttpMethod.GET, "/desafios").permitAll()
			.antMatchers(HttpMethod.POST, "/desafios").permitAll()
			.antMatchers(HttpMethod.PUT, "/desafios").permitAll()
			.antMatchers(HttpMethod.DELETE, "/desafios").permitAll()
			
			.antMatchers(HttpMethod.GET, "/entrega").permitAll()
			.antMatchers(HttpMethod.POST, "/entrega").permitAll()
			.antMatchers(HttpMethod.PUT, "/entrega").permitAll()
			.antMatchers(HttpMethod.DELETE, "/entrega").permitAll()
			
			.antMatchers(HttpMethod.GET, "/notas").permitAll()
			.antMatchers(HttpMethod.POST, "/notas").permitAll()
			.antMatchers(HttpMethod.PUT, "/notas").permitAll()
			.antMatchers(HttpMethod.DELETE, "/notas").permitAll()
			*/
			.anyRequest().authenticated()
			.and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilterBefore(new FiltroAutenticacao(autenticacaoService, usuarioService),UsernamePasswordAuthenticationFilter.class);
	}
}
