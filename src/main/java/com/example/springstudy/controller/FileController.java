package com.example.springstudy.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 该类没有任何用，仅供测试 可以删了
 */
@RestController
public class FileController {
    private static final String UPLOADED_FOLDER = System.getProperty("user.dir")+"/upload/";

    @PostMapping("/upload")
    public String upload(String nickname, MultipartFile f) throws IOException{
        System.out.println("上传"+nickname+",Size:"+f.getSize());
        saveFile(f);
        return "上 传 成 功";
    }

    public void saveFile(MultipartFile f) throws IOException{
        File dir = new File(UPLOADED_FOLDER);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(UPLOADED_FOLDER+f.getOriginalFilename());
        f.transferTo(file);
    }


}
