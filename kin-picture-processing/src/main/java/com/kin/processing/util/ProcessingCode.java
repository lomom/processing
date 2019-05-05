package com.kin.processing.util;

public class ProcessingCode {

	/**
	 * 
	 * @ClassName: SvgCode
	 * @Description:svg属性封装
	 * @author 50183
	 * @date 2019年4月12日 下午2:59:50
	 *
	 */
	public class SvgCode {
		public static final String QualifiedName = "svg";

		public static final String SvgNameSpaceUrl = "http://www.w3.org/1999/xlink";

		/**
		 * 
		 * @ClassName: ElementType
		 * @Description:Svg类型
		 * @author 50183
		 * @date 2019年4月12日 下午3:03:19
		 *
		 */
		public class SvgElementType {
			public static final String Image = "image";
			public static final String Text = "text";
		}

		/**
		 * 
		 * @ClassName: ElementAttribute
		 * @Description: 属性头
		 * @author 50183
		 * @date 2019年4月12日 下午3:02:57
		 *
		 */
		public class SvgElementAttribute {

			public static final String X = "x";
			public static final String Y = "y";
			public static final String XlinkHref = "xlink:href";
			public static final String Width = "width";
			public static final String Height = "height";
			public static final String Style = "style";
			public static final String Transform = "transform";
			
			

		}

		/**
		 * 
		 * @ClassName: FileType
		 * @Description: 文件类型头
		 * @author 50183
		 * @date 2019年4月12日 下午3:03:04
		 *
		 */
		public class SvgFileType {
			public static final String UriTypeHead = "file:///";
		}
	}

	/**
	 * 
	 * @ClassName: CanvasCode
	 * @Description: Canvascode
	 * @author 50183
	 * @date 2019年4月12日 下午2:48:38
	 *
	 */
	public class CanvasCode {
		public static final String CanvasWidth = "width";
		public static final String CanvasHeight = "height";
	}

	/**
	 * 
	 * @ClassName: ExceptionError
	 * @Description:异常
	 * @author 50183
	 * @date 2019年4月12日 下午2:48:07
	 *
	 */
	public class ExceptionError {
		public static final String CanvasBuildNullError = "CanvasBuild is not null!";
	}

}
