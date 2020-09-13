package com.xdsty.api.controller;

import com.xdsty.api.common.Constant;
import com.xdsty.api.common.Result;
import com.xdsty.api.common.ResultCode;
import com.xdsty.api.common.UserSession;
import com.xdsty.api.common.exceptions.TokenExpiredException;
import com.xdsty.api.config.annotation.PackageResult;
import com.xdsty.api.config.jwt.JwtTokenUtil;
import com.xdsty.api.controller.content.CityContent;
import com.xdsty.api.controller.content.TokenContent;
import com.xdsty.api.controller.content.UserLoginContent;
import com.xdsty.api.controller.content.user.UserCompanyAddressContent;
import com.xdsty.api.controller.param.TokenParam;
import com.xdsty.api.controller.param.UserLoginParam;
import com.xdsty.api.controller.param.UserRegisterParam;
import com.xdsty.api.entity.UserTokenInfo;
import com.xdsty.api.util.Base64Util;
import com.xdsty.api.util.RedisUtil;
import com.xdsty.api.util.RequestUtil;
import com.xdsty.api.util.SessionUtil;
import com.xdsty.userclient.dto.UserDetailDto;
import com.xdsty.userclient.dto.UserIdDto;
import com.xdsty.userclient.dto.UserLoginDto;
import com.xdsty.userclient.dto.UserRegisterDto;
import com.xdsty.userclient.re.UserCompanyInfoRe;
import com.xdsty.userclient.re.UserDetailRe;
import com.xdsty.userclient.re.UserLoginRe;
import com.xdsty.userclient.service.UserService;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
 * @author 张富华
 * @date 2020/6/16 14:05
 */
