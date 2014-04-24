
package com.krishagni.catissueplus.core.services.testdata;

import static com.krishagni.catissueplus.core.common.errors.CatissueException.reportError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.krishagni.catissueplus.core.administrative.domain.Address;
import com.krishagni.catissueplus.core.administrative.domain.Department;
import com.krishagni.catissueplus.core.administrative.domain.User;
import com.krishagni.catissueplus.core.administrative.domain.factory.UserErrorCode;
import com.krishagni.catissueplus.core.administrative.events.CloseUserEvent;
import com.krishagni.catissueplus.core.administrative.events.CreateUserEvent;
import com.krishagni.catissueplus.core.administrative.events.ForgotPasswordEvent;
import com.krishagni.catissueplus.core.administrative.events.PasswordDetails;
import com.krishagni.catissueplus.core.administrative.events.PatchUserEvent;
import com.krishagni.catissueplus.core.administrative.events.UpdatePasswordEvent;
import com.krishagni.catissueplus.core.administrative.events.UpdateUserEvent;
import com.krishagni.catissueplus.core.administrative.events.UserDetails;
import com.krishagni.catissueplus.core.auth.domain.AuthDomain;
import com.krishagni.catissueplus.core.auth.domain.AuthProvider;
import com.krishagni.catissueplus.core.auth.domain.Ldap;
import com.krishagni.catissueplus.core.biospecimen.domain.CollectionProtocol;
import com.krishagni.catissueplus.core.biospecimen.domain.Site;
import com.krishagni.catissueplus.core.common.util.Status;

import edu.wustl.common.beans.SessionDataBean;

public class UserTestData {

	public static final String ACTIVITY_STATUS_CLOSED = "Closed";

	public static final String FIRST_NAME = "first name";

	public static final String LAST_NAME = "last name";

	public static final String LOGIN_NAME = "login name";

	public static final String EMAIL_ADDRESS = "email address";

	public static final String DEPARTMENT = "department";
	
	public static final String AUTH_DOMAIN = "auth domain";

	public static final String LDAP = "ldap";

	private static final String PATCH_USER = "patch user";

	public static List<User> getUserList() {
		List<User> users = new ArrayList<User>();
		users.add(new User());
		users.add(new User());
		return users;
	}

	public static SessionDataBean getSessionDataBean() {
		SessionDataBean sessionDataBean = new SessionDataBean();
		sessionDataBean.setAdmin(true);
		sessionDataBean.setCsmUserId("1");
		sessionDataBean.setFirstName("admin");
		sessionDataBean.setIpAddress("127.0.0.1");
		sessionDataBean.setLastName("admin");
		sessionDataBean.setUserId(1L);
		sessionDataBean.setUserName("admin@admin.com");
		return sessionDataBean;
	}

	public static User getUser(Long id) {
		User user = new User();
		user.setId(id);
		user.setFirstName("firstName1");
		user.setLastName("lastName1");
		user.setLoginName("admin@admin.com");
		user.setEmailAddress("sci@sci.com");
		user.setPasswordToken("e5412f93-a1c5-4ede-b66d-b32302cd4018");
		user.setDepartment(new Department());
		user.setAddress(new Address());
		user.setAuthDomain(getAuthDomain(id));
		user.setSiteCollection(new HashSet<Site>());
		user.setCpCollection(new HashSet<CollectionProtocol>());
		return user;
	}

	public static CloseUserEvent getCloseUserEvent() {
		Long userId = 1l;
		CloseUserEvent event = new CloseUserEvent();
		event.setId(userId);
		event.setSessionDataBean(getSessionDataBean());
		return event;
	}

	public static ForgotPasswordEvent getForgotPasswordEvent() {
		ForgotPasswordEvent event = new ForgotPasswordEvent();
		event.setName("admin@admin.com");
		event.setSessionDataBean(getSessionDataBean());
		return event;
	}

