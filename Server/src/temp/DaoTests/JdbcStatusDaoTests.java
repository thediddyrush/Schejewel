/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DaoTests;

import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;


public class JdbcStatusDaoTests {
    /*
	static StatusDao statusDao;
	static JdbcTestDao testDao;
	Status status1, status2, badStatus;
	
	public JdbcStatusDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		statusDao = new JdbcStatusDao();
		statusDao.setDataSource(ds);
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
		status1 = new Status();
		status1.setDescription("status1");
		status1.setStatusId(-1);
		status2 = new Status();
		status2.setDescription("status2");
		badStatus = new Status();
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateStatus() {
		//test creation of status
		status1 = statusDao.createStatus(status1);
		assert(status1 != null);
		assert(status1.getStatusId() != -1);
		assert(status1.getStatusId() >= 0);
		assertEquals(status1.getDescription(), "status1");
		
		//test creation of status with null description
		thrown.expect(DataIntegrityViolationException.class);
		badStatus = statusDao.createStatus(badStatus);
	}
	
	@Test
	public void testUpdateStatus() {
		status1 = statusDao.createStatus(status1);
		status2.setStatusId(status1.getStatusId());
		
		//test that update works
		status2 = statusDao.updateStatus(status2);
		assertEquals(status1.getStatusId(), status2.getStatusId());
		assertEquals(status2.getDescription(), "status2");
		
		//test update on status that doesn't exist
		badStatus.setStatusId(-1);
		badStatus.setDescription("desc");
		badStatus = statusDao.updateStatus(badStatus);//doesn't throw exception
	}
	
	@Test
	public void testDeleteStatus() {
		//test for successful deletion
		status1 = statusDao.createStatus(status1);
		int stat1id = status1.getStatusId();
		status2 = statusDao.createStatus(status2);
		int stat2id = status2.getStatusId();
		statusDao.deleteStatus(status1.getStatusId());
		
		//try to get deleted status
		thrown.expect(EmptyResultDataAccessException.class);
		status1 = statusDao.getStatus(status1.getStatusId());
		
		//try to get non-deleted status
		status2 = statusDao.getStatus(status2.getStatusId());
		assertEquals(status2.getStatusId(), stat2id);
		assertEquals(status2.getDescription(), "status2");
		
		//try to delete already deleted status - this doesn't throw an exception
		statusDao.deleteStatus(stat1id);
	}
	
	@Test
	public void testGetStatus() {
		status1 = statusDao.createStatus(status1);
		status2 = statusDao.createStatus(status2);
		
		//test getstatus on statuses that exist
		Status a = statusDao.getStatus(status1.getStatusId());
		assertEquals(status1.getStatusId(), a.getStatusId());
		assertEquals(status1.getDescription(), a.getDescription());
		Status b = statusDao.getStatus(status2.getStatusId());
		assertEquals(status2.getStatusId(), b.getStatusId());
		assertEquals(status2.getDescription(), b.getDescription());
		
		//test getstatus on status that doesn't exist
		thrown.expect(EmptyResultDataAccessException.class);
		Status c = statusDao.getStatus(status2.getStatusId() + 1);
		
	}*/
}
