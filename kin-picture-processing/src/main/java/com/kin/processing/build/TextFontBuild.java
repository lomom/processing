package com.kin.processing.build;

import java.awt.Color;
import java.awt.Font;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @ClassName: TextFontBuild
 * @Description: 文字属性实体
 * @author cxz
 * @date 2019年4月19日 下午6:10:56
 *
 */
@Getter
@Setter
@ToString(callSuper = true)
public class TextFontBuild extends PositionSizeAbs {

	private String context;

	private Color color;

	private Font font;

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
	 * @param context
	 *        文字
	 * @param color
	 *        颜色
	 * @param font
	 *        字体
	 */
	@Builder
	public TextFontBuild(int x, int y, int translateX, int translateY, double scaleX, double scaleY, double rotate,
			Double opacity, String context, Color color, Font font) {
		super(x, y, translateX, translateY, scaleX, scaleY, rotate, opacity);
		this.context = context;

		this.color = Optional.ofNullable(color).orElseGet(() -> Color.BLACK);
		this.font = Optional.ofNullable(font).orElseGet(() -> new Font("宋体", Font.BOLD, 22));
	}

}
