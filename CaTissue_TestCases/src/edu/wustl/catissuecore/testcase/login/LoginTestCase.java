package edu.wustl.catissuecore.testcase.login;
import org.junit.Test;

import edu.wustl.catissuecore.actionForm.LoginForm;
import edu.wustl.catissuecore.testcase.CaTissueSuiteBaseTest;
import edu.wustl.catissuecore.testcase.util.CaTissueSuiteTestUtil;
import edu.wustl.common.audit.LoginAuditManager;
import edu.wustl.common.beans.LoginDetails;
import edu.wustl.common.beans.SessionDataBean;
/**
*
 * @author sachin_lale
 *
 */
public class LoginTestCase extends CaTissueSuiteBaseTest
{
	/**
	 * Negative Test Case.
	 * Test Login with empty Login name.
	 */
	@Test
	public void testLoginWithEmptyLoginName()
	{
		setRequestPathInfo("/Login") ;
		LoginForm loginForm = new LoginForm() ;
		loginForm.setLoginName("") ;
		loginForm.setPassword("Test") ;
		setActionForm(loginForm) ;
		actionPerform();
		//verifyForward("/Home.do");
		verifyForward("failure");

		//verify action errors
		String errormsg[] = new String[] {"errors.item.required"};
		verifyActionErrors(errormsg);
	}
	
	/**
	 * Negative Test Case.
	 * Test Login with empty Password.
	 */
	@Test
	public void testLoginWithEmptyLoginPassword()
	{
		setRequestPathInfo("/Login") ;
		LoginForm loginForm = new LoginForm() ;
		loginForm.setLoginName("admin@admin.com") ;
		loginForm.setPassword("") ;
		setActionForm(loginForm) ;
		actionPerform();
		//verifyForward("/Home.do");
		verifyForward("failure");

		//verify action errors
		String errormsg[] = new String[] {"errors.item.required"};
		verifyActionErrors(errormsg);
	}
	
	/**
	 * Negative Test Case.
	 * Test Login with Invalid Format of Login and Password.
	 */
	@Test
	public void testLoginWithInvalidFormatLogin()
	{
		setRequestPathInfo("/Login") ;
		LoginForm loginForm = new LoginForm() ;
		loginForm.setLoginName("@admin@admin.com") ;
		loginForm.setPassword("Test") ;
		setActionForm(loginForm) ;
		actionPerform();
		//verifyForward("/Home.do");
		verifyForward("failure");

		//verify action errors
		String errormsg[] = new String[] {"errors.item.format"};
		verifyActionErrors(errormsg);
	}

	/**
	 * Negative test case.
	 * Test Login with Invalid Login name and password.
	 * bug 10997
	 */
	@Test
	public void testInvalidLogin()
	{
		/**
		 * reset method is modified for running successfully.
		 * this test case using Login Form. 
		 */
		setRequestPathInfo("/Login") ;
		LoginForm loginForm = new LoginForm() ;
		loginForm.setLoginName("admin@admin.com") ;
		loginForm.setPassword("Test") ;
		setActionForm(loginForm) ;
		actionPerform();
		//verifyForward("/Home.do");
		verifyForward("failure");

		//verify action errors
		String errormsg[] = new String[] {"errors.incorrectLoginIDPassword"};
		verifyActionErrors(errormsg);
		LoginDetails loginDetails = new LoginDetails(loginForm.getLoginName(),1L,"",false) ;
		LoginAuditManager loginManager = new LoginAuditManager(loginDetails) ;
		loginManager.audit(false, loginDetails);
	}
	/**
	 * Test Login with Valid Login name and Password.
	 */
	@Test
	public void testSuccessfulLogin()
	{
		setRequestPathInfo("/Login") ;
		LoginForm loginForm = new LoginForm() ;
		loginForm.setLoginName("admin@admin.com") ;
		loginForm.setPassword("Shri123") ;
		setActionForm(loginForm) ;
		actionPerform();
		//verifyForward("/Home.do");
		verifyForward("success");

		SessionDataBean bean = (SessionDataBean)getSession().getAttribute("sessionData");
		assertEquals("user name should be equal to loggedinusername","admin@admin.com",bean.getUserName());
		CaTissueSuiteTestUtil.USER_SESSION_DATA_BEAN=bean;
		verifyNoActionErrors();
	}
	
/**
 * Test CLick Administrative->Site menu.
 */
//	@Test
//	 public void testSiteClick()
//	  {
//			addRequestParameter("operation", "add");
//			addRequestParameter("pageOf", "pageOfSite");
//			setRequestPathInfo("/Site");
//			actionPerform();
//			verifyForward("pageOfSite");
//			List stateList = (List)getRequest().getAttribute(Constants.STATELIST);
//			assertNotNull("State List should not be null",stateList);
//			actionPerform();
//			setRequestPathInfo("/Site");
//			addRequestParameter("isOnChange", "true");
//			addRequestParameter("coordinatorId", "1");
//			actionPerform();
//			verifyForward("pageOfSite");
//	  }

//	public void testInstitutionEdit()
//	{
//		setRequestPathInfo("/SimpleSearch");
//		addRequestParameter("aliasName", "Institution");
//		addRequestParameter("value(SimpleConditionsNode:1_Condition_DataElement_table)", "Institution");
//		addRequestParameter("value(SimpleConditionsNode:1_Condition_DataElement_field)","Institution.Name.varchar");
//		addRequestParameter("value(SimpleConditionsNode:1_Condition_Operator_operator)","Starts With");
//		addRequestParameter("value(SimpleConditionsNode:1__Condition_value)","I");
//		addRequestParameter("counter","1");
//		addRequestParameter("pageOf","pageOfInstitution");
//		addRequestParameter("operation","search");
//		actionPerform();
//
//		verifyForward("success");
//	}
	/**
	 * TEst institution add.
	 */
//	@Test
//	public void testAddInstitution()
//	{
//		InstitutionForm form = new InstitutionForm();
//		form.setName("");
//		form.setOperation(Constants.ADD);
//		getRequest().setAttribute("institutionForm", form);
//		setRequestPathInfo("/InstitutionAdd");
//		actionPerform();
//		verifyForward("success");
//
//	}

	/**
	 * Test institution edit.
	 */
//	@Test
//	public void testInstitutionEdit()
//	{
//		setRequestPathInfo("/SimpleSearch");
//		SimpleQueryInterfaceForm simpleQueryInterfaceform = new SimpleQueryInterfaceForm();
//		simpleQueryInterfaceform.setAliasName("Institution");
//		Map valueMap = new HashMap();
//		valueMap.put("SimpleConditionsNode:1_Condition_DataElement_table", "Institution");
//		valueMap.put("SimpleConditionsNode:1_Condition_DataElement_field","Institution.Name.varchar");
//		valueMap.put("SimpleConditionsNode:1_Condition_Operator_operator","Starts With");
//		valueMap.put("SimpleConditionsNode:1__Condition_value","");
//		addRequestParameter("counter","1");
//		addRequestParameter("pageOf","pageOfInstitution");
//		addRequestParameter("operation","search");
//		simpleQueryInterfaceform.setCounter("1");
//		simpleQueryInterfaceform.setValues(valueMap);
//		getRequest().setAttribute("simpleQueryInterfaceForm", simpleQueryInterfaceform);
//		actionPerform();
//
//		verifyForward("success");
//	}
}
