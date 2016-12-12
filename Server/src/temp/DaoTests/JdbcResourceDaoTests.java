
package DaoTests;

import static DaoTests.JdbcResourceDaoTests.resourceDao;
import static DaoTests.JdbcResourceDaoTests.testDao;
import static DaoTests.JdbcResourceScheduleDaoTests.resourceScheduleDao;
import static DaoTests.JdbcResourceScheduleDaoTests.tourTypeDao;
import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;
import legacy.daos.JdbcCompanyDao;
import legacy.daos.JdbcResourceDao;
import legacy.daos.JdbcResourceScheduleDao;
import legacy.daos.JdbcStatusDao;
import legacy.daos.JdbcTourDao;
import legacy.daos.JdbcTourTypeDao;
import legacy.daos.interfaces.CompanyDao;
import legacy.daos.interfaces.ResourceDao;
import legacy.daos.interfaces.ResourceScheduleDao;
import legacy.daos.interfaces.StatusDao;
import legacy.daos.interfaces.TourDao;
import legacy.daos.interfaces.TourTypeDao;
import legacy.models.Company;
import legacy.models.Resource;
import legacy.models.ResourceSchedule;
import legacy.models.ResourceType;
import legacy.models.Status;
import legacy.models.Tour;
import legacy.models.TourType;
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
import org.springframework.dao.EmptyResultDataAccessException;

public class JdbcResourceDaoTests {
	static ResourceScheduleDao resourceScheduleDao;
	static TourDao tourDao;
	static TourTypeDao tourTypeDao;
	static ResourceDao resourceDao;
	static StatusDao statusDao;
	static CompanyDao companyDao;
	static JdbcTestDao testDao;
	Resource resource1, resource2, badResource;
	Company company1, company2;
	ResourceType resourceType1, resourceType2;
	Status status1, status2;
	ResourceSchedule resourceSchedule1, resourceSchedule2;
	Tour tour1, tour2;
	TourType tourType1, tourType2;
	
