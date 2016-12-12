package legacy.daos.interfaces;

import legacy.models.CruiseShip;
import java.util.List;
import javax.sql.DataSource;

public interface CruiseShipDao {
	public void setDataSource(DataSource ds);
	public CruiseShip getCruiseShip(int cruiseShipId);
	public CruiseShip getCruiseShipByName(String name);
    public List<CruiseShip> getCruiseShips(int cruiseLineId);
    public CruiseShip updateCruiseShip(CruiseShip cruiseShip);
	public void deleteCruiseShip(int cruiseShipId);
	public CruiseShip createCruiseShip(CruiseShip cruiseShip);
}
