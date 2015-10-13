package util;

import java.util.List;
import java.util.LinkedList;
import java.util.Timer;


public class ScheduleManager extends Timer{
	private List<Schedule> scheduleList;
	
	public ScheduleManager(){
		scheduleList = new LinkedList<Schedule>();
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
		if(sch.cancel()){
			scheduleList.remove(sch);
		}		
	}
	
	
}
