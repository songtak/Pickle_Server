package com.pickle.web.commons;
import java.io.File;
import java.util.*;
import java.util.function.BiFunction;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

@Component @Data public class FileHandler{
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);
    public void uploadFile(MultipartFile mfile, String uploadFolder) {
        File uploadPath = makeDir(uploadFolder, getFolder());
        List<String> names= new ArrayList<String>();
        if(uploadPath.exists() == false) {
            uploadPath.mkdirs();
        }
        final String folderName = getFolder();
        String fname = mfile.getOriginalFilename();
        String extension = fname.substring(fname.lastIndexOf(".")+1);
        System.out.println(extension);
        fname = fname.substring(fname.lastIndexOf("\\")+1, fname.lastIndexOf("."));
        System.out.println(fname);
        File savedFile = makeFile(uploadPath, fname+"."+extension);
        names.add(fname+"-"+UUID.randomUUID().toString());
        try {
            mfile.transferTo(savedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFolder() {
        return "pickle";
    }
    public File makeDir(String t, String u) {
        BiFunction<String,String, File> f = File::new;
        return f.apply(t, u);
    }
    public File makeFile(File t, String u) {
        BiFunction<File,String, File> f = File::new;
        return f.apply(t, u);
    }
}