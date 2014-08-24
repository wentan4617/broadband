-- ****** BEGIN JAVA CLASS ******

-- Controller
    BillingController:
        toInvoice(): GET
        toChartMonthlyInvoiceStatistics(): GET
        toChartAnnuallyInvoiceStatistics(): GET
    CRMController:
        toCustomerView(): GET
        toCustomerOrderCreate(): GET
        orderSave(): GET
        orderCheckout(): POST
        toSignupPayment(): GET
    CRMRestController:
        doCustomerServiceGivingDateEdit(): POST
        doCustomerView(): GET
        doCheckCustomerExistence(): POST
    CustomerController:
        orderCheckout(): POST
        toSignupPayment(): GET
        planOrderDPS(): POST
        planOrderDPS(): GET
    CustomerRestController:
        customerBilling(): GET
        doPlanOrderConfirm(): POST
    SaleController:
        orderSave(): POST

-- PDFCreator
    ApplicationPDFCreator:
        createCustomerOrderBasicInfoTable()
        createCustomerInfoTable()
        createOrderDetailTable()
        createContactDetailsTable()
    CreditPDFCreator:
        createContactDetailsTable()
    EarlyTerminationChargePDFCreator:
        createCustomerBasicInfo()
    InvoicePDFCreator:
        create()
        createCustomerBasicInfo()
        createInvoiceSummary()
    OrderingPDFCreator:
        createCustomerOrderBasicInfoTable()
        createCustomerInfoTable()
        createOrderDetailTable()
    ReceiptPDFCreator:
        createCustomerOrderBasicInfoTable()
        createCustomerInfoTable()
        createOrderDetailTable()
    TerminationRefundPDFCreator:
        createCustomerBasicInfo()

-- Service
    CRMService:
        registerCustomer()
        registerCustomerCalling()
        saveCustomerOrder()
        editCustomer()
        createCustomer()
        sendTermPlanInvoicePDF()
        createInvoiceOverduePenalty()
        createTopupPlanInvoiceByOrder()
        createTermPlanInvoiceByOrder()
        createInvoicePDFBoth()
        serviceGivenPaid()

-- Util
    MailRetriever:
        mailAtValueRetriever(noti, cus)

-- ****** END JAVA CLASS ******


-- ****** BEGIN FRONT PAGE ******

-- broadband-user
    billing Directory:
        invoice-chart-annual.jsp
        invoice-chart.jsp
        invoice-view.jsp
    crm Directory:
        customer-create-business.jsp
        customer-create-personal.jsp
        customer-info.html
        customer-query.html
        customer-view-page.html
        customer-view.jsp
        customer.jsp
        order-confirm.jsp
        order-create.jsp
    data Directory:
        customer-view-page.html
    provision Directory:
        customer-information-form.html
        provision-view.jsp
    sale Directory:
        online-ordering-business-info.jsp
        online-ordering-confirm.jsp
        online-ordering-personal-info.jsp

-- broadband-customer
    customer-billing.html
    customer-billing.jsp
    customer-order-business.jsp
    customer-order-confirm.jsp
    customer-order-personal.jsp
    nav.jsp

-- ****** BEGIN FRONT PAGE ******