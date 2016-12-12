/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DaoTests;

import static DaoTests.JdbcResourceScheduleDaoTests.testDao;
import static DaoTests.JdbcResourceScheduleDaoTests.resourceScheduleDao;
import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;
import legacy.daos.JdbcCompanyDao;
import legacy.daos.JdbcResourceDao;
import legacy.daos.JdbcTourTypeDao;
import legacy.daos.JdbcResourceScheduleDao;
import legacy.daos.JdbcStatusDao;
import legacy.daos.JdbcTourDao;
import legacy.daos.interfaces.CompanyDao;
import legacy.daos.interfaces.ResourceDao;
import legacy.daos.interfaces.TourTypeDao;
import legacy.daos.interfaces.ResourceScheduleDao;
import legacy.daos.interfaces.StatusDao;
import legacy.daos.interfaces.TourDao;
import legacy.models.Company;
import legacy.models.Resource;
import legacy.models.ResourceType;
import legacy.models.TourType;
import legacy.models.ResourceSchedule;
import legacy.models.Status;
import legacy.models.Tour;
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
public class JdbcResourceScheduleDaoTests {
	static ResourceScheduleDao resourceScheduleDao;
	static TourDao tourDao;
	static TourTypeDao tourTypeDao;
	static ResourceDao resourceDao;
	static StatusDao statusDao;
	static CompanyDao companyDao;
	static JdbcTestDao testDao;
	ResourceSchedule resourceSchedule1, resourceSchedule2, badResourceSchedule;
	Tour tour1, tour2;
	TourType tourType1, tourType2;
	Resource resource1, resource2;
	ResourceType resourceType1, resourceType2;
	Status status1, status2;
	Company company1, company2;
	
	public JdbcResourceScheduleDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		resourceScheduleDao = new JdbcResourceScheduleDao();
		resourceScheduleDao.setDataSource(ds);
		tourDao = new JdbcTourDao();
		tourDao.setDataSource(ds);
		tourTypeDao = new JdbcTourTypeDao();
		tourTypeDao.setDataSource(ds);
		resourceDao = new JdbcResourceDao();
		resourceDao.setDataSource(ds);
		statusDao = new JdbcStatusDao();
		statusDao.setDataSource(ds);
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
		
		tourType1 = new TourType();
		tourType1.setName("tourType1");
		tourType1.setCompanyId(company1.getCompanyId());
		tourType1 = tourTypeDao.createTourType(tourType1);
		
		tourType2 = new TourType();
		tourType2.setName("tourType2");
		tourType2.setCompanyId(company2.getCompanyId());
		tourType2 = tourTypeDao.createTourType(tourType2);
		
		status1 = new Status();
		status1.setDescription("status1");
		status1 = statusDao.createStatus(status1);
		
		status2 = new Status();
		status2.setDescription("status2");
		status2 = statusDao.createStatus(status2);
		
		tour1 = new Tour();
		tour1.setOwnerId(company1.getCompanyId());
		tour1.setStartTime(5000000000l);
		tour1.setTourTypeId(tourType1.getTourTypeId());
		tour1.setStatusId(status1.getStatusId());
		tour1 = tourDao.createTour(tour1);
		
		tour2 = new Tour();
		tour2.setOwnerId(company2.getCompanyId());
		tour2.setStartTime(1000000000);
		tour2.setTourTypeId(tourType2.getTourTypeId());
		tour2.setStatusId(status2.getStatusId());
		tour2 = tourDao.createTour(tour2);
		
		resource1 = new Resource();
		resource1.setName("resource1");
		resource1.setCapacity(10);
		resource1.setOwnerId(company1.getCompanyId());
		resource1 = resourceDao.createResource(resource1);
		
		resource2 = new Resource();
		resource2.setName("resource2");
		resource2.setOwnerId(company2.getCompanyId());
		resource2 = resourceDao.createResource(resource2);
		
		resourceSchedule1 = new ResourceSchedule();
		resourceSchedule1.setResourceId(resource1.getResourceId());
		resourceSchedule1.setTourId(tour1.getTourId());
		resourceSchedule1.setStartTime(1000000000);
		resourceSchedule1.setDuration(100);
		resourceSchedule1.setStatusId(status1.getStatusId());
		
		resourceSchedule2 = new ResourceSchedule();
		resourceSchedule2.setResourceId(resource2.getResourceId());
		resourceSchedule2.setTourId(tour2.getTourId());
		resourceSchedule2.setStartTime(2000000000);
		resourceSchedule2.setDuration(200);
		resourceSchedule2.setStatusId(status2.getStatusId());
		
		badResourceSchedule = new ResourceSchedule();
		badResourceSchedule.setResourceId(resource1.getResourceId());
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateResourceSchedule() {
		//test creation of resourceSchedule
		resourceSchedule1 = resourceScheduleDao.createResourceSchedule(resourceSchedule1);
		assert(resourceSchedule1 != null);
		
		//test creation of resourceSchedule with only a tour type id 
		thrown.expect(DataIntegrityViolationException.class);
		badResourceSchedule = resourceScheduleDao.createResourceSchedule(badResourceSchedule);
	}
	
