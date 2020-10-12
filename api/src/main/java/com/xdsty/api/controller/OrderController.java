package com.xdsty.api.controller;

import basecommon.exception.BusinessRuntimeException;
import basecommon.util.PageUtil;
import com.google.common.collect.Lists;
import com.xdsty.api.config.annotation.PackageResult;
import com.xdsty.api.config.nacos.ConfigCenter;
import com.xdsty.api.config.nacos.ConfigKeyEnum;
import com.xdsty.api.controller.content.order.OrderListInfoContent;
import com.xdsty.api.controller.content.order.OrderListProductContent;
import com.xdsty.api.controller.content.order.OrderModuleContent;
import com.xdsty.api.controller.content.order.OrderPayPageContent;
import com.xdsty.api.controller.content.order.OrderPlaceContent;
import com.xdsty.api.controller.content.order.PayOrderContent;
import com.xdsty.api.controller.entity.OrderModule;
import com.xdsty.api.controller.param.order.OrderAddParam;
import com.xdsty.api.controller.param.order.OrderListQueryParam;
import com.xdsty.api.controller.param.order.OrderPayPageParam;
import com.xdsty.api.controller.param.order.OrderProductAdditionalParam;
import com.xdsty.api.controller.param.order.OrderProductParam;
import com.xdsty.api.controller.param.order.PayOrderParam;
import com.xdsty.api.service.IntegralCalculateService;
import com.xdsty.api.service.OrderCheckService;
import com.xdsty.api.util.JsonUtil;
import com.xdsty.api.util.SessionUtil;
import com.xdsty.orderclient.dto.*;
import com.xdsty.orderclient.enums.OrderStatusEnum;
import com.xdsty.orderclient.re.OrderListProductRe;
import com.xdsty.orderclient.re.OrderListRe;
import com.xdsty.orderclient.re.OrderModuleRe;
import com.xdsty.orderclient.re.OrderPayPageRe;
import com.xdsty.orderclient.re.OrderValidRe;
import com.xdsty.orderclient.service.OrderService;
import com.xdsty.productclient.dto.AdditionalListDto;
import com.xdsty.productclient.re.AdditionalItemRe;
import com.xdsty.productclient.re.OrderProductRe;
import com.xdsty.productclient.service.ProductService;
import com.xdsty.txclient.dto.PayOrderDto;
import com.xdsty.txclient.service.OrderTransactionService;
import com.xdsty.txclient.service.PayOrderTransactionService;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@PackageResult
@RequestMapping("order")
@RestController
public class OrderController {

    @DubboReference(version = "1.0", retries = 0)
    private OrderTransactionService orderTransactionService;

    @DubboReference(version = "1.0", retries = 0)
    private OrderService orderService;

    @DubboReference(version = "1.0", retries = 0)
    private PayOrderTransactionService payOrderTransactionService;

    @DubboReference(version = "1.0", retries = 0)
    private ProductService productService;

    @Resource
    private IntegralCalculateService integralCalculateService;

    @Resource
    private OrderCheckService orderCheckService;

    /**
     * 支付页
     */
    private static final Integer PAY_PAGE = 1;

    /**
     * 首页
     */
    private static final Integer HOME_PAGE = 2;

    @PostMapping("placeOrder")
    public OrderPlaceContent placeOrder(@RequestBody OrderAddParam param) {
        Long userId = SessionUtil.getUserId();
        // 校验商品是否有效和是价格否变化
        orderCheckService.checkOrderProductValid(param);

        // 添加订单
        OrderAddDto dto = convert2OrderAddDto(param);
        dto.setUserId(userId);
        Long orderId = orderTransactionService.placeOrder(dto);

        OrderPlaceContent content = new OrderPlaceContent();
        content.setOrderId(orderId);
        return content;
    }

    /**
     * 订单支付页
     */
    @PostMapping("payOrderPage")
    public OrderPayPageContent payOrderPage(@RequestBody OrderPayPageParam param) {
        Long userId = SessionUtil.getUserId();

        OrderPayPageContent content = new OrderPayPageContent();

        // 获取待支付订单信息
        OrderIdDto dto = new OrderIdDto();
        dto.setUserId(userId);
        dto.setOrderId(param.getOrderId());
        OrderPayPageRe re = orderService.getOrderPayInfo(dto);
        // 该订单不是待支付状态或者订单的付款时间超过限制，跳转到首页
        if(!OrderStatusEnum.WAIT_PAY.getStatus().equals(re.getStatus())
         || new Date().after(re.getEndTime())) {
            content.setJumpTo(HOME_PAGE);
            return content;
        }
        // 待支付订单，设置待支付信息
        content.setJumpTo(PAY_PAGE);
        content.setCreateTime(re.getCreateTime());
        content.setCurrentTime(new Date());
        content.setEndTime(re.getEndTime());
        content.setShouldPayPrice(re.getShouldPayPrice());
        return content;
    }

