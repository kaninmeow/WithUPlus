package com.withu.controller;


import com.withu.annotation.IgnoreAuth;
import com.withu.result.Result;
import com.withu.utils.AliOSSUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/oss")
public class OssController {
    @Autowired
    private AliOSSUtils aliOSSUtils;
    @IgnoreAuth
    @ApiOperation("图片上传")
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        System.out.println("执行了");
        //保存到阿里云oss
//        try {
//            if(file.isEmpty()){
//               return Result.error("file is empty");
//            }
//            String url = aliOSSUtils.upload(file);
//            return Result.success("上传成功",url);
//        } catch (Exception e) {
//            e.printStackTrace();
//             return Result.error("file is empty");
//        }
        if(file.isEmpty()){
            return Result.error("file is empty");
        }
        // 上传文件/图像到指定文件夹（这里可以改成你想存放地址的相对路径）
        String path = System.getProperty("user.dir") + "\\upload";
        File savePos = new File(path);
        if(!savePos.exists()){  // 不存在，则创建该文件夹
            savePos.mkdir();
        }
        // 获取存放位置的规范路径
        String realPath = null;
        try {
            realPath = savePos.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 上传该文件/图像至该文件夹下
        file.transferTo(new File(realPath+"/"+System.currentTimeMillis()/1000+file.getOriginalFilename()));
        return Result.success("上传成功","upload/"+System.currentTimeMillis()/1000+file.getOriginalFilename());
    }
}
