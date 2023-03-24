package com.lantu;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {


    public static void main(String[] args) {
        String url="jdbc:mysql:///xdb";
        String username="root";
        String password="123456";
        String outputDir="C:\\Users\\willi\\IdeaProjects\\x-admin2\\src\\main\\java";
        String moduleName="sys";
        String mapperXml="C:\\Users\\willi\\IdeaProjects\\x-admin2\\src\\main\\resources\\mapper\\"+moduleName;
        String tables="x_user,x_role,x_menu,x_user_role,x_role_menu";
        String prefix="x_";
        FastAutoGenerator.create(url,username,password)
                .globalConfig(builder -> {
                    builder.author("baomidou") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir(outputDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.lantu") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperXml)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .addTablePrefix(prefix); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
