
package DaoTests;

import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;

public class JdbcTourDaoTests {
	/*static TourDao tourDao;
	static TourTypeDao tourTypeDao;
	static CompanyDao companyDao;
	static StatusDao statusDao;
	static JdbcTestDao testDao;
	Tour tour1, tour2, badTour;
	TourType tourType1, tourType2;
	Company company1, company2;
	Status status1, status2;
	long time;
	
	public JdbcTourDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		
		tourDao = new JdbcTourDao();
		tourDao.setDataSource(ds);
		tourTypeDao = new JdbcTourTypeDao();
		tourTypeDao.setDataSource(ds);
		companyDao = new JdbcCompanyDao();
		companyDao.setDataSource(ds);
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
		
		tour2 = new Tour();
		tour2.setOwnerId(company2.getCompanyId());
		tour2.setStartTime(1000000000);
		tour2.setTourTypeId(tourType2.getTourTypeId());
		tour2.setStatusId(status2.getStatusId());
		
		badTour = new Tour();
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateTour() {
		tour1 = tourDao.createTour(tour1);
		Tour newTour = tourDao.getTour(tour1.getId());
		assertEquals(tour1.getId(), newTour.getId());
		assertEquals(tour1.getOwnerId(), newTour.getOwnerId());
		assertEquals(tour1.getStartTimeInMillis(), newTour.getStartTimeInMillis());
		assertEquals(tour1.getTourTypeId(), newTour.getTourTypeId());
		assertEquals(tour1.getStatusId(), newTour.getStatusId());
		
		//try creating tours in various stages of incompleteness
		thrown.expect(DataIntegrityViolationException.class);
		tourDao.createTour(badTour);
		
		badTour.setOwnerId(company1.getCompanyId());
		thrown.expect(DataIntegrityViolationException.class);
		tourDao.createTour(badTour);
		
		badTour.setStartTime(1000000000);
		thrown.expect(DataIntegrityViolationException.class);
		tourDao.createTour(badTour);
		
		badTour.setTourTypeId(tourType1.getTourTypeId());
		thrown.expect(DataIntegrityViolationException.class);
		tourDao.createTour(badTour);
		
		badTour.setStatusId(status1.getStatusId());
		badTour = tourDao.createTour(badTour);
		
		newTour = tourDao.getTour(badTour.getId());
		assertEquals(badTour.getId(), newTour.getId());
		assertEquals(badTour.getOwnerId(), newTour.getOwnerId());
		assertEquals(badTour.getStartTimeInMillis(), newTour.getStartTimeInMillis());
		assertEquals(badTour.getTourTypeId(), newTour.getTourTypeId());
		assertEquals(badTour.getStatusId(), newTour.getStatusId());
	}
	
	@Test
	public void testUpdateTour() {
		tour1 = tourDao.createTour(tour1);
		tour2.setTourId(tour1.getId());
		
		//test that update works
		tourDao.updateTour(tour2);
		Tour newTour = tourDao.getTour(tour1.getId());
		assertEquals(tour2.getId(), newTour.getId());
		assertEquals(tour2.getOwnerId(), newTour.getOwnerId());
		assertEquals(tour2.getStartTimeInMillis(), newTour.getStartTimeInMillis());
		assertEquals(tour2.getTourTypeId(), newTour.getTourTypeId());
		assertEquals(tour2.getStatusId(), newTour.getStatusId());
		
		//test update on tour that doesn't exist
		badTour.setOwnerId(company1.getCompanyId());
		badTour.setStartTime(1000000000);
		badTour.setTourTypeId(tourType1.getTourTypeId());
		badTour.setStatusId(status1.getStatusId());
		badTour = tourDao.updateTour(badTour);//doesn't throw exception
	}
	
	@Test
	public void testDeleteTour() {
		//test for successful deletion
		tour1 = tourDao.createTour(tour1);
		int tour1id = tour1.getId();
		tour2 = tourDao.createTour(tour2);
		int tour2id = tour2.getId();
		tourDao.deleteTour(tour1.getId());
		
		//try to get deleted tour
		thrown.expect(EmptyResultDataAccessException.class);
		tour1 = tourDao.getTour(tour1id);
		
		//try to get non-deleted tour
		Tour newTour = tourDao.getTour(tour2id);
		assertEquals(tour2.getId(), newTour.getId());
		assertEquals(tour2.getOwnerId(), newTour.getOwnerId());
		assertEquals(tour2.getStartTimeInMillis(), newTour.getStartTimeInMillis());
		assertEquals(tour2.getTourTypeId(), newTour.getTourTypeId());
		assertEquals(tour2.getStatusId(), newTour.getStatusId());
		
		//try to delete already deleted tour - this doesn't throw an exception
		tourDao.deleteTour(tour1id);
	}
	
	@Test
	public void testGetTour() {
		tour1 = tourDao.createTour(tour1);
		tour2 = tourDao.createTour(tour2);
		
		//test getTour on tours that exist
		Tour newTour = tourDao.getTour(tour1.getId());
		assertEquals(tour1.getId(), newTour.getId());
		assertEquals(tour1.getOwnerId(), newTour.getOwnerId());
		assertEquals(tour1.getStartTimeInMillis(), newTour.getStartTimeInMillis());
		assertEquals(tour1.getTourTypeId(), newTour.getTourTypeId());
		assertEquals(tour1.getStatusId(), newTour.getStatusId());
		newTour = tourDao.getTour(tour2.getId());
		assertEquals(tour2.getId(), newTour.getId());
		assertEquals(tour2.getOwnerId(), newTour.getOwnerId());
		assertEquals(tour2.getStartTimeInMillis(), newTour.getStartTimeInMillis());
		assertEquals(tour2.getTourTypeId(), newTour.getTourTypeId());
		assertEquals(tour2.getStatusId(), newTour.getStatusId());
		
		//test getTour on tour that doesn't exist
		thrown.expect(EmptyResultDataAccessException.class);
		newTour = tourDao.getTour(tour2.getId() + 1);
	}
	
	@Test
	public void testGetTours() {
		tour1 = tourDao.createTour(tour1);
		tour2 = tourDao.createTour(tour2);
		
		Tour tour3 = new Tour();
		tour3.setOwnerId(company1.getCompanyId());
		tour3.setStartTime(1000000000);
		tour3.setTourTypeId(tourType1.getTourTypeId());
		tour3.setStatusId(status1.getStatusId());
		tour3 = tourDao.createTour(tour3);
		
		List<Tour> tours = tourDao.getTours(company1.getCompanyId());
		assertEquals(tours.size(), 2);
		Tour newTour = tours.get(0);
		assertEquals(tour1.getId(), newTour.getId());
		assertEquals(tour1.getOwnerId(), newTour.getOwnerId());
		assertEquals(tour1.getStartTimeInMillis(), newTour.getStartTimeInMillis());
		assertEquals(tour1.getTourTypeId(), newTour.getTourTypeId());
		assertEquals(tour1.getStatusId(), newTour.getStatusId());
		newTour = tours.get(1);
		assertEquals(tour3.getId(), newTour.getId());
		assertEquals(tour3.getOwnerId(), newTour.getOwnerId());
		assertEquals(tour3.getStartTimeInMillis(), newTour.getStartTimeInMillis());
		assertEquals(tour3.getTourTypeId(), newTour.getTourTypeId());
		assertEquals(tour3.getStatusId(), newTour.getStatusId());
	}
	
	@Test
	public void testGetToursByDateRange() {
		tour1.setOwnerId(company2.getCompanyId());
		tour1 = tourDao.createTour(tour1);
		tour2 = tourDao.createTour(tour2);
		
		Tour tour3 = new Tour();
		tour3.setOwnerId(company2.getCompanyId());
		tour3.setStartTime(1500000000);
		tour3.setTourTypeId(tourType1.getTourTypeId());
		tour3.setStatusId(status1.getStatusId());
		tour3 = tourDao.createTour(tour3);
		
		Tour tour4 = new Tour();
		tour4.setOwnerId(company2.getCompanyId());
		tour4.setStartTime(2000000000);
		tour4.setTourTypeId(tourType1.getTourTypeId());
		tour4.setStatusId(status1.getStatusId());
		tour4 = tourDao.createTour(tour4);
		
		List<Tour> tours = tourDao.getToursByDateRange(company2.getCompanyId(), 1000000000, 2000000000);
		assertEquals(3, tours.size());
		Tour newTour = tours.get(0);
		assertEquals(tour2.getId(), newTour.getId());
		assertEquals(tour2.getOwnerId(), newTour.getOwnerId());
		assertEquals(tour2.getStartTimeInMillis(), newTour.getStartTimeInMillis());
		assertEquals(tour2.getTourTypeId(), newTour.getTourTypeId());
		assertEquals(tour2.getStatusId(), newTour.getStatusId());
		newTour = tours.get(1);
		assertEquals(tour3.getId(), newTour.getId());
		assertEquals(tour3.getOwnerId(), newTour.getOwnerId());
		assertEquals(tour3.getStartTimeInMillis(), newTour.getStartTimeInMillis());
		assertEquals(tour3.getTourTypeId(), newTour.getTourTypeId());
		assertEquals(tour3.getStatusId(), newTour.getStatusId());
		newTour = tours.get(2);
		assertEquals(tour4.getId(), newTour.getId());
		assertEquals(tour4.getOwnerId(), newTour.getOwnerId());
		assertEquals(tour4.getStartTimeInMillis(), newTour.getStartTimeInMillis());
		assertEquals(tour4.getTourTypeId(), newTour.getTourTypeId());
		assertEquals(tour4.getStatusId(), newTour.getStatusId());
	}
*/
}
