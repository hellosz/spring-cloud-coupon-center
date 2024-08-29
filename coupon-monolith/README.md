#### 项目说明
这是一个 springboot 构建的基于影响场景的 Coupon 优惠券系统，采用单体架构。
其中包含以下四个模块：
- coupon-template-serv， coupon 模板服务
- coupon-calculation-serv， coupon 试算服务
- coupon-customer-serv，用户服务
- middleware，基础服务

#### 每个服务采用三层目录分层
- api, 对外接口及相关 POJO 类；
- impl，业务逻辑实现；
- dao，数据访问层；

#### 使用以下相关依赖构建
