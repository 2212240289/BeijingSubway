package cn.edu.zucc;

import cn.edu.zucc.core.DijkstraUtil;
import cn.edu.zucc.data.FileManager;
import cn.edu.zucc.model.Routine;

import java.io.File;

public class Main {

    public static void main(String[] args)  {
	// write your code here

        switch (args[0]){
            //����subway.txt�ļ�����
            case "-map":
                //-map subway.txt
                if(args.length==2){
                    FileManager.READ_FILE = System.getProperty("user.dir") + File.separator + args[1];
                    //����·������ȡ������Ϣ������ӡ��
                    FileManager.readSubway();
                    System.out.println("�ɹ���ȡsubway.txt�ļ�");
                }else{
                    System.out.println("��֤������ʽ��");
                }
                break;
             //��ѯָ����·���ļ����ݣ���д�뵽station.txt�ļ���
            case "-a":
                //-a 1���� -map subway.txt -o station.txt
                if(args.length==6){
                    FileManager.READ_FILE = System.getProperty("user.dir") + File.separator  + args[3];
                    FileManager.WRITE_FILE = System.getProperty("user.dir") + File.separator  + args[5];

                    FileManager.readSubway();
                    if (FileManager.subwayLineinfo.keySet().contains(args[1])){
                        FileManager.writeStation(args[1]);
                        System.out.println("�ѽ�����"+args[1]+"�ĸ�վ����Ϣд��station.txt�ļ�");
                    }else{
                        System.out.println("��·������");
                    }
                }else{

                    System.out.println("��֤������ʽ��");
                }
                break;
             //��ѯָ��վ��֮������·����Ϣ
            case "-b":
                //-b �찲���� ������ѧ���� -map subway.txt -o routine.txt

                if(args.length==7){
                    FileManager.READ_FILE = System.getProperty("user.dir") + File.separator + args[4];
                    FileManager.WRITE_FILE = System.getProperty("user.dir") + File.separator + args[6];
                    FileManager.readSubway();
                    Routine routine = DijkstraUtil.getRoutine(args[1],args[2]);
                    if (routine!=null){
                        FileManager.writePassStation(routine);
                        System.out.println("�ѽ�"+args[1]+"��"+args[2]+"���·���Ľ��д��routine. txt�ļ�");
                    }
                }else{
                    System.out.println("��֤������ʽ��");
                }
                break;
        }
    }
}
