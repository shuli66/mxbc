package com.mxbc.Controller;

import cn.hutool.core.io.FileUtil;
import com.mxbc.Config.AuthAccess;
import com.mxbc.Config.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH =  System.getProperty("user.dir") + File.separator + "files";


    @PostMapping("/upload")
    public Map<String, Object> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 文件的原始名称
        // aaa.png
        String mainName = FileUtil.mainName(originalFilename);  // aaa
        String extName = FileUtil.extName(originalFilename);// png
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);  // 如果当前文件的父级目录不存在，就创建
        }
        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {  // 如果当前上传的文件已经存在了，那么这个时候我就要重名一个文件名称
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        return Result.setResultOk(url);  //返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
    }

    @AuthAccess
    @GetMapping("/download/{fileName:.+}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {

//        response.setContentType("application/octet-stream");

        // 对文件名进行 URL 编码，以处理特殊字符和空格问题
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());

        // 设置 Content-Disposition 头，指示浏览器以附件形式下载文件
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");


        
//        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileFullName, "UTF-8"));  // 附件下载

        String filePath = ROOT_PATH  + File.separator + fileName;
        System.out.println(filePath);
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }

}
