/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DaoTests;

import static DaoTests.JdbcResourceResourceTypeDaoTests.testDao;
import static DaoTests.JdbcResourceResourceTypeDaoTests.resourceResourceTypeDao;
import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;
import legacy.daos.JdbcCompanyDao;
import legacy.daos.JdbcResourceTypeDao;
import legacy.daos.JdbcResourceDao;
import legacy.daos.JdbcResourceResourceTypeDao;
import legacy.daos.interfaces.CompanyDao;
import legacy.daos.interfaces.ResourceTypeDao;
import legacy.daos.interfaces.ResourceDao;
import legacy.daos.interfaces.ResourceResourceTypeDao;
import legacy.models.Company;
import legacy.models.ResourceType;
import legacy.models.Resource;
import legacy.models.ResourceResourceType;
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
public class JdbcResourceResourceTypeDaoTests {
	static ResourceResourceTypeDao resourceResourceTypeDao;
	static ResourceDao resourceDao;
	static ResourceTypeDao resourceTypeDao;
	static CompanyDao companyDao;
	static JdbcTestDao testDao;
	ResourceResourceType resourceResourceType1, resourceResourceType2, badResourceResourceType;
	Resource resource1, resource2;
	ResourceType resourceType1, resourceType2;
	Company company1, company2;
	
	public JdbcResourceResourceTypeDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		resourceResourceTypeDao = new JdbcResourceResourceTypeDao();
		resourceResourceTypeDao.setDataSource(ds);
		resourceDao = new JdbcResourceDao();
		resourceDao.setDataSource(ds);
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
		
		resource1 = new Resource();
		resource1.setName("resource1");
		resource1.setCapacity(10);
		resource1.setOwnerId(company1.getCompanyId());
		resource1 = resourceDao.createResource(resource1);
		
		resource2 = new Resource();
		resource2.setName("resource2");
		resource2.setOwnerId(company2.getCompanyId());
		resource2 = resourceDao.createResource(resource2);
		
		resourceType1 = new ResourceType();
		resourceType1.setName("resourceType1");
		resourceType1 = resourceTypeDao.createResourceType(resourceType1);
		
		resourceType2 = new ResourceType();
		resourceType2.setName("resourceType2");
		resourceType2 = resourceTypeDao.createResourceType(resourceType2);
		
		resourceResourceType1 = new ResourceResourceType();
		resourceResourceType1.setResourceId(resource1.getResourceId());
		resourceResourceType1.setResourceTypeId(resourceType1.getResourceTypeId());
		
		resourceResourceType2 = new ResourceResourceType();
		resourceResourceType2.setResourceId(resource2.getResourceId());
		resourceResourceType2.setResourceTypeId(resourceType2.getResourceTypeId());
		
		badResourceResourceType = new ResourceResourceType();
		badResourceResourceType.setResourceId(resource1.getResourceId());
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateResourceResourceType() {
		//test creation of resourceResourceType
		resourceResourceType1 = resourceResourceTypeDao.createResourceResourceType(resourceResourceType1);
		assert(resourceResourceType1 != null);
		
		//test creation of resourceResourceType with null description
		thrown.expect(DataIntegrityViolationException.class);
		badResourceResourceType = resourceResourceTypeDao.createResourceResourceType(badResourceResourceType);
	}
	
	@Test
	public void testDeleteResourceType() {
		//test for successful deletion
		resourceResourceType1 = resourceResourceTypeDao.createResourceResourceType(resourceResourceType1);
		resourceResourceType2 = resourceResourceTypeDao.createResourceResourceType(resourceResourceType2);
		resourceResourceTypeDao.deleteResourceResourceType(resourceResourceType1);
		
		//try to get deleted resourceResourceType
		List<Integer> resourceResourceTypes = resourceResourceTypeDao.getResourceTypes(resource1.getResourceId());
		assertEquals(resourceResourceTypes.size(), 0);
		
		//try to get non-deleted resourceResourceType
		resourceResourceTypes = resourceResourceTypeDao.getResourceTypes(resource2.getResourceId());
		assertEquals(resourceResourceTypes.size(), 1);
		int a = resourceResourceTypes.get(0);
		assertEquals(resourceResourceType2.getResourceTypeId(), a);
		
		//try to delete already deleted resourceResourceType - this doesn't throw an exception
		resourceResourceTypeDao.deleteResourceResourceType(resourceResourceType1);
	}
	
	@Test
	public void testGetResourceResourceTypes() {
		resourceResourceType1 = resourceResourceTypeDao.createResourceResourceType(resourceResourceType1);
		resourceResourceType2 = resourceResourceTypeDao.createResourceResourceType(resourceResourceType2);
		
		ResourceResourceType resourceResourceType3 = new ResourceResourceType();
		resourceResourceType3.setResourceId(resource1.getResourceId());
		resourceResourceType3.setResourceTypeId(resourceType2.getResourceTypeId());
		resourceResourceType3 = resourceResourceTypeDao.createResourceResourceType(resourceResourceType3);
		
		//test getResourceResourceTypes on resourceResourceTypes that exist
		List<Integer> resourceResourceTypes = resourceResourceTypeDao.getResourceTypes(resource1.getResourceId());
		assertEquals(resourceResourceTypes.size(), 2);
		
		int a = resourceResourceTypes.get(0);
		assertEquals(resourceResourceType1.getResourceTypeId(), a);
		int b = resourceResourceTypes.get(1);
		assertEquals(resourceResourceType3.getResourceTypeId(), b);
		
		//test getResourceResourceTypes on resourceResourceType that doesn't exist
		resourceResourceTypes = resourceResourceTypeDao.getResourceTypes(resource2.getResourceId() + 1);
		assertEquals(resourceResourceTypes.size(), 0);
		
	}
}
