package com.anynote.file.test;

import com.anynote.file.api.model.bo.OSSSignature;
import com.anynote.file.factory.FilePluginFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class TestMinIOFilePlugin {

    @Resource
    private FilePluginFactory filePluginFactory;

    @Test
    public void test() {
        OSSSignature signature = filePluginFactory.filePlugin().getOssSignature(3600, "Java编程思想（第4版） (计算机科学丛书，Java学习必读经典,殿堂级著作！赢得了全球程序员的广泛赞誉！) (Bruce Eckel [Eckel, Bruce]) (Z-Library).epub");
        System.out.println(signature);
    }
}
