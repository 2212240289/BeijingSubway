package cn.edu.zucc.model;

import java.util.ArrayList;
import java.util.List;

public class Routine {

    private Station beginStation;   //����վ��

    private Station endStation;     //����վ��

    private List<Station> passStations = new ArrayList<>();     //���·���ϵ�վ��

    public Station getBeginStation() {
        return beginStation;
    }

    public void setBeginStation(Station beginStation) {
        this.beginStation = beginStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public void setEndStation(Station endStation) {
        this.endStation = endStation;
    }

    public List<Station> getPassStations() {
        return passStations;
    }

    public void setPassStations(List<Station> passStations) {
        this.passStations = passStations;
    }
}
