package bean;

public class Sector {
	private int sectorId;
	//部门名称
	private String sectorName;
	//部门人数
	private int sectorPeopleNumber;
	//部门信息
	private String sectorDescription;
	public String getSectorDescription() {
		return sectorDescription;
	}
	public void setSectorDescription(String description) {
		this.sectorDescription = description;
	}
	public int getSectorId() {
		return sectorId;
	}
	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}
	public String getSectorName() {
		return sectorName;
	}
	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}
	public int getSectorPeopleNumber() {
		return sectorPeopleNumber;
	}
	public void setSectorPeopleNumber(int sectorPeopleNumber) {
		this.sectorPeopleNumber = sectorPeopleNumber;
	}
}
