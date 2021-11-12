package com.example.lunchver2.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class SendMissionPool {
    public class Mission
    {
        public String Result = "";
        public String Type = "";
        public String SendTime = "";
    }

    private Queue<Mission> missions = null;
    private Object locker = null;

    public SendMissionPool()
    {
        missions = new LinkedList<Mission>();
        locker = new Object();
    }

    public void addNewMission(String result,String type)
    {
        Mission mission = new Mission();
        mission.SendTime = LocalDateTime.now().toString();
        mission.Type = type;
        mission.Result = result;
        missions.offer(mission);
    }

    public Mission getMission()
    {
        synchronized (locker)
        {
            return missions.poll();
        }
    }

    public boolean checkMaveMission()
    {
        synchronized (locker)
        {
            return missions.size() > 0;
        }
    }
}
