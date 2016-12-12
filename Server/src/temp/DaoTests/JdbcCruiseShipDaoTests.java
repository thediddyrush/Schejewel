
package DaoTests;

import static DaoTests.JdbcCruiseShipDaoTests.testDao;
import static DaoTests.JdbcCruiseShipDaoTests.cruiseShipDao;
import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;
import legacy.daos.JdbcCruiseLineDao;
import legacy.daos.JdbcCruiseShipDao;
import legacy.daos.interfaces.CruiseShipDao;
import legacy.models.CruiseLine;
import legacy.models.CruiseShip;
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

public class JdbcCruiseShipDaoTests {
	static CruiseShipDao cruiseShipDao;
	static JdbcCruiseLineDao cruiseLineDao;
	static JdbcTestDao testDao;
	CruiseShip cruiseShip1, cruiseShip2, badCruiseShip;
	CruiseLine cruiseLine1, cruiseLine2;
	
	public JdbcCruiseShipDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		cruiseShipDao = new JdbcCruiseShipDao();
		cruiseShipDao.setDataSource(ds);
		cruiseLineDao = new JdbcCruiseLineDao();
		cruiseLineDao.setDataSource(ds);
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
		cruiseShip1.setCruiseShipId(-1);
		cruiseShip1.setName("cruiseShip1");
		cruiseShip1.setCruiseLineId(cruiseLine1.getCruiseLineId());
		
		cruiseShip2 = new CruiseShip();
		cruiseShip2.setName("cruiseShip2");
		cruiseShip2.setCruiseLineId(cruiseLine2.getCruiseLineId());
		
		badCruiseShip = new CruiseShip();
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateCruiseShip() {
		//test creation of cruiseShip
		cruiseShip1 = cruiseShipDao.createCruiseShip(cruiseShip1);
		assert(cruiseShip1 != null);
		assert(cruiseShip1.getCruiseShipId() != -1);
		assert(cruiseShip1.getCruiseShipId() >= 0);
		
		//test creation of cruiseShip with null name
		thrown.expect(DataIntegrityViolationException.class);
		badCruiseShip = cruiseShipDao.createCruiseShip(badCruiseShip);
		
		//test creation of cruiseShip with null cruiseLineid
		badCruiseShip.setName("");
		thrown.expect(DataIntegrityViolationException.class);
		badCruiseShip = cruiseShipDao.createCruiseShip(badCruiseShip);
	}
	
	@Test
	public void testUpdateCruiseShip() {
		cruiseShip1 = cruiseShipDao.createCruiseShip(cruiseShip1);
		cruiseShip2.setCruiseShipId(cruiseShip1.getCruiseShipId());
		cruiseShip2.setCruiseLineId(cruiseLine2.getCruiseLineId());
		
		//test that update works
		CruiseShip cruiseShip3 = cruiseShipDao.updateCruiseShip(cruiseShip2);
		assertEquals(cruiseShip2.getCruiseShipId(), cruiseShip3.getCruiseShipId());
		assertEquals(cruiseShip3.getName(), "cruiseShip2");
		
		//test update on cruiseShip that doesn't exist
		badCruiseShip.setCruiseShipId(-1);
		badCruiseShip.setName("name");
		badCruiseShip = cruiseShipDao.updateCruiseShip(badCruiseShip);//doesn't throw exception
	}
	
	@Test
	public void testDeleteCruiseShip() {
		//test for successful deletion
		cruiseShip1 = cruiseShipDao.createCruiseShip(cruiseShip1);
		int cs1id = cruiseShip1.getCruiseShipId();
		cruiseShip2 = cruiseShipDao.createCruiseShip(cruiseShip2);
		int cs2id = cruiseShip2.getCruiseShipId();
		cruiseShipDao.deleteCruiseShip(cruiseShip1.getCruiseShipId());
		
		//try to get deleted cruiseShip
		thrown.expect(EmptyResultDataAccessException.class);
		cruiseShip1 = cruiseShipDao.getCruiseShip(cruiseShip1.getCruiseShipId());
		
		//try to get non-deleted cruiseShip
		cruiseShip2 = cruiseShipDao.getCruiseShip(cruiseShip2.getCruiseShipId());
		assertEquals(cruiseShip2.getCruiseShipId(), cs2id);
		assertEquals(cruiseShip2.getName(), "cruiseShip2");
		assertEquals(cruiseShip2.getCruiseShipId(), cruiseLine2.getCruiseLineId());
		
		//try to delete already deleted cruiseShip - this doesn't throw an exception
		cruiseShipDao.deleteCruiseShip(cs1id);
	}
	
