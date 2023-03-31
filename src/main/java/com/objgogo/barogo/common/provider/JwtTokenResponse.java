package com.objgogo.barogo.common.provider;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenResponse {

    @ApiModelProperty(name = "grant type" , example = "Bearer")
    private String grantType;

    @ApiModelProperty(name = "access token" , example = "sdfsdfsdfdsf")
    private String accessToken;

    @ApiModelProperty(name = "refresh token" , example = "dsffwsdfsd")
    private String refreshToken;
}
