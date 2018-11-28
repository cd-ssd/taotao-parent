package com.taotao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.util.UploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadController {

    @Autowired
    private ObjectMapper objectMapper;

    @ResponseBody
    @RequestMapping(value="/rest/pic/upload",method= RequestMethod.POST)
    public String upload(@RequestParam(value="uploadFile")MultipartFile file, HttpSession session) throws Exception{

        String subfix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        String path = System.getProperty("user.dir")+"/src/main/resources/";
        String[] uploadinfos = UploadUtil.upload(path+"tracker.conf", file.getBytes(), subfix);
        for (String string : uploadinfos) {
            System.out.println(string);
        }

        Map<String, Object> map = new HashMap<String,Object>();
        map.put("error", 0);
        map.put("url", "http://image.taotao.com/"+uploadinfos[0]+"/"+uploadinfos[1]);
        map.put("height", 100);
        map.put("width", 100);
        String json = objectMapper.writeValueAsString(map);

        return json;
    }
}
