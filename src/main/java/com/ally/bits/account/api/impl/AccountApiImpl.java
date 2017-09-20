/**
 * 
 */
package com.ally.bits.account.api.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.ally.bits.account.api.AccountApi;
import com.ally.bits.account.service.AccountService;
import com.ally.ifx.account.AccountAddRqType;
import com.ally.ifx.account.AccountAddRsType;
import com.ally.ifx.account.AccountInqRqType;
import com.ally.ifx.account.AccountInqRsType;

/**
 * @author Rohit_Sharma32
 *
 */
@WebService(serviceName="Account",
endpointInterface="com.ally.bits.account.api.AccountApi",
portName="AccountSoapHttpsPort",
wsdlLocation="WEB-INF/classes/WSDL/Account-SoapHttp.wsdl",
targetNamespace="http://ally.com/wsd/Account/v1/")
public class AccountApiImpl implements AccountApi {
	
	@Autowired
	private AccountService accountService;

	/* (non-Javadoc)
	 * @see com.ally.bits.account.api.AccountApi#accountInq(com.ally.ifx.account.AccountInqRqType)
	 */
	@Override
	public AccountInqRsType accountInq(AccountInqRqType accountInqRqType) {
		return accountService.accountInq(accountInqRqType);
	}

	/* (non-Javadoc)
	 * @see com.ally.bits.account.api.AccountApi#accountAdd(com.ally.ifx.account.AccountAddRqType)
	 */
	@Override
	public AccountAddRsType accountAdd(AccountAddRqType accountAddRqType) {
		return accountService.accountAdd(accountAddRqType);
	}

	/**
	 * @return the accountService
	 */
	public AccountService getAccountService() {
		return accountService;
	}

	/**
	 * @param accountService the accountService to set
	 */
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

}
