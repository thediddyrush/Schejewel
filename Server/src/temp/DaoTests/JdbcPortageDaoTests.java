
package DaoTests;

import static DaoTests.JdbcPortageDaoTests.testDao;
import static DaoTests.JdbcPortageDaoTests.portageDao;
import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;
import legacy.daos.JdbcCompanyDao;
import legacy.daos.JdbcCruiseLineDao;
import legacy.daos.JdbcPortageDao;
import legacy.daos.JdbcCruiseShipDao;
import legacy.daos.JdbcStatusDao;
import legacy.daos.JdbcTourDao;
import legacy.daos.JdbcTourGroupDao;
import legacy.daos.JdbcTourTypeDao;
import legacy.daos.interfaces.CompanyDao;
import legacy.daos.interfaces.CruiseLineDao;
import legacy.daos.interfaces.PortageDao;
import legacy.daos.interfaces.CruiseShipDao;
import legacy.daos.interfaces.StatusDao;
import legacy.daos.interfaces.TourDao;
import legacy.daos.interfaces.TourGroupDao;
import legacy.daos.interfaces.TourTypeDao;
import legacy.models.Company;
import legacy.models.CruiseLine;
import legacy.models.Portage;
import legacy.models.CruiseShip;
import legacy.models.Status;
import legacy.models.Tour;
import legacy.models.TourGroup;
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

public class JdbcPortageDaoTests {
	static PortageDao portageDao;
	static CruiseShipDao cruiseShipDao;
	static CruiseLineDao cruiseLineDao;
	static TourDao tourDao;
	static TourGroupDao tourGroupDao;
	static CompanyDao companyDao;
	static StatusDao statusDao;
	static TourTypeDao tourTypeDao;
	static JdbcTestDao testDao;
	Portage portage1, portage2, badPortage;
	CruiseShip cruiseShip1, cruiseShip2;
	CruiseLine cruiseLine1, cruiseLine2;
	
	public JdbcPortageDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		
		portageDao = new JdbcPortageDao();
		portageDao.setDataSource(ds);
		cruiseShipDao = new JdbcCruiseShipDao();
		cruiseShipDao.setDataSource(ds);
		cruiseLineDao = new JdbcCruiseLineDao();
		cruiseLineDao.setDataSource(ds);
		tourDao = new JdbcTourDao();
		tourDao.setDataSource(ds);
		tourGroupDao = new JdbcTourGroupDao();
		tourGroupDao.setDataSource(ds);
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
		cruiseShip2 = cruiseShipDao.createCruiseShip(cruiseShip2);
		
		portage1 = new Portage();
		portage1.setCruiseShipId(cruiseShip1.getCruiseShipId());
		portage1.setArrival(1000000000);
		portage1.setDeparture(2000000000);
		portage1.setPassengerCount(50);
		portage1.setAllAboard(5000l);
		portage1.setDock(1);
		portage1.setVoyage("voyage1");
		portage1.setLocation("location1");
		
		portage2 = new Portage();
		portage2.setCruiseShipId(cruiseShip2.getCruiseShipId());
		portage2.setArrival(3000000000l);
		portage2.setDeparture(4000000000l);
		
		badPortage = new Portage();
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreatePortage() {
		portage1 = portageDao.createPortage(portage1);
		Portage newPortage = portageDao.getPortage(portage1.getPortageId());
		assertEquals(portage1.getPortageId(), newPortage.getPortageId());
		assertEquals(portage1.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage1.getArrival(), newPortage.getArrival());
		assertEquals(portage1.getDeparture(), newPortage.getDeparture());
		assertEquals(portage1.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage1.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage1.getDock(), newPortage.getDock());
		assertEquals(portage1.getVoyage(), newPortage.getVoyage());
		assertEquals(portage1.getLocation(), newPortage.getLocation());
		
		//try creating portages in various stages of incompleteness
		thrown.expect(DataIntegrityViolationException.class);
		portageDao.createPortage(badPortage);
		
		portage1.setCruiseShipId(cruiseShip1.getCruiseShipId());
		thrown.expect(DataIntegrityViolationException.class);
		portageDao.createPortage(badPortage);
		
		portage1.setArrival(1000000000);
		thrown.expect(DataIntegrityViolationException.class);
		portageDao.createPortage(badPortage);
		
		portage1.setDeparture(2000000000);
		badPortage = portageDao.createPortage(badPortage);
		
		newPortage = portageDao.getPortage(portage1.getPortageId());
		assertEquals(portage1.getPortageId(), newPortage.getPortageId());
		assertEquals(portage1.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage1.getArrival(), newPortage.getArrival());
		assertEquals(portage1.getDeparture(), newPortage.getDeparture());
		assertEquals(portage1.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage1.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage1.getDock(), newPortage.getDock());
		assertEquals(portage1.getVoyage(), newPortage.getVoyage());
		assertEquals(portage1.getLocation(), newPortage.getLocation());
	}
	
