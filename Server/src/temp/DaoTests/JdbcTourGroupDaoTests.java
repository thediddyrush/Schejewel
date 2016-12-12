
package DaoTests;

import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;
import legacy.daos.JdbcCompanyDao;
import legacy.daos.JdbcCruiseLineDao;
import legacy.daos.JdbcCruiseShipDao;
import legacy.daos.JdbcTourGroupDao;
import legacy.daos.JdbcTourDao;
import legacy.daos.JdbcPortageDao;
import legacy.daos.JdbcStatusDao;
import legacy.daos.JdbcTourTypeDao;
import legacy.daos.interfaces.CompanyDao;
import legacy.daos.interfaces.CruiseLineDao;
import legacy.daos.interfaces.CruiseShipDao;
import legacy.daos.interfaces.PortageDao;
import legacy.daos.interfaces.StatusDao;
import legacy.daos.interfaces.TourDao;
import legacy.daos.interfaces.TourGroupDao;
import legacy.daos.interfaces.TourTypeDao;
import legacy.models.Company;
import legacy.models.CruiseLine;
import legacy.models.CruiseShip;
import legacy.models.TourGroup;
import legacy.models.Tour;
import legacy.models.Portage;
import legacy.models.Status;
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

public class JdbcTourGroupDaoTests {
	static TourGroupDao tourGroupDao;
	static PortageDao portageDao;
	static TourDao tourDao;
	static CruiseLineDao cruiseLineDao;
	static CruiseShipDao cruiseShipDao;
	static CompanyDao companyDao;
	static StatusDao statusDao;
	static TourTypeDao tourTypeDao;
	static JdbcTestDao testDao;
	
	TourGroup tourGroup1, tourGroup2, badTourGroup;
	Portage portage1, portage2;
	Tour tour1, tour2;
	CruiseLine cruiseLine1, cruiseLine2;
	CruiseShip cruiseShip1, cruiseShip2;
	Company company1, company2;
	Status status1, status2;
	TourType tourType1, tourType2;
	
	public JdbcTourGroupDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		
		tourGroupDao = new JdbcTourGroupDao();
		tourGroupDao.setDataSource(ds);
		portageDao = new JdbcPortageDao();
		portageDao.setDataSource(ds);
		tourDao = new JdbcTourDao();
		tourDao.setDataSource(ds);
		cruiseLineDao = new JdbcCruiseLineDao();
		cruiseLineDao.setDataSource(ds);
		cruiseShipDao = new JdbcCruiseShipDao();
		cruiseShipDao.setDataSource(ds);
		companyDao = new JdbcCompanyDao();
		companyDao.setDataSource(ds);
		statusDao = new JdbcStatusDao();
		statusDao.setDataSource(ds);
		tourTypeDao = new JdbcTourTypeDao();
		tourTypeDao.setDataSource(ds);
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
		
		//set up all of the dependencies
		company1 = new Company();
		company1.setName("company1");
		company1 = companyDao.createCompany(company1);
		
		company2 = new Company();
		company2.setName("company2");
		company2 = companyDao.createCompany(company2);
		
		status1 = new Status();
		status1.setDescription("status1");
		status1 = statusDao.createStatus(status1);
		
		status2 = new Status();
		status2.setDescription("status2");
		status2 = statusDao.createStatus(status1);
		
		tourType1 = new TourType();
		tourType1.setName("tourType1");
		tourType1.setCompanyId(company1.getCompanyId());
		tourType1 = tourTypeDao.createTourType(tourType1);
		
		tourType2 = new TourType();
		tourType2.setName("tourType2");
		tourType2.setCompanyId(company2.getCompanyId());
		tourType2 = tourTypeDao.createTourType(tourType2);
		
		tour1 = new Tour();
		tour1.setOwnerId(company1.getCompanyId());
		tour1.setStartTime("2015-2-25");
		tour1.setTourTypeId(tourType1.getTourTypeId());
		tour1.setStatusId(status1.getStatusId());
		tour1 = tourDao.createTour(tour1);
		
