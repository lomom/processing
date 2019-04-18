package com.kin.processing.build;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @ClassName: PositionSizeBuild
 * @Description: 坐标定位
 * @author 50183
 * @date 2019年3月22日 下午4:24:25
 *
 */

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
public class PositionSizeAbs {

	private int x;

	private int y;

	private int translateX;

	private int translateY;

	private double scaleX;

	private double scaleY;

	private double rotate;

	private Double opacity;
}