    /**
     * 支付订单并添加积分
     * @param param
     */
    @PostMapping("payOrder")
    public PayOrderContent payOrder(@RequestBody PayOrderParam param) {
        if(param.getOrderId() == null || param.getTotalPrice() == null) {
            throw new BusinessRuntimeException("入参错误");
        }

        PayOrderContent content = new PayOrderContent();

        Long memberId = SessionUtil.getUserId();
        // 校验订单是否未付款且订单状态正常
        OrderValidDto validDto = new OrderValidDto();
        validDto.setOrderId(param.getOrderId());
        validDto.setTotalPrice(param.getTotalPrice());
        validDto.setUserId(memberId);
        OrderValidRe re = orderService.checkOrderValid(validDto);
        if(re.getCode() != null) {
            content.setSuccess(false);
            content.setMsg(re.getMsg());
            return content;
        }

        // 计算积分
        int integral = integralCalculateService.calculateIntegral(param.getTotalPrice());

        // 开启付款分布式事务
        PayOrderDto dto = new PayOrderDto();
        dto.setUserId(memberId);
        dto.setOrderId(param.getOrderId());
        dto.setTotalAmount(param.getTotalPrice());
        dto.setPayType(param.getPayType());
        dto.setPayChannel(param.getPayChannel());
        dto.setIntegral(integral);
        payOrderTransactionService.payOrder(dto);

        content.setSuccess(true);
        return content;
    }

    /**
     * 获取订单模块
     * @return
     */
    @GetMapping("modules")
    public List<OrderModuleContent> getUserOrderModule() {
        Long userId = SessionUtil.getUserId();
        List<OrderModuleContent> contents = new ArrayList<>();
        // 获取module
        List<OrderModule> modules = JsonUtil.parseArrJson(ConfigCenter.getConfigValue(ConfigKeyEnum.ORDER_MODULE_LIST.dataId), OrderModule.class);
        if(!CollectionUtils.isEmpty(modules)) {
            // 对module进行排序
            modules = modules.stream().sorted(Comparator.comparingInt(OrderModule::getModuleOrder)).collect(Collectors.toList());

            // 筛选出需要查询数量的module并获取对应的数量
            List<Integer> showNumModuleTypes = modules.stream().filter(OrderModule::isShowNum).map(OrderModule::getModuleType).collect(Collectors.toList());
            OrderModuleDto dto = new OrderModuleDto();
            dto.setUserId(userId);
            dto.setModuleType(showNumModuleTypes);
            List<OrderModuleRe> moduleNums = orderService.getOrderModules(dto);

            for(OrderModule orderModule : modules) {
                OrderModuleContent moduleContent = new OrderModuleContent();
                moduleContent.setStatus(orderModule.getStatus());
                moduleContent.setIcon(orderModule.getIcon());
                moduleContent.setModuleName(orderModule.getModuleName());
                moduleContent.setModuleType(orderModule.getModuleType());
                moduleContent.setShowNum(orderModule.isShowNum());
                if(moduleContent.isShowNum()) {
                    Integer num = moduleNums.stream().filter(e -> e.getStatus().equals(moduleContent.getStatus())).findFirst().get().getNum();
                    moduleContent.setNum(num);
                }
                contents.add(moduleContent);
            }
        }
        return contents;
    }