		tour2 = new Tour();
		tour2.setOwnerId(company2.getCompanyId());
		tour2.setStartTime("2015-2-25");
		tour2.setTourTypeId(tourType2.getTourTypeId());
		tour2.setStatusId(status2.getStatusId());
		tour2 = tourDao.createTour(tour2);
		
		cruiseLine1 = new CruiseLine();
		cruiseLine1.setName("cruiseLine1");
		cruiseLine1 = cruiseLineDao.createCruiseLine(cruiseLine1);
		
		cruiseLine2 = new CruiseLine();
		cruiseLine2.setName("cruiseLine2");
		cruiseLine2 = cruiseLineDao.createCruiseLine(cruiseLine2);
		
		cruiseShip1 = new CruiseShip();
		cruiseShip1.setName("cruiseShip1");
		cruiseShip1.setCruiseLineId(cruiseLine1.getCruiseLineId());
		cruiseShip1 = cruiseShipDao.createCruiseShip(cruiseShip1);
		
		cruiseShip2 = new CruiseShip();
		cruiseShip2.setName("cruiseShip2");
		cruiseShip2.setCruiseLineId(cruiseLine2.getCruiseLineId());
		cruiseShip2 = cruiseShipDao.createCruiseShip(cruiseShip1);
		
		portage1 = new Portage();
		portage1.setCruiseShipId(cruiseShip1.getCruiseShipId());
		portage1.setArrival(1000000000);
		portage1.setDeparture(2000000000);
		portage1.setPassengerCount(50);
		portage1.setAllAboard(190000l);
		portage1.setDock(1);
		portage1.setVoyage("voyage1");
		portage1 = portageDao.createPortage(portage1);
		
		portage2 = new Portage();
		portage2.setCruiseShipId(cruiseShip2.getCruiseShipId());
		portage2.setArrival(3000000000l);
		portage2.setDeparture(4000000000l);
		portage2 = portageDao.createPortage(portage2);
		
		//set up tourGroups
		tourGroup1 = new TourGroup();
		tourGroup1.setPortageId(portage1.getPortageId());
		tourGroup1.setTourId(tour1.getId());
		tourGroup1.setGroupSize(10);
		tourGroup1.setSettled(true);
		
		tourGroup2 = new TourGroup();
		tourGroup2.setPortageId(portage2.getPortageId());
		tourGroup2.setTourId(tour2.getId());
		tourGroup2.setGroupSize(20);
		tourGroup2.setSettled(false);
		
