
public class ILS {
	Light[] light = new Light[InitialValue.LIGHT_NUM];
	Sensor[] sensor = new Sensor[InitialValue.MAX_SENSOR_NUM];

	//lightオブジェクトとsensorオブジェの作成
	public ILS() {
		for (int i = 0; i < light.length; i++) {
			light[i] = new Light(i);		//引数：照明ID
			System.out.println("Create Light"+i);
		}

		for (int i = 0; i < sensor.length; i++) {
			sensor[i] = new Sensor(i);		//引数：センサID
		}
	}
	
	//照度センサの追加
	public void add_newSensor(int x, int y, double LX, double K) {
		
	}
	
	//目標照度・色温度の変更
	public void change_Target(int ID,double LX, double K) {
		sensor[ID].set_Target_LX(LX);
		sensor[ID].set_Target_K(K);
	}

	//点灯光度・色度の最適化
	public void Optimization() {
		//最急降下法を使い照度と色温度の最適化を行うメソッド
		SDM.calc_CD(light, sensor);
		SDM.calc_K(light, sensor);
	}
}
