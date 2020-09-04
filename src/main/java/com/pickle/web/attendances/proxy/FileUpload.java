package com.pickle.web.attendances.proxy;
import com.pickle.web.attendances.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;


public class FileUpload {


    /*@Service("uploader")
    @Lazy

    public class FileUploader extends Proxy{
        @Autowired
        StoreMapper storeMapper;
        Path path;
        public boolean upload(){
            boolean success = false;

            try{
                success = true;
                BufferedReader reader = new BufferedReader(new FileReader(new File(path.STORES.toString())));
                String storeInfo ="";
                Attendance attendance = new Attendance();

                while ((storeInfo=reader.readLine())!=null){
                    print(storeInfo);
                    String[] arr;
                    arr = storeInfo.split(",\"");
                    attendance.setNum(Integer.parseInt(arr[0].replaceAll("\"","")));
                    attendance.setState(arr[1].replaceAll("\"",""));
                    attendance.setStoreName(arr[2].replaceAll("\"",""));
                    attendance.setCategory(arr[3].replaceAll("\"",""));
                    storeDTO.setLandLot(arr[4].replaceAll("\"",""));
                    storeDTO.setRoadName(arr[5].replaceAll("\"",""));
                    storeDTO.setPhoneNumber(arr[6].replaceAll("\"",""));
                    storeDTO.setPostCode(arr[7].replaceAll("\"",""));
                    storeDTO.setLatitude(arr[8].replaceAll("\"",""));
                    storeDTO.setLongitude(arr[9].replaceAll("\"",""));
                    storeDTO.setRegiDate(new SimpleDateFormat("yyyy-MM-dd").parse(arr[10].replaceAll("\"","")));
                    storeMapper.insertStore(storeDTO);
                }

            }catch(Exception e){
                print("파일리딩에러");
                e.printStackTrace();}

            return success;
        }
    }*/
}
