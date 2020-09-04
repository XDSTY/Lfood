package com.xdsty.payservice.service;

import basecommon.exception.BusinessRuntimeException;
import com.xdsty.payclient.dto.PayDto;
import com.xdsty.payclient.service.PayTxService;
import com.xdsty.payservice.entity.UserAmount;
import com.xdsty.payservice.entity.UserPayFlow;
import com.xdsty.payservice.mapper.UserAmountMapper;
import com.xdsty.payservice.mapper.UserPayFlowMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 张富华
 */
@Service
@DubboService(version = "1.0")
public class PayTxServiceImpl implements PayTxService {

    private UserAmountMapper userAmountMapper;

    private UserPayFlowMapper userPayFlowMapper;

    public PayTxServiceImpl(UserAmountMapper userAmountMapper, UserPayFlowMapper userPayFlowMapper) {
        this.userAmountMapper = userAmountMapper;
        this.userPayFlowMapper = userPayFlowMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pay(PayDto payDto) {
        if(payDto.getUserId() == null || payDto.getTotalAmount() == null) {
            throw new BusinessRuntimeException("支付人或者金额不能为空");
        }
        // 判断金额是否充足
        UserAmount amount = new UserAmount(payDto.getUserId(), payDto.getTotalAmount());
        if(userAmountMapper.judgeAmount(amount) == null) {
            throw new BusinessRuntimeException("支付人余额不足");
        }
        // 扣款并写入流水
        int count = userAmountMapper.deductAmount(amount);
        if(count < 1) {
            throw new BusinessRuntimeException("扣款失败");
        }

        UserPayFlow flow = new UserPayFlow();
        flow.setUserId(payDto.getUserId());
        flow.setPayType(payDto.getPayType());
        flow.setAmount(payDto.getTotalAmount());
        flow.setType(payDto.getType());
        count = userPayFlowMapper.insertOne(flow);
        if(count < 1) {
            throw new BusinessRuntimeException("支付失败");
        }
    }
}
