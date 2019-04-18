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
 * @Description: 文字定位
 * @author 50183
 * @date 2019年3月22日 下午4:47:01
 *
 */
@Getter
@Setter
@ToString(callSuper = true)
public class TextFontBuild extends PositionSizeAbs {

	private String context;

	private Color color;

	private Font font;

	@Builder

	public TextFontBuild(int x, int y, int translateX, int translateY, double scaleX, double scaleY, double rotate,
			Double opacity, String context, Color color, Font font) {
		super(x, y, translateX, translateY, scaleX, scaleY, rotate, opacity);
		this.context = context;

		this.color = Optional.ofNullable(color).orElseGet(() -> Color.BLACK);
		this.font = Optional.ofNullable(font).orElseGet(() -> new Font("宋体", Font.BOLD, 22));
	}

}
