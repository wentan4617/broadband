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

demand version 1.6.0 2014-07-07

* 编写发给provision和accountant的短信模版.(steven)
* 基于application form的PDF模版，衍生出两种新模版：Ordering Form，Receipt(steven)
* 后台order界面顶部添加两个按钮：View Ordering Form，View Receipt.(steven)
* 添加一个字段next_invoice_create_date_flag，定位每个月的当天，取出order后根据该日期减7天存入next_invoice_create_date.(steven)
* 修改出no term和no ddpay的invoice的next invoice create date不减7天.(steven)
* 更改prepay下单后的email模版内容.(steven)
* 编写Service Given时选择unpaid的email模版，提醒客户从他余额里.(steven)
* 点Save Service Given的时候，如果不是DDPay或是No Term的order则设置其next_invoice_create_date的同时在该字段日期上加7天赋给next_invoice_create_date_flag.(steven)
* 点Save Service Given的时候，弹出气泡框，选择invoice paid则逻辑不变，如果选择invoice unpaid则通过余额来抵消账单然后通知客户你的账单余额已通过账户余额抵消了.(steven)
* 前台选plan时选非一个月的plan则提示他该plan的discount有多少个percentage.(kanny)
* 在前台下单点击checkout时弹出一个气泡，里面有两个按钮：pay by dps，bank deposit(I'll do it by myself, in 3 working days).(kanny)
* 前台下订单所扣款项数额不立即抵消账单，而是存储在客户的credit里，等到Service Given给完才会用客户credit余额抵消账单.(kanny)
* 下订单时发送短信给provision和accounting.(kanny)

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
* customer order 界面加一个功能，添加new installation按钮，点击可以为该order增加金额(待定...)

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
* 重构后台创建customer页面(kanny)
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
* 修改用户登入后所看到的界面
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
