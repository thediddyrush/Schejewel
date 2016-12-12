/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DaoTests;

import TestSuite.JdbcTestDao;
import TestSuite.TestDatabaseInfo;
import legacy.daos.JdbcCompanyDao;
import legacy.daos.interfaces.CompanyDao;
import legacy.models.Company;
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

/**
 *
 * @author Ivy Bridge
 */
public class JdbcCompanyDaoTests {
	static CompanyDao companyDao;
	static JdbcTestDao testDao;
	Company company1, company2, badCompany;
	
	public JdbcCompanyDaoTests() {
		TestDatabaseInfo tdi = new TestDatabaseInfo();
		DataSource ds = tdi.getDataSource();
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
		company1.setCompanyId(-1);
		company2 = new Company();
		company2.setName("company2");
		badCompany = new Company();
	}
	
	@After
	public void tearDown() {
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateCompany() {
		//test creation of company
		company1 = companyDao.createCompany(company1);
		assert(company1 != null);
		assert(company1.getCompanyId() != -1);
		assert(company1.getCompanyId() >= 0);
		assertEquals(company1.getName(), "company1");
		
		//test creation of company with null name
		thrown.expect(DataIntegrityViolationException.class);
		badCompany = companyDao.createCompany(badCompany);
	}
	
	@Test
	public void testUpdateCompany() {
		company1 = companyDao.createCompany(company1);
		company2.setCompanyId(company1.getCompanyId());
		
		//test that update works
		company2 = companyDao.updateCompany(company2);
		assertEquals(company1.getCompanyId(), company2.getCompanyId());
		assertEquals(company2.getName(), "company2");
		
		//test update on company that doesn't exist
		badCompany.setCompanyId(-1);
		badCompany.setName("name");
		badCompany = companyDao.updateCompany(badCompany);//doesn't throw exception
	}
	
	@Test
	public void testDeleteCompany() {
		//test for successful deletion
		company1 = companyDao.createCompany(company1);
		int comp1id = company1.getCompanyId();
		company2 = companyDao.createCompany(company2);
		int comp2id = company2.getCompanyId();
		companyDao.deleteCompany(company1.getCompanyId());
		
		//try to get deleted company
		thrown.expect(EmptyResultDataAccessException.class);
		company1 = companyDao.getCompany(company1.getCompanyId());
		
		//try to get non-deleted company
		company2 = companyDao.getCompany(company2.getCompanyId());
		assertEquals(company2.getCompanyId(), comp2id);
		assertEquals(company2.getName(), "company2");
		
		//try to delete already deleted company - this doesn't throw an exception
		companyDao.deleteCompany(comp1id);
	}
	
	@Test
	public void testGetCompany() {
		company1 = companyDao.createCompany(company1);
		company2 = companyDao.createCompany(company2);
		
		//test getCompany on companies that exist
		Company a = companyDao.getCompany(company1.getCompanyId());
		assertEquals(company1.getCompanyId(), a.getCompanyId());
		assertEquals(company1.getName(), a.getName());
		Company b = companyDao.getCompany(company2.getCompanyId());
		assertEquals(company2.getCompanyId(), b.getCompanyId());
		assertEquals(company2.getName(), b.getName());
		
		//test getCompany on company that doesn't exist
		thrown.expect(EmptyResultDataAccessException.class);
		Company c = companyDao.getCompany(company2.getCompanyId() + 1);
		
	}
	
}
