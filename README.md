broadband 1.0.x 2014
=========

Total Mobile Solution Internet Service Web Project

###Specification

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
 
 
demand version 1.0.21 2014-06-17

* View Termination Refunde界面添加邮件发送功能(steven)
* View Termination Refunde界面添加两个切换已发送和未发送的badge(steven)
* View Early Termination Charge界面添加邮件发送功能(steven)
* View Early Termination Charge界面添加两个切换已发送和未发送的badge(steven)
* order表添加一个termination_date字段，customer order切换至cancel状态时根据service given日期和手动记录的termination date相差月数自动出Early Termination Charge Invoice(steven)
 
 
demand version 1.0.21 2014-06-16

* 根据Keith给的资料，在计算拨打记录到invoice时加入判断，判断该区号的国家是否存在（座机，手机同费用）的列表中，如果存在则费率表中该国家的手机以及座机分钟数都可以用赠送的分钟数来抵消，否则只能抵消该国家的非Mobile的分钟数。(steven)
 
demand version 1.0.21 2014-06-13

* [给order detail加discount时记录user_id，记录是谁给的discount](steven)
* [regenerate invoice PDF时保持之前的invoice_id和create_date以及due_date](steven)
 
demand version 1.0.21 2014-06-11

* [检查一下为什么手动出账单没有将order的discount去掉](steven)
* [做early termination charge之前做好该表的一个参数表，表名为最后加上parameter以区分是该表的参数表](steven)
* billing模块做一个early termination charge的界面，列出所有的记录，在operation那一列给出一个Send early termination charge email提示的email图标功能(steven)
* 新建一张记录email和sms的表，记录所有已发送的email以及sms，记录时将notification的sort插入至该表的sort中(steven)
 
demand version 1.0.20 2014-06-10

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
 
 
demand version 1.0.20 2014-06-09

* [开发手动出termed的invoice功能](steven)
 
demand version 1.0.10 2014-06-03

* [后台首页ContactUs后面显示未处理数量](steven)
 
demand version 1.0.9 2014-05-30

* [order表加个ddpay_form字段，界面上加一行操作，上传，下载功能。](steven)
* [order表加一个user_id字段，记录后台下单人员的user_id，order界面判断如果sale_id不为空则该user_name显示(显示user_role)，如果user_id不为空则显示该user_name(显示user_role)](steven)
 
demand version 1.0.9 2014-05-27

* discount 根据过期日期加入改成根据单位加入，单位为0则不加入，单位没加一次减一。(steven)
* [order里添加重新生成order application form，上传application form。](steven)
* order下面添加两个存放文件路径的字段，一个voice record，一个documentation record，用来存放客户的录音。(steven)
* 下单时如果pstn或地址已存在数据库中则不予以通过。(kanny)

demand version 1.0.9 2014-05-26

* [customer order界面每提交一个请求都要返回提示结果，无论成功失败。](steven)
* 前台查询地址框之上添加文字，Class Mode时显示“First text entry box is your flat/house number, second one is for street, suburb, city”。Auto-Match Mode时显示“Please start with your flat/house number first, e.g. type in "863A Domi" will automatically show "863A Dominion Road" at below for choose。”
* 前台查询地址框添加两个单选“Classic Mode”，“Auto-Match Mode”。自动匹配为现有查询模式，经典模式房屋号与地址名称分开。选Classic Mode隐藏自动匹配框并显示经典输入框。选Auto-Match Mode隐藏经典输入框并显示自动匹配框。
* 前台用户下单后点check out弹出温馨提示框，提示如果用户“若在支付过程中有任何疑问，请将该情况告知我们或等待我们的回访”。支付失败页面提示“亲爱的customer_name！不好意思，请将该情况告知我们或您可以等待我们的回访！”(Kanny)
* 实现后台下单如果order_type为order-no-term或order-topup则将order_detail迭代至invoice_detail内，然后paid为payable，balance为0d
* 调通xero接口，每次生成invoice都传送到xero系统，让xero客户去催款。
 
demand version 1.0.9 2014-05-15

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

