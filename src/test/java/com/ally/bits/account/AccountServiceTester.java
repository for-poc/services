package com.ally.bits.account;


import org.ifxforum.ifx_2x.CustIdType;
import org.ifxforum.ifx_2x.MsgRqHdrType;
import com.ally.ifx.account.AccountInqRqType;
import com.ally.ifx.account.AccountInqRsType;
import com.ally.bits.account.api.AccountApi;

public class AccountServiceTester {

	AccountApi account;

	public AccountApi getAccount() {
		return account;
	}

	public void setAccount(AccountApi account) {
		this.account = account;
	}
	
	public void testAccountInq(){
		AccountInqRqType requestMessage = new AccountInqRqType();
		requestMessage.setRqUID("1234");
		CustIdType custId = new CustIdType();
		custId.setCustPermId("10000011245");
		requestMessage.setCustId(custId);
		MsgRqHdrType msgHeader = new MsgRqHdrType();
		
		System.out.println("Sending request for Account Inq");
		AccountInqRsType accountInq = account.accountInq(requestMessage);
		System.out.println("Respone Recd "+accountInq.getRqUID());
	}
}
