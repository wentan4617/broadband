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
Manager Id                      =     1000000
Wholesaler Id                   =     2000000
Order Id                        =     7000000
Invoice Id                      =     3000000
Material, MaterialWholesaler    =     8000000
Combo, ComboWholesaler Id       =     9000000

TMS Formula: 2014-08-05
* 当我们向wholesaler收费时，统计他/她手下所有用户的流量使用数量，并运用如下公式：
    当总流量小于1000： n * 0.39
    当总流量小于10000并且大于1000： (1000 * 0.39) + ((n - 1000) * 0.31)
    当总流量大于10000：(1000 * 0.39) + (9000 * 0.31) + ((n - 10000) * 0.25)

TMS Permissions:
* wholesaler权限有：Administrator, Accountant, Provisioner
* Manager权限有：administrator, accountant, provision

TMS Order Status:
    pending, processing, reconnection, rfs, in-service, suspend, disconnected, void, cancel


demand version 1.2.0 2014-09-25

* 如果 wholesaler 下单时选择 customer pay type 为 pre-pay，则附带 ordering form 再出两张 receipt，如果选择 post-pay 则仅出两张 ordering form.(kanny)
* service giving 时判断，如果该 order 为 pre-pay，则附带设置下一次出账日期再出两张 invoice（给Wholesaler和Customer），如果该 order 为 post-pay 则仅设置下一次出账日期.(steven)
* pre-pay 按CyberPark的Residential出账逻辑走，post-pay 每月1号固定出账单，从服务开通日开始算起.(steven)
* 添加Wholesaler时可以给Wholesaler指定一些给客户看得到的Wholesaler公司的信息.(steven)
* 添加order_group表：id, first_name, last_name, email, mobile, is_send_invoice_statement, is_send_invoice_together，order添加一个group_id字段，可以将相关的order关联起来.(steven)
* invoice出账逻辑顺序：

1. 出账：
    1：有group：
    is_combined_send==true，is_statement==true：相关 order 打包发送给 group 的 contactor，并附带 statement
    is_combined_send==true，is_statement==false：相关 order 打包发送给 group 的 contactor，不附带 statement
    is_combined_send==false，is_statement==true：相关 order 分别发送给 order 的 contactor，并发送 statement 给 group 的 contactor
    is_combined_send==false，is_statement==false：相关 order 分别发送给 order 的 contactor，不发送 statement 给 group 的 contactor

    2：无group：
    is_wholesaler_invoice_mobile_notification==true，发送 order 给指定 order 的 contractor 的 mobile
    is_wholesaler_invoice_email_notification==true，发送 order  给指定 order 的 contractor 的 email
    is_wholesaler_invoice_mobile_notification==false，不发送 order 给指定 order 的 contractor 的 mobile
    is_wholesaler_invoice_email_notification==false，不发送 order  给指定 order 的 contractor 的 email
    
    
    

1. 给Wholesaler出账时是否发送邮件或短信提醒：
is_wholesaler_invoice_mobile_notification==true || is_wholesaler_invoice_email_notification==true

2. 给Customer出账时是否发送邮件提醒：
is_customer_invoice_mobile_notification==true || is_customer_invoice_email_notification==true



demand version 1.1.6 2014-09-13

* 在customer order界面添加一个按钮，点击后提醒用户我们收到他支付订单的金额，我们将继续process他的订单.(steven)
* 创建agent角色用户时通过短信和邮件发送友好提示，邮件内容包含他的注册信息.(steven)


demand version 1.1.5 2014-09-10

* 制作OrderingPDFCreator，用来下单时创建PDF.(steven)
* [删除material关联删除相应的material_wholesaler及combo_wholesaler.](steven)
* [删除combo关联删除相应的combo_wholesaler.](steven)
* [删除wholsaler关联删除相应的material_wholesaler及combo_wholesaler.](steven)


demand version 1.1.0 2014-09-04

* wholesaler下单时选择combo或material，DIY形式下单.
* wholesaler下错单时我们可以灵活滴更改订单信息，并重新向客户收取订单剩余差价或退还多于差价.(steven)
* 订单对应多个service record，manager发布新的record则在站内消息提醒wholesaler，wholesaler从order点击该service record后就去掉该提示，在order里service recor选项卡要加一个badge显示新service record数目.(steven)


demand version 1.0.8 2014-09-03

* [完美创建更新wholeslaer及其附属material及combo产品.](steven)
* [更新material表不影响material_wholesaler表，删除才会同时删除.](steven)
* [更新combo表不影响combo_wholesaler表，删除才会同时删除.](steven)
* 将wholesaler界面改成模版，wholesaler添加更新界面在权限下面的排版：
    所有的material以表格形式列出。
    所有combo以表格形式列出。
    所有该wholesaler下的sales：
* [选中material操作下拉菜单选create combo，modal里填写combo名称comfirm则创建一个新combo.](steven)
* [点击combo编号进入修改界面，修改界面列出所有material供勾选或反选.](steven)
* [wholesaler列表界面只显示每家公司的主wholesaler帐号.](steven)
* wholesaler列表的operations下拉菜单可以上下架(on/off shelf)选中wholesaler的material和combo.(steven)


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
 