package com.otago.lecturerweb.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.otago.lecturerweb.Dao.UploadImage;


@Controller
public class UploadController {

    @RequestMapping(value = "/upload")
    public String userRegistration(HttpServletRequest request) {
        return "uploadImages";
    }

    @RequestMapping(value = "/uploadimage")
    @ResponseBody()
    public String uploadImages(@ModelAttribute("uplodForm") UploadImage uploadForm, HttpServletRequest request) {

        List<MultipartFile> multipartFile = uploadForm.getFiles();
        String path = "G:\\fastticket\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\E-Market\\images\\";
        for (MultipartFile file : multipartFile) {
            try {
                file.transferTo(new File(path + file.getOriginalFilename()));
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

}
