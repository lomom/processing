# Batik-Employ

[![Gitter](https://badges.gitter.im/Kin-Picture-Processing/community.svg)](https://gitter.im/Kin-Picture-Processing/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.lomom/oss-parent.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.lomom%22%20AND%20a:%22oss-parent%22)
[![Build status](https://travis-ci.com/lomom/processing.svg?branch=master)](https://travis-ci.org/twitter/util)
[![codecov](https://codecov.io/gh/lomom/processing/branch/master/graph/badge.svg)](https://codecov.io/gh/lomom/processing)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/35aa47e5d4e14e70a4757c4cfa911c2a)](https://www.codacy.com/app/lomom/processing?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=lomom/processing&amp;utm_campaign=Badge_Grade)
[![License](https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg)](https://dev.tencent.com/u/chenxinzhe/p/Kin-Picture-Processing/git/blob/master/License)

[toc]

------

写在前面:
在公司的业务发展中，会遇到很多很棘手的事情。例如这个项目，就是由于公司的业务部门在实际的工作中为了节约人力成本，继而提出了一个需求。需要进行图片的二次填充，即根据模版，在图片中补充内容，最终生成一个对应业务的产品图。
最初我尝试使用java的Graphics2D来进行图片操作，最终结果并不理想。主要因素是无法满足业务部门的需求，图片生成的质量不高而且失真问题严重。市面上第三方的图片处理框架并不多见。所以进度一度陷入困境。
在一次沟通中，我意识到通过前端Canves可以解决质量问题，但是Canves又局限与浏览器再此我就考虑解决方案，采用SVG进行处理。
SVG的好处在于他是通过代码语言描述的，不但可以通过前端进行处理和生成也有第三方的后端开源框架支持。
我找到了Batik。我的想法是采用Batik来处理svg。把图片加载进入svg，通过Batik来进行填充内容，最终将svg转为图片。经过测试，最终处理出来的图片质量得到保证。符合需求。

------
##1. 添加Batik处理包 

version 采用 1.6-1

```xml
<dependency>
    <groupId>batik</groupId>
    <artifactId>batik-dom</artifactId>
    <version>${batik.version}</version>
</dependency>
<dependency>
    <groupId>batik</groupId>
    <artifactId>batik-svggen</artifactId>
    <version>${batik.version}</version>
</dependency>

<dependency>
    <groupId>batik</groupId>
    <artifactId>batik-transcoder</artifactId>
    <version>${batik.version}</version>
</dependency>
```

##2. 填充svg
    
详见项目 [SvgGenerUtil](https://coding.net/u/chenxinzhe/p/Batik-Employ/git/blob/master/batik-employ/src/main/java/com/util/svg/SvgGenerUtil.java)
    

##3. cookbook

###1. imageIO https 加载

imageIO 功能很方便，支持加载网络图片，但是当遇见https协议的图片时将产生异常，需要通过HttpsURLConnection 来进行获取
    
```java
public InputStream getHttpsFile(String destUrl) {
        try {

            URL url = new URL(destUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);// 设置允许输出
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            return conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

```
    
    
------

[![@chenxinzhe](http://chenxinzhe.coding.me/Static-File-Employ/readme.svg)](https://coding.net/u/chenxinzhe)

2018 年 04月 24日    