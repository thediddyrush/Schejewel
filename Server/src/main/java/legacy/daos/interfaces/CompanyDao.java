package legacy.daos.interfaces;

import legacy.models.Company;
import javax.sql.DataSource;

public interface CompanyDao {
	public void setDataSource(DataSource ds);
	public Company getCompany(int companyid);
	public Company updateCompany(Company company);
	public void deleteCompany(int companyid);
	public Company createCompany(Company company);
}
