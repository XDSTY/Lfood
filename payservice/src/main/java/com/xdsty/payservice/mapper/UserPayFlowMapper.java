package com.xdsty.payservice.mapper;

import com.xdsty.payservice.entity.UserPayFlow;
import org.springframework.stereotype.Repository;

/**
 * @author 张富华
 */
@Repository
public interface UserPayFlowMapper {

    /**
     * 增加一条流水记录
     * @param userPayFlow
     * @return
     */
    int insertOne(UserPayFlow userPayFlow);

}
