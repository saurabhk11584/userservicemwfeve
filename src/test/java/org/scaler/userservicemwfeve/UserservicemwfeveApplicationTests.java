package org.scaler.userservicemwfeve;

import org.junit.jupiter.api.Test;
import org.scaler.userservicemwfeve.repositories.JpaRegisteredClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserservicemwfeveApplicationTests {
    @Autowired
    private JpaRegisteredClientRepository jpaRegisteredClientRepository;

    @Test
    void contextLoads() {
    }

//    @Test
//    @Commit
//    void storedRegisteredClientIntoDB() {
//                RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("oidc-client")
//                .clientSecret("{noop}secret")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("https://oauth.pstmn.io/v1/callback")
//                .postLogoutRedirectUri("https://oauth.pstmn.io/v1/callback")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .scope("ADMIN")
//                .scope("STUDENT")
//                .scope("MENTOR")
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//
//                jpaRegisteredClientRepository.save(oidcClient);
//    }

}
