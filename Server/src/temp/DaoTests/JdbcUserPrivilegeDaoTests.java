/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DaoTests;

import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;
import legacy.daos.JdbcCompanyDao;
import legacy.daos.JdbcPrivilegeDao;
import legacy.daos.JdbcUserDao;
import legacy.daos.JdbcUserPrivilegeDao;
import legacy.daos.interfaces.CompanyDao;
import legacy.daos.interfaces.PrivilegeDao;
import legacy.daos.interfaces.UserDao;
import legacy.daos.interfaces.UserPrivilegeDao;
import legacy.models.Company;
import legacy.models.Privilege;
import legacy.models.User;
import legacy.models.UserPrivilege;
import java.util.List;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Ivy Bridge
 */
public class JdbcUserPrivilegeDaoTests {
	static UserPrivilegeDao userPrivilegeDao;
	static UserDao userDao;
	static PrivilegeDao privilegeDao;
	static CompanyDao companyDao;
	static JdbcTestDao testDao;
	UserPrivilege userPrivilege1, userPrivilege2, badUserPrivilege;
	User user1, user2;
	Privilege privilege1, privilege2;
	Company company1, company2;
	
	public JdbcUserPrivilegeDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		userPrivilegeDao = new JdbcUserPrivilegeDao();
		userPrivilegeDao.setDataSource(ds);
		userDao = new JdbcUserDao();
		userDao.setDataSource(ds);
		privilegeDao = new JdbcPrivilegeDao();
		privilegeDao.setDataSource(ds);
		companyDao = new JdbcCompanyDao();
		companyDao.setDataSource(ds);
		testDao = new JdbcTestDao(ds);
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		testDao.clearDataBase();
		
		company1 = new Company();
		company1.setName("company1");
		company1 = companyDao.createCompany(company1);
			
		company2 = new Company();
		company2.setName("company2");
		company2 = companyDao.createCompany(company2);
		
		user1 = new User();
		user1.setUsername("user1");
		user1.setPassword("password1");
		user1.setCompanyId(company1.getCompanyId());
		user1 = userDao.createUser(user1);
		
		user2 = new User();
		user2.setUsername("user2");
		user2.setPassword("password2");
		user2.setCompanyId(company2.getCompanyId());
		user2 = userDao.createUser(user2);
		
		privilege1 = new Privilege();
		privilege1.setDescription("privilege1");
		privilege1 = privilegeDao.createPrivilege(privilege1);
		
		privilege2 = new Privilege();
		privilege2.setDescription("privilege2");
		privilege2 = privilegeDao.createPrivilege(privilege2);
		
		userPrivilege1 = new UserPrivilege();
		userPrivilege1.setUserId(user1.getUserId());
		userPrivilege1.setPrivilegeId(privilege1.getPrivilegeId());
		
		userPrivilege2 = new UserPrivilege();
		userPrivilege2.setUserId(user2.getUserId());
		userPrivilege2.setPrivilegeId(privilege2.getPrivilegeId());
		
		badUserPrivilege = new UserPrivilege();
		badUserPrivilege.setUserId(user1.getUserId());
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateUserPrivilege() {
		//test creation of userPrivilege
		userPrivilege1 = userPrivilegeDao.createUserPrivilege(userPrivilege1);
		assert(userPrivilege1 != null);
		
		//test creation of userPrivilege with null description
		thrown.expect(DataIntegrityViolationException.class);
		badUserPrivilege = userPrivilegeDao.createUserPrivilege(badUserPrivilege);
	}
	
	@Test
	public void testDeletePrivilege() {
		//test for successful deletion
		userPrivilege1 = userPrivilegeDao.createUserPrivilege(userPrivilege1);
		userPrivilege2 = userPrivilegeDao.createUserPrivilege(userPrivilege2);
		userPrivilegeDao.deleteUserPrivilege(userPrivilege1);
		
		//try to get deleted userPrivilege
		List<Integer> userPrivileges = userPrivilegeDao.getUserPrivileges(user1.getUserId());
		assertEquals(userPrivileges.size(), 0);
		
		//try to get non-deleted userPrivilege
		userPrivileges = userPrivilegeDao.getUserPrivileges(user2.getUserId());
		assertEquals(userPrivileges.size(), 1);
		int a = userPrivileges.get(0);
		assertEquals(userPrivilege2.getPrivilegeId(), a);
		
		//try to delete already deleted userPrivilege - this doesn't throw an exception
		userPrivilegeDao.deleteUserPrivilege(userPrivilege1);
	}
	
	@Test
	public void testGetUserPrivileges() {
		userPrivilege1 = userPrivilegeDao.createUserPrivilege(userPrivilege1);
		userPrivilege2 = userPrivilegeDao.createUserPrivilege(userPrivilege2);
		
		UserPrivilege userPrivilege3 = new UserPrivilege();
		userPrivilege3.setUserId(user1.getUserId());
		userPrivilege3.setPrivilegeId(privilege2.getPrivilegeId());
		userPrivilege3 = userPrivilegeDao.createUserPrivilege(userPrivilege3);
		
		//test getUserPrivileges on userPrivileges that exist
		List<Integer> userPrivileges = userPrivilegeDao.getUserPrivileges(user1.getUserId());
		assertEquals(userPrivileges.size(), 2);
		
		int a = userPrivileges.get(0);
		assertEquals(userPrivilege1.getPrivilegeId(), a);
		int b = userPrivileges.get(1);
		assertEquals(userPrivilege3.getPrivilegeId(), b);
		
		//test getUserPrivileges on userPrivilege that doesn't exist
		userPrivileges = userPrivilegeDao.getUserPrivileges(user2.getUserId() + 1);
		assertEquals(userPrivileges.size(), 0);
		
	}
}
