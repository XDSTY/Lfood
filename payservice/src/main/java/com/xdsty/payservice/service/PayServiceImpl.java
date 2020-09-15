package com.xdsty.payservice.service;

import com.xdsty.payclient.dto.UserAmountDto;
import com.xdsty.payclient.re.UserAmountRe;
import com.xdsty.payclient.service.PayService;
import com.xdsty.payservice.entity.UserAmount;
import com.xdsty.payservice.mapper.UserAmountMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@DubboService(version = "1.0", timeout = 3000)
public class PayServiceImpl implements PayService {

    @Resource
    private UserAmountMapper userAmountMapper;

    @Override
    public UserAmountRe getUserAmount(UserAmountDto userAmountDto) {
        // 获取用户的余额
        UserAmount userAmount = userAmountMapper.getUserAmount(userAmountDto.getUserId());

        UserAmountRe re = new UserAmountRe();
        re.setAmount(userAmount.getAmount());
        return re;
    }
}