	public JdbcResourceDaoTests() {
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
		
		resource2 = new Resource();
		resource2.setName("resource2");
		resource2.setOwnerId(company2.getCompanyId());
		
		badResource = new Resource();
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateResource() {
		//test creation of resource
		resource1 = resourceDao.createResource(resource1);
		assert(resource1 != null);
		assert(resource1.getResourceId() >= 0);
		assertEquals(resource1.getName(), "resource1");
		
		Resource newResource = resourceDao.getResource(resource1.getResourceId());
		assertEquals(resource1.getResourceId(), newResource.getResourceId());
		assertEquals(resource1.getName(), newResource.getName());
		assertEquals(resource1.getCapacity(), newResource.getCapacity());
		assertEquals(resource1.getOwnerId(), newResource.getOwnerId());
		
		//test creation of resource with null name
		thrown.expect(DataIntegrityViolationException.class);
		badResource = resourceDao.createResource(badResource);
	}
	
	@Test
	public void testUpdateResource() {
		resource1 = resourceDao.createResource(resource1);
		resource2.setResourceId(resource1.getResourceId());
		
		//test that update works
		resource2 = resourceDao.updateResource(resource2);
		
		Resource newResource = resourceDao.getResource(resource1.getResourceId());
		assertEquals(resource2.getResourceId(), newResource.getResourceId());
		assertEquals(resource2.getName(), newResource.getName());
		assertEquals(resource2.getCapacity(), newResource.getCapacity());
		assertEquals(resource2.getOwnerId(), newResource.getOwnerId());
		
		//test update on resource that doesn't exist
		badResource.setResourceId(-1);
		badResource.setName("name");
		badResource.setOwnerId(company1.getCompanyId());
		badResource = resourceDao.updateResource(badResource);//doesn't throw exception
	}
	
	@Test
	public void testDeleteResource() {
		//test for successful deletion
		resource1 = resourceDao.createResource(resource1);
		int res1id = resource1.getResourceId();
		resource2 = resourceDao.createResource(resource2);
		resourceDao.deleteResource(resource1.getResourceId());
		
		//try to get deleted resource
		thrown.expect(EmptyResultDataAccessException.class);
		resource1 = resourceDao.getResource(resource1.getResourceId());
		
		//try to get non-deleted resource
		Resource newResource = resourceDao.getResource(resource2.getResourceId());
		assertEquals(resource2.getResourceId(), newResource.getResourceId());
		assertEquals(resource2.getName(), newResource.getName());
		assertEquals(resource2.getCapacity(), newResource.getCapacity());
		assertEquals(resource2.getOwnerId(), newResource.getOwnerId());
		
		//try to delete already deleted resource - this doesn't throw an exception
		resourceDao.deleteResource(res1id);
	}
	
	@Test
	public void testGetResource() {
		resource1 = resourceDao.createResource(resource1);
		resource2 = resourceDao.createResource(resource2);
		
		//test getResource on resources that exist
		Resource a = resourceDao.getResource(resource1.getResourceId());
		assertEquals(resource1.getResourceId(), a.getResourceId());
		assertEquals(resource1.getName(), a.getName());
		assertEquals(resource1.getCapacity(), a.getCapacity());
		assertEquals(resource1.getOwnerId(), a.getOwnerId());
		Resource b = resourceDao.getResource(resource2.getResourceId());
		assertEquals(resource2.getResourceId(), b.getResourceId());
		assertEquals(resource2.getName(), b.getName());
		assertEquals(resource2.getCapacity(), b.getCapacity());
		assertEquals(resource2.getOwnerId(), b.getOwnerId());
		
		//test getResource on resource that doesn't exist
		thrown.expect(EmptyResultDataAccessException.class);
		Resource c = resourceDao.getResource(resource2.getResourceId() + 1);
	}
	
	@Test
	public void testGetResources() {
		resource1 = resourceDao.createResource(resource1);
		resource2 = resourceDao.createResource(resource2);
		
		Resource resource3 = new Resource();
		resource3.setName("resource3");
		resource3.setCapacity(30);
		resource3.setOwnerId(company1.getCompanyId());
		resource3 = resourceDao.createResource(resource3);
		
		List<Resource> resources = resourceDao.getResources(company1.getCompanyId());
		assertEquals(resources.size(), 2);
		
		Resource newResource = resources.get(0);
		assertEquals(resource1.getResourceId(), newResource.getResourceId());
		assertEquals(resource1.getName(), newResource.getName());
		assertEquals(resource1.getCapacity(), newResource.getCapacity());
		assertEquals(resource1.getOwnerId(), newResource.getOwnerId());
		
		newResource = resources.get(1);
		assertEquals(resource3.getResourceId(), newResource.getResourceId());
		assertEquals(resource3.getName(), newResource.getName());
		assertEquals(resource3.getCapacity(), newResource.getCapacity());
		assertEquals(resource3.getOwnerId(), newResource.getOwnerId());
	}
	
	@Test
	public void testGetResources2() {//tests with companyId and date range
		resource1 = resourceDao.createResource(resource1);
		resource2 = resourceDao.createResource(resource2);
		
		resourceSchedule1 = new ResourceSchedule();
		resourceSchedule1.setResourceId(resource1.getResourceId());
		resourceSchedule1.setTourId(tour1.getTourId());
		resourceSchedule1.setStartTime(1000000000);
		resourceSchedule1.setDuration(100);
		resourceSchedule1.setStatusId(status1.getStatusId());
		resourceSchedule1 = resourceScheduleDao.createResourceSchedule(resourceSchedule1);
		
		Resource resource3 = new Resource();
		resource3.setName("resource3");
		resource3.setCapacity(30);
		resource3.setOwnerId(company1.getCompanyId());
		resource3 = resourceDao.createResource(resource3);
		
		resourceSchedule2 = new ResourceSchedule();
		resourceSchedule2.setResourceId(resource3.getResourceId());
		resourceSchedule2.setTourId(tour2.getTourId());
		resourceSchedule2.setStartTime(2000000000);
		resourceSchedule2.setDuration(200);
		resourceSchedule2.setStatusId(status2.getStatusId());
		resourceSchedule2 = resourceScheduleDao.createResourceSchedule(resourceSchedule2);
		
		List<Resource> resources = resourceDao.getResources(company1.getCompanyId(), 
			1000000000, 2000000000);
		assertEquals(resources.size(), 2);
		
		Resource newResource = resources.get(0);
		assertEquals(resource1.getResourceId(), newResource.getResourceId());
		assertEquals(resource1.getName(), newResource.getName());
		assertEquals(resource1.getCapacity(), newResource.getCapacity());
		assertEquals(resource1.getOwnerId(), newResource.getOwnerId());
		
		newResource = resources.get(1);
		assertEquals(resource3.getResourceId(), newResource.getResourceId());
		assertEquals(resource3.getName(), newResource.getName());
		assertEquals(resource3.getCapacity(), newResource.getCapacity());
		assertEquals(resource3.getOwnerId(), newResource.getOwnerId());
	}
}
