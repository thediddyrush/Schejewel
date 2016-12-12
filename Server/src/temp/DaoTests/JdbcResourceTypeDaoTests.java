
package DaoTests;


import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;

public class JdbcResourceTypeDaoTests {
	/*static ResourceTypeDao resourceTypeDao;
	static JdbcTestDao testDao;
	ResourceType resourceType1, resourceType2, badResourceType;
	
	public JdbcResourceTypeDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		resourceTypeDao = new JdbcResourceTypeDao();
		resourceTypeDao.setDataSource(ds);
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
		resourceType1 = new ResourceType();
		resourceType1.setName("resourceType1");
		resourceType2 = new ResourceType();
		resourceType2.setName("resourceType2");
		badResourceType = new ResourceType();
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateResourceType() {
		//test creation of resourceType
		resourceType1 = resourceTypeDao.createResourceType(resourceType1);
		assert(resourceType1 != null);
		assert(resourceType1.getResourceTypeId() >= 0);
		assertEquals(resourceType1.getName(), "resourceType1");
		
		//test creation of resourceType with null name
		thrown.expect(DataIntegrityViolationException.class);
		badResourceType = resourceTypeDao.createResourceType(badResourceType);
	}
	
	@Test
	public void testUpdateResourceType() {
		resourceType1 = resourceTypeDao.createResourceType(resourceType1);
		resourceType2.setResourceTypeId(resourceType1.getResourceTypeId());
		
		//test that update works
		resourceType2 = resourceTypeDao.updateResourceType(resourceType2);
		assertEquals(resourceType1.getResourceTypeId(), resourceType2.getResourceTypeId());
		assertEquals(resourceType2.getName(), "resourceType2");
		
		//test update on resourceType that doesn't exist
		badResourceType.setResourceTypeId(-1);
		badResourceType.setName("name");
		badResourceType = resourceTypeDao.updateResourceType(badResourceType);//doesn't throw exception
	}
	
	@Test
	public void testDeleteResourceType() {
		//test for successful deletion
		resourceType1 = resourceTypeDao.createResourceType(resourceType1);
		int comp1id = resourceType1.getResourceTypeId();
		resourceType2 = resourceTypeDao.createResourceType(resourceType2);
		int comp2id = resourceType2.getResourceTypeId();
		resourceTypeDao.deleteResourceType(resourceType1.getResourceTypeId());
		
		//try to get deleted resourceType
		thrown.expect(EmptyResultDataAccessException.class);
		resourceType1 = resourceTypeDao.getResourceType(resourceType1.getResourceTypeId());
		
		//try to get non-deleted resourceType
		resourceType2 = resourceTypeDao.getResourceType(resourceType2.getResourceTypeId());
		assertEquals(resourceType2.getResourceTypeId(), comp2id);
		assertEquals(resourceType2.getName(), "resourceType2");
		
		//try to delete already deleted resourceType - this doesn't throw an exception
		resourceTypeDao.deleteResourceType(comp1id);
	}
	
	@Test
	public void testGetResourceType() {
		resourceType1 = resourceTypeDao.createResourceType(resourceType1);
		resourceType2 = resourceTypeDao.createResourceType(resourceType2);
		
		//test getResourceType on companies that exist
		ResourceType a = resourceTypeDao.getResourceType(resourceType1.getResourceTypeId());
		assertEquals(resourceType1.getResourceTypeId(), a.getResourceTypeId());
		assertEquals(resourceType1.getName(), a.getName());
		ResourceType b = resourceTypeDao.getResourceType(resourceType2.getResourceTypeId());
		assertEquals(resourceType2.getResourceTypeId(), b.getResourceTypeId());
		assertEquals(resourceType2.getName(), b.getName());
		
		//test getResourceType on resourceType that doesn't exist
		thrown.expect(EmptyResultDataAccessException.class);
		ResourceType c = resourceTypeDao.getResourceType(resourceType2.getResourceTypeId() + 1);
		
	}
*/
}
