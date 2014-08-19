broadband 1.0.x 2014
=========

Total Mobile Solution Internet Service Web Project

###Specification

* 多次提交数据库的操作尽量都封装进一个Service方法
* 编写测试文档（测试数据填写规范），测试行程表（测试时间段，例如11，1，3，5点各测一轮），上报测试结果
* 所有表单提交的controller方法，如果是要页面跳转的都要redirect.
* Naming Conventions
 * Controller Function Naming Conventions (Plan)
 * planView (/plan/view/1)(get)
 * toPlanCreate (/plan/create)(get)
 * planCreate (/plan/create)(post)
 * toPlanEdit (/plan/edit/{id})(get)
 * planEdit (/plan/edit)(post)
 * planRemove (/plan/remove/{id})(get)

TMS ID's prefix stands for:

1000000  =  User Id
2000000  =  Wholesaler Id
3000000  =  Order Id
5000000  =  Customer Id
6000000  =  Invoice Id


TMS Formula: 2014-08-05

* 当我们向wholesaler收费时，统计他/她手下所有用户的流量使用数量，并运用如下公式：
    当总流量小于1000： n * 0.39
    当总流量小于10000并且大于1000： (1000 * 0.39) + ((n - 1000) * 0.31)
    当总流量大于10000：(1000 * 0.39) + (9000 * 0.31) + ((n - 10000) * 0.25)

demand version 1.0.8 2014-08-13

* 开发tms_combo创建界面.(steven)


demand version 1.0.6 2014-08-08

* [给创建用户时一个选项：是否是新公司的wholesaler，还是现有公司的wholesaler.](steven)

demand version 1.0.5 2014-08-07

* 
* [列出Whosaler时判断如果User不为空则显示所有wholesaler否则wholesalerSession的wholesaler_id为空则只显示wholesaler_id为该whosaler编号的wholesaler.](steven)
* [开发Whosaler列表界面.](steven)
* [开发Whosaler创建界面.](steven)

demand version 1.0.3 2014-08-06

* [每个有金钱的输入框都在blur事件上绑定回填保留两位小数的原数值.](steven)
* [通过模版+Ajax开发material列表界面.](steven)
* [开发material创建界面.](steven)
* [开发type创建Modal.](steven)
* [开发group创建Modal.](steven)
 