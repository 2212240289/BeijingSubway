package cn.edu.zucc.data;

import cn.edu.zucc.model.Routine;
import cn.edu.zucc.model.Station;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileManager {

    public  static String READ_FILE;
    public  static String WRITE_FILE;
    public  static HashMap<String, List<Station>> subwayLineinfo = new HashMap<>();


    public static void readSubway(){    //��ȡsubway.txt�ļ�
        File file = new File(READ_FILE);

        BufferedReader reader = null;

        List<Station> stations;
        Station station;

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");

            reader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = reader.readLine()) != null) {    //�����ļ����ݣ����ֱ��ȡ����·վ����Ϣ

                String[] subwayLine = line.trim().split(" ");

                stations = new ArrayList<>();

                for(int i = 1;i<subwayLine.length;i++){     //������ new Station()�����뵽��Ӧ��·��List��
                    station = new Station(subwayLine[i],subwayLine[0]);

                    stations.add(station);
                }
                subwayLineinfo.put(subwayLine[0],stations);//����Ӧ��·�洢վ���List��Ϊvalue������·����Ϊkey����subwayLineinfo��
            }

        } catch (UnsupportedEncodingException e) {  //�쳣����
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void writeStation(String lineName) {  //д��ָ��������·��վ����Ϣ
        File file = new File(WRITE_FILE);
        BufferedWriter writer = null;
        List<Station> list;

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            writer = new BufferedWriter(outputStreamWriter);

            list = subwayLineinfo.get(lineName);    //��ȡsubwayLineinfoָ����·ȫ����վ��List�����������
            writer.write(lineName+"\n");
            for(int i=0;i<list.size();i++){
                writer.write(list.get(i).getStationName()+"\n");
            }

            writer.close();

        } catch (IOException e) {   //�쳣����
            e.printStackTrace();
        }
    }

    public static void writePassStation(Routine routine){   //д��ָ��վ��֮������·���ϵ�վ����Ϣ
        File file = new File(WRITE_FILE);
        BufferedWriter writer = null;

        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            writer = new BufferedWriter(outputStreamWriter);

            writer.write(routine.getPassStations().size()-1+"\n");  //������·����Ҫ������վ������


            String linename = getLineNmae(routine.getPassStations().get(0),routine.getPassStations().get(1));

            for (int i = 0; i < routine.getPassStations().size();i++)   //д�����·���ϸ���վ�����Ϣ
            {
                writer.write(routine.getPassStations().get(i).getStationName()+"\n");

                if (i<routine.getPassStations().size()-1){
                    if (!linename.equals(getLineNmae(routine.getPassStations().get(i),routine.getPassStations().get(i+1)))){    //�����·�����仯������и��²������˺����·����д��routine.txt
                        linename = getLineNmae(routine.getPassStations().get(i),routine.getPassStations().get(i+1));
                        writer.write("--->���˵���--<"+linename+">--\n");
                    }
                }


            }

            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static String getLineNmae(Station station1,Station station2){        //�ж�����վ���Ƿ���ͬһ����·��
        String res = null;

        List<Station> item;


        for (String key : subwayLineinfo.keySet()){
            item = subwayLineinfo.get(key);
            if (item.contains(station1)&&item.contains(station2)){
                return key;     //����ǣ�������·����,���򣬷���null
            }

        }

        return res;
    }
}
