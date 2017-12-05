/***************************************************
 ******************初期値の設定クラス******************
 ***************************************************/

public class InitialValue {
	//使用機器の数
	final public static int LIGHT_NUM = 36;								//照明台数
	final public static int MAX_SENSOR_NUM = 10;					//最大センサ台数

	//照明の設定
	final public static double INITIAL_CD = 300;						//初期点灯光度
	final public static double MAX_CD = 2000;							//最大点灯光度
	final public static double MIN_CD = 20;								//最小点灯光度

	final public static double INITIAL_K = 4000;						//初期点灯色温度
	final public static double MAX_K = 6500;								//最大点灯色温度
	final public static double MIN_K = 2700;								//最小点灯色温度

	//照度センサの設定
	final public static int MAX_TARGET_LX = 900;						//目標照度の上限値
	final public static int MIN_TARGET_LX = 200;						//目標照度の下限値

	final public static int MAX_TARGET_K = 6000;						//目標色温度の上限値
	final public static int MIN_TARGET_K = 3000;						//目標色温度の下限値
}
