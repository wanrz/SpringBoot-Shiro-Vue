package com.heeexy.example.controller;

import com.heeexy.example.common.domain.AjaxResult;
import com.heeexy.example.common.utils.file.FileUploadUtils;
import com.heeexy.example.common.utils.file.FileUtils;
import com.heeexy.example.config.ProjectConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import oshi.util.FileUtil;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    private static Logger logger = LoggerFactory.getLogger(FileController.class);
    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public AjaxResult avatar(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String imgUrl = FileUploadUtils.upload(ProjectConfig.getProfile(), file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", imgUrl);
            String imgBase64=FileUtils.encodeFileToBase64(ProjectConfig.getProfile()+ File.separator+imgUrl);
            logger.info(imgBase64);
            ajax.put("imgBase64",imgBase64);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
}
