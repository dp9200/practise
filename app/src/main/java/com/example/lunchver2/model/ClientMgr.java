package com.example.lunchver2.model;

import com.example.lunchver2.myInterface.IThreadAction;


import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ClientMgr implements IThreadAction {

    private SendMissionPool missionPool = null;
    private ThreadMgr threadMgr = null;
    private String url = "https://script.google.com/macros/s/AKfycbxkE7QbCO2kD1NW_jO3pwkwV5y5oE8FIcSL0c4w42_7GzPScQKxQfVzv02Ts8YxaQvNLg/exec";
    private URL urlConnector = null;
    private OkHttpClient httpClient = null;

    private static ClientMgr instance = null;

    public static ClientMgr getInstance()
    {
        if (instance == null)
        {
            instance = new ClientMgr();
        }
        return instance;
    }

    private ClientMgr() {
        missionPool = new SendMissionPool();
        threadMgr = new ThreadMgr(this);
        httpClient = new OkHttpClient().newBuilder().build();
    }

    public void sendResult(String result, String type) {
        missionPool.addNewMission(result, type);
        threadMgr.start();
    }

    @Override
    public void excutedAction() {
        sendMissonToServer();
    }

    private void sendMissonToServer() {
        SendMissionPool.Mission mission = null;
        RequestBody body = null;
        Request request = null;
        Call call = null;

        while (missionPool.checkMaveMission()) {
            mission = missionPool.getMission();
            body = getRequestBody(mission);
            request = getRequest(body);
            sendAPI(request);
        }
    }

    private RequestBody getRequestBody(SendMissionPool.Mission mission)
    {
        return new FormBody.Builder()
        .add("Type", mission.Type)
        .add("Result", mission.Result)
        .add("SendTime", mission.SendTime)
                .build();
    }

    private Request getRequest(RequestBody body)
    {
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    private void sendAPI(Request request)
    {
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("失敗: " + call.toString() + " E:" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("成功");
            }
        });
    }
}
