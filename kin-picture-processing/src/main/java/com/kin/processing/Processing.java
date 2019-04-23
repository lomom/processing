package com.kin.processing;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.springframework.util.StringUtils;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import com.kin.processing.build.CanvasBuild;
import com.kin.processing.build.PositionSizeAbs;
import com.kin.processing.build.ProcessingBuild;
import com.kin.processing.build.TextFontBuild;
import com.kin.processing.util.GetHttpImages;
import com.kin.processing.util.ProcessingCode.CanvasCode;
import com.kin.processing.util.ProcessingCode.ExceptionError;
import com.kin.processing.util.ProcessingCode.SvgCode;
import com.kin.processing.util.ProcessingCode.SvgCode.SvgElementAttribute;
import com.kin.processing.util.ProcessingCode.SvgCode.SvgElementType;
import com.kin.processing.util.ProcessingCode.SvgCode.SvgFileType;
import com.kin.processing.util.VerificationAndResetG2d;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName: Processing
 * @Description: 方法主体
 * @author cxz
 * @date 2019年4月19日 下午5:56:08
 *
 */
@Getter
@Setter
@Builder
public class Processing {

	private static final String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

	private Element svgRoot;

	private SVGDocument doc;

	private SVGGraphics2D g2d;

	private CanvasBuild canvasBuild;

	/**
	 * 
	 * @Title: init
	 * @Description: 初始化方法
	 * @param quality
	 *        透明度设置
	 * @param filePath
	 *        文件路径
	 */
	public void init(double quality, String filePath) {
		// 初始化g2d工具以及原石document文档流操作
		DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();
		this.doc = (SVGDocument) domImpl.createDocument(svgNS, SvgCode.QualifiedName, null);
		this.g2d = new SVGGraphics2D(doc);
		this.svgRoot = doc.getDocumentElement();

		// 执行svg画布写入
		execution();

		// 图片生成流程
		JPEGTranscoder t = new JPEGTranscoder();

		t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(quality));

		TranscoderInput input = new TranscoderInput(doc);