		badTourGroup = new TourGroup();
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateTourGroup() {
		tourGroup1 = tourGroupDao.createTourGroup(tourGroup1);
		
		TourGroup newTourGroup = tourGroupDao.getTourGroup(tourGroup1.getId());
		assertEquals(tourGroup1.getId(), newTourGroup.getId());
		assertEquals(tourGroup1.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup1.getId(), newTourGroup.getId());
		assertEquals(tourGroup1.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup1.isSettled(), newTourGroup.isSettled());
		
		//try creating tourGroup in various stages of incompleteness
		thrown.expect(DataIntegrityViolationException.class);
		tourGroupDao.createTourGroup(badTourGroup);
		
		badTourGroup.setPortageId(portage1.getPortageId());
		thrown.expect(DataIntegrityViolationException.class);
		tourGroupDao.createTourGroup(badTourGroup);
		
		badTourGroup.setTourId(tour1.getId());
		thrown.expect(DataIntegrityViolationException.class);
		tourGroupDao.createTourGroup(badTourGroup);
		
		badTourGroup.setGroupSize(20);
		thrown.expect(DataIntegrityViolationException.class);
		tourGroupDao.createTourGroup(badTourGroup);
		
		newTourGroup = tourGroupDao.getTourGroup(tourGroup1.getId());
		assertEquals(badTourGroup.getId(), newTourGroup.getId());
		assertEquals(badTourGroup.getPortageId(), newTourGroup.getPortageId());
		assertEquals(badTourGroup.getId(), newTourGroup.getId());
		assertEquals(badTourGroup.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(badTourGroup.isSettled(), newTourGroup.isSettled());
	}
	
	@Test
	public void testUpdateTourGroup() {
		tourGroup1 = tourGroupDao.createTourGroup(tourGroup1);
		tourGroup2.setId(tourGroup1.getId());
		
		//test that update works
		tourGroupDao.updateTourGroup(tourGroup2);
		
		TourGroup newTourGroup = tourGroupDao.getTourGroup(tourGroup2.getId());
		assertEquals(tourGroup2.getId(), newTourGroup.getId());
		assertEquals(tourGroup2.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup2.getId(), newTourGroup.getId());
		assertEquals(tourGroup2.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup2.isSettled(), newTourGroup.isSettled());
	}
	
	@Test
	public void testDeleteTourGroup() {
		//test for successful deletion
		tourGroup1 = tourGroupDao.createTourGroup(tourGroup1);
		int tg1id = tourGroup1.getPortageId();
		tourGroup2 = tourGroupDao.createTourGroup(tourGroup2);
		tourGroupDao.deleteTourGroup(tg1id);
		
		//try to get deleted tourGroup
		thrown.expect(EmptyResultDataAccessException.class);
		tourGroup1 = tourGroupDao.getTourGroup(tg1id);
		
		//try to get non-deleted tourGroup
		TourGroup newTourGroup = tourGroupDao.getTourGroup(tourGroup2.getId());
		assertEquals(tourGroup2.getId(), newTourGroup.getId());
		assertEquals(tourGroup2.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup2.getId(), newTourGroup.getId());
		assertEquals(tourGroup2.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup2.isSettled(), newTourGroup.isSettled());
		
		//try to delete already deleted tourGroup - this doesn't throw an exception
		portageDao.deletePortage(tg1id);
	}
	
	@Test
	public void testGetTourGroup() {
		tourGroup1 = tourGroupDao.createTourGroup(tourGroup1);
		tourGroup2 = tourGroupDao.createTourGroup(tourGroup2);
		
		//test getTourGroup on tourGroups that exist
		TourGroup newTourGroup = tourGroupDao.getTourGroup(tourGroup1.getId());
		assertEquals(tourGroup1.getId(), newTourGroup.getId());
		assertEquals(tourGroup1.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup1.getId(), newTourGroup.getId());
		assertEquals(tourGroup1.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup1.isSettled(), newTourGroup.isSettled());
		
		newTourGroup = tourGroupDao.getTourGroup(tourGroup2.getId());
		assertEquals(tourGroup2.getId(), newTourGroup.getId());
		assertEquals(tourGroup2.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup2.getId(), newTourGroup.getId());
		assertEquals(tourGroup2.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup2.isSettled(), newTourGroup.isSettled());
		
		//test getTourGroup on tourGroup that doesn't exist
		thrown.expect(EmptyResultDataAccessException.class);
		newTourGroup = tourGroupDao.getTourGroup(tourGroup2.getId() + 1);
	}
	
	@Test
	public void testGetTourGroups() {
		tourGroup1 = tourGroupDao.createTourGroup(tourGroup1);
		tourGroup2 = tourGroupDao.createTourGroup(tourGroup2);
		
		TourGroup tourGroup3 = new TourGroup();
		tourGroup3.setPortageId(portage1.getPortageId());
		tourGroup3.setTourId(tour1.getId());
		tourGroup3.setGroupSize(30);
		tourGroup3.setSettled(true);
		tourGroup3 = tourGroupDao.createTourGroup(tourGroup3);
		
		List<TourGroup> tourGroups = tourGroupDao.getTourGroups(company1.getCompanyId());
		assertEquals(tourGroups.size(), 2);
		TourGroup newTourGroup = tourGroups.get(0);
		assertEquals(tourGroup1.getId(), newTourGroup.getId());
		assertEquals(tourGroup1.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup1.getId(), newTourGroup.getId());
		assertEquals(tourGroup1.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup1.isSettled(), newTourGroup.isSettled());
		
		newTourGroup = tourGroups.get(1);
		assertEquals(tourGroup3.getId(), newTourGroup.getId());
		assertEquals(tourGroup3.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup3.getId(), newTourGroup.getId());
		assertEquals(tourGroup3.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup3.isSettled(), newTourGroup.isSettled());
	}
	
	@Test
	public void testGetTourGroupsByTourId() {
		tourGroup1 = tourGroupDao.createTourGroup(tourGroup1);
		tourGroup2 = tourGroupDao.createTourGroup(tourGroup2);
		
		TourGroup tourGroup3 = new TourGroup();
		tourGroup3.setPortageId(portage1.getPortageId());
		tourGroup3.setTourId(tour1.getId());
		tourGroup3.setGroupSize(30);
		tourGroup3.setSettled(true);
		tourGroup3 = tourGroupDao.createTourGroup(tourGroup3);
		
		List<TourGroup> tourGroups = tourGroupDao.getTourGroupsByTourId(company1.getCompanyId(), tour1.getId());
		assertEquals(tourGroups.size(), 2);
		TourGroup newTourGroup = tourGroups.get(0);
		assertEquals(tourGroup1.getId(), newTourGroup.getId());
		assertEquals(tourGroup1.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup1.getId(), newTourGroup.getId());
		assertEquals(tourGroup1.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup1.isSettled(), newTourGroup.isSettled());
		
		newTourGroup = tourGroups.get(1);
		assertEquals(tourGroup3.getId(), newTourGroup.getId());
		assertEquals(tourGroup3.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup3.getId(), newTourGroup.getId());
		assertEquals(tourGroup3.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup3.isSettled(), newTourGroup.isSettled());
	}
	
	@Test
	public void testGetTourGroupsBySettledStatus() {
		tourGroup1 = tourGroupDao.createTourGroup(tourGroup1);
		tourGroup2 = tourGroupDao.createTourGroup(tourGroup2);
		
		TourGroup tourGroup3 = new TourGroup();
		tourGroup3.setPortageId(portage1.getPortageId());
		tourGroup3.setTourId(tour1.getId());
		tourGroup3.setGroupSize(30);
		tourGroup3.setSettled(true);
		tourGroup3 = tourGroupDao.createTourGroup(tourGroup3);
		
		List<TourGroup> tourGroups = tourGroupDao.getTourGroupsBySettledStatus(company1.getCompanyId(), true);
		assertEquals(tourGroups.size(), 2);
		TourGroup newTourGroup = tourGroups.get(0);
		assertEquals(tourGroup1.getId(), newTourGroup.getId());
		assertEquals(tourGroup1.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup1.getId(), newTourGroup.getId());
		assertEquals(tourGroup1.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup1.isSettled(), newTourGroup.isSettled());
		
		newTourGroup = tourGroups.get(1);
		assertEquals(tourGroup3.getId(), newTourGroup.getId());
		assertEquals(tourGroup3.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup3.getId(), newTourGroup.getId());
		assertEquals(tourGroup3.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup3.isSettled(), newTourGroup.isSettled());
		
		tourGroups = tourGroupDao.getTourGroupsBySettledStatus(company2.getCompanyId(), false);
		assertEquals(tourGroups.size(), 1);
		newTourGroup = tourGroups.get(0);
		assertEquals(tourGroup2.getId(), newTourGroup.getId());
		assertEquals(tourGroup2.getPortageId(), newTourGroup.getPortageId());
		assertEquals(tourGroup2.getId(), newTourGroup.getId());
		assertEquals(tourGroup2.getGroupSize(), newTourGroup.getGroupSize());
		assertEquals(tourGroup2.isSettled(), newTourGroup.isSettled());
	}
	
}
