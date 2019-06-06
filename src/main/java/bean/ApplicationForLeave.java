package bean;

import java.util.Date;
//请假
public class ApplicationForLeave {
	//是否批准
	private boolean state ;
	//开始时间
	private Date startTime; 
	//结束时间
	private Date endTime;
	//申请人id
	private int applicatdPerson;
	//批准人id
	private int certificatedPerson;
	
	//是否销假
	private boolean isReportBack;
	//销假结束日期
	private Date reportBackTime;
	
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getApplicatdPerson() {
		return applicatdPerson;
	}
	public void setApplicatdPerson(int applicatdPerson) {
		this.applicatdPerson = applicatdPerson;
	}
	public int getCertificatedPerson() {
		return certificatedPerson;
	}
	public void setCertificatedPerson(int certificatedPerson) {
		this.certificatedPerson = certificatedPerson;
	}
	public boolean isReportBack() {
		return isReportBack;
	}
	public void setReportBack(boolean isReportBack) {
		this.isReportBack = isReportBack;
	}
	public Date getReportBackTime() {
		return reportBackTime;
	}
	public void setReportBackTime(Date reportBackTime) {
		this.reportBackTime = reportBackTime;
	}
	
	
}