demand version 1.0.8 2014-05-14

* topup notification提醒邮件中附上该order的下一次payment支付链接，用户点击后可直接进入支付界面，无需登录帐号和密码(steven)
* [order里添加order-topup类型判断，如果是order-topup则设置当天日期+7天赋给order_due，quartz里定时判断如果当前+1或+2天后等于order_due，则通过email和sms提醒该用户topup服务即将到期，让客户续费](steven)

demand version 1.0.8 2014-05-12
 
* [invoice重构代码，so important!](steven)
* [sales模块在线下单功能，在确认生成order PDF浮窗里添加一个optional_request输入框，提交sales额外请求。](steven)
* customer order 界面加一个功能，添加new installation按钮，点击可以为该order增加金额(待定...)

demand version 1.0.7 2014-05-06
 
* 写plan query，方便查询plan(kanny, 2014-05-07)
* [比如说，那个忘记密码，发手机短信的，还是要加验证码，不然一个手机可以无限发](steven, 2014-05-06)

demand version 1.0.6 2014-04-24

* [online ordering list如果该order没有credit，则显示一个添加credit的图标链接到添加credit界面](steven)
* 数据库加一个字段，用来限制客户在没有修改随即密码的情况下频繁使用忘记密码功能(kanny)
* [制作Contact Us动态加载客户在customer的contact us界面新提交的request的功能，客户提交时需要输入验证码](steven)
* [create customer, company detail的地址框都加上google map auto complete](steven)
* customer首页下方添加follow us on(twitter, facebook, email, youtube)
* [重新完善cyberpark首页设计](kanny)
* [客户忘记密码可以点击forgotten password?来选择是通过email或sms来获取随机密码](steven)
* [sale模块下单后随机生成密码插入customer属性存入数据库并将该随即密码发送给客户](steven)

demand version 1.0.6 2014-04-21

* [company_detail创建多种类型的T&C字段，至少4个，然后每一个T&C在一个可以折叠的panel里便于修改](steven)
* [完成personal plan-term的页面](kanny)
* 重构后台创建customer页面(kanny)
* [重构sale签约界面](kanny)
* [完善各种界面的连接](kanny)
* [完善各种T&C排版和布局](kanny)
* [完善公司介绍页面](kanny)

demand version 1.0.5 2014-04-16

* [user界面权限区域每个模块前都加上全选框](STEVEN)

demand version 1.0.5 2014-04-04

* [customer下添加organization表单从customer.organization里取出数据，如果为business则显示该表单，personal则不显示](steven)
* [sale模块加个列表如果操作的user角色为administrator则将角色为sale的user迭代进下拉菜单，如果为sale角色则屏蔽下拉菜单其只能查看自己的signed和unsigned的order和credit PDF](steven)
* [修改前端，注册购买页面，用mobile and email代替登入](kanny)
* [给购买流添加导航](kanny)
* 更换dps支付页面
* 修改用户登入后所看到的界面
* [order information界面，添加属性，可以下载签约的PDF](steven)

demand version 1.0.4 2014-04-04

* [customer order表添加一个客户签字的字段signature，已签字=signed、未签字=unsigned](kanny)

demand version 1.0.4 2014-04-03

* [customer order detail里voip加和pstn一样的修改号码的按钮和功能](steven)
* [界面上在customer order detail在table处用style将字体设为12px](kanny)
* [添加organization表，字段：org_type、org_trading_name、org_register_no、org_incoporate_date、org_trading_months](kanny)
* [organization表再加holder相关字段：holder_name、holder_job_title、holder_phone、holder_email](kanny)
* [organization表添加一个customer_id字段，和customer表关联，如果customer是business类型则可以通过id到organization表里查出相应数据](kanny)

demand version 1.0.4 2014-04-02

* [界面上补全customer以及customer order新添的字段](steven)
* [customer 加字段](kanny)
* [customerOrder 加字段](kanny)
* [添加customer credit表](kanny)

demand version 1.0.4 2014-03-31

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

demand version 1.0.4 2014-03-28

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
