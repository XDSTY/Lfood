# Lfood
Lfood（Love Food）是一个面向企业的分布式点餐系统，主要用于企业的团餐定制。
此项目为后台服务，主要包括订单，商品，用户，支付以及订单回滚服务。
使用springboot + dubbo + mybatis搭建，使用jwt构建登录模块，使用seata解决下单和付款时的数据不一致问题。
使用redis zset构建简单的订单回滚模块。
