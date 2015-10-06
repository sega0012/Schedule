package com.example.samuel.schedule;

/**
 * Created by user05 on 2015/10/5.
 */
public class ScheduleThum {
    private int id;
    private String tag;
    private String title;
    private String time;
    public ScheduleThum(){

    }
    public ScheduleThum(int id,String tag, String title, String time) {
        this.id = id;
        this.tag = tag;
        this.title = title;
        this.time = time;
    }

    @Override
    public String toString() {
        return "ScheduleThum{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public int getId(){return  id;}
    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }
}
