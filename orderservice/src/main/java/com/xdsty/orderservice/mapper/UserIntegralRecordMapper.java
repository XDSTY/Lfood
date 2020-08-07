package com.xdsty.orderservice.mapper;

import org.springframework.stereotype.Repository;

/**
 * @author 张富华
 * @date 2020/8/7 17:51
 */
@Repository
public interface UserIntegralRecordMapper {

    long getLastRecord();

}