	@Test
	public void testDeleteTourTypeType() {
		//test for successful deletion
		resourceSchedule1 = resourceScheduleDao.createResourceSchedule(resourceSchedule1);
		resourceSchedule2 = resourceScheduleDao.createResourceSchedule(resourceSchedule2);
		resourceScheduleDao.deleteResourceSchedule(resourceSchedule1);
		
		//try to get deleted resourceSchedule
		List<ResourceSchedule> resourceSchedules = resourceScheduleDao.getResourceSchedulesByResourceId(
			resource1.getResourceId());
		assertEquals(resourceSchedules.size(), 0);
		
		//try to get non-deleted resourceSchedule
		resourceSchedules = resourceScheduleDao.getResourceSchedulesByResourceId(resource2.getResourceId());
		assertEquals(resourceSchedules.size(), 1);
		ResourceSchedule a = resourceSchedules.get(0);
		assertEquals(resourceSchedule2.getResourceId(), a.getResourceId());
		assertEquals(resourceSchedule2.getTourId(), a.getTourId());
		assertEquals(resourceSchedule2.getStartTimeInMillis(), a.getStartTimeInMillis());
		assertEquals(resourceSchedule2.getDuration(), a.getDuration());
		assertEquals(resourceSchedule2.getStatusId(), a.getStatusId());
		
		//try to delete already deleted resourceSchedule - this doesn't throw an exception
		resourceScheduleDao.deleteResourceSchedule(resourceSchedule1);
	}
	
	@Test
	public void testGetResourceSchedulesByResourceId() {
		resourceSchedule1 = resourceScheduleDao.createResourceSchedule(resourceSchedule1);
		resourceSchedule2 = resourceScheduleDao.createResourceSchedule(resourceSchedule2);
		
		ResourceSchedule resourceSchedule3 = new ResourceSchedule();
		resourceSchedule3.setResourceId(resource1.getResourceId());
		resourceSchedule3.setTourId(tour1.getTourId());
		resourceSchedule3.setStartTime(1000000000);
		resourceSchedule3.setDuration(100);
		resourceSchedule3.setStatusId(status1.getStatusId());
		resourceSchedule3 = resourceScheduleDao.createResourceSchedule(resourceSchedule3);
		
		//test getTourTypeTourTypes on tourTypeTourTypes that exist
		List<ResourceSchedule> resourceSchedules = resourceScheduleDao.getResourceSchedulesByResourceId(
			resource1.getResourceId());
		assertEquals(resourceSchedules.size(), 2);
		
		ResourceSchedule a = resourceSchedules.get(0);
		assertEquals(resourceSchedule1.getResourceId(), a.getResourceId());
		assertEquals(resourceSchedule1.getTourId(), a.getTourId());
		assertEquals(resourceSchedule1.getStartTimeInMillis(), a.getStartTimeInMillis());
		assertEquals(resourceSchedule1.getDuration(), a.getDuration());
		assertEquals(resourceSchedule1.getStatusId(), a.getStatusId());
		ResourceSchedule b = resourceSchedules.get(1);
		assertEquals(resourceSchedule3.getResourceId(), b.getResourceId());
		assertEquals(resourceSchedule3.getTourId(), b.getTourId());
		assertEquals(resourceSchedule3.getStartTimeInMillis(), b.getStartTimeInMillis());
		assertEquals(resourceSchedule3.getDuration(), b.getDuration());
		assertEquals(resourceSchedule3.getStatusId(), b.getStatusId());
		
		//test getTourTypeTourTypes on tourTypeTourType that doesn't exist
		resourceSchedules = resourceScheduleDao.getResourceSchedulesByResourceId(
			resource2.getResourceId() + 1);
		assertEquals(resourceSchedules.size(), 0);	
	}
	
	@Test
	public void testGetResourceSchedulesByTourId() {
		resourceSchedule1 = resourceScheduleDao.createResourceSchedule(resourceSchedule1);
		resourceSchedule2 = resourceScheduleDao.createResourceSchedule(resourceSchedule2);
		
		ResourceSchedule resourceSchedule3 = new ResourceSchedule();
		resourceSchedule3.setResourceId(resource1.getResourceId());
		resourceSchedule3.setTourId(tour1.getTourId());
		resourceSchedule3.setStartTime(1000000000);
		resourceSchedule3.setDuration(100);
		resourceSchedule3.setStatusId(status1.getStatusId());
		resourceSchedule3 = resourceScheduleDao.createResourceSchedule(resourceSchedule3);
		
		//test getTourTypeTourTypes on tourTypeTourTypes that exist
		List<ResourceSchedule> resourceSchedules = resourceScheduleDao.getResourceSchedulesByTourId(
			tour1.getTourId());
		assertEquals(resourceSchedules.size(), 2);
		
		ResourceSchedule a = resourceSchedules.get(0);
		assertEquals(resourceSchedule1.getResourceId(), a.getResourceId());
		assertEquals(resourceSchedule1.getTourId(), a.getTourId());
		assertEquals(resourceSchedule1.getStartTimeInMillis(), a.getStartTimeInMillis());
		assertEquals(resourceSchedule1.getDuration(), a.getDuration());
		assertEquals(resourceSchedule1.getStatusId(), a.getStatusId());
		ResourceSchedule b = resourceSchedules.get(1);
		assertEquals(resourceSchedule3.getResourceId(), b.getResourceId());
		assertEquals(resourceSchedule3.getTourId(), b.getTourId());
		assertEquals(resourceSchedule3.getStartTimeInMillis(), b.getStartTimeInMillis());
		assertEquals(resourceSchedule3.getDuration(), b.getDuration());
		assertEquals(resourceSchedule3.getStatusId(), b.getStatusId());
		
		//test getTourTypeTourTypes on tourTypeTourType that doesn't exist
		resourceSchedules = resourceScheduleDao.getResourceSchedulesByTourId(
			tour2.getTourId() + 1);
		assertEquals(resourceSchedules.size(), 0);
	}
}
