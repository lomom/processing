package com.test.processing;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.text.ParseException;

import org.junit.Test;

import com.kin.processing.Processing;
import com.kin.processing.build.CanvasBuild;
import com.kin.processing.build.ProcessingBuild;
import com.kin.processing.build.TextFontBuild;

public class ProcessingTest {

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

}
