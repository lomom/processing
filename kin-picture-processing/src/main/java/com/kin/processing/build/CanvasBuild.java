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

/**
 * 
 * @ClassName: CanvasBuild
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cxz
 * @date 2019年4月19日 下午6:04:59
 *
 */
@Getter
@Setter
@ToString(callSuper = true)
public class CanvasBuild extends PositionSizeAbs {

	private int cavHeight;

	private int cavWidth;

	private File backendImageFile;

	private String backendImageUrl;

	private BufferedImage backendImage;

	private List<ProcessingBuild> imagesList;

	private List<TextFontBuild> textList;

	private List<Element> elementList;

	/**
	 * 
	 * @Title: addElements
	 * @Description: 添加一个属性节点
	 * @param e
	 *        添加属性节点
	 * @return 画板
	 */
	public CanvasBuild addElements(Element e) {
		elementList.add(e);
		return this;
	}

	/**
	 * 
	 * @Title: addImagesFile
	 * @Description: 添加图片文件
	 * @param img
	 *        添加图片文件
	 * @return 画板
	 */
	public CanvasBuild addImagesFile(ProcessingBuild img) {
		imagesList.add(img);
		return this;
	}

	/**
	 * 
	 * @Title: addTextSize
	 * @Description: 添加文字
	 * @param text
	 *        添加文字
	 * @return 画板
	 */
	public CanvasBuild addTextSize(TextFontBuild text) {
		textList.add(text);
		return this;
	}

	/**
	 * 
	 * Title:
	 * Description:
	 * 
	 * @param x
	 *        x坐标
	 * @param y
	 *        y坐标
	 * @param translateX
	 *        坐标轴平移x
	 * @param translateY
	 *        坐标轴平移y
	 * @param scaleX
	 *        缩放x
	 * @param scaleY
	 *        缩放y
	 * @param rotate
	 *        旋转
	 * @param opacity
	 *        透明度
	 * @param cavHeight
	 *        画布高度
	 * @param cavWidth
	 *        画布宽度
	 * @param backendImageFile
	 *        图片文件
	 * @param backendImageUrl
	 *        图片地址
	 * @param backendImage
	 *        图片
	 * @param imagesList
	 *        图片列表
	 * @param textList
	 *        文字列表
	 * @param elementList
	 *        属性列表
	 */
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
