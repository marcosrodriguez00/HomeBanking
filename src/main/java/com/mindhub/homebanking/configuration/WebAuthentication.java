package com.mindhub.homebanking.configuration;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


// le indica a spring que debe crear un objeto de este tipo cuando se está iniciando la aplicación
// para que cuando se configure el módulo de spring utilice ese objeto ya creado
// tiene efecto cuando la aplicacion se inicializa
@Configuration
// es el objeto que utiliza el Spring Security para saber cómo buscará los detalles del usuario.
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private ClientRepository clientRepository;

    // este metodo nos indica como se realiza la busqueda de los detalles del usuario
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        // configura detalladamente el logeo de nuestro usuario
        auth.userDetailsService(userNameEmail -> {
            Client client = clientRepository.findByEmail(userNameEmail);
            if (client != null) {
                // se genera un token (JSESSIONID) con la info de la sesion, este token va al navegador
                return new User(client.getEmail(), client.getPassword(),
                        (client.isAdmin()) ? AuthorityUtils.createAuthorityList("ADMIN")
                                : AuthorityUtils.createAuthorityList("CLIENT"));
            } else {
                throw new UsernameNotFoundException("Unknown user: " + userNameEmail);
            }
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}