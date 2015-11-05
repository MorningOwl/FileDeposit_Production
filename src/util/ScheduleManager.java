package util;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import java.util.Timer;


public class ScheduleManager extends Timer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5882231421567688284L;
	private List<Schedule> scheduleList;
	private String userId;
	
	public ScheduleManager(String userId){
		scheduleList = new LinkedList<Schedule>();
		this.userId = userId;
	}
	
	/**
	 * Returns the userId that owns this schedule Manager.
	 * @return
	 */
	public String getUserId(){
		return userId;
	}
	
	/**
	 * Gets the list of schedules in this instance of SchedulerManager.
	 * @return
	 */
	public List<Schedule> getScheduleList(){
		return scheduleList;
	}
	
	/**
	 * Gets schedule from specified index
	 * @return
	 */
	public Schedule getSchedule(int index){
		return scheduleList.get(index);
	}
	
	/**
	 * Adds a schedule to this ScheduleManager and starts it if enabled.
	 * @param sch
	 */
	public void addSchedule (Schedule sch){
		scheduleList.add(sch);
		if(sch.isEnabled()){
			schedule(sch, sch.getFirstTime(), sch.getPeriod());
		}		
	}
	
	/**
	 * Removes a schedule from the ScheduleManager. First cancels the task
	 * then removes the schedule from the list if canceled.
	 * @param sch
	 */
	public void removeSchedule(Schedule sch){		
		sch.cancel();
		scheduleList.remove(sch);
	}
	
	/**
	 * Saves this instance of the manager using the given user id
	 */
	public void saveToFile(){
		try{
			 
			FileOutputStream fout = new FileOutputStream(userId +"_"+ "manager.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			oos.writeObject(this);
			oos.close();

		   }catch(Exception ex){
			   ex.printStackTrace();
		}
	}
	
	public void rescheduleAllSchedules(){
		for(Schedule s: scheduleList){
			schedule(s,s.getFirstTime(),s.getPeriod());
		}
	}
}
