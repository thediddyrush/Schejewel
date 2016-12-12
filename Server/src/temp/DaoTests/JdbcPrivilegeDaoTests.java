
package DaoTests;

import static DaoTests.JdbcPrivilegeDaoTests.privilegeDao;
import static DaoTests.JdbcPrivilegeDaoTests.testDao;
import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;
import legacy.daos.JdbcPrivilegeDao;
import legacy.daos.interfaces.PrivilegeDao;
import legacy.models.Privilege;
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
import org.springframework.dao.EmptyResultDataAccessException;

public class JdbcPrivilegeDaoTests {
	static PrivilegeDao privilegeDao;
	static JdbcTestDao testDao;
	Privilege privilege1, privilege2, badPrivilege;
	
	public JdbcPrivilegeDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		privilegeDao = new JdbcPrivilegeDao();
		privilegeDao.setDataSource(ds);
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
		privilege1 = new Privilege();
		privilege1.setDescription("privilege1");
		privilege1.setPrivilegeId(-1);
		privilege2 = new Privilege();
		privilege2.setDescription("privilege2");
		badPrivilege = new Privilege();
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreatePrivilege() {
		//test creation of privilege
		privilege1 = privilegeDao.createPrivilege(privilege1);
		assert(privilege1 != null);
		assert(privilege1.getPrivilegeId() != -1);
		assert(privilege1.getPrivilegeId() >= 0);
		assertEquals(privilege1.getDescription(), "privilege1");
		
		//test creation of privilege with null description
		thrown.expect(DataIntegrityViolationException.class);
		badPrivilege = privilegeDao.createPrivilege(badPrivilege);
	}
	
	@Test
	public void testUpdatePrivilege() {
		privilege1 = privilegeDao.createPrivilege(privilege1);
		privilege2.setPrivilegeId(privilege1.getPrivilegeId());
		
		//test that update works
		privilege2 = privilegeDao.updatePrivilege(privilege2);
		assertEquals(privilege1.getPrivilegeId(), privilege2.getPrivilegeId());
		assertEquals(privilege2.getDescription(), "privilege2");
		
		//test update on privilege that doesn't exist
		badPrivilege.setPrivilegeId(-1);
		badPrivilege.setDescription("desc");
		badPrivilege = privilegeDao.updatePrivilege(badPrivilege);//doesn't throw exception
	}
	
	@Test
	public void testDeletePrivilege() {
		//test for successful deletion
		privilege1 = privilegeDao.createPrivilege(privilege1);
		int stat1id = privilege1.getPrivilegeId();
		privilege2 = privilegeDao.createPrivilege(privilege2);
		int stat2id = privilege2.getPrivilegeId();
		privilegeDao.deletePrivilege(privilege1.getPrivilegeId());
		
		//try to get deleted privilege
		thrown.expect(EmptyResultDataAccessException.class);
		privilege1 = privilegeDao.getPrivilege(privilege1.getPrivilegeId());
		
		//try to get non-deleted privilege
		privilege2 = privilegeDao.getPrivilege(privilege2.getPrivilegeId());
		assertEquals(privilege2.getPrivilegeId(), stat2id);
		assertEquals(privilege2.getDescription(), "privilege2");
		
		//try to delete already deleted privilege - this doesn't throw an exception
		privilegeDao.deletePrivilege(stat1id);
	}
	
	@Test
	public void testGetPrivilege() {
		privilege1 = privilegeDao.createPrivilege(privilege1);
		privilege2 = privilegeDao.createPrivilege(privilege2);
		
		//test getprivilege on privilegees that exist
		Privilege a = privilegeDao.getPrivilege(privilege1.getPrivilegeId());
		assertEquals(privilege1.getPrivilegeId(), a.getPrivilegeId());
		assertEquals(privilege1.getDescription(), a.getDescription());
		Privilege b = privilegeDao.getPrivilege(privilege2.getPrivilegeId());
		assertEquals(privilege2.getPrivilegeId(), b.getPrivilegeId());
		assertEquals(privilege2.getDescription(), b.getDescription());
		
		//test getprivilege on privilege that doesn't exist
		thrown.expect(EmptyResultDataAccessException.class);
		Privilege c = privilegeDao.getPrivilege(privilege2.getPrivilegeId() + 1);
		
	}
}
