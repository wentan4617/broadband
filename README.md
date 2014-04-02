broadband 1.0.x 2014
=========

Total Mobile Solution Internet Service Web Project

###Specification

* 所有表单提交的controller方法，如果是要页面跳转的都要redirect.

demand version 1.0.4 2014-04-02

* customer 加字段
* customerOrder 加字段
* 

demand version 1.0.4 2014-03-31

* [customer update按钮旁加个delete customer按钮，提示操作员该操作将永久删除所有跟客户相关的信息，（customer、order、order detail、invoice、invoice detail、transaction）](STEVEN)
* [在detail处判断detail_type为pstn类型的行如果pstn_number不为空则在detail_name后显示(pstn_number)否则显示(Number is Empty)](STEVEN)
* [在customer order下的detail里detail_type为pstn则在后面加个Update PSTN Number来修改pstn_number字段](STEVEN)
* 在customer order标题加个Add Order跳转到create customer的第二个界面，选plan然后继续下单(STEVEN)
* [制作与term里图像一样布局的PDF，用以存放下单后生成的用户信息及订单细目](STEVEN)
* 填写好支付信息后点Next生成PDF，供客户签字，之后通过一个上传的界面上传至系统，跟相关order关联。
* 选择支付方式后跳转到填写支付信息界面
* online-ordering界面：界面1（填写基本信息），界面2（选plan），界面3（confirm界面，列出order的信息，显示选择Credit Card付款）
* 添加sale模块
* 给User添加sale权限
* order_status字段添加两个状态：stop，close
* VoIP信息
* [plan的NON-NAKED改用CLOTHING](STEVEN)

demand version 1.0.4 2014-03-28

* [修改provision的order层显示，压缩customer信息，order信息排版和customer里的一致，order detail显示字段和customer里一致但多一个操作列](steven)
* [user权限限制有bug, tm的chart没有勾选上，一样有权利进入观看](steven)
* [把后台登入换成ajax](kanny)
* 把plan，hareware功能换成ajax
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
* [在customer登入后，可以进行支付，topup]
* 用户可以对自己购买的plan，进行change, cancel, stop等操作
* 后台开始编写，创建customer, 创建customer的plan, 管理员可帮助customer进行支付操作(STEVEN-1)
* [完善各类email模版，SMS模版](STEVEN-3)
* 用户，注册成功，make payment成功，top up成功，change, cancel, stop他的order，需要发送email和sms
* 管理员帮助客户支付，启动某一个用户的order, change, cancel, stop，需要发送email
* 创建post_pay类型的order, 加入pstn电话的相关表
* [调用google map api，开始支持地址自动匹配](steven)
