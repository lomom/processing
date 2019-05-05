package com.kin.processing.build;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @ClassName: PositionSizeAbs
 * @Description: 坐标定位实体
 * @author cxz
 * @date 2019年4月19日 下午6:09:33
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
