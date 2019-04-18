package com.kin.processing.build;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Element;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CanvasBuild extends PositionSizeAbs {

	private int cavHeight;

	private int cavWidth;

	private File backendImageFile;

	private String backendImageUrl;

	private BufferedImage backendImage;

	private List<ProcessingBuild> imagesList;

	private List<TextFontBuild> textList;

	private List<Element> elementList;

	public CanvasBuild addElements(Element e) {
		elementList.add(e);
		return this;
	}

	public CanvasBuild addImagesFile(ProcessingBuild img) {
		imagesList.add(img);
		return this;
	}

	public CanvasBuild addTextSize(TextFontBuild text) {
		textList.add(text);
		return this;
	}

	@Builder
	public CanvasBuild(int x, int y, int translateX, int translateY, double scaleX, double scaleY, double rotate,
			Double opacity, int cavHeight, int cavWidth, File backendImageFile, String backendImageUrl,
			BufferedImage backendImage, List<ProcessingBuild> imagesList, List<TextFontBuild> textList,
			List<Element> elementList) {
		super(x, y, translateX, translateY, scaleX, scaleY, rotate, opacity);
		this.cavHeight = cavHeight;
		this.cavWidth = cavWidth;
		this.backendImageFile = backendImageFile;
		this.backendImageUrl = backendImageUrl;
		this.backendImage = backendImage;
		this.imagesList = Optional.ofNullable(imagesList).orElseGet(() -> new ArrayList<ProcessingBuild>());
		this.textList = Optional.ofNullable(textList).orElseGet(() -> new ArrayList<TextFontBuild>());
		this.elementList = Optional.ofNullable(elementList).orElseGet(() -> new ArrayList<Element>());
	}

}