@PackageResult
@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @DubboReference(version = "1.0")
    private UserService userService;

    /**
     * 登录接口
     *
     * @param param 登录参数
     * @return 成功返回token
     */
    @PostMapping("login")
    public UserLoginContent login(@RequestBody @Valid UserLoginParam param, HttpServletResponse response) {
        UserLoginDto dto = new UserLoginDto();
        dto.setPhone(param.getPhone());
        dto.setPassword(param.getPassword());
        UserLoginRe re = userService.login(dto);

        Integer origin = Integer.valueOf(RequestUtil.getHeader(Constant.SOURCE_CODE));
        // 生成accessToken和refreshToken
        UserTokenInfo tokenInfo = new UserTokenInfo(re.getUserId(), origin);
        String accessToken = JwtTokenUtil.createAccessToken(tokenInfo);
        String refreshToken = JwtTokenUtil.createRefreshToken(tokenInfo);

        // token存入redis
        String key = Constant.REDIS_TOKEN_PREFIX + origin + "_" + re.getUserId();
        RedisUtil.delKey(key);
        RedisUtil.sset(key, Constant.REDIS_TOKEN_TTL, accessToken, refreshToken);
        log.error("access token :" + accessToken);

        // 用户信息存入redis
        String userKey = Constant.REDIS_USER_PREFIX + re.getUserId();
        UserDetailDto detailDto = new UserDetailDto();
        detailDto.setUserId(re.getUserId());
        UserDetailRe detailRe = userService.getUserDetail(detailDto);
        RedisUtil.set(userKey, convert2UserRedisInfo(detailRe), Constant.REDIS_USER_TTL);

        UserLoginContent content = new UserLoginContent();
        content.setAccessToken(accessToken);
        content.setRefreshToken(refreshToken);
        return content;
    }

    /**
     * 刷新用户的tokens
     *
     * @param param refreshToken
     * @return
     */
    @PostMapping("refreshToken")
    public Result refreshToken(@RequestBody @Valid TokenParam param) {
        UserSession userSession = SessionUtil.getUserSession();
        if (StringUtils.isEmpty(param.getAccessToken()) || StringUtils.isEmpty(param.getRefreshToken())) {
            throw new TokenExpiredException(userSession.getUserId() + " token为空，重新登录");
        }
        String atoken = param.getAccessToken().substring(7);
        Claims claims = JwtTokenUtil.parseToken(atoken);
        long userId = Long.parseLong(Base64Util.decode(claims.get("userId", String.class)));
        String origin = RequestUtil.getHeader(Constant.SOURCE_CODE);
        String tokenRedisKey = Constant.REDIS_TOKEN_PREFIX + origin + "_" + userId;
        // accessToken无效
        if (!RedisUtil.sexist(tokenRedisKey, param.getAccessToken())) {
            throw new TokenExpiredException(userId + "accessToken失效，重新登录");
        }
        Claims refreshTokenClaims = JwtTokenUtil.parseToken(param.getRefreshToken());
        // refreshToken过期或者失效
        if (refreshTokenClaims.getExpiration().before(new Date()) || !RedisUtil.sexist(tokenRedisKey, param.getRefreshToken())) {
            throw new TokenExpiredException(userId + "refreshToken失效，重新登录");
        }

        // 生成新的tokens
        UserTokenInfo tokenInfo = new UserTokenInfo(userId, Integer.valueOf(origin));
        TokenContent tokens = generateNewTokens(tokenInfo);
        //刷新redis中的token
        RedisUtil.delKey(tokenRedisKey);
        RedisUtil.sset(tokenRedisKey, Constant.REDIS_TOKEN_TTL, tokens.getAccessToken(), tokens.getRefreshToken());
        String userKey = Constant.REDIS_USER_PREFIX + userId;
        RedisUtil.expire(userKey, Constant.REDIS_USER_TTL);
        return Result.success(ResultCode.SIGNATURE_SUCCESS, tokens);
    }

    private TokenContent generateNewTokens(UserTokenInfo tokenInfo) {
        TokenContent tokens = new TokenContent();
        tokens.setAccessToken(JwtTokenUtil.createAccessToken(tokenInfo));
        tokens.setRefreshToken(JwtTokenUtil.createRefreshToken(tokenInfo));
        return tokens;
    }

    private UserSession convert2UserRedisInfo(UserDetailRe re) {
        UserSession info = new UserSession();
        info.setUserId(re.getUserId());
        info.setUsername(re.getUsername());
        info.setCityId(re.getCityId());
        info.setCityName(re.getCityName());
        info.setCompanyId(re.getCompanyId());
        info.setCompanyName(re.getCompanyName());
        info.setLinkPhone(re.getLinkPhone());
        return info;
    }

    /**
     * 注册接口
     *
     * @param param 用户信息
     */
    @PostMapping("register")
    public void register(@RequestBody @Valid UserRegisterParam param) {
        UserRegisterDto dto = new UserRegisterDto();
        dto.setUsername(param.getUsername());
        dto.setCompanyId(param.getCompanyId());
        dto.setLinkPhone(param.getPhone());
        dto.setPassword(param.getPassword());
        dto.setProfilePic(param.getProfilePic());
        userService.registerUser(dto);
    }

    /**
     * 获取用户当前公司所在城市
     *
     * @return
     */
    @GetMapping("city")
    public CityContent getUserCity() {
        UserSession userSession = SessionUtil.getUserSession();
        CityContent cityContent = new CityContent();
        cityContent.setCityId(userSession.getCityId());
        cityContent.setCityName(userSession.getCityName());
        return cityContent;
    }

    /**
     * 获取用户当前公司的地址等信息
     * @return
     */
    @GetMapping("companyInfo")
    public UserCompanyAddressContent getUserCompanyAddr() {
        UserSession session = SessionUtil.getUserSession();
        UserIdDto dto = new UserIdDto();
        dto.setUserId(session.getUserId());
        UserCompanyInfoRe re = userService.getUserCompanyInfo(dto);

        UserCompanyAddressContent content = new UserCompanyAddressContent();
        content.setCompanyAddr(re.getCompanyAddr());
        content.setCompanyId(re.getCompanyId());
        content.setCompanyName(re.getCompanyName());
        content.setPhone(session.getLinkPhone());
        content.setUsername(session.getUsername());
        return content;
    }

}