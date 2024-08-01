package com.rumor.yumback.domains.oauth2.application;

import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import com.rumor.yumback.domains.oauth2.dto.GoogleOauthResponse;
import com.rumor.yumback.domains.oauth2.dto.Oauth2Response;
import com.rumor.yumback.domains.oauth2.dto.Oauth2UserDto;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import com.rumor.yumback.enumeration.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final UserJpaRepository userJpaRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Oauth2Response oauth2Response = new GoogleOauthResponse(oAuth2User.getAttributes());
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String username = oauth2Response.getProvider() + oauth2Response.getProviderId();

        User user = userJpaRepository.findByUsername(username)
                .orElseGet(() -> createUser(username, oauth2Response));

        user.changeName(user.getName());

        Oauth2UserDto oauth2UserDto = new Oauth2UserDto(UserRole.ROLE_USER, user.getName(), user.getUsername(), user.getPicture());
        return new CustomOauth2User(oauth2UserDto);
    }

    private User createUser(String username, Oauth2Response oauth2Response) {
        User user = new User(username, oauth2Response.getName(), oauth2Response.getEmail(), oauth2Response.getPicture(),  UserRole.ROLE_USER);
        return userJpaRepository.save(user);
    }
}
