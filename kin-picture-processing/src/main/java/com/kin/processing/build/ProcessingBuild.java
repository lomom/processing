package com.kin.processing.build;

import java.awt.image.BufferedImage;
import java.io.File;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @ClassName: ProcessingBuild
 * @Description: 工具Build类
 * @author 50183
 * @date 2019年3月14日 下午2:06:19
 *
 */

@Getter
@Setter
@ToString
public class ProcessingBuild extends PositionSizeAbs {

	private String imagePath;

	private File imageFile;

	private BufferedImage bufferedImage;

	private int width;

	private int height;

	@Builder
	public ProcessingBuild(int x, int y, int translateX, int translateY, double scaleX, double scaleY, double rotate,
			Double opacity, String imagePath, File imageFile, BufferedImage bufferedImage, int width, int height) {
		super(x, y, translateX, translateY, scaleX, scaleY, rotate, opacity);
		this.imagePath = imagePath;
		this.imageFile = imageFile;
		this.bufferedImage = bufferedImage;
		this.width = width;
		this.height = height;
	}

}
