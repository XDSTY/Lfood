package com.xdsty.orderservice.mapper;

import com.xdsty.orderservice.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserIntegralRecordMapperTest extends BaseTest {

    @Autowired
    private UserIntegralRecordMapper mapper;

    @Test
    void updateRecord() {

        mapper.updateRecord(1L, 1);

    }
}