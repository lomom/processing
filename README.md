# A library that helps process pictures.

[![Gitter](https://badges.gitter.im/Kin-Picture-Processing/community.svg)](https://gitter.im/Kin-Picture-Processing/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.lomom/oss-parent.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.lomom%22%20AND%20a:%22oss-parent%22)
[![Build status](https://travis-ci.com/lomom/processing.svg?branch=master)](https://travis-ci.org/twitter/util)
[![codecov](https://codecov.io/gh/lomom/processing/branch/master/graph/badge.svg)](https://codecov.io/gh/lomom/processing)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/35aa47e5d4e14e70a4757c4cfa911c2a)](https://www.codacy.com/app/lomom/processing?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=lomom/processing&amp;utm_campaign=Badge_Grade)
[![License](https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg)](https://dev.tencent.com/u/chenxinzhe/p/Kin-Picture-Processing/git/blob/master/License)

------ 

[toc]

------

## 前言

公司的业务推动中，会有形形色色的问题。或许棘手或许不合理的需求会造成代码开发过程中遇到很多的阻碍。

就是由于公司的业务部门在实际的工作中为了节约人力成本，继而提出了一个需求。需要进行图片的二次填充，即根据模版，在图片中补充内容，最终生成一个对应业务的产品图。

最初我尝试使用java的Graphics2D来进行图片操作，最终结果并不理想。主要因素是无法满足业务部门的需求，图片生成的质量不高而且失真问题严重。市面上第三方的图片处理框架并不多见。所以进度一度陷入困境。

在探索中，我意识到通过前端Canves可以解决质量问题，但是Canves又局限与浏览器再此我就考虑解决方案，采用SVG进行处理。

SVG的好处在于他是通过代码语言描述的，不但可以通过前端进行处理和生成也有第三方的后端开源框架支持。

我找到了Batik。我的想法是采用Batik来处理svg。把图片加载进入svg，通过Batik来进行填充内容，最终将svg转为图片。经过测试，最终处理出来的图片质量得到保证。符合需求。

基于上面的可行性实验，我依据目前需要的功能封装了JAR，以便后续使用。

------


## Quick Start

### Step 1

#### Maven 

```xml
<!-- https://mvnrepository.com/artifact/io.github.lomom/kin-picture-processing -->
<dependency>
    <groupId>io.github.lomom</groupId>
    <artifactId>kin-picture-processing</artifactId>
    <version>1.0.1-Release</version>
</dependency>

```

### Step 2

#### base Attribute (基础属性设置)

| 属性名 | 含义 |
| :--- | :--- |
|x | x坐标|
|y | y坐标|
|translateX | 坐标轴平移|
|xtranslateY | 坐标轴平移|
|yscaleX | 缩放|
|xscaleY | 缩放|
|yrotate | 旋转|
|opacity | 透明度|


#### 1. Create CanvasBuild (创建画板)


| 属性名 | 含义 |
| :--- | :--- |
|cavHeight | 画布高度|
|cavWidth | 画布宽度|
|backendImageFile | 图片文件|
|backendImageUrl | 图片地址|
|backendImage | 图片|
|imagesList | 图片列表|
|textList | 文字列表|
|elementList | 属性列表|


Create SVG Canvas

```java
CanvasBuild.builder().xxx().xxx().xxx().build();
```

#### 2. Add Images (添加一副图片)


| 属性名 | 含义 |
| :--- | :--- | 
|imageFile | 图片文件|
|imagePath | 图片地址|
|bufferedImage | 图片|
|width | 宽|
|height | 高|


##### 1. Http Images (添加网络图片)

```java
canvasBuild.addImagesFile(ProcessingBuild.builder().width(300).height(800).y(300).rotate(45).imagePath("https://pic1.zhimg.com/v2-1dbd32963f19d1c5eba1acc103c1d398_b.jpg").build());
```

##### 2. File Images (添加本地图片)

```java
File file = new File("C:/data/abc.jpg");
canvasBuild.addImagesFile(ProcessingBuild.builder().rotate(19).x(100).imageFile(file1).build());
```

#### 3. Add Text (添加文字)

| 属性名 | 含义 |
| :--- | :--- | 
|context | 文字|
|color | 颜色|
|font | 字体|

```java
canvasBuild.addTextSize(TextFontBuild.builder().context("测试数据").x(310).y(210).rotate(45).color(Color.YELLOW).font(new Font("宋体", Font.BOLD, 144)).opacity(0.2).build());
```

#### 4. Add Elemnt (添加一个属性节点)

TODO

#### 5. Generate picture (生成图片)

```java
Processing.builder().canvasBuild(canvasBuild).build().init(.8, "/data/img/", "test.jpg");
```

### Step 3

#### Demo

```java
@Test
public void contextLoads() throws ParseException {
    CanvasBuild canvasBuild = CanvasBuild.builder()
            .backendImageUrl("https://pic1.zhimg.com/v2-1dbd32963f19d1c5eba1acc103c1d398_b.jpg").build();

    canvasBuild.addImagesFile(ProcessingBuild.builder().width(300).height(800).y(300).rotate(45)
            .imagePath("https://pic1.zhimg.com/v2-1dbd32963f19d1c5eba1acc103c1d398_b.jpg").build());

    canvasBuild.addTextSize(TextFontBuild.builder().context("1111").x(120).y(220).color(Color.yellow).build());

    canvasBuild.addTextSize(TextFontBuild.builder().context("测试数据").x(310).y(210).rotate(45).color(Color.YELLOW)
            .font(new Font("宋体", Font.BOLD, 144)).opacity(0.2).build());

    canvasBuild.addTextSize(TextFontBuild.builder().context("123aaaaaaaaaaaaa").x(310).y(210).color(Color.GREEN)
            .font(new Font("宋体", Font.BOLD, 44)).opacity(0.9).build());

    canvasBuild.addTextSize(TextFontBuild.builder().context("145").rotate(45).x(100).y(120).build());

    canvasBuild.addTextSize(TextFontBuild.builder().context("123123123").x(100).y(220).build());

    canvasBuild.addTextSize(TextFontBuild.builder().context("789").x(100).y(420).build());

    Processing.builder().canvasBuild(canvasBuild).build().init(.8, "/data/img/", "test.jpg");
}
```

------

[![@chenxinzhe](http://chenxinzhe.coding.me/Static-File-Employ/readme.svg)](https://coding.net/u/chenxinzhe)

2019 年 05月 07日    

