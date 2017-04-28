package com.example.miket.inclassassignment11_michaelt;

import java.io.Serializable;

/**
 * Created by miket on 4/26/2017.
 */

public class NHLTeam implements Serializable {
    private String teamName;
    private String teamRanking;
    private String fileName;

    public NHLTeam(){

    }

    public  NHLTeam (String teamName, String teamRanking, String fileName){
        this.teamName=teamName;
        this.teamRanking=teamRanking;
        this.fileName=fileName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamRanking() {
        return teamRanking;
    }

    public void setTeamRanking(String teamRanking) {
        this.teamRanking = teamRanking;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
