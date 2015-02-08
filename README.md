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
 
 
 
demand version 1.9.9 2015-02-28

* [7号生成的invoice里，一个订单有多个VoIP的拨打记录有录入到invoice，但是invoice_id没有回绑，检查问题。](steven)
 
 
demand version 1.9.8 2015-01-23
 
* [检查并修复700340订单的帐单，解决重复出现的chorus-addon.](steven)
* [检查客户在线支付invoice后为何calling detail没有记录进去.](steven)
 
 
 
 
demand version 1.9.7 2015-01-21
 
* [计算calling时，如果有设置nz calling rates，则将设置的作为费率.](steven)
* [order界面添加一个Set NZ Calling Rates按钮，点击后弹出modal，可指定Local, National, Mobile的费率.](steven)
 
 
 
demand version 1.9.6 2015-01-20

* order界面添加一个Send Order Confirmation，点击后通过order的email发送给客户，当客户点击邮件里的链接时，该order的is_confirmed变为true.(steven)
* [在读取calling时不取900，在读取rental时读取900，价格为税后价*1.15.](steven)
* [前台下单去掉open term.](steven)
 
 
 
demand version 1.9.5 2015-01-12

* [New Order下Business单子时，is_ddpay不是true，检查原因.](steven)
* [在order界面添加一个按钮，弹出modal，可勾选指定的add-on，出账计算add-on时如果该add-on有定制价格，则按定制的价格算.](steven)
* [添加一张tm_customer_order_chorus_addon,字段有：id, order_id, addon_name, addon_price.](steven)
* [新账单只能包含rental，但不更新rental的invoice_id，检查原因.](steven)
* [检查所有rental及其订单，如若发现已被包含至invoice，但invoice_id没有成功更新至rental上的话，将其关联的invoice_id加上.](steven)
* [invoice的total balance显示为基于order的total invoice balance.](steven)
 
 
 
demand version 1.9.3 2015-01-07

* [Business出第一张invoice时next_invoice_create_date没变，检查原因.](steven)
 
 
 
demand version 1.9.2 2015-01-06

* [Chorus add-on存入时加多几个description，将rental的费用改成总费用*1.15 = 最终应付金额.](steven)
* [business上一个帐单的balance会累加至下一个帐单payable amount上.](steven)
* [筛选列出的chorus addon，与之前的addon存在同一张表里.](steven)
* 列出所有非monthly paid的order，及其plan单价，列出合同时间以及剩余服务月份.(steven)
* 统计每个月所产生成本，根据statement date统计chorus成本，根据calling date统计callplus及vos成本.(steven)
* [列出所有is_ddpay=true的所有order，In Service的，所有非paid的帐单，导出成Excel表，所需列:Customer Id, Order Id, Create Date, DD/CC detail, Balance.](steven)
* 列出所有用来算Commission的order，order的sales_id或user_id必须有一个非空，所需列: Customer Id, Order Id, Plan Price, Plan Type, Order Type, Order Term, Service Date, Receivables, Invoice Payable, Order Status, Hardware.(steven)
    过滤选项：Sales, Begin Date - End Date, Personal/Business, On Going/One Off
    可以导出成Excel并下载
* [根据Chorus Calling的PSTN匹配改成根据Chorus Rental的PSTN匹配.](steven)
* equipment状态加一个exchanged，在equipment列表界面可以勾选某个equipment点击change status to exchanged/faulty.(steven)
 
 
 
demand version 1.9.0 2014-12-23

* [customer edit选项卡里添加一个Migrate Customer按钮，弹出层根据order id模糊匹配，匹配到则点亮隔壁Confirm, Migrate按钮，点击后将order的所有相关信息都关联至该customer下.](steven)
* [将600313和600087并到有DDPay的customer下面，之后操作同上.](steven)
* [检查Chart(Calling Statistic)界面，为何出现500.](steven)
* [将600282和600283并到有account credit的customer下面，确保要丢弃的customer的有用信息都叠加至保留的customer信息里.](steven)

 
 
demand version 1.8.5 2014-12-15
 
* [在invoice的calling列表下添加一个Calling Area Statistic，列出各个calling的描述，分钟数，费用总和.](steven)
* [无法查看流量，确认服务器是否被迁移，导致地址指向不正确，无法获取数据.](steven)


 
demand version 1.8.5 2014-12-15

* [invoice列表添加一列customer name，去掉一列invoice status.](steven)
* [有order_id的地方都要加上customer_id.](steven)
* [DD/CC 表加多一个order_id字段.](steven)
* [将equipment里的order加上order的链接，需要customer_id.](steven)




demand version 1.8.2 2014-12-10
 
* [新增修改super-free-calling以及其他free-calling拨打分钟数的输入框.](steven)


demand version 1.8.0 2014-12-09

* [将TMUtil里日期缩写函数的日前面的0去掉.](steven)
* [修复This Order Belongs To提交时的user_id以及sale_id同时存在的问题.](steven)
* [修正700018,700255,700330,700331,700334，这几个订单的next_invoice_create_date.](steven)
* [出第一个账单的is_ddpay订单日期是当月07号，排查问题并解决.](steven)
* [增强SimpleMapperCreator类，在update里添加是否设置指定属性为NULL.](steven)
* [将onsite_id变成200000开头.](steven)
 
 
demand version 1.7.8 2014-12-08
 
* 加一套邮件&短信模版，标题：ReceivePaymentNotice，内容：Dear ___, We have already received your payment, which is NZ$____. Thank you for the payment. Have a nice day!(steven)

 
 
demand version 1.7.6 2014-12-05
 
* [hardware添加界面加多一个hardware类型，以便添加硬件其附属品.](steven)
* [在Equipment列表界面添加一个按钮组，点击以列出指定状态的设备.](steven)
* [tm_customer_order_detail表增加detail_cost字段，记录detail成本.](steven)
* [将Equipment列表的Order Detail Id列标题改成Order Id.](steven)
 
 
demand version 1.7.6 2014-12-02
 
* [由于服务器迁移，需要出order_id为700327的账单.](steven)

 
demand version 1.7.5 2014-12-01
 
* [新建tm_equip表，字段：id, equip_name, equip_type, equip_purpose, equip_sn, equip_status, order_detail_id.](steven)
* [新建tm_equip_log表，字段：equip_id, oper_type, log_desc.](steven)

 
demand version 1.7.2 2014-11-28
 
* [将各个order分隔开，每一个order就是一个模版.](steven)

 
demand version 1.7.1 2014-11-27
 
* [invoice列表添加一列order_status，将order状态移植到invoice上.](steven)

 
demand version 1.7.0 2014-11-26
 
* [创建一张工单表tm_customer_order_onsite字段有：id, customer_id, order_id, customer_type, first_name, last_name, mobile, address, plan_name, data_flow, terms, onsite_type, onsite_date, onsite_type: delivery, faulty, installation, other](steven)
* [创建一张工单细目表tm_customer_order_onsite_detail字段有：id, onsite_id, detail_name, detail_type, detail_unit](steven)
* [invoice列表显示其对应order的状态.](steven)
 
 
demand version 1.6.5 2014-11-24
 
* [customer order界面，完善term perid修改功能.](steven)
* [customer order界面的所有modal提交后刷新模版回传当前操作order的id，以确保该order的相关选项卡不恢复默认状态，防止其隐藏.](steven)
 
 
 
demand version 1.6.0 2014-11-21

* [customer order列表界面点击标题后可以根据相关字段升降序排列.](steven)
* [新建一张表，tm_customer_order_provision_checklist，字段有：payment_form, has_pstn, has_fax, has_voip, pstn_count, fax_count, voip_count, has_alarm, has_emergency, has_cctv, has_eftpos, has_smart_bundle, has_call_restrict, has_call_waiting, has_faxability, has_call_display, has_wire_maintenance, has_static_ip, has_dial_wrap, smart_bundle_count, call_restrict_count, call_waiting_count, faxability_count, call_display_count, wire_maintenance_count, static_ip_count, dial_wrap_count, has_router_post, router_post_count, has_svcv_lan, has_service_given, has_pstn_nca, pstn_nca_count](steven)
 
 
demand version 1.5.8 2014-11-20

* [在创建user里，非system-developer权限的限制其开放system-developer权限，因为系统部署前准备工作都放在system-developer能看到的界面当中，运行后就无需再去操作部署前准备工作.](steven)
* [出下一张DD或CCPay账单时调整continue关键词位置以解决next_invoice_create_date的准确值，并将该段代码放到detail_type为plan-term的逻辑里.](steven)
* [自动出ddpay order的账单，加多一个判断，最大一个账单不是当月.](steven)


 
demand version 1.5.6 2014-11-19
 
