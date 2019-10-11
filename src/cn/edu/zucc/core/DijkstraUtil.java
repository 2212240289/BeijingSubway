package cn.edu.zucc.core;

import cn.edu.zucc.data.FileManager;
import cn.edu.zucc.model.Routine;
import cn.edu.zucc.model.Station;

import java.util.HashMap;
import java.util.List;

public class DijkstraUtil {

    public static HashMap<String,Station> allStation = new HashMap<>();     //�洢ȫ����վ����Ϣ

    public Station FindMinDist( Routine routine,HashMap<HashMap<Station,Station>,Integer> distance , HashMap<Station,Integer> collected){

        Station MinV = null;
        Station item = null;
        int MinDist = 10000;

        for(HashMap<Station,Station> key:distance.keySet()){
            for (Station id: key.keySet()){
                item = key.get(id);
            }
            if(collected.get(item)==0&&distance.get(key)<MinDist){
                MinDist = distance.get(key);
                MinV = item;
            }
        }
        if (MinDist<10000)
            return MinV;
        else
            return new Station("-1");

    }

    public Station getFinStation(HashMap<Station,Station> t){
        Station res = null;

        for (Station key:t.keySet()){
            res = t.get(key);
        }

        return res;
    }

    public HashMap<Station,Station> getFromtoFin (Routine routine,Station station){
        HashMap<Station,Station> res = new HashMap<>();
        res.put(routine.getBeginStation(),station);

        return res;

    }

    public Routine Dijkstra_algorithm ( Routine routine ) {

        HashMap<HashMap<Station,Station>,Integer> distance = new HashMap<>();   //�����վ��֮�����̾��룬��IntegerΪ��λ����ʾվ����
        HashMap<Station,Integer> collected = new HashMap<>();                   //�жϸ�Station�Ƿ񱻷��ʹ�
        HashMap<Station,Station> path = new HashMap<>();                        //�洢ĳ��ָ��վ���ǰһ��վ��
        HashMap<Station,Station> disitem = new HashMap<>();
        Station item;
        Station V;

        for (String key :allStation.keySet()){      //��ʼ��distance��collected��path
            item = allStation.get(key);

            collected.put(item,new Integer(0));

            if (!routine.getBeginStation().equals(item)){
                if (routine.getBeginStation().getLinkStations().contains(item)){    //����������ڣ��򽫾�������Ϊ1��������Ӧ��path����Ϊ���
                    disitem = new HashMap<>();
                    disitem.put(routine.getBeginStation(),item);
                    distance.put(disitem,new Integer(1));
                    path.put(item,routine.getBeginStation());
                }
                else{
                    disitem = new HashMap<>();                              //��δ��������ڣ��򽫳�ֵ����Ϊ10000
                    disitem.put(routine.getBeginStation(),item);
                    distance.put(disitem,new Integer(10000));
                }
            }
            else{
                disitem = new HashMap<>();
                disitem.put(routine.getBeginStation(),item);
                distance.put(disitem,new Integer(0));
            }
        }

        collected.put(routine.getBeginStation(),1);

        while (true){
            V = FindMinDist(routine,distance,collected);    //ȡδ�����ʶ�����distance��С��
            if (V.getStationName().equals("-1"))            //��������V�����ڣ��㷨����
                break;

            collected.put(V,1);

            for (String key:allStation.keySet()){           //����ÿ��վ��
                if (V.getLinkStations().contains(allStation.get(key))&&collected.get(allStation.get(key))==0){
                    if (distance.get(getFromtoFin(routine,V))+1<distance.get(getFromtoFin(routine,allStation.get(key)))){   //����¼�Ķ���ʹdistance��С������и���
                        distance.put(getFromtoFin(routine,allStation.get(key)),distance.get(getFromtoFin(routine,V))+1);
                        path.put(allStation.get(key),V);
                    }
                }
            }
        }
        V = path.get(routine.getEndStation());

        while(!V.equals(routine.getBeginStation())){        //�����·����վ�����ݴ���routine֮��
            routine.getPassStations().add(0,V);
            V = path.get(V);
        }
        routine.getPassStations().add(0,routine.getBeginStation());
        routine.getPassStations().add(routine.getEndStation());


        return routine;         //����
    }



    public static Routine getRoutine ( String begin,String end)  {
        Routine routine = new Routine();
        List<Station> lineStationlist = null;


        Station station = null;
        Station repeatstation = null;

        for(String key:FileManager.subwayLineinfo.keySet()){    //����������·��Ϣ����stationȥ���غ����allStation֮��

            lineStationlist = FileManager.subwayLineinfo.get(key);

            for(int i = 0;i<lineStationlist.size();i++){
                station = lineStationlist.get(i);
                if(allStation.keySet().contains(station.getStationName())){ //�ж��Ƿ��ظ�
                    repeatstation = allStation.get(station.getStationName());

                    if (i==0){          //���Ƹ���Sation������վ��LinkStation��Ϣ
                        repeatstation.getLinkStations().add(lineStationlist.get(i+1));
                    }else if(i==lineStationlist.size()-1){
                        repeatstation.getLinkStations().add(lineStationlist.get(i-1));
                    }else{
                        repeatstation.getLinkStations().add(lineStationlist.get(i+1));
                        repeatstation.getLinkStations().add(lineStationlist.get(i-1));
                    }
                    continue;
                }
                else{

                    if (i==0){
                        station.getLinkStations().add(lineStationlist.get(i+1));
                    }else if(i==lineStationlist.size()-1){
                        station.getLinkStations().add(lineStationlist.get(i-1));
                    }else{
                        station.getLinkStations().add(lineStationlist.get(i+1));
                        station.getLinkStations().add(lineStationlist.get(i-1));
                    }

                    allStation.put(station.getStationName(),station);

                    if(station.getStationName().equals(begin)){     //���ݴ��������ȷ����㣬�����뵽routine֮��
                        routine.setBeginStation(station);
                        continue;
                    }

                    if(station.getStationName().equals(end)){       //���ݴ��������ȷ���յ㣬�����뵽routine֮��
                        routine.setEndStation(station);
                        continue;
                    }
                }
            }
        }

        if (routine.getBeginStation().equals(routine.getEndStation())){ //һЩ�쳣����Ĵ���
            System.out.println("������յ���ͬ������������");
            return null;
        }
        else if (routine.getBeginStation() == null){
            System.out.println("��㲻����");
            return null;
        }
        else if (routine.getEndStation() == null){
            System.out.println("�յ㲻����");
            return null;
        }
        else{
            routine = new DijkstraUtil().Dijkstra_algorithm(routine);
        }

        return routine;
    }
}
