
package DaoTests;

import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;
import legacy.daos.JdbcCompanyDao;
import legacy.daos.JdbcResourceTypeDao;
import legacy.daos.JdbcTourTypeDao;
import legacy.daos.JdbcTourTypeResourceDao;
import legacy.daos.interfaces.CompanyDao;
import legacy.daos.interfaces.ResourceTypeDao;
import legacy.daos.interfaces.TourTypeDao;
import legacy.daos.interfaces.TourTypeResourceDao;
import legacy.models.Company;
import legacy.models.ResourceType;
import legacy.models.TourType;
import legacy.models.TourTypeResource;
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

public class JdbcTourTypeResourceDaoTests {
	static TourTypeResourceDao tourTypeResourceDao;
	static TourTypeDao tourTypeDao;
	static ResourceTypeDao resourceTypeDao;
	static CompanyDao companyDao;
	static JdbcTestDao testDao;
	TourTypeResource tourTypeResource1, tourTypeResource2, badTourTypeResource;
	TourType tourType1, tourType2;
	ResourceType resourceType1, resourceType2;
	Company company1, company2;
	
	public JdbcTourTypeResourceDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		tourTypeResourceDao = new JdbcTourTypeResourceDao();
		tourTypeResourceDao.setDataSource(ds);
		tourTypeDao = new JdbcTourTypeDao();
		tourTypeDao.setDataSource(ds);
		resourceTypeDao = new JdbcResourceTypeDao();
		resourceTypeDao.setDataSource(ds);
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
		
		resourceType1 = new ResourceType();
		resourceType1.setName("resourceType1");
		resourceType1 = resourceTypeDao.createResourceType(resourceType1);
		
		resourceType2 = new ResourceType();
		resourceType2.setName("resourceType2");
		resourceType2 = resourceTypeDao.createResourceType(resourceType2);
		
		tourTypeResource1 = new TourTypeResource();
		tourTypeResource1.setTourTypeId(tourType1.getTourTypeId());
		tourTypeResource1.setResourceTypeId(resourceType1.getResourceTypeId());
		tourTypeResource1.setOrderNumber(1);
		tourTypeResource1.setDuration(100);
		
		tourTypeResource2 = new TourTypeResource();
		tourTypeResource2.setTourTypeId(tourType2.getTourTypeId());
		tourTypeResource2.setResourceTypeId(resourceType2.getResourceTypeId());
		tourTypeResource2.setOrderNumber(2);
		tourTypeResource2.setDuration(200);
		
		badTourTypeResource = new TourTypeResource();
		badTourTypeResource.setTourTypeId(tourType1.getTourTypeId());
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateTourTypeTourType() {
		//test creation of tourTypeResource
		tourTypeResource1 = tourTypeResourceDao.createTourTypeResource(tourTypeResource1);
		assert(tourTypeResource1 != null);
		
		//test creation of tourTypeResource with only a tour type id 
		thrown.expect(DataIntegrityViolationException.class);
		badTourTypeResource = tourTypeResourceDao.createTourTypeResource(badTourTypeResource);
	}
	
	@Test
	public void testDeleteTourTypeType() {
		//test for successful deletion
		tourTypeResource1 = tourTypeResourceDao.createTourTypeResource(tourTypeResource1);
		tourTypeResource2 = tourTypeResourceDao.createTourTypeResource(tourTypeResource2);
		tourTypeResourceDao.deleteTourTypeResource(tourTypeResource1);
		
		//try to get deleted tourTypeResource
		List<TourTypeResource> tourTypeResources = tourTypeResourceDao.getTourTypeResourcesByTourTypeId(
			tourType1.getTourTypeId());
		assertEquals(tourTypeResources.size(), 0);
		
		//try to get non-deleted tourTypeResource
		tourTypeResources = tourTypeResourceDao.getTourTypeResourcesByTourTypeId(tourType2.getTourTypeId());
		assertEquals(tourTypeResources.size(), 1);
		TourTypeResource a = tourTypeResources.get(0);
		assertEquals(tourTypeResource2.getTourTypeId(), a.getTourTypeId());
		assertEquals(tourTypeResource2.getResourceTypeId(), a.getResourceTypeId());
		assertEquals(tourTypeResource2.getOrderNumber(), a.getOrderNumber());
		assertEquals(tourTypeResource2.getDuration(), a.getDuration());
		
		//try to delete already deleted tourTypeResource - this doesn't throw an exception
		tourTypeResourceDao.deleteTourTypeResource(tourTypeResource1);
	}
	