	@Test
	public void testGetCruiseShip() {
		cruiseShip1 = cruiseShipDao.createCruiseShip(cruiseShip1);
		cruiseShip2 = cruiseShipDao.createCruiseShip(cruiseShip2);
		
		//test getCruiseShip on cruiseShips that exist
		CruiseShip a = cruiseShipDao.getCruiseShip(cruiseShip1.getCruiseShipId());
		assertEquals(cruiseShip1.getCruiseShipId(), a.getCruiseShipId());
		assertEquals(cruiseShip1.getName(), a.getName());
		assertEquals(cruiseShip1.getCruiseLineId(), a.getCruiseLineId());
		CruiseShip b = cruiseShipDao.getCruiseShip(cruiseShip2.getCruiseShipId());
		assertEquals(cruiseShip2.getCruiseShipId(), b.getCruiseShipId());
		assertEquals(cruiseShip2.getName(), b.getName());
		assertEquals(cruiseShip2.getCruiseLineId(), b.getCruiseLineId());
		
		//test getCruiseShip on cruiseShip that doesn't exist
		thrown.expect(EmptyResultDataAccessException.class);
		CruiseShip c = cruiseShipDao.getCruiseShip(cruiseShip2.getCruiseShipId() + 1);	
	}
	
	@Test
	public void testGetCruiseShipByName() {
		cruiseShip1 = cruiseShipDao.createCruiseShip(cruiseShip1);
		cruiseShip2 = cruiseShipDao.createCruiseShip(cruiseShip2);
		
		//test getCruiseShipByName on cruiseShips that exist
		CruiseShip a = cruiseShipDao.getCruiseShipByName("cruiseShip1");
		assertEquals(cruiseShip1.getCruiseShipId(), a.getCruiseShipId());
		assertEquals(cruiseShip1.getName(), a.getName());
		assertEquals(cruiseShip1.getCruiseLineId(), a.getCruiseLineId());
		CruiseShip b = cruiseShipDao.getCruiseShipByName("cruiseShip2");
		assertEquals(cruiseShip2.getCruiseShipId(), b.getCruiseShipId());
		assertEquals(cruiseShip2.getName(), b.getName());
		assertEquals(cruiseShip2.getCruiseLineId(), b.getCruiseLineId());
		
		//test getCruiseShipByName on cruiseShip that doesn't exist
		thrown.expect(EmptyResultDataAccessException.class);
		CruiseShip c = cruiseShipDao.getCruiseShipByName("cruiseShip3");	
	}
	
	@Test
	public void testGetCruiseShips() {
		cruiseShip1 = cruiseShipDao.createCruiseShip(cruiseShip1);
		cruiseShip2 = cruiseShipDao.createCruiseShip(cruiseShip2);
		CruiseShip cruiseShip3 = new CruiseShip();
		cruiseShip3.setName("cruiseShip3");
		cruiseShip3.setCruiseLineId(cruiseLine1.getCruiseLineId());
		cruiseShip3 = cruiseShipDao.createCruiseShip(cruiseShip3);
		
		//test getCruiseShips on cruiseShips that exist
		List<CruiseShip> ships = cruiseShipDao.getCruiseShips(cruiseLine1.getCruiseLineId());
		assertEquals(ships.size(), 2);
		CruiseShip a = ships.get(0);
		assertEquals(cruiseShip1.getCruiseShipId(), a.getCruiseShipId());
		assertEquals(cruiseShip1.getName(), a.getName());
		assertEquals(cruiseShip1.getCruiseLineId(), a.getCruiseLineId());
		CruiseShip b = ships.get(1);
		assertEquals(cruiseShip3.getCruiseShipId(), b.getCruiseShipId());
		assertEquals(cruiseShip3.getName(), b.getName());
		assertEquals(cruiseShip3.getCruiseLineId(), b.getCruiseLineId());
		
		//test getCruiseShips on cruiseShip that doesn't exist
		ships = cruiseShipDao.getCruiseShips(cruiseLine2.getCruiseLineId() + 1);
		assertEquals(ships.size(), 0);
	}
}