	public static UpdateUserEvent getUpdateUserEvent() {
		UserDetails details = new UserDetails();
		details.setActivityStatus("Active");
		details.setFirstName("firstName");
		details.setLastName("lastName");
		details.setDeptName("Chemical");
		details.setEmailAddress("sci@sci.com");
		details.setLoginName("admin@admin.com");
		details.setDomainName("MyLdap");
		details.setSiteNames(getSites());
		details.setCpTitles(getCpTitles());

		UpdateUserEvent reqEvent = new UpdateUserEvent(details, 1L);
		reqEvent.setSessionDataBean(getSessionDataBean());
		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static AuthDomain getAuthDomain(long id) {
		AuthDomain authDomain = new AuthDomain();
		authDomain.setId(id);
		authDomain.setName("MyAuth");

		Ldap ldap = new Ldap();
		ldap.setHost("ldap.testathon.net");
		ldap.setPort(389l);
		ldap.setIdField("cn");
		ldap.setDirectoryContext("OU=users,DC=testathon,DC=net");
		ldap.setBindUser("john");
		ldap.setBindPassword("john");

		ldap.setGivenNameField("givenName");
		ldap.setSurnameField("sn");
		ldap.setEmailField("mail");

		ldap.setSearchBaseDir("OU=users,DC=testathon,DC=net");
		ldap.setFilterString("(&(objectClass=*)(uid={0}))");
		AuthProvider authProvider = new AuthProvider();
		authProvider.setAuthType("ldap");
		authProvider.setImplClass("com.krishagni.catissueplus.core.auth.services.impl.LdapAuthServiceImpl");
		authDomain.setAuthProvider(authProvider);
		authDomain.setLdap(ldap);
		return authDomain;
	}

	public static UpdateUserEvent getUpadteUserEventWithLNUpdate() {
		UpdateUserEvent reqEvent = getUpdateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		details.setLoginName("admin");
		reqEvent.setUserDetails(details);
		return reqEvent;
	}
	
	public static CreateUserEvent getCreateUserEvent() {
		CreateUserEvent reqEvent = new CreateUserEvent(null);
		reqEvent.setSessionDataBean(getSessionDataBean());
		UserDetails details = getUserDetails();
		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static UserDetails getUserDetails() {
		UserDetails details = new UserDetails();
		details.setActivityStatus("Active");
		details.setFirstName("firstName");
		details.setLastName("lastName");
		details.setDeptName("Chemical");
		details.setEmailAddress("sci@sci.com");
		details.setLoginName("admin@admin.com");
		details.setDomainName("myLdap");
		details.setSiteNames(getSites());
		details.setCpTitles(getCpTitles());
		return details;
	}
	
	public static CreateUserEvent getCreateUserEventWithInvalidEmail() {
		CreateUserEvent reqEvent = getCreateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		details.setEmailAddress("admin");
		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static UpdateUserEvent getCreateUserEventWithNonDupEmail() {
		UpdateUserEvent reqEvent = getUpdateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		details.setEmailAddress("admin@admin");
		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static CreateUserEvent getCreateUserEventForUserCreation() {
		CreateUserEvent reqEvent = getCreateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static CreateUserEvent getCreateUserEventWithNullSite() {
		CreateUserEvent reqEvent = getCreateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		details.setSiteNames(null);
		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static CreateUserEvent getCreateUserEventWithNullCP() {
		CreateUserEvent reqEvent = getCreateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		details.setCpTitles(null);
		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static CreateUserEvent getCreateUserEventWithEmptyLoginName() {
		CreateUserEvent reqEvent = getCreateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		details.setLoginName("");
		reqEvent.setUserDetails(details);
		return reqEvent;
	}
	
	public static CreateUserEvent getCreateUserEventWithEmptyDomainName() {
		CreateUserEvent reqEvent = getCreateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		details.setDomainName("");
		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static CreateUserEvent getCreateUserEventWithEmptyEmail() {
		CreateUserEvent reqEvent = getCreateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		details.setEmailAddress("");

		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static CreateUserEvent getCreateUserEventWithEmptyFirstName() {
		CreateUserEvent reqEvent = getCreateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		details.setFirstName("");
		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static CreateUserEvent getCreateUserEventWithEmptyLastName() {
		CreateUserEvent reqEvent = getCreateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		details.setLastName("");
		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static Department getDeparment(String name) {
		Department department = new Department();
		department.setId(10l);

		department.setName(name);
		return department;
	}

	public static UpdatePasswordEvent getUpdatePasswordEvent() {
		PasswordDetails details = new PasswordDetails();
		details.setUserId(80L);
		details.setOldPassword("Krishagni123");
		details.setNewPassword("Krishagni21");

		UpdatePasswordEvent reqEvent = new UpdatePasswordEvent(details, "e5412f93-a1c5-4ede-b66d-b32302cd4018", 80L);
		reqEvent.setSessionDataBean(getSessionDataBean());
		reqEvent.setPasswordToken("e5412f93-a1c5-4ede-b66d-b32302cd4018");
		return reqEvent;
	}
	
	public static UpdatePasswordEvent getUpdatePasswordEventInvalid() {
		PasswordDetails details = new PasswordDetails();
		details.setUserId(80L);
		details.setOldPassword("");
		details.setNewPassword("Krishagni21");

		UpdatePasswordEvent reqEvent = new UpdatePasswordEvent(details, "e5412f93-a1c5-4ede-b66d-b32302cd4018", 80L);
		reqEvent.setSessionDataBean(getSessionDataBean());
		reqEvent.setPasswordToken("e5412f93-a1c5-4ede-b66d-b32302cd4018");
		return reqEvent;
	}

	public static UpdatePasswordEvent getUpdatePasswordEventForReSet() {
		UpdatePasswordEvent reqEvent = getUpdatePasswordEvent();
		reqEvent.getPasswordDetails().setOldPassword("Darshan123");
		return reqEvent;
	}

	public static UpdatePasswordEvent getUpdatePasswordEventForBlankNewPass() {
		UpdatePasswordEvent reqEvent = getUpdatePasswordEvent();
		reqEvent.getPasswordDetails().setNewPassword("");
		reqEvent.getPasswordDetails().setOldPassword("catissue");
		return reqEvent;
	}

	public static UpdatePasswordEvent getUpdatePasswordEventForBlankOldPass() {
		UpdatePasswordEvent reqEvent = getUpdatePasswordEvent();
		reqEvent.getPasswordDetails().setOldPassword("");
		return reqEvent;
	}

	public static UpdatePasswordEvent getUpdatePasswordEventForBlankConfirmPass() {
		UpdatePasswordEvent reqEvent = getUpdatePasswordEvent();
		reqEvent.getPasswordDetails().setOldPassword("catissue");
		return reqEvent;
	}

	public static UpdatePasswordEvent getUpdatePasswordEventForDiffPass() {
		UpdatePasswordEvent reqEvent = getUpdatePasswordEvent();
		reqEvent.getPasswordDetails().setNewPassword("catissue");
		return reqEvent;
	}

	public static UpdatePasswordEvent getUpdatePasswordEventForDiffTokens() {
		UpdatePasswordEvent reqEvent = getUpdatePasswordEvent();
		reqEvent.setPasswordToken("e5412f93-a1c5-4ede-b66d-b32302");
		return reqEvent;
	}

	public static List<String> getOldPasswordList() {
		List<String> Passwords = new ArrayList<String>();
		Passwords.add("kris");
		Passwords.add("$2a$04$0J4/9Cb3eYcvHAjxEw43Wud9ii1jYxiogouyXe5nUVa28LXQ6al7K");
		return Passwords;
	}

	public static Site getSite() {
		Site site = new Site();
		site.setName("LABS");
		site.setId(1l);
		site.setType("My type");
		return site;
	}

	public static CollectionProtocol getCp() {
		CollectionProtocol collectionProtocol = new CollectionProtocol();
		collectionProtocol.setTitle("Query CP");
		collectionProtocol.setId(1l);
		collectionProtocol.setShortTitle("qcp");
		return collectionProtocol;
	}

	private static List<String> getCpTitles() {
		List<String> cpTitles = new ArrayList<String>();
		cpTitles.add("Query CP");
		return cpTitles;
	}

	private static List<String> getSites() {
		List<String> sites = new ArrayList<String>();
		sites.add("ATCC");
		return sites;
	}

	public static User getUserWithCatissueDomain(long id) {
		User user = new User();
		user.setId(id);
		user.setFirstName("firstName1");
		user.setLastName("lastName1");
		user.setLoginName("admin@admin.com");
		user.setEmailAddress("sci@sci.com");
		user.setPasswordToken("e5412f93-a1c5-4ede-b66d-b32302cd4018");
		user.setDepartment(new Department());
		user.setAddress(new Address());
		AuthDomain authDomain = new AuthDomain();
		AuthProvider authProvider = new AuthProvider();
		authProvider.setAuthType("catissue");
		authProvider.setImplClass("com.krishagni.catissueplus.core.auth.services.impl.LdapAuthServiceImpl");
		authDomain.setName("catissue");
		
		user.setAuthDomain(authDomain);
		return user;
	}

	public static CreateUserEvent getCreateUserEventForNonLdapUserCreation() {
		CreateUserEvent reqEvent = getCreateUserEvent();
		UserDetails details = reqEvent.getUserDetails();
		details.setDomainName("catissue");
		reqEvent.setUserDetails(details);
		return reqEvent;
	}

	public static UpdatePasswordEvent getUpdatePasswordEventWithBlankToken() {
		UpdatePasswordEvent reqEvent = getUpdatePasswordEvent();
		reqEvent.setPasswordToken("");
		return reqEvent;
	}

	public static PatchUserEvent getPatchData() {
		PatchUserEvent event = new PatchUserEvent();
		event.setUserId(1l);
		UserDetails details = new UserDetails();
		try {
			BeanUtils.populate(details, getUserPatchAttributes());
		}
		catch (Exception e) {
			reportError(UserErrorCode.BAD_REQUEST, PATCH_USER);
		}
		//details.setModifiedAttributes(new ArrayList<String>(getUserPatchAttributes().keySet()));
		event.setUserDetails(details);
		return event;
	}

	private static Map<String, Object> getUserPatchAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("firstName", "daresh");
		attributes.put("lastName", "patil");
		attributes.put("siteNames", getSites());
		attributes.put("cpTitles", getCpTitles());
		attributes.put("activityStatus", Status.ACTIVITY_STATUS_DISABLED.getStatus());
		attributes.put("comments", "blah blah");
		attributes.put("loginName", "dpatil");
		attributes.put("emailAddress", "daresh@smaol.com");
		attributes.put("deptName", "chemical");

		attributes.put("country", "us");
		attributes.put("state", "alaska");
		attributes.put("city", "newuor");
		attributes.put("faxNumber", "43249-434");
		attributes.put("phoneNumber", "76543");

		attributes.put("street", "john road");
		attributes.put("zipCode", "76543");
		return attributes;
	}
}
