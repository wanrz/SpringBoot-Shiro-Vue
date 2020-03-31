package com.heeexy.example.controller;

import com.heeexy.example.common.domain.AjaxResult;
import com.heeexy.example.common.utils.file.FileUploadUtils;
import com.heeexy.example.config.ProjectConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public AjaxResult avatar(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String avatar = FileUploadUtils.upload(ProjectConfig.getProfile(), file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", avatar);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
}