	@Test
	public void testUpdatePortage() {
		portage1 = portageDao.createPortage(portage1);
		portage2.setPortageId(portage1.getPortageId());
		
		//test that update works
		portageDao.updatePortage(portage2);
		Portage newPortage = portageDao.getPortage(portage1.getPortageId());
		assertEquals(portage2.getPortageId(), newPortage.getPortageId());
		assertEquals(portage2.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage2.getArrival(), newPortage.getArrival());
		assertEquals(portage2.getDeparture(), newPortage.getDeparture());
		assertEquals(portage2.getLocation(), newPortage.getLocation());
		assertEquals(portage2.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage2.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage2.getDock(), newPortage.getDock());
		assertEquals(portage2.getVoyage(), newPortage.getVoyage());
	}
	
	@Test
	public void testDeletePortage() {
		//test for successful deletion
		portage1 = portageDao.createPortage(portage1);
		int portage1id = portage1.getPortageId();
		portage2 = portageDao.createPortage(portage2);
		int portage2id = portage2.getPortageId();
		portageDao.deletePortage(portage1.getPortageId());
		
		//try to get deleted portage
		thrown.expect(EmptyResultDataAccessException.class);
		portage1 = portageDao.getPortage(portage1id);
		
		//try to get non-deleted portage
		Portage newPortage = portageDao.getPortage(portage2id);
		assertEquals(portage2.getPortageId(), newPortage.getPortageId());
		assertEquals(portage2.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage2.getArrival(), newPortage.getArrival());
		assertEquals(portage2.getDeparture(), newPortage.getDeparture());
		assertEquals(portage2.getLocation(), newPortage.getLocation());
		assertEquals(portage2.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage2.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage2.getDock(), newPortage.getDock());
		assertEquals(portage2.getVoyage(), newPortage.getVoyage());
		
		//try to delete already deleted portage - this doesn't throw an exception
		portageDao.deletePortage(portage1id);
	}
	
	@Test
	public void testGetPortage() {
		portage1 = portageDao.createPortage(portage1);
		portage2 = portageDao.createPortage(portage2);
		
		//test getPortage on portages that exist
		Portage newPortage = portageDao.getPortage(portage1.getPortageId());
		assertEquals(portage1.getPortageId(), newPortage.getPortageId());
		assertEquals(portage1.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage1.getArrival(), newPortage.getArrival());
		assertEquals(portage1.getDeparture(), newPortage.getDeparture());
		assertEquals(portage1.getLocation(), newPortage.getLocation());
		assertEquals(portage1.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage1.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage1.getDock(), newPortage.getDock());
		assertEquals(portage1.getVoyage(), newPortage.getVoyage());
		
		newPortage = portageDao.getPortage(portage2.getPortageId());
		assertEquals(portage2.getPortageId(), newPortage.getPortageId());
		assertEquals(portage2.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage2.getArrival(), newPortage.getArrival());
		assertEquals(portage2.getDeparture(), newPortage.getDeparture());
		assertEquals(portage2.getLocation(), newPortage.getLocation());
		assertEquals(portage2.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage2.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage2.getDock(), newPortage.getDock());
		assertEquals(portage2.getVoyage(), newPortage.getVoyage());
		
		//test getPortage on portage that doesn't exist
		thrown.expect(EmptyResultDataAccessException.class);
		newPortage = portageDao.getPortage(portage2.getPortageId() + 1);
	}
	
