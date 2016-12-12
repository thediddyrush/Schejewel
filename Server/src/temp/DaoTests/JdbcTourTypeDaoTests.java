
package DaoTests;

import TestSuite.JdbcTestDao;
import static DaoTests.JdbcTourTypeDaoTests.testDao;
import TestSuite.TestDatabaseInfo;
import legacy.daos.JdbcCompanyDao;
import legacy.daos.JdbcTourTypeDao;
import legacy.daos.interfaces.TourTypeDao;
import legacy.models.Company;
import legacy.models.TourType;
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

public class JdbcTourTypeDaoTests {
	static TourTypeDao tourTypeDao;
	static JdbcCompanyDao companyDao;
	static JdbcTestDao testDao;
	TourType tourType1, tourType2, badTourType;
	Company company1, company2;
	
	public JdbcTourTypeDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
		tourTypeDao = new JdbcTourTypeDao();
		tourTypeDao.setDataSource(ds);
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
		tourType1.setTourTypeId(-1);
		tourType1.setCompanyId(company1.getCompanyId());
		
		tourType2 = new TourType();
		tourType2.setName("tourType2");
		tourType2.setCompanyId(company2.getCompanyId());
		
		badTourType = new TourType();
	}
	
	@After
	public void tearDown() {
	}

    @Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateTourType() {
		//test creation of tourType
		tourType1 = tourTypeDao.createTourType(tourType1);
		assert(tourType1 != null);
		assert(tourType1.getTourTypeId() != -1);
		assert(tourType1.getTourTypeId() >= 0);
		assertEquals(tourType1.getName(), "tourType1");
		assertEquals(tourType1.getCompanyId(), company1.getCompanyId());
		
		//test creation of tourType with null name
		thrown.expect(DataIntegrityViolationException.class);
		badTourType = tourTypeDao.createTourType(badTourType);
		
		//test creation of tourType with null companyid
		badTourType.setName("");
		thrown.expect(DataIntegrityViolationException.class);
		badTourType = tourTypeDao.createTourType(badTourType);
	}
	
	@Test
	public void testUpdateTourType() {
		tourType1 = tourTypeDao.createTourType(tourType1);
		tourType2.setTourTypeId(tourType1.getTourTypeId());
		tourType2.setCompanyId(company2.getCompanyId());
		
		//test that update works
		TourType tourType3 = tourTypeDao.updateTourType(tourType2);
		assertEquals(tourType1.getTourTypeId(), tourType3.getTourTypeId());
		assertEquals(tourType3.getName(), "tourType2");
		assertEquals(tourType3.getCompanyId(), company2.getCompanyId());
		
		//test update on tourType that doesn't exist
		badTourType.setTourTypeId(-1);
		badTourType.setName("name");
		badTourType = tourTypeDao.updateTourType(badTourType);//doesn't throw exception
	}
	
	@Test
	public void testDeleteTourType() {
		//test for successful deletion
		tourType1 = tourTypeDao.createTourType(tourType1);
		int tt1id = tourType1.getTourTypeId();
		tourType2 = tourTypeDao.createTourType(tourType2);
		int tt2id = tourType2.getTourTypeId();
		tourTypeDao.deleteTourType(tourType1.getTourTypeId());
		
		//try to get deleted tourType
		thrown.expect(EmptyResultDataAccessException.class);
		tourType1 = tourTypeDao.getTourType(tourType1.getTourTypeId());
		
		//try to get non-deleted tourType
		tourType2 = tourTypeDao.getTourType(tourType2.getTourTypeId());
		assertEquals(tourType2.getTourTypeId(), tt2id);
		assertEquals(tourType2.getName(), "tourType2");
		assertEquals(tourType2.getCompanyId(), company2.getCompanyId());
		
		//try to delete already deleted tourType - this doesn't throw an exception
		tourTypeDao.deleteTourType(tt1id);
	}
	
	@Test
	public void testGetTourType() {
		tourType1 = tourTypeDao.createTourType(tourType1);
		tourType2 = tourTypeDao.createTourType(tourType2);
		
		//test getTourType on tourTypes that exist
		TourType a = tourTypeDao.getTourType(tourType1.getTourTypeId());
		assertEquals(tourType1.getTourTypeId(), a.getTourTypeId());
		assertEquals(tourType1.getName(), a.getName());
		assertEquals(tourType1.getCompanyId(), company1.getCompanyId());
		TourType b = tourTypeDao.getTourType(tourType2.getTourTypeId());
		assertEquals(tourType2.getTourTypeId(), b.getTourTypeId());
		assertEquals(tourType2.getName(), b.getName());
		assertEquals(tourType2.getCompanyId(), company2.getCompanyId());
		
		//test getTourType on tourType that doesn't exist
		thrown.expect(EmptyResultDataAccessException.class);
		TourType c = tourTypeDao.getTourType(tourType2.getTourTypeId() + 1);
		
	}
}
