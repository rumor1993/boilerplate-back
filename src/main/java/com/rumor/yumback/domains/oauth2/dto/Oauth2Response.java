package com.rumor.yumback.domains.oauth2.dto;

public interface Oauth2Response {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
    String getPicture();
}