    /**
     * 获取订单列表
     * @param param
     * @return
     */
    @PostMapping("list")
    public List<OrderListInfoContent> getProductList(@RequestBody OrderListQueryParam param) {
        Long userId = SessionUtil.getUserId();
        // 查询订单
        OrderListQueryDto queryDto = new OrderListQueryDto();
        PageUtil.initPageInfo(queryDto, param);
        queryDto.setUserId(userId);
        queryDto.setStatus(param.getStatus());
        List<OrderListRe> orderList = orderService.getOrderList(queryDto);
        if(CollectionUtils.isEmpty(orderList)) {
            return Lists.newArrayList();
        }

        // 查询订单下的商品信息
        List<OrderListProductRe> orderListProductRes = orderList.stream().flatMap(e -> e.getProducts().stream()).collect(Collectors.toList());
        List<Long> productIds = orderListProductRes.stream().map(OrderListProductRe::getProductId).collect(Collectors.toList());
        List<OrderProductRe> productRes = productService.getOrderProducts(productIds);

        // 查询订单商品下的附加项信息
        List<Long> additionalIds = orderListProductRes.stream()
                .filter(e -> !CollectionUtils.isEmpty(e.getAdditionalIds()))
                .flatMap(e -> e.getAdditionalIds().stream()).collect(Collectors.toList());
        List<AdditionalItemRe> additionalItemRes = new ArrayList<>();
        if(!CollectionUtils.isEmpty(additionalIds)) {
            AdditionalListDto additionalListDto = new AdditionalListDto();
            additionalListDto.setItemIds(additionalIds);
            additionalListDto.setValid(false);
            additionalItemRes = productService.getAdditionalItemList(additionalListDto);
        }

        // 组装数据
        return assemblyOrderInfo(orderList, productRes, additionalItemRes);
    }

    private List<OrderListInfoContent> assemblyOrderInfo(List<OrderListRe> orderList, List<OrderProductRe> productRes, List<AdditionalItemRe> additionalItemRes) {
        List<OrderListInfoContent> orderListContents = new ArrayList<>(orderList.size());
        // 将数据改为map形式
        Map<Long, OrderProductRe> productReMap = productRes.stream().collect(Collectors.toMap(OrderProductRe::getProductId, e -> e));
        Map<Long, AdditionalItemRe> additionalItemReMap = additionalItemRes.stream().collect(Collectors.toMap(AdditionalItemRe::getId, e -> e));
        for(OrderListRe order : orderList) {
            OrderListInfoContent content = new OrderListInfoContent();
            content.setOrderId(order.getOrderId());
            content.setStatus(order.getStatus());
            content.setTotalPrice(order.getTotalPrice());
            List<OrderListProductContent> productContents = new ArrayList<>(order.getProducts().size());
            // 组装商品
            for(OrderListProductRe productRe : order.getProducts()) {
                OrderListProductContent productContent = new OrderListProductContent();
                productContent.setProductId(productRe.getProductId());
                productContent.setProductNum(productRe.getNum());
                productContent.setTotalPrice(productRe.getTotalPrice());
                // 商品服务获取到的商品信息
                OrderProductRe orderProductRe = productReMap.get(productRe.getProductId());
                productContent.setThumbnail(orderProductRe.getThumbnail());
                // 构建商品名
                StringBuilder productName = new StringBuilder(orderProductRe.getProductName());
                if(!CollectionUtils.isEmpty(productRe.getAdditionalIds())) {
                    productName.append("(");
                    productRe.getAdditionalIds().forEach(e -> productName.append(additionalItemReMap.get(e).getName()).append(","));
                    productName.setCharAt(productName.length() - 1, ')');
                }
                productContent.setProductName(productName.toString());
                productContents.add(productContent);
            }
            content.setProducts(productContents);
            orderListContents.add(content);
        }
        return orderListContents;
    }

    private OrderAddDto convert2OrderAddDto(OrderAddParam param) {
        OrderAddDto dto = new OrderAddDto();
        dto.setTotalPrice(param.getTotalPrice());
        dto.setUniqueRow(param.getUniqueRow());
        dto.setProductDtos(param.getOrderProductAdds().stream().map(this::convert2OrderProductAddDto).collect(Collectors.toList()));
        return dto;
    }

    private OrderProductAddDto convert2OrderProductAddDto(OrderProductParam param) {
        OrderProductAddDto dto = new OrderProductAddDto();
        dto.setProductId(param.getProductId());
        dto.setProductNum(param.getProductNum());
        dto.setProductPrice(param.getProductPrice());
        if(!CollectionUtils.isEmpty(param.getItems())) {
            dto.setOrderProductAdditionals(param.getItems().stream().map(this::convert2OrderProductAdditionalDto).collect(Collectors.toList()));
        }
        return dto;
    }

    private OrderProductAdditionalDto convert2OrderProductAdditionalDto(OrderProductAdditionalParam param) {
        OrderProductAdditionalDto dto = new OrderProductAdditionalDto();
        dto.setAdditionalId(param.getId());
        dto.setPrice(param.getPrice());
        dto.setNum(param.getNum());
        return dto;
    }

}