	@Test
	public void testGetTourTypeResourcesByTourTypeId() {
		tourTypeResource1 = tourTypeResourceDao.createTourTypeResource(tourTypeResource1);
		tourTypeResource2 = tourTypeResourceDao.createTourTypeResource(tourTypeResource2);
		
		TourTypeResource tourTypeResource3 = new TourTypeResource();
		tourTypeResource3.setTourTypeId(tourType1.getTourTypeId());
		tourTypeResource3.setResourceTypeId(resourceType2.getResourceTypeId());
		tourTypeResource3 = tourTypeResourceDao.createTourTypeResource(tourTypeResource3);
		
		//test getTourTypeTourTypes on tourTypeTourTypes that exist
		List<TourTypeResource> tourTypeResources = tourTypeResourceDao.getTourTypeResourcesByTourTypeId(
			tourType1.getTourTypeId());
		assertEquals(tourTypeResources.size(), 2);
		
		TourTypeResource a = tourTypeResources.get(0);
		assertEquals(tourTypeResource1.getTourTypeId(), a.getTourTypeId());
		assertEquals(tourTypeResource1.getResourceTypeId(), a.getResourceTypeId());
		assertEquals(tourTypeResource1.getOrderNumber(), a.getOrderNumber());
		assertEquals(tourTypeResource1.getDuration(), a.getDuration());
		TourTypeResource b = tourTypeResources.get(1);
		assertEquals(tourTypeResource3.getTourTypeId(), b.getTourTypeId());
		assertEquals(tourTypeResource3.getResourceTypeId(), b.getResourceTypeId());
		assertEquals(tourTypeResource3.getOrderNumber(), b.getOrderNumber());
		assertEquals(tourTypeResource3.getDuration(), b.getDuration());
		
		//test getTourTypeTourTypes on tourTypeTourType that doesn't exist
		tourTypeResources = tourTypeResourceDao.getTourTypeResourcesByTourTypeId(
			tourType2.getTourTypeId() + 1);
		assertEquals(tourTypeResources.size(), 0);	
	}
	
	@Test
	public void testGetTourTypeResourcesByResourceTypeId() {
		tourTypeResource1 = tourTypeResourceDao.createTourTypeResource(tourTypeResource1);
		tourTypeResource2 = tourTypeResourceDao.createTourTypeResource(tourTypeResource2);
		
		TourTypeResource tourTypeResource3 = new TourTypeResource();
		tourTypeResource3.setTourTypeId(tourType1.getTourTypeId());
		tourTypeResource3.setResourceTypeId(resourceType1.getResourceTypeId());
		tourTypeResource3 = tourTypeResourceDao.createTourTypeResource(tourTypeResource3);
		
		//test getTourTypeTourTypes on tourTypeTourTypes that exist
		List<TourTypeResource> tourTypeResources = tourTypeResourceDao.getTourTypeResourcesByResourceTypeId(
			resourceType1.getResourceTypeId());
		assertEquals(tourTypeResources.size(), 2);
		
		TourTypeResource a = tourTypeResources.get(0);
		assertEquals(tourTypeResource1.getTourTypeId(), a.getTourTypeId());
		assertEquals(tourTypeResource1.getResourceTypeId(), a.getResourceTypeId());
		assertEquals(tourTypeResource1.getOrderNumber(), a.getOrderNumber());
		assertEquals(tourTypeResource1.getDuration(), a.getDuration());
		TourTypeResource b = tourTypeResources.get(1);
		assertEquals(tourTypeResource3.getTourTypeId(), b.getTourTypeId());
		assertEquals(tourTypeResource3.getResourceTypeId(), b.getResourceTypeId());
		assertEquals(tourTypeResource3.getOrderNumber(), b.getOrderNumber());
		assertEquals(tourTypeResource3.getDuration(), b.getDuration());
		
		//test getTourTypeTourTypes on tourTypeTourType that doesn't exist
		tourTypeResources = tourTypeResourceDao.getTourTypeResourcesByResourceTypeId(
			resourceType2.getResourceTypeId() + 1);
		assertEquals(tourTypeResources.size(), 0);	
	}
}