	@Test
	public void testGetPortageByCruiseShipId() {
		portage1 = portageDao.createPortage(portage1);
		portage2 = portageDao.createPortage(portage2);
		
		Portage portage3 = new Portage();
		portage3.setCruiseShipId(cruiseShip1.getCruiseShipId());
		portage3.setArrival(1000000000);
		portage3.setDeparture(2200000000l);
		portage3.setLocation("location3");
		portage3.setPassengerCount(55);
		portage3.setAllAboard(210000l);
		portage3.setDock(2);
		portage3.setVoyage("voyage3");
		portage3 = portageDao.createPortage(portage3);
		
		List<Portage> portages = portageDao.getPortageByCruiseShipId(cruiseShip1.getCruiseShipId());
		assertEquals(portages.size(), 2);
		Portage newPortage = portages.get(0);
		assertEquals(portage1.getPortageId(), newPortage.getPortageId());
		assertEquals(portage1.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage1.getArrival(), newPortage.getArrival());
		assertEquals(portage1.getDeparture(), newPortage.getDeparture());
		assertEquals(portage1.getLocation(), newPortage.getLocation());
		assertEquals(portage1.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage1.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage1.getDock(), newPortage.getDock());
		assertEquals(portage1.getVoyage(), newPortage.getVoyage());
		newPortage = portages.get(1);
		assertEquals(portage3.getPortageId(), newPortage.getPortageId());
		assertEquals(portage3.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage3.getArrival(), newPortage.getArrival());
		assertEquals(portage3.getDeparture(), newPortage.getDeparture());
		assertEquals(portage3.getLocation(), newPortage.getLocation());
		assertEquals(portage3.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage3.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage3.getDock(), newPortage.getDock());
		assertEquals(portage3.getVoyage(), newPortage.getVoyage());
	}
	
	@Test
	public void testGetPortageByCruiseLineId() {
		CruiseShip cruiseShip3 = new CruiseShip();
		cruiseShip3 = new CruiseShip();
		cruiseShip3.setName("cruiseShip3");
		cruiseShip3.setCruiseLineId(cruiseLine1.getCruiseLineId());
		cruiseShip3 = cruiseShipDao.createCruiseShip(cruiseShip3);
		
		portage1 = portageDao.createPortage(portage1);
		portage2 = portageDao.createPortage(portage2);
		
		Portage portage3 = new Portage();
		portage3.setCruiseShipId(cruiseShip3.getCruiseShipId());
		portage3.setArrival(1000000000);
		portage3.setDeparture(2200000000l);
		portage3.setLocation("location3");
		portage3.setPassengerCount(55);
		portage3.setAllAboard(210000l);
		portage3.setDock(2);
		portage3.setVoyage("voyage3");
		portage3 = portageDao.createPortage(portage3);
		
		List<Portage> portages = portageDao.getPortageByCruiseLineId(cruiseLine1.getCruiseLineId());
		assertEquals(portages.size(), 2);
		
		Portage newPortage = portages.get(0);
		assertEquals(portage1.getPortageId(), newPortage.getPortageId());
		assertEquals(portage1.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage1.getArrival(), newPortage.getArrival());
		assertEquals(portage1.getDeparture(), newPortage.getDeparture());
		assertEquals(portage1.getLocation(), newPortage.getLocation());
		assertEquals(portage1.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage1.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage1.getDock(), newPortage.getDock());
		assertEquals(portage1.getVoyage(), newPortage.getVoyage());
		
		newPortage = portages.get(1);
		assertEquals(portage3.getPortageId(), newPortage.getPortageId());
		assertEquals(portage3.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage3.getArrival(), newPortage.getArrival());
		assertEquals(portage3.getDeparture(), newPortage.getDeparture());
		assertEquals(portage3.getLocation(), newPortage.getLocation());
		assertEquals(portage3.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage3.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage3.getDock(), newPortage.getDock());
		assertEquals(portage3.getVoyage(), newPortage.getVoyage());
	}
	
