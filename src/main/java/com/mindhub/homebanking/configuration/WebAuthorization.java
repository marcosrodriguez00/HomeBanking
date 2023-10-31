package com.mindhub.homebanking.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Habilita la configuracion de seguridad de la aplicación y nos permite personalizarlo
@EnableWebSecurity
@Configuration
class WebAuthorization extends WebSecurityConfigurerAdapter { // depricated

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // primero los permisos genericos (permitAll())
                .antMatchers("/index.html", "/Web/login.html", "/Web/register.html").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                // permisos de admin
                .antMatchers("/rest/**", "/api/clients").hasAuthority("ADMIN")
                // permisos para el cliente
                .antMatchers(HttpMethod.GET, "/api/clients/currents").authenticated()
                .antMatchers(HttpMethod.GET, "/api/accounts/{id}").authenticated()
                .antMatchers(HttpMethod.POST, "/api/clients/current/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/transactions").authenticated()
                .antMatchers("/web/accounts.html", "/web/account.html", "/web/cards.html",
                        "/web/create-card.html", "/web/transfers.html").authenticated()
                //.antMatchers("/**").hasAuthority("ADMIN")
                .anyRequest().denyAll();


        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("pwd")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        // turn off checking for CSRF tokens
        http.csrf().disable();

        // frameOptions no permite a terceros ingresar codigo a la vista y al deshabilitarlo estamos permitiendo el uso de h2
        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // limpia los intentos fallidos
        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    // funcion que se utiliza para limpiar los atributos de la sesion
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        // verifica que no haya una sesion activa
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Si hay una sesión, esta línea elimina el atributo denominado
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}