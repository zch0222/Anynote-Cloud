package com.anynote.file.test;

import com.anynote.file.api.model.vo.OssSliceUploadSignatureVO;
import com.anynote.file.api.model.vo.OssSliceUploadTaskVO;
import com.anynote.file.service.FileService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class FileServiceTest {


    @Resource
    private FileService fileService;


    @Test
    void testCreateOssSliceUploadTask() {
        OssSliceUploadTaskVO vo = fileService.createOssSliceUploadTask("testSliceUpload",
                "Java编程思想（第4版） (计算机科学丛书，Java学习必读经典,殿堂级著作！赢得了全球程序员的广泛赞誉！) (Bruce Eckel [Eckel, Bruce]) (Z-Library)_副本.epub",
                "e43a9988f6dc06080f7dbe3dd3f7ad4aab65c80101911adc22bf8979ceb702466a5f9a1f93a7838d74d2b67e7aa7c9f2f33fb5439e42bdb67ddc90d8906f8b74",
                50.1, "1", 1);
        System.out.println(new Gson().toJson(vo));
    }

    @Test
    void testGetOssSliceUploadSignature() {
        Set<Integer> list = new HashSet<>();
        list.add(1);
        list.add(2);
        list.add(3);
        OssSliceUploadSignatureVO ossSliceUploadSignatureVO = fileService
                .getOssSliceUploadSignature("ab9a8d0982f34704806635100933e3fd", list);
        System.out.println(new Gson().toJson(ossSliceUploadSignatureVO));
    }
}
