package legacy.daos;

import legacy.daos.interfaces.CompanyDao;
import legacy.models.Company;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCompanyDao implements CompanyDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void setDataSource(DataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}
    
	@Override
	public Company getCompany(int companyid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",companyid);
        String sql = "SELECT * FROM company WHERE id = :id";
        Company company = jdbc.queryForObject(sql, params, new BeanPropertyRowMapper<>(Company.class));
		company.setCompanyId(companyid);
        return company;
	}

	@Override
	public Company updateCompany(Company company) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id",company.getCompanyId());
        params.addValue("name",company.getName());
		String sql = "UPDATE company SET name=:name WHERE id=:id";
		jdbc.update(sql, params);
		return company;
	}

	@Override
	public void deleteCompany(int companyid) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",companyid);
		String sql = "DELETE FROM company WHERE id=:id";
		jdbc.update(sql, params);
	}

	@Override
	public Company createCompany(Company company) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", company.getName());
		String sql = "INSERT INTO company(name)"
			+ " VALUES(:name)";
		KeyHolder kh = new GeneratedKeyHolder();
		jdbc.update(sql, params, kh);
		company.setCompanyId(kh.getKey().intValue());
		return company;
	}
}
