package com.tm.broadband.paymentexpress;

import java.io.Serializable;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class PxPostRequest implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String Amount = "";
	private String CardHolderName = "";
	private String CardNumber = "";
	private String BillingId = "";
	private String Cvc2 = "";
	//================================================
	private String Cvc2Presence = "";
	private String DateExpiry = "";
	private String DpsBillingId = "";
	private String DpsTxnRef = "";
	private String EnableAddBillCard = "";
	//=================================================
	private String InputCurrency = "";
	private String MerchantReference = "";
	private String PostUsername = "";
	private String PostPassword = "";
	private String TxnType = "";
	//================================================
	private String TxnData1 = "";
	private String TxnData2 = "";
	private String TxnData3 = "";
	private String TxnId = "";
	private String EnableAvsData = "";
	//=================================================
	private String AvsAction = "";
	private String AvsPostCode = "";
	private String AvsStreetAddress = "";
	private String DateStart = "";
	private String IssueNumber = "";
	//=================================================
	private String Track2 = "";
	
	private String requestXml;

	public PxPostRequest() {
		
	}
	
	private void buildXml() {

		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("Txn");
			doc.appendChild(root);

			Element child;
			Text text;
			
			child = doc.createElement("Amount"); root.appendChild(child);
			text = doc.createTextNode(this.Amount); child.appendChild(text);
			child = doc.createElement("CardHolderName"); root.appendChild(child);
			text = doc.createTextNode(this.CardHolderName); child.appendChild(text);
			child = doc.createElement("CardNumber"); root.appendChild(child);
			text = doc.createTextNode(this.CardNumber); child.appendChild(text);
			child = doc.createElement("BillingId"); root.appendChild(child);
			text = doc.createTextNode(this.BillingId); child.appendChild(text);
			child = doc.createElement("Cvc2"); root.appendChild(child);
			text = doc.createTextNode(this.Cvc2); child.appendChild(text);
			//====================================================================
			child = doc.createElement("Cvc2Presence"); root.appendChild(child);
			text = doc.createTextNode(this.Cvc2Presence); child.appendChild(text);
			child = doc.createElement("DateExpiry"); root.appendChild(child);
			text = doc.createTextNode(this.DateExpiry); child.appendChild(text);
			child = doc.createElement("DpsBillingId"); root.appendChild(child);
			text = doc.createTextNode(this.DpsBillingId); child.appendChild(text);
			child = doc.createElement("DpsTxnRef"); root.appendChild(child);
			text = doc.createTextNode(this.DpsTxnRef); child.appendChild(text);
			child = doc.createElement("EnableAddBillCard"); root.appendChild(child);
			text = doc.createTextNode(this.EnableAddBillCard); child.appendChild(text);
			//================================================================
			child = doc.createElement("InputCurrency"); root.appendChild(child);
			text = doc.createTextNode(this.InputCurrency); child.appendChild(text);
			child = doc.createElement("MerchantReference"); root.appendChild(child);
			text = doc.createTextNode(this.MerchantReference); child.appendChild(text);
			child = doc.createElement("PostUsername"); root.appendChild(child);
			text = doc.createTextNode(this.PostUsername); child.appendChild(text);
			child = doc.createElement("PostPassword"); root.appendChild(child);
			text = doc.createTextNode(this.PostPassword); child.appendChild(text);
			child = doc.createElement("TxnType"); root.appendChild(child);
			text = doc.createTextNode(this.TxnType); child.appendChild(text);
			//================================================================
			child = doc.createElement("TxnData1"); root.appendChild(child);
			text = doc.createTextNode(this.TxnData1); child.appendChild(text);
			child = doc.createElement("TxnData2"); root.appendChild(child);
			text = doc.createTextNode(this.TxnData2); child.appendChild(text);
			child = doc.createElement("TxnData3"); root.appendChild(child);
			text = doc.createTextNode(this.TxnData3); child.appendChild(text);
			child = doc.createElement("TxnId"); root.appendChild(child);
			text = doc.createTextNode(this.TxnId); child.appendChild(text);
			child = doc.createElement("EnableAvsData"); root.appendChild(child);
			text = doc.createTextNode(this.EnableAvsData); child.appendChild(text);
			//===================================================================
			child = doc.createElement("AvsAction"); root.appendChild(child);
			text = doc.createTextNode(this.AvsAction); child.appendChild(text);
			child = doc.createElement("AvsPostCode"); root.appendChild(child);
			text = doc.createTextNode(this.AvsPostCode); child.appendChild(text);
			child = doc.createElement("AvsStreetAddress"); root.appendChild(child);
			text = doc.createTextNode(this.AvsStreetAddress); child.appendChild(text);
			child = doc.createElement("DateStart"); root.appendChild(child);
			text = doc.createTextNode(this.DateStart); child.appendChild(text);
			child = doc.createElement("IssueNumber"); root.appendChild(child);
			text = doc.createTextNode(this.IssueNumber); child.appendChild(text);
			//======================================================================
			child = doc.createElement("Track2"); root.appendChild(child);
			text = doc.createTextNode(this.Track2); child.appendChild(text);
			
			

			// ///////////////
			// Output the XML

			// set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");

			// create string from xml tree
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
			this.requestXml = sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getCardHolderName() {
		return CardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		CardHolderName = cardHolderName;
	}

	public String getCardNumber() {
		return CardNumber;
	}

	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}

	public String getBillingId() {
		return BillingId;
	}

	public void setBillingId(String billingId) {
		BillingId = billingId;
	}

	public String getCvc2() {
		return Cvc2;
	}

	public void setCvc2(String cvc2) {
		Cvc2 = cvc2;
	}

	public String getCvc2Presence() {
		return Cvc2Presence;
	}

	public void setCvc2Presence(String cvc2Presence) {
		Cvc2Presence = cvc2Presence;
	}

	public String getDateExpiry() {
		return DateExpiry;
	}

	public void setDateExpiry(String dateExpiry) {
		DateExpiry = dateExpiry;
	}

	public String getDpsBillingId() {
		return DpsBillingId;
	}

	public void setDpsBillingId(String dpsBillingId) {
		DpsBillingId = dpsBillingId;
	}

	public String getDpsTxnRef() {
		return DpsTxnRef;
	}

	public void setDpsTxnRef(String dpsTxnRef) {
		DpsTxnRef = dpsTxnRef;
	}

	public String getEnableAddBillCard() {
		return EnableAddBillCard;
	}

	public void setEnableAddBillCard(String enableAddBillCard) {
		EnableAddBillCard = enableAddBillCard;
	}

	public String getInputCurrency() {
		return InputCurrency;
	}

	public void setInputCurrency(String inputCurrency) {
		InputCurrency = inputCurrency;
	}

	public String getMerchantReference() {
		return MerchantReference;
	}

	public void setMerchantReference(String merchantReference) {
		MerchantReference = merchantReference;
	}

	public String getPostUsername() {
		return PostUsername;
	}

	public void setPostUsername(String postUsername) {
		PostUsername = postUsername;
	}

	public String getPostPassword() {
		return PostPassword;
	}

	public void setPostPassword(String postPassword) {
		PostPassword = postPassword;
	}

	public String getTxnType() {
		return TxnType;
	}

	public void setTxnType(String txnType) {
		TxnType = txnType;
	}

	public String getTxnData1() {
		return TxnData1;
	}

	public void setTxnData1(String txnData1) {
		TxnData1 = txnData1;
	}

	public String getTxnData2() {
		return TxnData2;
	}

	public void setTxnData2(String txnData2) {
		TxnData2 = txnData2;
	}

	public String getTxnData3() {
		return TxnData3;
	}

	public void setTxnData3(String txnData3) {
		TxnData3 = txnData3;
	}

	public String getTxnId() {
		return TxnId;
	}

	public void setTxnId(String txnId) {
		TxnId = txnId;
	}

	public String getEnableAvsData() {
		return EnableAvsData;
	}

	public void setEnableAvsData(String enableAvsData) {
		EnableAvsData = enableAvsData;
	}

	public String getAvsAction() {
		return AvsAction;
	}

	public void setAvsAction(String avsAction) {
		AvsAction = avsAction;
	}

	public String getAvsPostCode() {
		return AvsPostCode;
	}

	public void setAvsPostCode(String avsPostCode) {
		AvsPostCode = avsPostCode;
	}

	public String getAvsStreetAddress() {
		return AvsStreetAddress;
	}

	public void setAvsStreetAddress(String avsStreetAddress) {
		AvsStreetAddress = avsStreetAddress;
	}

	public String getDateStart() {
		return DateStart;
	}

	public void setDateStart(String dateStart) {
		DateStart = dateStart;
	}

	public String getIssueNumber() {
		return IssueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		IssueNumber = issueNumber;
	}

	public String getTrack2() {
		return Track2;
	}

	public void setTrack2(String track2) {
		Track2 = track2;
	}

	public String getRequestXml() {
		this.buildXml();
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}
	
	

}
