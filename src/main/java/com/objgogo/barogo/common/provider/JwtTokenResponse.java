package com.objgogo.barogo.common.provider;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenResponse {

    @ApiModelProperty(value = "grant type" , example = "Bearer")
    private String grantType;

    @ApiModelProperty(value = "access token" , example = "sdfsdfsdfdsf")
    private String accessToken;

    @ApiModelProperty(value = "refresh token" , example = "dsffwsdfsd")
    private String refreshToken;
}
