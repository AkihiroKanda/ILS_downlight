
public class ILS {
	Light[] light = new Light[InitialValue.LIGHT_NUM];
	Sensor[] sensor = new Sensor[InitialValue.SENSOR_NUM];
	private int sensor_num = 0; 			//使用センサ数

	//lightオブジェクトとsensorオブジェの作成
	public ILS() {
		for (int i = 0; i < light.length; i++) {
			light[i] = new Light(i);		//引数：照明ID
		}

		for (int i = 0; i < sensor.length; i++) {
			sensor[i] = new Sensor(i);		//引数：センサID
		}
	}

	//照度センサの追加
	public void add_newSensor(double x, double y, double LX, double K) {
		if (sensor_num < InitialValue.SENSOR_NUM) {
			sensor[sensor_num].set_Target_LX(LX);
			sensor[sensor_num].set_Target_K(K);
			sensor[sensor_num].set_Sensor_X(x);
			sensor[sensor_num].set_Sensor_Y(y);
			sensor_num++;
		}else{
			System.err.println("cannot add a new sensor");
		}
	}

	//目標照度・色温度の変更
	public void change_Target(int ID,double LX, double K) {
		sensor[ID].set_Target_LX(LX);
		sensor[ID].set_Target_K(K);
	}

	//逐点法による影響度係数の計算
	public void Calc_influence_coefficient() {
		Point_method.calc_degree(sensor, light);
	}

	//点灯光度・色度の最適化
	public void Optimization() {
		//最急降下法を使い照度と色温度の最適化を行うメソッド
		SDM.mainSDM_CD(light, sensor);
		SDM.mainSDM_K(light, sensor);
	}
	
	//表示
	public void show() {
		System.out.println("============================");
		for (int i = 0; i < sensor.length; i++) {
			System.out.println("Sensor"+(sensor[i].get_ID()+1));
			System.out.println("Target LX : "+Math.round(sensor[i].get_Target_LX())
					+" lx, Current LX : "+Math.round(sensor[i].get_Current_LX())+" lx");
			System.out.println("Target K : "+Math.round(sensor[i].get_Target_K())
					+" lx, Current K : "+Math.round(sensor[i].get_Current_K())+" lx");
			System.out.println("============================");
		}
	}
}
