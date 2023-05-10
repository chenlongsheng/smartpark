/**
 * 
 */
package com.jeeplus.modules.smartpark.utilsWeiXin;

import lombok.Data;

@Data
public class AccessToken {
    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String openid;
    private String scope;
} 