	@Test
	public void testGetPortageByDateRange() {
		portage1 = portageDao.createPortage(portage1);
		portage2 = portageDao.createPortage(portage2);
		
		Portage portage3 = new Portage();
		portage3.setCruiseShipId(cruiseShip1.getCruiseShipId());
		portage3.setArrival(2000000000);
		portage3.setDeparture(2200000000l);
		portage3.setLocation("location3");
		portage3.setPassengerCount(55);
		portage3.setAllAboard(210000l);
		portage3.setDock(2);
		portage3.setVoyage("voyage3");
		portage3 = portageDao.createPortage(portage3);
		
		List<Portage> portages = portageDao.getPortageByArrivalRange(1000000000, 2000000000);
		assertEquals(portages.size(), 2);
		
		Portage newPortage = portages.get(0);
		assertEquals(portage1.getPortageId(), newPortage.getPortageId());
		assertEquals(portage1.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage1.getArrival(), newPortage.getArrival());
		assertEquals(portage1.getDeparture(), newPortage.getDeparture());
		assertEquals(portage1.getLocation(), newPortage.getLocation());
		assertEquals(portage1.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage1.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage1.getDock(), newPortage.getDock());
		assertEquals(portage1.getVoyage(), newPortage.getVoyage());
		
		newPortage = portages.get(1);
		assertEquals(portage3.getPortageId(), newPortage.getPortageId());
		assertEquals(portage3.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage3.getArrival(), newPortage.getArrival());
		assertEquals(portage3.getDeparture(), newPortage.getDeparture());
		assertEquals(portage3.getLocation(), newPortage.getLocation());
		assertEquals(portage3.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage3.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage3.getDock(), newPortage.getDock());
		assertEquals(portage3.getVoyage(), newPortage.getVoyage());
	}
	
	@Test
	public void testGetPortages() {
		Company company1 = new Company();
		company1.setName("company1");
		company1 = companyDao.createCompany(company1);
		
		Status status1 = new Status();
		status1.setDescription("status1");
		status1 = statusDao.createStatus(status1);
		
		TourType tourType1 = new TourType();
		tourType1.setName("tourType1");
		tourType1.setCompanyId(company1.getCompanyId());
		tourType1 = tourTypeDao.createTourType(tourType1);
		
		Tour tour1 = new Tour();
		tour1.setOwnerId(company1.getCompanyId());
		tour1.setStartTime(5000000000l);
		tour1.setTourTypeId(tourType1.getTourTypeId());
		tour1.setStatusId(status1.getStatusId());
		tour1 = tourDao.createTour(tour1);
		
		portage1 = portageDao.createPortage(portage1);
		portage2 = portageDao.createPortage(portage2);
		
		TourGroup tourGroup1 = new TourGroup();
		tourGroup1.setPortageId(portage1.getPortageId());
		tourGroup1.setTourId(tour1.getTourId());
		tourGroup1.setGroupSize(10);
		tourGroup1.setSettled(true);
		tourGroup1 = tourGroupDao.createTourGroup(tourGroup1);
		
		List<Portage> portages = portageDao.getPortages(company1.getCompanyId());
		assertEquals(1, portages.size());
		Portage newPortage = portages.get(0);
		assertEquals(portage1.getPortageId(), newPortage.getPortageId());
		assertEquals(portage1.getCruiseShipId(), newPortage.getCruiseShipId());
		assertEquals(portage1.getArrival(), newPortage.getArrival());
		assertEquals(portage1.getDeparture(), newPortage.getDeparture());
		assertEquals(portage1.getLocation(), newPortage.getLocation());
		assertEquals(portage1.getPassengerCount(), newPortage.getPassengerCount());
		assertEquals(portage1.getAllAboard(), newPortage.getAllAboard());
		assertEquals(portage1.getDock(), newPortage.getDock());
		assertEquals(portage1.getVoyage(), newPortage.getVoyage());
	}
	
}
