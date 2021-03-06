package serviceimpl;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.Employee;
import bean.Sector;
import dao.SectorDao;
import service.SectorService;

@Service
public class SectorServiceImpl implements SectorService {
	
	@Autowired
	SectorDao sectorDao;


	@Override
	public Boolean setIsMasterById(int id, boolean isM) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findAllSector() {
		List<Sector> l = sectorDao.getAllSector();
		
		if(l.isEmpty()){
			JSONObject ans= new JSONObject();
			ans.put("state", 0);
			ans.put("error_message", "boooooooom");
			return ans.toString();
		}
		else return new JSONArray(l).toString();
	}

	@Override
	public String findSectorById(int sectorId) {
		List<Sector> l=sectorDao.FindSectorById(sectorId);
		if(l.isEmpty()){
			JSONObject ans= new JSONObject();
			ans.put("state", 0);
			ans.put("error_message", "没有该部门");
			return ans.toString();
		}
		else return new JSONArray(l).toString();
	}

	@Override
	public String AddSector(Sector s) {
		JSONObject json = new JSONObject();
		int i =sectorDao.AddSector(s);
		if(i==0){
			json.put("state", 0);
			json.put("error_message", "gg,添加部门失败");
		}else{
			json.put("state", 1);
		}
		return json.toString();
	}

	@Override
	public String ModifySector(Sector s) {
		JSONObject json = new JSONObject();
		int i =sectorDao.ModifySector(s);
		if(i==0){
			json.put("state", 0);
			json.put("error_message", "gg,更改部门失败");
		}else{
			json.put("state", 1);
		}
		return json.toString();
	}

	@Override
	public String DeleteSector(int sectorId) {
		JSONObject json = new JSONObject();
		int i =sectorDao.DeleteSector(sectorId);
		if(i==0){
			json.put("state", 0);
			json.put("error_message", "gg,删除部门失败");
		}else{
			json.put("state", 1);
		}
		return json.toString();
		
	}

	@Override
	public String findAllEmployeeBySectorId(int sectorId) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		int i =  sectorDao.findAllEmployeeBySectorId(sectorId);
		if(i==0){
			json.put("state", 0);
			json.put("error_message", "查询失败");
		}else{
			json.put("state", i);
		}
		return json.toString();
	}

	@Override
	public String findPeopleNumInSector(int sectorId) {
		JSONObject json = new JSONObject();
		int ans = sectorDao.findPeopleNum(sectorId);
		json.put("PeopleNumber", ans);
		return json.toString();
	}

	@Override
	public String findPeopleNumInConpany() {
		List<Sector> l = sectorDao.getAllSector();
		JSONObject json = new JSONObject();
		int total=0;
		for (Sector sector : l) {
			int ans = sectorDao.findPeopleNum(sector.getSectorId());
			total+=ans;
		}
		json.put("PeopleNumber", total);
		return json.toString();
	}
	
	
	
	
	

}
