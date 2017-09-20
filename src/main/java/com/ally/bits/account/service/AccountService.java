/**
 * 
 */
package com.ally.bits.account.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ifxforum.ifx_2x.BankAcctInfoType;
import org.ifxforum.ifx_2x.CurCodeType;
import org.ifxforum.ifx_2x.CustIdType;
import org.ifxforum.ifx_2x.DepAppInfoType;
import org.ifxforum.ifx_2x.DepAppRecType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ally.bits.account.entity.Account;
import com.ally.ifx.account.AccountAddRqType;
import com.ally.ifx.account.AccountAddRsType;
import com.ally.ifx.account.AccountInqRqType;
import com.ally.ifx.account.AccountInqRsType;

/**
 * @author Rohit_Sharma32
 *
 */
@Service
//@Transactional
@SuppressWarnings("unchecked")
public class AccountService {

	private static final String USD = "USD";

	@PersistenceContext
	EntityManager entityManager;

	public AccountInqRsType accountInq(AccountInqRqType accountInqRqType) {
		Query query = entityManager
				.createQuery("SELECT * FROM Account where CUST_ID = " + accountInqRqType.getCustId().getCustPermId());

		List<Account> accounts = query.getResultList();
		return getAccountFromAccountList(accounts);
	}

	/**
	 * 
	 * @param accounts
	 * @return AccountInqRsType
	 */
	private AccountInqRsType getAccountFromAccountList(List<Account> accounts) {

		// TODO: Need to complete it to generate complete response
		AccountInqRsType accountInqRsType = new AccountInqRsType();
		CustIdType custIdType = new CustIdType();
		if(null != accounts && !accounts.isEmpty()){
			Account account = accounts.get(0);
			custIdType.setCustPermId(account.getAcctId());
			
		}else{
			custIdType.setCustPermId("10000011245");
		}
		accountInqRsType.setCustId(custIdType);
		
		return accountInqRsType;
	}

	/**
	 * 
	 * @param accountAddRqType
	 * @return
	 */
	@Transactional
	public AccountAddRsType accountAdd(AccountAddRqType accountAddRqType) {
		Account account = populateAccountEntity(accountAddRqType);

		
		persistAccount(account);

		AccountAddRsType accountAddRsType = new AccountAddRsType();
		accountAddRsType.setRqUID(accountAddRqType.getRqUID());
		DepAppRecType depAppRecType = new DepAppRecType();
		DepAppInfoType depAppInfoType = new DepAppInfoType();
		BankAcctInfoType bankAcctInfoType = new BankAcctInfoType();
		CurCodeType curCodeType = new CurCodeType();
		curCodeType.setCurCodeValue(USD);
		bankAcctInfoType.setCurCode(curCodeType);
		depAppInfoType.setBankAcctInfo(bankAcctInfoType);
		depAppRecType.setDepAppInfo(depAppInfoType);
		depAppRecType.setDepAppId(accountAddRqType.getDepAppInfo().getDepApplicant().get(0).getCustId().getCustPermId());
		accountAddRsType.setDepAppRec(depAppRecType);

		return accountAddRsType;

	}

	/**
	 * 
	 * @param account
	 */
	@Transactional
	public void persistAccount(Account account) {
		try {
			entityManager.persist(account);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * 
	 * @param accountAddRqType
	 * @return
	 */
	private Account populateAccountEntity(AccountAddRqType accountAddRqType) {
		Account account = new Account();
		account.setCustId(accountAddRqType.getDepAppInfo().getDepApplicant().get(0).getCustId().getCustPermId());
		account.setAcctId(accountAddRqType.getDepAppInfo().getDepApplicant().get(0).getCustId().getCustPermId()+"2");
		account.setAcctTypeValue(accountAddRqType.getDepAppInfo().getDepAppAcctId().getAcctType().getAcctTypeValue());
		account.setCurCodeValue(accountAddRqType.getDepAppInfo().getBankAcctInfo().getCurCode().getCurCodeValue());
		account.setOwnership(accountAddRqType.getDepAppInfo().getBankAcctInfo().getOwnership());
		account.setBalTypeValues(accountAddRqType.getDepAppInfo().getAcctBal().getBalType().getBalTypeValues());
		account.setAmt(accountAddRqType.getDepAppInfo().getAcctBal().getCurAmt().getAmt().doubleValue());

		return account;
	}

}
