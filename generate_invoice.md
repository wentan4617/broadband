Invoice Generate 逻辑总纲
==========================

###当前逻辑结构

目前 CyberPark 推出 3种 Plan, 分别为 plan-topup, plan-no-term, plan-term.
这三种plan购买后，所生成的order, 也分别为 3种， order-topup, order-no-term, order-term
以下生成invoice的逻辑目前只针对order类型为order-term的.

###生成order类型为order-term的invoice的逻辑思路(系统自动)

* 当一个order被设置了Service Giving Date<当前生成时间（yyyy-mm-08 03:00）并且order-type=using的时候，才会被系统查询出来准备出invoice
* 系统查询出所有可以出invoice的order
* （为了系统效率，每月的8号凌晨3点，生成invoice, 每张invoice的due date固定设置为每月20号，在每月的10号，早上11点，以email的形式推送生成的invoice pdf）
* 查询当前order是否有关联的invoice,如果没有说明，接下来要生成的invoice，为此order的第一张invoice
* 如果是第一张invoice，则判断当前order的Service Giving Date是否为当月，
* 判断1：为当月
* 则将order中plan的价格除以当月的天数，计算出每天应该支付的价钱，然后在用每月的天数减去Service Giving Date，计算出这个月还可以使用多少天，再乘以每天的价格，计算出当前月需要为这个plan支付多少钱（这个算法可以封装到一个calculationResidualPrice中）
* 计算出来的这个plan价格作为一条invoice detail, (如果customer为personal的，那么他的order-detail都是incl. gst，为business都是excl. gst.所以最后invoice总价要再乘以1.15)
* 判断2：为上一个月
* 则用calculationResidualPrice函数计算出上个月这个plan的应该需要支付的价格做为一条invoice detail
* 而且还需要把order中每月需要支付的plan的detail也做为一条invoice detail加入到invoice中
* 判断3：当以上两种情况不符合，则系统将不在自动生成此order的invoice，可以return,需要靠（手动生成）
* 如果不是第一张invoice
* 就把order中每月需要支付的plan的detail也做为一条invoice detail加入到invoice中
* 其他的order-detail还是按原有规则加入invoice detail. 
* 每次生成一个order的invoice时，都去检测这张order是否有上个月的pstn电话记录，
* 如果有，将计算出上一个月的pstn电话记录总价做为一个invoice detail加入到当前invoice中

###手动版本