* [在customer order界面添加一个按钮，Get PSTN Order's in Excel，所需字段：address, broadband_type, pstn_number，逻辑是迭代有pstn以及无pstn的order信息输出到Excel里.](steven)
* [customer order列表界面优化查询条件，并新增org_name以及user_id查询.](steven)
* [后台登陆后将用户ID显示到用户名右侧，方便根据自身user_id查询order.](steven)
* [customer query界面添加org_name查询.](steven)
 
demand version 1.5.5 2014-11-18

* [将order的period做成可以修改的.](steven)
* [将customer order信息界面的所有order包含在可伸展层内，默认伸展状态为false](steven)
 
 
demand version 1.5.2 2014-11-17

* [添加Add DDPay，类似Add Credit，列出有Credit Card或DDPay的客户，方便财务扣款. id, customer_id, account name, account number](steven)
* [在出rental时加入：Calling Numbers Presented - Minimum Charge，在出chorus calling时将其去掉.](steven)

demand version 1.5.0 2014-11-13

* 将订单相关账单的balance累加并存至当前账单的PDF中.



demand version 1.5.0 2014-11-13

* [新建一张表tm_website_editable_details](steven)
    字段：company_name, company_name_ltd, company_hot_line_no, company_hot_line_no_alphabet, company_address, company_email, website_year
    值：
    
* [新建一张表tm_plan_introductions](steven)
    字段：adsl_title, vdsl_title, ufb_title, adsl_content, vdsl_content, ufb_content,
    
* [新建一张表tm_website_static_resources](steven)
    字段：logo_path, facebook_lg_path, googleplus_lg_path, twitter_lg_path, youtube_lg_path

* [新建一张表tm_terms_conditions](steven)
    字段：term_contracts, terms_conditions_business_retails, terms_conditions_business_wifi, terms_conditions_personal, terms_conditions_ufb

* [将CompanyDetail的Terms & Conditions移到tm_terms_conditions表上](steven)

* [新建一张表tm_pdf_resources](steven)
    字段：common_company_lg_path, invoice_company_lg_path, company_lg_customer_service_bar_path, two_dimensional_code_path

* [将客户登录后的提示改成：Welcome to ${tm_website_editable_details.company_name}](steven)


demand version 1.5.0 2014-11-12

* [将CyberPark系统的Logo等信息变成灵活可更改的：](steven)

    前端要去掉的东西：
    Voucher Checking界面
    About界面的Company Overview、Telecommunication services and WiFi coverage start from US!
    Wi-Fi Solution界面
    E-Commerce界面
    

    网站：-
    1. 前端(客户)：
        头部Logo（approximately H:46px、W:111px）
        首页大图片（approximately H:322px、W:1001px）
        
        Header:
        Information：About ${company_name}
        0800 229 237替换成${company_hot_line_no}
        0800 229 237：
        Contact Us下方：
        ${company_name_ltd}
        ${company_address}
        Give us a call ${company_hot_line_no_alphabet}
        send email to ${company_email}
        
        Footer：
        左下角：About ${company_name}
        右下角：${company_name_ltd}
        右下角：${website_year}
        
        
    2. 后端(管理)：
        
        
    
    PDF：-
        



demand version 1.4.6 2014-11-11

* [tm_customer_invoice添加6个字段：chorus_calling_credit, chorus_calling_charge, nca_calling_credit, nca_calling_charge, vos_calling_credit, vos_calling_charge.](steven)
* [在customer-info界面添加一个New Order按钮，将Kanny给的链接放进去.](steven)
* [customer界面添加一个按钮，Eliminate Account Credit，点击后输入要减去的金额，提交后判断输入金额不能小于账户余额，最后保存最终金额.](steven)


demand version 1.4.5 2014-11-10

* [创建一张tm_customer_billing_log表，记录所有跟Billing有关的操作，字段有customer_id, order_id, invoice_id, user_id, oper_type, oper_name, oper_date.](steven)
* [当点击Next Invoice Create或Regenerate Invoice时，记录操作者ID，操作时间，操作类型，操作名称.](steven)
* 创建一张tm_customer_order_log表，记录所有跟Order有关的操作，字段有customer_id, order_id, user_id, oper_type, oper_name, oper_date.(steven)





demand version 1.4.5 2014-11-09

* [将显示日期缩写的日重新替换一下，除了11、12、13号加th外，其余的以1、2、3结尾的分别加上st、nd、rd.](steven)



demand version 1.4.4 2014-11-06

* [添加一个界面，列出所有类型为pstn的order_detail，并且分类为：Disconnected以及In Service，在order_detail上加四个字段：is_nca, to_nca_by_who, is_out_nca, out_nca_by_who.](steven)
* [添加一个界面，列出所有In Service以及Disconnected的pstn类型的pstn_number，以供Provision去NCA查看是否存在相应的号码.](steven)


demand version 1.4.4 2014-11-06

* [在customer-info界面显示所有该customer关联的credit card信息，可以添加新的，更新/删除已有的.](steven)
* [加一条类似pstn的detail，type为fax.](steven)



demand version 1.4.4 2014-11-01

* [大改一对多逻辑，预计一周完成，提前一半时间完成.](steven)


demand version 1.4.4 2014-10-28

* [分别列出CHORUS，NCA，VOS三种拨打账单的买入，卖出价格差值.](steven)
* [需要新建一张tm_chorus_broadband_asid表，将每个月的broadband_asid导进数据库，以供比配.](steven)
* [添加一个界面，列出未绑定的CHORUS，NCA，VOS，以及ASID的记录，并且要分类：Disconnected为未绑定且断开服务的记录，Unmatched为未绑定且未匹配的记录.](steven)




demand version 1.4.1 2014-10-21

* [新增一种超级free calling minutes，可以任意组合赠送的拨打分钟数.](steven)
* [在tm_customer_order_detail_delete_record表上添加一个字段：is_recoverable，为true则显示可逆操作按钮，点击后detail出现在order里.](steven)
* [新增tm_customer_order_detail_recoverable_list表，字段：id包括detail所使用的所有字段，配合delete_record表的is_recoverable字段使用.](steven)
* [添加tm_customer_order_detail_delete_record，字段有：id, order_id, detail_name, detail_type, delete_reason，存放order_detail的删除记录.](steven)
* customer_order_detail表新增一个字段：credit_note，给credit时记录原因.(steven)
* [在order表上添加一个字段：xero_invoice_status，用来向Xero发送invoice时判断该invoice是InvoiceStatus.DRAFT还是InvoiceStatus.AUTHORISED.](steven)
* [在order表上添加一个字段：is_send_xero_invoice，如果为true则发送invoice至xero.](steven)
* 添加一张transaction_can_redo_list表，字段有：id,transaction_id, invoice_id, credit, paid.(steven)
* transaction添加一个字段can_redo，如果为true则transaction列表显示Redo按钮，点击后去transaction_can_redo_list取出相关数据并开始重做.(steven)
* transaction添加一个invoice id下拉选项，更改选项关联到指定invoice上.(steven)
    步骤：
    1. transaction amount必须与原invoice的paid amount一致
    2. 原invoice状态必须为paid，目标invoice状态必须为unpaid且paid amount为0
    3. 原invoice的final payable必须与目标invoice的final payable一致
    4. 设置原invoice的balance为paid amount的值，paid amount为0，如果due date小于今天则状态为unpaid，否则为overdue
    5. 设置目标invoice的paid amount为transaction amount的值，balance为0
    6. 设置transaction的invoice id为目标invoice的id
    7. 更新transaction、原invoice、目标invoice

* 



demand version 1.4.0 2014-10-20

* 检查看invoice due date为何不显示，数据库里有数据.(steven)
* 重新生成invoice的postSingleInvoice已完毕，剩下所有自动的postMultiInvoices还未做.(steven)



demand version 1.4.0 2014-10-18

* 添加debit或credit时不需要指定unit，自动设为1.(steven)
* billing里列出所有now()时间之前未出的in service订单的账单.(steven)
* 服务器每生成一张账单，则在Xero生成同样的账单.(steven)
* 在导入chorus add on账单时判断，如果应付金额小于等于0则不计入.(steven)
* order detail后面添加可以修改基本信息的铅笔点击可以修改，根据detail_type来显示除基本信息外的输入框:(steven)
    基本信息：detail_name, detail_price, detail_type
    1. plan-term, plan-no-term, plan-topup:
        detail_plan_type, detail_plan_sort, detail_data_flow, detail_plan_group
    2. pstn:
        pstn_number
    3. voip:
        pstn_number, voip_password, voip_assign_date
    4. present-calling-minutes:
        detail_desc, detail_calling_minute



demand version 1.3.9 2014-10-16

* [invoice的已付金额加一个铅笔点击可以修改.](steven)
* [transaction的amount添加一个铅笔点击可以修改，在controller要同时修改transaction的amount和settle_amount.](steven)
* [order的next invoice create date添加一个Calendar，点击可以修改日期.](steven)



demand version 1.3.8 2014-10-15

* [制作invoice反关联配合修改invoice状态，用以重新生成之前的账单.](steven)
    1. 将customer_id及order_id存放至original_customer_id及original_order_id字段.
    2. 将customer_id及order_id清空.
    完成以上两步，则invoice反关联成功.

* [制作列出反关联的invoice，用以还原反关联的invoice.](steven)
    1. 将该customer反关联的invoice全都列出



demand version 1.3.7 2014-10-14

* Invite Rates & Rules:
    Inviter Gained Order Total Rates:
       Customer:    2%       2 layer
       User Agent:  5%       1 layer

    Invitee Gained Order Total Rates:
       Customer:    3%



demand version 1.3.6 2014-10-13

* [屏蔽USA Alaska，ALASKA.](steven)



demand version 1.3.5 2014-10-12

* VoIP计算赠送拨打分钟数顺序：
    1. Local                        本地
    2. National                     国内
    3. NZ Mobile                    NZ手机
    4. Mobile+Fixed Line            手机&固话
    5. Fixed Line                   固话
    6. International                国际



demand version 1.3.3 2014-10-10

* [Business 添加一个判断，在service giving之后的一个月开始出账单.](steven)
* [North大华加上8月的credit，然后重新出一张匹配上一张账单的当月账单.](steven)



demand version 1.3.1 2014-10-09

* [order界面添加Generate Receipt Only按钮，点击只生成receipt.](steven)
* [策划VOS拨打记录号码匹配处理及计算方式.](steven)
    CRMService里需要添加该功能的方法有：
        createNextCallingInvoicePDF（Undone）
        createInvoicePDFBoth（Undone）
        createTermPlanInvoiceByOrder（Undone）
        createTopupPlanInvoiceByOrder（Undone）
        
* order底部添加一个按钮：Add Chorus Add-On，可以添加以下5种add on（由于Chorus每月自动统计该Detail，功能取消）：
	Call restrict with no Directory Access nat Res
	Caller Display Monthly Charge per line Res
	Call waiting nat Res
    Faxability Monthly Rental Res
	Smart Bundle package



demand version 1.3.0 2014-10-08

* [排查DDPay出账看看为什么生成账单，next_invoice_create_date以及flag没有变化.](steven)
* [将后台invoice通过各种方式手动销帐改成可以指定金额.](steven)
* [检查修改plan detail为什么名字不能更改.](steven)


demand version 1.2.5 2014-10-01

* [customer 列表界面添加asid查询.](steven)


demand version 1.2.4 2014-09-29

* transaction列表分类：
    Visa:visa,
    MasterCard:master card,
    Cash:cash,
    (Account2Account,Account-2-Account):a2a,
    DDPay:ddpay,
    Credit Card:credit card,
    (account-credit,Account Credit):account credit,
    CyberPark Credit:cyberpark credit,
    Voucher:voucher


demand version 1.2.2 2014-09-26

* billing模块做一个手动出invoice的功能，点击可以填写


demand version 1.2.0 2014-09-19

* [后台invoice Make Payment添加一个选项，Account Credit，可以通过客户account credit余额抵消账单.](steven)



demand version 1.1.9 2014-09-18
* [Agent的commission做判断，如果是一个月的plan则amount paid * 0.45，如果是3个月及以上的预付月份则](steven)
Unit个月：plan detail price + ((Unit - 3) * plan detail price * commission)
* [CRM里所需升级：（仅system-developer权限能使用](steven)
transaction添加删除按钮
invoice添加删除按钮.关联删除相关invoice detail
service giving和next invoice create添加日期置空按钮
    

demand version 1.1.8 2014-09-16

* [添加一种新的credit，重新生成ordering和receipt时不显示，service giving的第一个账单也不显示.](steven)


demand version 1.1.7 2014-09-15

* [在tm_user表上添加一个字段，agent_commision_rates，用来分别给不同的agent不同的commision.](steven)


demand version 1.1.6 2014-09-13

* 通过邀请人customer id注册后的
* 在customer order界面添加一个按钮，点击后提醒用户我们收到他支付订单的金额，我们将继续process他的订单.(steven)
* 创建agent角色用户时通过短信和邮件发送友好提示，邮件内容包含他的注册信息.(steven)


demand version 2.5.5 2014-09-12

* [customer order界面添加一个按钮：Regenerate Receipt，点击后系统根据order细目重新生成收据.](steven)


demand version 2.5.2 2014-09-11

* [在Chorus账单里查找出不再服务内的pstn及asid.](steven)
* [customer 列表界面添加通过ASID查询的功能.](steven)


demand version 2.5.2 2014-09-10

* [customer界面加一个Tickets选项卡列出与该customer相关的Ticket，需要列出的字段：create_date, description，日期降序排列，点击日期跳转到${ctx}/broadband-user/crm/ticket/edit/${t.id}界面查看detail.](steven)
* [重新生成ordering form时将计算出的订单应付金额更新到order里.](steven)
* [后台customer invoice界面make payment添加一个选项，AccountCredit，可以让财务直接在后台通过客户的账户余额销帐.](steven)
* [Edit Company Detail界面查看为什么不能更新公司信息.](steven)


demand version 2.5.1 2014-09-06

* ordering form和receipt里的号码如果为空则显示Empty.(steven)


demand version 2.5.0 2014-09-05

* 制作工单（Onsite Sheet PDF）：在order界面点击生成工单，填写所需信息确认生成
    header：order信息
    body：faulty comments，resolution comments，generator
    footer：customer signature
* [计算多个电话号码拨打记录需要更改CRMService里的方法：createInvoicePDFBoth(), createTermPlanInvoiceByOrder(), createTopupPlanInvoiceByOrder()](steven)


demand version 2.4.5 2014-09-04

* [在ordering form和receipt里显示pstn号码.](steven)
* [给pstn和voip的detail删除功能.](steven)
* [add detail里加多一个pstn和voip的选项.](steven)
* [出invoice时取出order下所有的pstn+voip，迭代计算拨打记录，然后在invoice里显示.](steven)


demand version 2.3.9 2014-08-31

* 在发送invoice时提示用户如果想shrink your broadband bill（缩短你的宽带账单金额），可以一次性充值3/6/12个月的钱+3%/+7%/+15%到他的账户余额，以供该用户在CyberPark网站消费.(steven)


demand version 2.3.7 2014-08-29

* [chorus账单的rental fee在出账时需要加入我们给客户的价格.](steven)
        Call restrict with no Directory Access nat Res: NZ$ 6.00
        Call waiting nat Res: NZ$: 6.00
        Faxability Monthly Rental Res: NZ$ 6.00
        Caller Display Monthly Charge per line Res： NZ$ 6.00
        Smart Bundle package: NZ$ 18.00
    
* 在出下一次非DDPay账单时如果自动支付了.(steven)
    初步判定的算法为：（account credit || prepayment或account credit && prepayment为true的情况）
        情况一： account credit == true，告知客户通过account credit抵消了相应金额的账单余额
        情况二： previous invoice prepayment == true，告知客户通过prepayment抵消了相应金额的账单余额
        情况三： (account credit && previous invoice prepayment) == true，告知用户通过account credit & prepayment抵消了相应金额的账单余额

    邮件及短信模版应显示的内容有：
        invoice no: invoice id
        status: current status
        payable amount:
        credit back:
        paid:
        balance: remaining balance
        

demand version 2.3.5 2014-08-27

* [在生成只有拨打记录的账单时在invoice的memo里记录上calling-only.](steven)
* [在每次生成账单时如果当前账单的memo包含calling-only则不将plan计入invoice detail.](steven)
* [将客户account credit的添加方式改为类似invoice的销帐方式，点击Topup，输入客户所付金额确认.](steven)
* [检查Business第一次生成的账单，如果credit超过balance的话是否会将balance变成负值.](steven)
* [看看如何能够判断出上个月账单的余额负数到底是否有credit还是只是单单的paid amount，如果有credit则将上个月balance里所剩credit累加到这个月，这个月的final_payable_amount减去所剩credit.](steven)
    初步判定的算法为：（Balance为负数的情况）
        情况一： paid小于等于final payable amount，剩下的都是credit
            paid <= final payable amount ? abs(balance)
        情况二： paid大于final payable amount，如果无credit（balance == paid - final payable amount）则剩下的都是paid
            (paid > final payable amount) && (balance == paid - final payable amount) ? abs(balance)
        情况三： paid大于final payable amount，如果有credit（balance > paid - final payable amount）则剩下的paid是paid - final payable amount的绝对值，credit是balance - (paid - final payable amount)
* [出账时判断如果账单的balance小于等于0，则状态为paid，自动抵消该账单.](steven)
* [在出账判断如果prepayment不够抵消账单，则查customer的account credit，如果account credit不为空且有余额则接着抵消账单余额，并且该account credit的抵消记录也作为一条对应该invoice的card_name为account-credit的transaction记录.](steven)


demand version 2.3.0 2014-08-26

* [检查sales下单后为什么点击Order Form下载按钮报500.](steven)
* [在sales列表界面添加两个按钮：avoid掉pending和warning-pending的订单，download order form，也就是application form.](steven)
* [将sales下单步骤缩短一步，只到order confirm即可，将credit card填写信息搬到order confirm里.](steven)
* 在tm_customer_order表上添加两个字段：customer_type, address。为单用户对多订单逻辑做准备.(steven)
* [在customer order界面限制只有account以上权限能看到credit card及操作其他牵扯到billing逻辑的关键信息.](steven)
* [transaction图表总金额不对.](steven)
* [invoice月统计排版有问题.](steven)
* [在invoice各统计项点击后跳转到相应日期invoice列表.](steven)

demand version 2.2.8 2014-08-25

* [invoice邮件及短信上显示最终应付金额，短信显示invoice逾期日期.](steven)
* [变成pending-warning的订单不包含business的.](steven)


demand version 2.2.7 2014-08-23

* [Manoj的7月账单等更新项目后重新生成测试一下看有没有其他问题.](steven)
* [修改赠送fixed+mobile的匹配方式后，测试有该细目的pstn看是否逻辑正确，先以QianNa的pstn_number：98150689 为例.](steven)
 

demand version 2.2.5 2014-08-22

* [筛选出Chorus以及Callplus未计算的拨打记录，供下次出账时计算.](steven)
* [为了方便Provision通过order id来查找customer，在view customer界面新增一个查询字段:Order Id，将已有的Id改为Customer Id.](steven)
* [清除现有chorus账单中无用的数据，减少冗余数据在数据库内所占空间.](steven)
* [在导入chorus账单时筛选出T1,T3及那些能收的rental细目，其余忽略不计.](steven)
* [定时给预付1月以上的order出只有拨打记录的账单：（时间定在每个月10号中午，也就是导入callplus账单后就可以出了）](steven)
        条件：
           1. order_status = 'using' : 必须在服务中的order
           2. and order_type in('order-term','order-no-term') : 类型仅限term和no-term
           3. and date_format(next_invoice_create_date,'%Y-%m') > date_format(now(),'%Y-%m') : 下次生成账单大于当月.(steven)
           4. and detail_type = 'pstn' and (pstn_number!=null && !"".equals(pstn_number.trim())) : order detail类型有pstn并且pstn号码不为空.(steven)
* [出账时根据used的true false来取改成用invoice_id来取，invoice_id是空的则calling detail为未使用，否则已使用.](steven)


demand version 2.2.1 2014-08-21

* [模仿invoice列表的做法，将personal和busines客户分开列出及查找，方便统计.](steven)
 

demand version 2.1.9 2014-08-20

* [sale的view online orders界面的optional_request开放更改权限，显示所有order对应的request_record修改按钮.](steven)
        

demand version 2.1.8 2014-08-19
 
* [统计bad-debit下的paid及credit到图表中.](steven)
* [Make Payment里更改状态的选项里新增一个Bad Debit，点击后改变状态为bad-debit.](steven)
* [在Service Given时点击Service Only后判断如果customer_type是business则则next_invoice_create_date为下个月7号且next_invoice_create为下个月14号.](steven)
* [检查7号出账时Business客户是否跟老term逻辑一致，如果using_start_date是当月则只列出开通日到月底的日期及费用，如果using_start_date是上个月则列出上个月开通日至上个月月底及这个月正月的日期及费用.](steven)
* [customer显示的Account Credit输入框只允许Accountant及以上权限的人员输入，其他人均disabled.](steven)

demand version 2.1.7 2014-08-18

* [Service Given时判断客户类型，如果是personal则用service-given短信及邮件模版，如果是business则用business-service-given短信及邮件模版.](steven)
* [下单时判断plan的客户类型，如果是personal则用online-ordering短信及邮件模版，如果是business则用business-online-ordering短信及邮件模版.](steven)
* [10号发送business账单时只发送邮件，不发送短信提醒.](steven)
 

demand version 2.1.5 2014-08-15

* [在后台Regenerate Invoice时判断，如果最近账单是unpaid或not_pay_off或overdue，则重新生成最近一张账单.](steven)
* [在后台customer invoice列表里判断，如果是unpaid或not_pay_off或overdue，则显示Make Payment按钮.](steven)
* [在前台customer invoice列表里判断，如果是(unpaid或not_pay_off或overdue)且customer_type为business，则显示Make Payment.](steven)
* [将overdue penalty选取范围设定为residential的，business允许最多拖欠三个月，第三个月月底则变成坏账，bad debit.](steven)
* [做个定时器，在每天凌晨0点取出due date小于等于今天且status为unpaid的账单，将其status改为overdue.](steven)
* [做个定时器，在每天凌晨0点30分取出due date小于等于三个月前的最后一天且status为overdue，将其status改为bad-debit.](steven)
* [在invoice列表页添加三个选项:All, YEAR, MONTH，点击All则走现有逻辑不变，点击YEAR则根据所选年份列出该年invoice及各项统计，MONTH则根据所选月份列出选定月invoice及各项统计.](steven)
* [business客户invoice列表界面只显示最近一张invoice的Make Payment按钮，只显示第一张并且是unpaid或overdue或not_pay_off账单的balance.](steven)
* [residential用户的账单图表页只统计invoice amount,unpaid,paid,overdue,voided,credit。overdue只显示overdue的账单（也就是order被suspended后该order最近一张状态被更改的账单）。voided只显示void的账单（也就是order到期未付款被disconnected后该order最近一张状态被更改的账单）.](steven)
* [business用户的账单图表页统计invoice amount,unpaid,paid,overdue,voided,credit,bad debit](steven)
* [invoice添加一个bad-debit状态，标记business账单过期日期超过三个月的为坏账.](steven)

demand version 2.1.3 2014-08-14

* [去掉前端查voucher时的serial number，只要pin number匹配即可.](steven)
* [客户看的账单列表判断是business的则屏蔽Make Payment按钮，如果该账单所在下标等于后台传出的未付款账单总数则显示Make Payment的按钮.](steven)
* [Debug给rfs date时所报的错，错误所报之处已定位到，由于替换字符串时如果存在特殊符号，则需要加斜杠进行转义，该错误已被解决.](steven)
* [将business用户的出账逻辑稍作修改，如果上一个账单balance大于0则累加到当前账单.](steven)

demand version 2.1.1 2014-08-13

* Voucher模块只有agent和sales是看不到的，其他权限皆可看到.(steven)
* 新增一个epay-customer-service用户权限，该用户只能查看Voucher模块.(steven)
* 将voucher功能从billing模块移出，新建一个Voucher模块，将移出的voucher功能放到新建的Voucher模块中.(steven)
* voucher列表的customer_id列可以点击进入customer信息界面，并列出该voucher的transaction记录.(steven)

demand version 2.1.0 2014-08-12

* [检查为何赠送拨打分钟数不被计算.](steven)
* [在每次出下一张账单时判断如果上一个账单的balance小于0则将上一个账单的balance的绝对值存入新账单的paid里新账单的balance则为final_payable减去paid，并且置上一个账单的balance为0.](steven)
* [检查topup的下一次出账日期为何间隔了7天而不是5天.](steven)
* [在invoice列表和图标两个界面列出的所有order都为using的invoice.](steven)
* 在customer信息invoice列表界面添加一个发短信提醒用户的按钮.(steven)
* [在列出的所有customer的order_serial上标注old，在出账时判断，如果order_serial存在old或者用户类型为business则按老的逻辑显示日期.](steven)
* [Nathan无法给rfs，后台报错，检查并解决.(未知问题，后来RFS给掉了)](steven)

demand version 2.0.8 2014-08-11

* [更新Callplus费率换算.](steven)

demand version 2.0.6 2014-08-08

* 每个月10号定期出预付N个月的order的电话账单.(steven)
判断条件为:
    order_status='using' AND order_using_start!=(current month)
    AND order_next_invoice_create!=(current month)
    AND id IN(select order_id from tm_customer_order_detail where detail_type='pstn')


demand version 2.0.5 2014-08-07

* 如果客户选择的是有合约的VDSL并且选择了硬件，就要在该detail的description上标注：credit-back，在出账单时判断如果detail类型不为空且包含hardware则判断是否上一次有返还credit，如果没有则hardware price - (hardware price / 12)保留两位小数并存到每一次返还后保存结果的字段上.

demand version 2.0.3 2014-08-04

* [在customer service record及ticket comment处加上 word-wrap:break-word; 并测试否能避免留言过长溢出指定区域.](steven)
* [在Pay off this receipt的时候加一个逻辑，如果输入的钱少于order的total price则用CyberPark Credit来填补上.](steven)
* Topup账单提示：invoice create date到第5天发送第一个提示，第7天发送最后一天提示，第8天发送suspension提示，第9天发送disconnected提示.(steven)
* 客户购买term plan界面，加一段话：You can either use your BYO(Bring Your Own) Router for first month free or get a free router for first month charged.(steven)
* 所有在Due Date之前3天的未付款invoice给客户发邮件及短信提醒一下还剩余几天就Due Date了.(steven)
* 在order支付发票的界面添加记录user_id.(steven)

demand version 2.0.1 2014-08-01

* [contact-us新增user_id字段，在回复时记录其id，显示时显示其用户名.](steven)
* [设计wholesale模块的所需表第一阶段(基础表).](steven)

demand version 2.0.1 2014-07-31

* [user_role添加一个agent角色，在sales的ordering online列表将客户id加链接指向provision里的order_form的modal.](steven)
* [开发agent的invoice查看界面，列出paid账单所能给予的commisssion.](steven)
* [开发agent的invoice commission月/周统计界面.](steven)

demand version 1.9.9 2014-07-30

* [分别复审term和no term出账时plan描述所记录的服务开通至终止日期是否准确，调整term及no term非第一张invoice的plan desc里显示的服务开通至终止日期.](steven)
* [复审Service Given时topup出账plan desc服务开通至终止日期.](steven)
* [开发invoice的年度统计，柱状显示每个月的invoice支付额.](steven)
* 开发transaction的年度统计，柱状显示每个月的transaction交易额.(steven)
* 尝试将所有图表都做成局部刷新，然后将相关的年月统计合并到单个页面以选项卡来区分开.(steven)
* customer order界面status选择disconnect时下方显示隐藏的

demand version 1.9.8 2014-07-29

* [在出账时添加used为false的以下billing_description细目: Call restrict with no Directory Access nat Res, Caller Display Monthly Charge per line Res, Call waiting nat Res, Faxability Monthly Rental Res, Smart Bundle package.](steven)
* 在customer query界面分别统计出personal及business用户的总数，以及分别列出各种状态下的customer.(steven)
* [新增topup出账单逻辑，plan的钱是service given开始7天，第6天发下一个7天的账单，due date是1天，due date一到则订单suspended.](steven)
* [is_ddpay的order每个月出账后都更新其next_invoice_create_date.](steven)
* customer order界面添加一个更改next_invoice_create_date的功能.(steven)

demand version 1.9.5 2014-07-28

* 下单时判断如果该地址存在于disconnected并且服务使用日期小于4个月的一律额外收取Reconnention Fee.(steven)
* [客户列表界面多加几个字段的查询，可以通过PSTN和地址查询该用户Detail，将customer id字段放到后面，mobile放首个字段.](steven)
* Ticket的Existing查询客户加多一个PSTN字段.(steven)
* 只有Weekly Topup的才能使用Voucher充值.(steven)
* [将Provision显示的价格从order的price改成monthly pay.](steven)
* 在后台顶部导航的用户名旁边显示个Message，实时更新Message的数量.(steven)
* [后台invoice的Make Payment新增一个CyberPark Credit.](steven)

demand version 1.9.2 2014-07-25

* CRM的customer列表界面添加筛选功能，可以指定显示ADSL,VDSL,UFB等宽带类型的客户.(steven)
* [Ticket列表界面添加筛选功能，可以指定显示发布类型是publilc还是protect，客户是existed还是new，Ticket的类型是faulty还是billing还是其他.](steven)

demand version 1.9.0 2014-07-24

* [ticket发布时所需属性，几种情况:- 情况一，现有用户: customer_id, user_id, cellphone, email, first_name, last_name, ticket_type, publish_type, description, not_yet_viewer字符串集合, existing_customer, create_date. 情况二，新用户: 没有customer_id. 情况三，发布类型为protected：protected_viewer字符串集合](steven)
    

demand version 1.8.7 2014-07-23

* [ticket发布时at的列表有这些权限列表：accountant,provision-team,administrator,system-developer.](steven)

demand version 1.8.5 2014-07-22

###Important Level (High)
* [CyberPark后台主页添加一个ticket发布界面列出所有公共及受保护的published message，包含的badge: public(谁都能看见), protected(指定的人能看见).](steven)
* [ticket发布界面添加一个发布按钮，点击后弹出一个modal，可以选择发送protected还是public，如果是protected则选中的user才会看到，如果是public则所有用户都能看到，选中用户所看到的该message为高亮显示.](steven)
* [迭代输出ticket时判断如果session的user_id存在于not_yet_viewer的字符串中，则高亮显示.](steven)
* [将显示Transaction图表的逻辑多加过滤，使数据更精准，只取card_name为Visa,Cash,Account2Account,DDPay,Credit Card,MasterCard的Transaction记录.](steven)

###Important Level (Medium)
* 在CRM的customer info界面添加一个按钮：New Ordering，点击后可以选择plan重新下新订单.(kanny)
* 所有用户类型的order如果账单到期则suspended该order.(steven)
* 将invoice状态显示的badge的Pending改为Processing.(steven)
* 显示invoice的controller里头的pending都要改成processing.(steven)
* 将customer invoice界面Make Payment的pending选项改成processing，customer脚本界面的make payment的pending也改成processing，数据库invoice的payment_status的pending也统一改成processing.(steven)


demand version 1.8.3 2014-07-21

* [在Provision列表里判断如果order状态是rfs则显示RFS Date.](steven)
* [Add Chart(Invoice) page to view every month's paid_amount, balance, credit, void balance's statistics.](steven)
* [Add Chart(Transaction) page to view every month and most recent week transaction's statistics.](steven)
* [修改sales的online ordering list切换其他sales的url传参方式，将通过parameter传参改为通过url传参.](steven)

demand version 1.8.2 2014-07-18

* [term和no-term的invoice里plan的description日期都从(账单生成日+5天-1月+1天)到(账单生成日+5天).](steven)
* [账单里plan的description填写该plan所收取费用的服务起始和终止日期.](steven)


demand version 1.8.1 2014-07-17

###Important Level (High)
* [添加赠送Local，National，Mobile，International的拨打分钟数.](steven)
* [参照Callplus拨打记录的出法根据juristiction字段出Chorus准确的拨打记录.](steven)
* [将Chorus账单的Juristiction也记录到tm_customer_call_record表中，L,O,N,I分别代表Local,OTH NETWORK, National, International.](steven)


demand version 1.7.2 2014-07-16

###Important Level (High)
* [customer后台所有invoice的balance都用括号扩住并加红.](steven)
* [tm_customer表加一个md5_password字段，在前台，后台，sales下单，客户修改密码，我们后台帮客户修改密码时用DigestUtils.md5Hex("")方法加密customer密码后并存入该字段.](steven)
* [invoice和ordering online邮件模版里分别添加一个链接：ordering online的是(域名/customer/home/客户id/加密后的登录密码+3位随机字符串)，invoice的是(域名/customer/billing/1/客户id/加密后的登录密码+3位随机字符串)，点击后传入的客户id与customer的id匹配及去掉后3位随机字符串的加密密码与md5_password匹配则跳转至customer登录后的相应界面，否则跳转至登录页.](steven)

###Important Level (Medium)
* [添加一个重新生成Ordering Form的按钮，点击确认后根据order的detail重新生成该Ordering Form.](steven)
* Add Detail里添加两个选项：Plan和Item，Plan需要写name, type, plan type, plan sort, price, flow，Item有几种：Broadband New Connection, Hardware(steven)
* [invoice列表的Make Payment添加一个Avoid This Invoice选项，点击确认后该账单status变为void状态.](steven)

###Important Level (Normal)
* 发invoice的email使用新添加的accounts@cyberpark.co.nz邮箱，发faulty回复邮件使用provision@cyberpark.co.nz邮箱.(steven)
* 后台留言板添加发短信和发邮件功能，将David给的回复模版加入可选模版.(steven)
* 目前Contact-Us和内部人员后台留言的选项有:fualty, billing, hardware issue, application, booking appointment.(steven)
* [添加一个内部人员留言表(Ticket系统)，记录打电话客户的cellphone, email, name，都是非必填，description必填，再加个customer_id字段显示时根据该字段区分New Customer和Existing Customer.](steven)
* 在Customer Service Record界面点击添加Service Record时选择@后面列出所有非sales的用户，将选定的用户id迭代记录到mention字段中，在这之前user的id要全部改成以1000开头以防模糊匹配出多条类似id的用户.(steven)
* 添加一个Mentioned About Me，tm_customer_service_record表的mention字段储存@到的所有user的id.(steven)


demand version 1.7.1 2014-07-15

###Important Level (High)
* 所有order的最近一张invoice为unpaid且invoice逾期日期为当天的所有账单发短信告知用户该付款了，并且将order状态变成suspended.(steven)
* 添加一个界面，ticket系统，可以更改，有状态，类型，如果存在该用户，则让其选择并关联上，可以追加答案，类似customer service record.(steven)

###Important Level (Medium)
* [添加一个界面，查看各种月份下invoice各数值的统计数据.](steven)

###Important Level (Normal)
* [RFS Date为空时对应的Save按钮变为红色，否则显示绿色.](steven)
* [如果belongs to为空则显示online.](steven)


demand version 1.6.8 2014-07-14

* [更正View Online Orders界面列表的显示样式以及Optional Request的modal显示内容.](steven)
* [user views page add **Set Operation**, add **Provision Notice** option when click Yes then *is_provision* is true, else false.](steven)
* [user page add **Provision Notice** checkbox, **checked** means true, unchecked means false.](steven)

demand version 1.6.7 2014-07-13

* [Changing all PDF's date format from yyyy-MM-dd style to dd(th)-MM-yyyy.](steven)
* [Writing various operating documents for CyberPark Management System.](steven)
* Writing whole operating process from customer DPS ordering to provision and accountant.(steven)
* Writing whole operating process from customer Bank Deposit ordering to provision and accountant.(steven)

demand version 1.6.6 2014-07-11

* [Chorus拨打账单分析并根据里面的Juristiction字段来出各个地域的拨打话费.](steven)
* [Chorus的Business Local Call是AccountName为SME ACCOUNT且RecordType为T3的记录，需要计算到CallingRecord里.](steven)
* [做个定时器，取出所有pending-warning状态且order_create_date为4天前的order，将order状态改为void，并且提示客户由下单5天还未付款，该订单已被void.](steven)
* [做个定时器，取出所有pending状态且order_create_date为2天前的order，将order状态改为pending-warning，并且发送一封附带ordering form的提醒邮件.](steven)
* [change *order_status* to *rfs* while changing the **RFS Date**.](steven)

demand version 1.6.5 2014-07-10

* [编写客户服务录列表页，按服务记录创建日期降序排列.](steven)
* [order界面顶部添加一个按钮：Pay Off This Order，若客户不是DPS的方式下单，当accountant收到客户的款项则需要点击该按钮。作用是将该order的应付金额转成customer的account credit，并将order状态改为paid，以供Service Given时Service With Invoice按钮扣除account credit之用.](steven)
* [According to the complexity of **ccr**'s structure, try interweave filtering *residential*'s **local call** logic structure.](steven)
* [According to the complexity of **ccr**'s structure, try interweave presenting **national calling minutes** logic structure.](steven)
 
demand version 1.6.1 2014-07-09

* [给order设置多个状态属性，这是一个非常重要的需求。](kanny)
* 给查询流量，添加查询功能，统计每天总流量

demand version 1.6.0 2014-07-08

* [10号需要手动发账单邮件的order：700087，700088，700089，700090，700091，700093。这些是早期签下的order，我,Gavin于08-07-2014将其全部录入CyberPark后台管理系统.](steven)

demand version 1.6.0 2014-07-07

* [create *sms template* for *provision* and *accountant* team.](steven)
* [create *unpaid email template* for **Service Given** action.](steven)
* [基于application form的PDF模版，衍生出两种新模版：Ordering Form，Receipt](steven)
* [后台order界面顶部添加两个按钮：Download Ordering Form，Download Ordering Receipt.](steven)
* [添加一个字段next_invoice_create_date_flag，定位每个月的当天，取出order后根据该日期减7天存入next_invoice_create_date.](steven)
* [修改出no term和no ddpay的invoice的next invoice create date不减7天.](steven)
* [modify *prepay* **Ordering** action's *email template contents*.](steven)
* [点Save Service Given的时候，如果不是DDPay并且是termed的order或者是No Term的order则设置next_invoice_create_date_flag为服务开通日+1个月，并且设置next_invoice_create_date为服务开通日+1个月零7天.](steven)
* [点Save Service Given的时候，弹出按钮组，选择Service Only则逻辑不变，若选择Service With Invoice则通过余额来抵消账单然后通知客户你的账单余额已通过账户余额抵消了.](steven)
* 前台选plan时选非一个月的plan则提示他该plan的discount有多少个percentage.(kanny)
* [在前台下单点击checkout时弹出一个气泡，里面有两个按钮：pay by dps，bank deposit(I'll do it by myself, in 5 working days).](kanny)
* [前台下订单所扣款项数额不立即抵消账单，而是存储在客户的credit里，等到Service Given给完才会用客户credit余额抵消账单.](kanny)
* 下订单时发送短信给provision和accountant.(kanny)

demand version 1.5.1 2014-07-05

* [由于个人用户与商业用户显示名称所用字段不一，需修改邮件模版的客户名称匹配逻辑.](steven)

demand version 1.5.0 2014-07-04

* commission的出法，再商议.(steven)
* [完善Early Termination Charge界面发送email的功能，重复测试.](steven)
* 完善Termination Refund界面发送email的功能，重复测试.(steven)
* [添加Early Termination Charge邮件发送模版.](steven)
* 添加Termination Refund邮件发送模版.(steven)

demand version 1.4.9 2014-07-03

* [新建一张表，用来代替原来记录Calling Record的表，由于Chorus和CallPlus的字段个数及类型不同，需编写将CallPlus记录导入表的逻辑代码，并调整Chorus导入表的逻辑.](steven)
* [Calling Record View界面将Chorus和Callplus用badge来完成分开显示.](steven)
* [由于CallPlus号码存储格式与Chorus有区别，需修改在出账时的区号匹配算法.](steven)
* 能够更改order状态的地方尽量都保存状态改变操作记录.(steven)

demand version 1.4.8 2014-07-02

* [No Term填写客户信息界面头部加一条提示信息：All No Term Plans Won't Provide Free Router, You Can Purchase A Router Which Had Been Listed Below. You Can Get A Free Router From <a href="termed plan的链接">Here</a>.](kanny)
* [更改termed及no term订单的出账逻辑，出termed账单条件是termed且is_ddpay为true，出no term账单条件是no term或termed且is_ddpay为false.](steven)
* [更改Service Given的逻辑，如果是termed且is_ddpay为空或者is_ddpay不为空且is_ddpay不为true则给予next_invoice_create_date日期.](steven)
 
demand version 1.4.7 2014-07-01

* [首页添加一个voucher checking功能，任何人都可以在该界面上检查自己的voucher是否可用](steven)
 
demand version 1.4.6 2014-06-27

* 
* [调整customer invoice的Make Payment按钮按下后显示的dropdown位置](steven)
 
demand version 1.4.5 2014-06-26

* 前台做一个CyberPark Terminology界面，里面列出所有客户可能不懂的术语，都写在扩展卡里，What is Voucher：描述什么是Voucher。以及其他一些客户可能不明白的术语(kanny)
* 前台客户进入topup界面选择Join Plan时弹出气泡框显示两个选项：Voucher,Online Defray。选Voucher则弹出填写序号和卡密的输入框，如果到数据库中匹配了则跳转至填写信息页面继续填写客户信息，否则告知该序号卡密组合不匹配，请重试。
* tm_voucher_banned_list表添加一个last_attempts_date字段，在客户登陆时判断如果其last_attempts_date小于当前日期则将其attempt_times置0(steven)
* [新建一张tm_voucher_banned_list表，在客户提交卡密时查询该表是否存在该客户记录，如果返回null则继续否则提示“You had been banned for being attempted brute force”，条件为客户id且禁止时间小于今天](steven)
* [通过banned list验证后，接下来判断输入的pin number是否正确，如果不正确则判断banned list表是否有该用户，如果有则更新尝试次数+1，如果没有则插入该记录，并返回提示“You have x time(s) to try! If you have tried 3 times incorrect then you will temporarily be blocked into voucher banned list. But this won't affect your other operations.”](steven)
* [在voucher表里添加一个post_to字段，用来记录充值卡的配送点，记录配送点时填写配送点以及起始和结束卡号，更新post_to时判断大于等于起始且小于等于结束范围内的卡号](steven)
 
demand version 1.4.4 2014-06-24

* [transaction表添加一个字段，将后台付款时将操作者id存至transaction表](steven)
* [在Billing模块中开发View Invoice界面，有5个badge：unpaid,pending,not paid off,paid,order no invoice](steven)
 
demand version 1.4.3 2014-06-23

* [customer order的invoice的make payment添加一个Credit Card选项，原理和DDPay无异，在设置transaction类型时改成Credit Card](steven)
* [customer order的invoice的make payment添加一个A2A（Account2Account）选项，原理和DDPay无异，在设置transaction类型时改成Account2Account](steven)
* [客户后台界面的invoice的make payment功能，添加一个Account Credit选项，客人点击后在controller里判断，如果cudtomer的credit不够的话，提示客人不够钱付款，如果钱够的话则将credit减去balance，invoice改变status为paid以及amount_paid的值, balance为0并且更新invoice，剩下的credit覆盖原来的credit并且更新customer，transaction的process _ay是"Account Credit# - "+customer_id，currency_input是"Account Credit"](steven)

 
demand version 1.4.2 2014-06-19
 
* [点击Regenerate most recent invoice按钮时，后台判断最新账单是否已付款，如果未付则逻辑不变，如果状态为paid并且create_date不是当月则生成新的账单并且不包含当月plan的月租费](steven)
* [修改DDPay的逻辑,将按payable付款改成按balance付款](steven)
* [每个invoice都不叠加上个月invoice的balance](steven)
* [重新生成invoice时如果不是生成新的invoice则保留原invoice的create以及due date](steven))
* [DDPay支付没有问题，账单逻辑大改动之后Cash支付会叠加上一张账单的detail，检查为什么会出现这种情况并解决](steven)
* [每月20号定时执行overdue penalty定时器，判断取得invoice due date在前3个月内至1个月之前的所有状态为非paid的账单加一个overdue penalty到detail中并且更新payable及final payable以及balance](steven)
* [添加一张tm_customer_service_record表记录服务客户的一些note，字段包括id,customer_id,user_id,description,create_date](steven)
* [invoice加一个payment_status字段，用来记录该invoice的付款状态，如果billing正在付款的途中则他会将其改变成pending状态，显示在Invoice的status后面](steven)
* [检查并调试plan-no-term的invoice生成代码，主要检查final_payable以及total credit的最终值](steven)
* [检查并调试plan-term的invoice下一次生成账单的代码，重新生成plan-term的invoice功能已完成，所以要确保下一次生成的账单是准确无误的](steven)
 
 
demand version 1.4.0 2014-06-18

* [customer invoice表添加一个final_payable_amount，存储用discount抵消后的最终应付金额](steven)
* [invoice PDF的recent transaction里的布局做个调整，添加一行总discount的detail，以及一行final_payable_amount的detail](steven)
* [复查invoice的Recent Trancation以及This Invoice Summary的总金额逻辑](steven)
* [所有term invoice在生成下一张账单的时候如果上一张账单不是paid，那么把上一张账单的balance加过来并且状态改为discard](steven)
* [将early termination charge和termination refund分别做成order的detail](steven)
* [在生成invoice的时候判断并计算early-termination-charge以及termination-refund类型的invoice detail](steven)
* [优化页面Header部分的Description以及Keywords内容，改进搜索引擎优化效果](steven)
 
demand version 1.3.4 2014-06-17

* //View Termination Refunde界面添加邮件发送功能(steven)
* //View Termination Refunde界面添加两个切换已发送和未发送的badge(steven)
* //View Early Termination Charge界面添加邮件发送功能(steven)
* //View Early Termination Charge界面添加两个切换已发送和未发送的badge(steven)
* order表添加一个termination_date字段，customer order切换至cancel状态时根据service given日期和手动记录的termination date相差月数自动出Early Termination Charge Invoice(steven)
 
 
demand version 1.3.3 2014-06-16

* [根据Keith给的资料，在计算拨打记录到invoice时加入判断，判断该区号的国家是否存在（座机，手机同费用）的列表中，如果存在则费率表中该国家的手机以及座机分钟数都可以用赠送的分钟数来抵消，否则只能抵消该国家的非Mobile的分钟数。](steven)
 
demand version 1.3.2 2014-06-13

* [给order detail加discount时记录user_id，记录是谁给的discount](steven)
* [regenerate invoice PDF时保持之前的invoice_id和create_date以及due_date](steven)
 
demand version 1.3.1 2014-06-11

* [检查一下为什么手动出账单没有将order的discount去掉](steven)
* [做early termination charge之前做好该表的一个参数表，表名为最后加上parameter以区分是该表的参数表](steven)
* [billing模块做一个early termination charge的界面，列出所有的记录，在operation那一列给出一个Send early termination charge email提示的email图标功能](steven)
* 新建一张记录email和sms的表，记录所有已发送的email以及sms，记录时将notification的sort插入至该表的sort中(steven)
 
demand version 1.3.0 2014-06-10

* [调试并解决手机以27开头的费率换算问题](steven)
* [审查重新生成order的application form时的逻辑，看是否能加入discount的判断](steven)
* 未付款账单，全部罗列到billing模块里，列出所有的order的最近一张invoice(steven)
* [检查为什么sales下的单不显示在belongs to里](steven)
* [复审出账时invoice拨打记录的rate，检查各rate是否正常转化](steven)
* [customer order界面添加手动生成下一张term invoice的功能。](steven)
* [customer order界面添加手动重新生成最近invoice的功能，先删除最近invoice的PDF再删除该invoice信息和相关invoice detail的信息，最后重新生成最近一张invoice及PDF](steven)
* [从服务器拷贝所有order和invoice信息，重新在本地调整后发出](steven)
* [Arun Vanama的invoice PDF上传到服务器](steven)
* [检查发送term invoice的定时器，为何串数据](steven)
* [重新生成order，修复出discount类型detail问题](steven)
 
 
demand version 1.2.5 2014-06-09

* [开发手动出termed的invoice功能](steven)
 
demand version 1.2.4 2014-06-03

* [后台首页ContactUs后面显示未处理数量](steven)
 
demand version 1.2.3 2014-05-30

* [order表加个ddpay_form字段，界面上加一行操作，上传，下载功能。](steven)
* [order表加一个user_id字段，记录后台下单人员的user_id，order界面判断如果sale_id不为空则该user_name显示(显示user_role)，如果user_id不为空则显示该user_name(显示user_role)](steven)
 
demand version 1.2.2 2014-05-27

* discount 根据过期日期加入改成根据单位加入，单位为0则不加入，单位没加一次减一。(steven)
* [order里添加重新生成order application form，上传application form。](steven)
* order下面添加两个存放文件路径的字段，一个voice record，一个documentation record，用来存放客户的录音。(steven)
* 下单时如果pstn或地址已存在数据库中则不予以通过。(kanny)

demand version 1.2.1 2014-05-26

* [customer order界面每提交一个请求都要返回提示结果，无论成功失败。](steven)
* 前台查询地址框之上添加文字，Class Mode时显示“First text entry box is your flat/house number, second one is for street, suburb, city”。Auto-Match Mode时显示“Please start with your flat/house number first, e.g. type in "863A Domi" will automatically show "863A Dominion Road" at below for choose。”
* 前台查询地址框添加两个单选“Classic Mode”，“Auto-Match Mode”。自动匹配为现有查询模式，经典模式房屋号与地址名称分开。选Classic Mode隐藏自动匹配框并显示经典输入框。选Auto-Match Mode隐藏经典输入框并显示自动匹配框。
* 前台用户下单后点check out弹出温馨提示框，提示如果用户“若在支付过程中有任何疑问，请将该情况告知我们或等待我们的回访”。支付失败页面提示“亲爱的customer_name！不好意思，请将该情况告知我们或您可以等待我们的回访！”(Kanny)
* 实现后台下单如果order_type为order-no-term或order-topup则将order_detail迭代至invoice_detail内，然后paid为payable，balance为0d
* 调通xero接口，每次生成invoice都传送到xero系统，让xero客户去催款。
 
demand version 1.2.0 2014-05-15

* [在customer order里提交service giving是判断SV/CV Lan不为空才能提交。](steven)
* 提交后通过短信模版将SV/CV Lan信息以及service giving date发送到胡洁和Nathan手机。(steven)
* [在order表里添加一个direct_debit_pdf_path字段，在customer order里添加一个上传direct debit copy PDF功能，操作员可以提交客户签字的direct debit表单PDF](steven)
* [在order表里添加一个rfs_date(Ready For Service Date)字段，在customer order里添加一个填写并提交rfs_date日期的输入框。](steven)
* [order model增加broadband_asid属性，添加一个model叫ManualDefrayLog，添加其Mapper文件及相关操作类](steven)
* [order detail的next invoice create date判断如果不为空且order type='order-term'则显示后台传过来的存储在customer的params里的plan_term_invoice_create_date的值](steven)
* 设计一个界面用来实现上传PSTN数据到数据库里。(kanny)
* [customer的invoice detail里Make Payment功能里的Cash点击确认后将该invoice的status变成paid或not_pay_off，payable减去传进来的数目赋给paid然后payable减去paid赋给balance，将该记录存至transaction。](steven)
* [customer的invoice detail里Make Payment功能里的DDPay点击确认后将该invoice的status变成paid，payable赋给paid然后balance值赋为0d，将该记录存至transaction。](steven)
* order表加个previous_provider_invoice字段，sale下单时选择transition则显示上传功能，上传文件的保存路径存放到pervious_provider_invoice字段中(steven)
* [在每月的10号，11 am，以email的形式推送生成的plan-term的invoice pdf](steven)
* [修改customer order, 添加更多的属性，把sv cv和 service giving分开更改](steven)
* 给provision team添加feedback to sales功能，在sales模块的view sales里可以给某一个sale发送到他的手机，或者邮箱，记录内容及发送时间到数据库(steven)
* [customer edit界面的order,如果有sale显示sale,可以当前订单的所有者改变成其他sales](steven)
* customer edit的order information界面，现实order的option request，并且可以修改(steven)
* [重构客户购买订购流程](kanny)
* [重构sale下单流程](kanny)
* [重构管理员下单流程](kanny)
* [更新出invoice算法，判断如果是term的order则在Service Giving的时候不出账单，再写一个Quartz的Schedule类，每个月10号获取类型为term的订单并且出账单，判断当前是否是第一张账单，如果是第一张账单判断该customer是business还是personal，如果是business则总价加上15%GST，总价除以当月的天数取得单天价格再乘以Service Giving开通后本月剩余天数再加上该term的包月价格得到该invoice。](steven)
* [invoicePDF和orderPDF里的12%businessGST要改成15%](steven)
* [管理员有权限将某一张单转变到某个sales名下，改变账单sales id 即可，将此功能添加至customer order界面](steven)
* [ordering online界面将显示sales id更改为显示sales name](steven)
* [cyberpark home 做seo初步工作](steven)

demand version 1.1.6 2014-05-14

* topup notification提醒邮件中附上该order的下一次payment支付链接，用户点击后可直接进入支付界面，无需登录帐号和密码(steven)
* [order里添加order-topup类型判断，如果是order-topup则设置当天日期+7天赋给order_due，quartz里定时判断如果当前+1或+2天后等于order_due，则通过email和sms提醒该用户topup服务即将到期，让客户续费](steven)

demand version 1.1.5 2014-05-12
 
* [invoice重构代码，so important!](steven)
* [sales模块在线下单功能，在确认生成order PDF浮窗里添加一个optional_request输入框，提交sales额外请求。](steven)
* [customer order 界面加一个功能，添加new installation按钮，点击可以为该order增加金额(用添加Debit替代...)](steven)

demand version 1.1.4 2014-05-06
 
* 写plan query，方便查询plan(kanny, 2014-05-07)
* [比如说，那个忘记密码，发手机短信的，还是要加验证码，不然一个手机可以无限发](steven, 2014-05-06)

demand version 1.1.3 2014-04-24

* [online ordering list如果该order没有credit，则显示一个添加credit的图标链接到添加credit界面](steven)
* 数据库加一个字段，用来限制客户在没有修改随即密码的情况下频繁使用忘记密码功能(kanny)
* [制作Contact Us动态加载客户在customer的contact us界面新提交的request的功能，客户提交时需要输入验证码](steven)
* [create customer, company detail的地址框都加上google map auto complete](steven)
* [customer首页上方添加follow us on(twitter, facebook, g+, instagram, Pinterest)](steven)
* [重新完善cyberpark首页设计](kanny)
* [客户忘记密码可以点击forgotten password?来选择是通过email或sms来获取随机密码](steven)
* [sale模块下单后随机生成密码插入customer属性存入数据库并将该随即密码发送给客户](steven)

demand version 1.1.2 2014-04-21

* [company_detail创建多种类型的T&C字段，至少4个，然后每一个T&C在一个可以折叠的panel里便于修改](steven)
* [完成personal plan-term的页面](kanny)
* [重构后台创建customer页面](kanny)
* [重构sale签约界面](kanny)
* [完善各种界面的连接](kanny)
* [完善各种T&C排版和布局](kanny)
* [完善公司介绍页面](kanny)

demand version 1.1.1 2014-04-16

* [user界面权限区域每个模块前都加上全选框](STEVEN)

demand version 1.1.0 2014-04-04

* [customer下添加organization表单从customer.organization里取出数据，如果为business则显示该表单，personal则不显示](steven)
* [sale模块加个列表如果操作的user角色为administrator则将角色为sale的user迭代进下拉菜单，如果为sale角色则屏蔽下拉菜单其只能查看自己的signed和unsigned的order和credit PDF](steven)
* [修改前端，注册购买页面，用mobile and email代替登入](kanny)
* [给购买流添加导航](kanny)
* [更换dps支付页面](kanny)
* [修改用户登入后所看到的界面](kanny)
* [order information界面，添加属性，可以下载签约的PDF](steven)

demand version 1.0.9 2014-04-04

* [customer order表添加一个客户签字的字段signature，已签字=signed、未签字=unsigned](kanny)

demand version 1.0.8 2014-04-03

* [customer order detail里voip加和pstn一样的修改号码的按钮和功能](steven)
* [界面上在customer order detail在table处用style将字体设为12px](kanny)
* [添加organization表，字段：org_type、org_trading_name、org_register_no、org_incoporate_date、org_trading_months](kanny)
* [organization表再加holder相关字段：holder_name、holder_job_title、holder_phone、holder_email](kanny)
* [organization表添加一个customer_id字段，和customer表关联，如果customer是business类型则可以通过id到organization表里查出相应数据](kanny)

demand version 1.0.7 2014-04-02

* [界面上补全customer以及customer order新添的字段](steven)
* [customer 加字段](kanny)
* [customerOrder 加字段](kanny)
* [添加customer credit表](kanny)

demand version 1.0.6 2014-03-31

* [customer update按钮旁加个delete customer按钮，提示操作员该操作将永久删除所有跟客户相关的信息，（customer、order、order detail、invoice、invoice detail、transaction）](STEVEN)
* [在detail处判断detail_type为pstn类型的行如果pstn_number不为空则在detail_name后显示(pstn_number)否则显示(Number is Empty)](STEVEN)
* [在customer order下的detail里detail_type为pstn则在后面加个Update PSTN Number来修改pstn_number字段](STEVEN)
* 在customer order标题加个Add Order跳转到create customer的第二个界面，选plan然后继续下单(STEVEN)
* [制作与term里图像一样布局的PDF，用以存放下单后生成的用户信息及订单细目](STEVEN)
* [填写好支付信息后点Next生成PDF，供客户签字，之后通过一个上传的界面上传至系统，跟相关order关联](STEVEN)
* 选择支付方式后跳转到填写支付信息界面
* online-ordering界面：界面1（填写基本信息），界面2（选plan），界面3（confirm界面，列出order的信息，显示选择Credit Card付款）
* [添加sale模块](steven)
* [给User添加sale权限](steven)
* order_status字段添加两个状态：stop，close
* VoIP信息
* [plan的NON-NAKED改用CLOTHING](STEVEN)

demand version 1.0.5 2014-03-28

* [修改provision的order层显示，压缩customer信息，order信息排版和customer里的一致，order detail显示字段和customer里一致但多一个操作列](steven)
* [user权限限制有bug, tm的chart没有勾选上，一样有权利进入观看](steven)
* [把后台登入换成ajax](kanny)
* [把plan，hareware功能换成ajax](steven)
* customer支付账单功能，user支付order功能
* 设定cyberpark的term condition
* 设计about cyberpark界面

demand version 1.0.4 2014-03-27

* [屏蔽下一次自动生成账单的函数，用createInvoicePDF测试，是否可以代替](steven)
* [添加删除discount按钮的提示，提示信息为remove](steven)
* [修改service given 的 edit bug](steven)
* [给customer order detial，的order due,添加一个，日历input，不要忘了插入provision记录](steven)
* [给customer order detial，的order status，添加一个，下拉选择框，这样管理员就可以某一张订单的状态，不要忘了插入provision记录](steven)
* [修正后台创建用户下单时一些detail没有显示price和unit值的问题，price为null则0d，unit为null则1](steven)

demand version 1.0.3 2014-03-24

* [后台添加hardware删除功能](steven)
* [后天添加plan删除功能，改变状态功能，group,type, sort, status](steven)
* [继续下次出账单的时间，改为在月份单位上增加，而不是在天单位上增加](steven)
* [后台customer inovice部分，给还没有付款的invoice添加支付功能，支付类型为罗列出来的5种](steven)
* [后台customer order detail部分，添加，一种discount detial类型，可以输入detail_name, detail_price, detail_unit, detail_expired,提交后刷新页面](steven)
* 需要支持5种，支付方式，dps-creditcard,dps-accounttoaccount,paypal,dd,topup-voucher,cash
* [google map address in company-detail table](kanny)
* [pppoe_loginname, pppoe_password, in customer order](kanny)

demand version 1.0.2 2014-03-17

* [前后台购买plan时，在checkout前，要勾选company的term condition](kanny)
* 前后台增加voucher支付方式
* [添加tm_voucher表，记录所有voucher](kanny)
* [plan表添加，pstn_count字段，表明此plan默认配有几根pstn电话线，pstn_rental_amount,单条pstn的租用费用](kanny)
* [customerOrder表，添加, pstn_count,默认是plan的pstn数量，可另外添加数量，pstn_rental_amount](kanny)
* [罗列出所有provision的log](STEVEN-6)
* 在CRM下面新增添加customer功能，三个页面：customer创建页，plan创建页，invoice查看页。(STEVEN-1.1)

demand version 1.0.1 2014-03-16

* [用filter控制购买plan的几个页面](kanny)
* [给customer_order 添加一个 order due 字段，用此字段判断这个order的服务周期](STEVEN-5，字段已被Kanny添加)
* [加入最新版本的datepicker for boostrap，让所有时间字段可选](kanny)
* [添加user权限，每一个模块是一个大的权限区，每一个某块下的功能页面是一个小的功能区。此功能区域用filter控制进入各个功能页面](STEVEN-2)
* [利用chart.js做出用户注册图标，标注天，周，月，用户注册情况](STEVEN-4)
* [在customer登入后，可以进行支付，topup](kanny)
* 用户可以对自己购买的plan，进行change, cancel, stop等操作(kanny)
* [后台开始编写，创建customer, 创建customer的plan, 管理员可帮助customer进行支付操作](STEVEN-1)
* [完善各类email模版，SMS模版](STEVEN-3)
* [用户，注册成功，make payment成功，top up成功，change, cancel, stop他的order，需要发送email和sms](kanny)
* [管理员帮助客户支付，启动某一个用户的order, change, cancel, stop，需要发送email](kanny)
* [创建post_pay类型的order, 加入pstn电话的相关表](kanny)
* [调用google map api，开始支持地址自动匹配](steven)