		try (OutputStream ostream = new FileOutputStream(filePath)) {

			TranscoderOutput output = new TranscoderOutput(ostream);

			t.transcode(input, output);

			ostream.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: execution
	 * @Description: 绘图总控
	 */
	public void execution() {
		drawCanvasSize();
		for (ProcessingBuild imagesItem : canvasBuild.getImagesList()) {
			drawCanvasImages(imagesItem);
		}

		for (TextFontBuild textItem : canvasBuild.getTextList()) {
			drawText(textItem);
		}
	}

	/**
	 * 
	 * @Title: drawCanvasSize
	 * @Description: 画布布局写入
	 */
	private void drawCanvasSize() {

		// 验证canvasbuild是否为空
		Optional.ofNullable(this.getCanvasBuild())
				.orElseThrow(() -> new NullPointerException(ExceptionError.CanvasBuildNullError));

		Integer canvasWidth = 0, canvasHeight = 0;

		getImagesByFileOrUrl(canvasBuild);

		Boolean hasBackendImg = Optional.ofNullable(canvasBuild.getBackendImage()).isPresent();

		// 验证是否设置了背景图片，如果设置了，新添加背景图片为画布宽高
		if (hasBackendImg) {
			canvasWidth = canvasBuild.getBackendImage().getWidth();
			canvasHeight = canvasBuild.getBackendImage().getHeight();
		}
		// 验证是否手动设置了画布宽高，如果设置了，则覆盖背景图片的宽高
		if (canvasBuild.getCavWidth() != 0) {
			canvasWidth = canvasBuild.getCavWidth();
		}
		if (canvasBuild.getCavHeight() != 0) {
			canvasHeight = canvasBuild.getCavHeight();
		}
		this.g2d.setSVGCanvasSize(new Dimension(canvasWidth.intValue(), canvasHeight.intValue()));
		this.svgRoot.setAttributeNS(null, CanvasCode.CanvasWidth, canvasWidth.toString());
		this.svgRoot.setAttributeNS(null, CanvasCode.CanvasHeight, canvasHeight.toString());

		// 验证是否设置了背景图片，有则直接画出图片
		if (hasBackendImg) {
			verification(canvasBuild);
			this.g2d.drawImage(canvasBuild.getBackendImage(), canvasBuild.getX(), canvasBuild.getY(), canvasWidth,
					canvasHeight, null);
		}

	}

	/**
	 * 
	 * @Title: drawCanvasImages
	 * @Description: 绘制背景
	 * @param 背景Build实体
	 */
	private void drawCanvasImages(ProcessingBuild imagesItem) {

		verification(imagesItem);

		// 如果不设置透明度，则使用画板模式
		if (!Optional.ofNullable(imagesItem.getOpacity()).isPresent()) {

			BufferedImage read = buildImageFileOrUrl(imagesItem);

			Optional.ofNullable(read).ifPresent(readItem -> {
				this.g2d.drawImage(readItem, imagesItem.getX(), imagesItem.getY(),
						imagesItem.getWidth() == 0 ? readItem.getWidth() : imagesItem.getWidth(),
						imagesItem.getHeight() == 0 ? readItem.getHeight() : imagesItem.getHeight(), null);
			});

		} else {

			// 使用透明度，则使用节点模式
			Element topLevelGroup = this.g2d.getTopLevelGroup();
			svgRoot.appendChild(topLevelGroup);

			Element e = doc.createElementNS(svgNS, SvgElementType.Image);
			e.setAttributeNS(null, SvgElementAttribute.X, imagesItem.getX() + "");
			e.setAttributeNS(null, SvgElementAttribute.Y, imagesItem.getY() + "");

			if (!StringUtils.isEmpty(imagesItem.getImagePath())) {
				e.setAttributeNS(SvgCode.SvgNameSpaceUrl, SvgElementAttribute.XlinkHref, imagesItem.getImagePath());
				e.setAttributeNS(null, SvgElementAttribute.Width, imagesItem.getWidth() + "");
				e.setAttributeNS(null, SvgElementAttribute.Height, imagesItem.getHeight() + "");
			} else {

				BufferedImage read = null;
				try {
					read = ImageIO.read(imagesItem.getImageFile());
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				File imageFile = imagesItem.getImageFile();
				e.setAttributeNS(SvgCode.SvgNameSpaceUrl, SvgElementAttribute.XlinkHref,
						SvgFileType.UriTypeHead + imageFile.getAbsolutePath());

				Optional.ofNullable(read).ifPresent(readItem -> {

					e.setAttributeNS(null, SvgElementAttribute.Width,
							imagesItem.getWidth() == 0 ? readItem.getWidth() + "" : imagesItem.getWidth() + "");
					e.setAttributeNS(null, SvgElementAttribute.Height,
							imagesItem.getHeight() == 0 ? readItem.getHeight() + "" : imagesItem.getHeight() + "");

				});

			}

			String format = String.format("opacity:%s; ", imagesItem.getOpacity());
			System.out.println(format);
			e.setAttributeNS(null, SvgElementAttribute.Style, format);
			e.setAttributeNS(null, SvgElementAttribute.Transform, verTransform(imagesItem));
			this.svgRoot.appendChild(e);
		}
		resetG2d(imagesItem);

	}

	/**
	 * 
	 * @Title: drawText
	 * @Description: 绘制文字
	 * @param textItem
	 *        文字Build实体
	 */
	private void drawText(TextFontBuild textItem) {

		verification(textItem);

		if (!Optional.ofNullable(textItem.getOpacity()).isPresent()) {

			this.g2d.setFont(textItem.getFont());
			this.g2d.setColor(textItem.getColor());
			this.g2d.drawString(textItem.getContext(), textItem.getX(), textItem.getY());

		} else {
			Element topLevelGroup = this.g2d.getTopLevelGroup();
			svgRoot.appendChild(topLevelGroup);

			Element e = doc.createElementNS(svgNS, SvgElementType.Text);
			e.setAttributeNS(null, SvgElementAttribute.X, textItem.getX() + "");
			e.setAttributeNS(null, SvgElementAttribute.Y, textItem.getY() + "");

			String format = String.format("font-size: %spx; font-family: '%s'; fill: rgb(%s,%s,%s); opacity:%s; ",
					textItem.getFont().getSize(), textItem.getFont().getFamily(), textItem.getColor().getRed(),
					textItem.getColor().getGreen(), textItem.getColor().getBlue(), textItem.getOpacity());

			e.setAttributeNS(null, SvgElementAttribute.Style, format);

			e.setAttributeNS(null, SvgElementAttribute.Transform, verTransform(textItem));
			e.setTextContent(textItem.getContext());
			this.svgRoot.appendChild(e);
		}
		resetG2d(textItem);

	}

	/**
	 * 
	 * @Title: verification
	 * @Description: 设置特效
	 * @param build
	 *        特效Build实体
	 */
	private void verification(PositionSizeAbs build) {
		build = VerificationAndResetG2d.verification(build);
		this.g2d.translate(build.getTranslateX(), build.getTranslateY());
		this.g2d.scale(build.getScaleX(), build.getScaleY());
		this.g2d.rotate(build.getRotate(), build.getX(), build.getY());
	}

	/**
	 * 
	 * @Title: verTransform
	 * @Description: 设置transform属性
	 * @param 坐标定位
	 * @return String 生成坐标字符串
	 * @throws
	 */
	private String verTransform(PositionSizeAbs posinItem) {
		String transform = String.format("translate(%s,%s) scale(%s,%s) rotate(%s)", posinItem.getTranslateX(),
				posinItem.getTranslateY(), posinItem.getScaleX(), posinItem.getScaleY(), posinItem.getRotate());
		return transform;
	}

	/**
	 * 
	 * @Title: resetG2d
	 * @Description: 重置画笔
	 * @param build 坐标build文件
	 * @throws
	 */
	private void resetG2d(PositionSizeAbs build) {
		Element topLevelGroup = this.g2d.getTopLevelGroup();
		this.svgRoot.appendChild(topLevelGroup);
		this.g2d = new SVGGraphics2D(doc);
	}

	/**
	 * 
	 * @Title: buildImageFileOrUrl
	 * @Description: 解析图片
	 * @param buildImage
	 *        图片Build实体
	 * @return 图片Build实体
	 */
	public BufferedImage buildImageFileOrUrl(ProcessingBuild buildImage) {

		if (Optional.ofNullable(buildImage.getBufferedImage()).isPresent()) {
			return buildImage.getBufferedImage();
		}

		if (!StringUtils.isEmpty(buildImage.getImagePath())) {
			InputStream httpsFile = GetHttpImages.getHttpsFile(buildImage.getImagePath());
			try {
				buildImage.setBufferedImage(ImageIO.read(httpsFile));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		Optional.ofNullable(buildImage.getImageFile()).ifPresent(e -> {
			try {
				buildImage.setBufferedImage(ImageIO.read(e));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		return buildImage.getBufferedImage();
	}

	/**
	 * 
	 * @Title: getImagesByFileOrUrl
	 * @Description: 根据参数获取图片Buffered
	 * @param canvasBuild 图片Buffered
	 * @return 图片Buffered
	 * @throws
	 */
	private BufferedImage getImagesByFileOrUrl(CanvasBuild canvasBuild) {

		if (Optional.ofNullable(canvasBuild.getBackendImage()).isPresent()) {
			return canvasBuild.getBackendImage();
		}

		if (!StringUtils.isEmpty(canvasBuild.getBackendImageUrl())) {
			InputStream httpsFile = GetHttpImages.getHttpsFile(canvasBuild.getBackendImageUrl());
			try {
				canvasBuild.setBackendImage(ImageIO.read(httpsFile));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		Optional.ofNullable(canvasBuild.getBackendImageFile()).ifPresent(e -> {
			try {
				canvasBuild.setBackendImage(ImageIO.read(e));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		return canvasBuild.getBackendImage();
	}

	// public static void main(String[] args) throws Exception {
	//
	// File file = new File("C:/data/abc.jpg");
	// File file1 = new File("C:/data/1.jpg");
	//
	// CanvasBuild canvasBuild =
	// CanvasBuild.builder().backendImageFile(file).build();
	//
	// canvasBuild.addImagesFile(ProcessingBuild.builder().rotate(19).x(100).imageFile(file1).build());
	//
	// canvasBuild.addImagesFile(ProcessingBuild.builder().width(300).height(800).y(300).rotate(45)
	// .imagePath("https://pic1.zhimg.com/v2-1dbd32963f19d1c5eba1acc103c1d398_b.jpg").build());
	//
	// canvasBuild.addTextSize(TextFontBuild.builder().context("1111").x(120).y(220).color(Color.yellow).build());
	//
	// canvasBuild.addTextSize(TextFontBuild.builder().context("测试数据").x(310).y(210).rotate(45).color(Color.YELLOW)
	// .font(new Font("宋体", Font.BOLD, 144)).opacity(0.2).build());
	//
	// canvasBuild.addTextSize(TextFontBuild.builder().context("123aaaaaaaaaaaaa").x(310).y(210).color(Color.GREEN)
	// .font(new Font("宋体", Font.BOLD, 44)).opacity(0.9).build());
	//
	// canvasBuild.addTextSize(TextFontBuild.builder().context("145").rotate(45).x(100).y(120).build());
	//
	// canvasBuild.addTextSize(TextFontBuild.builder().context("123123123").x(100).y(220).build());
	//
	// canvasBuild.addTextSize(TextFontBuild.builder().context("789").x(100).y(420).build());
	//
	// Processing.builder().canvasBuild(canvasBuild).build().init(.8,
	// "C:\\data\\img\\out1.jpg");
	// }
}
