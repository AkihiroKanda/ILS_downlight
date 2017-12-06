
public class Sensor {
	private int ID;						//センサID
	private double Target_LX;		//目標照度
	private double Target_K;		//目標色温度
	private double Sensor_X;						//センサのx座標
	private double Sensor_Y;						//センサのy座標

	public Sensor(int ID) {
		this.ID = ID;
	}

	//センサIDを取得するメソッド
	public int get_ID() {
		return ID;
	}

	//目標照度・色温度の設定と取得
	public void set_Target_LX(double LX) {
		if (LX <= InitialValue.MAX_TARGET_LX && LX >= InitialValue.MIN_TARGET_LX) {
			Target_LX = LX;
		} else {
			System.err.println("Setting of the target illuminance is not right.");
			System.err.println("Please set the target illuminance from "
			+InitialValue.MIN_TARGET_LX+" to "+InitialValue.MAX_TARGET_LX+".");
		}
	}
	public double get_Target_LX(){
		return Target_LX;
	}
	public void set_Target_K(double K) {
		if (K <= InitialValue.MAX_TARGET_K && K >= InitialValue.MIN_TARGET_K) {
			Target_K = K;;
		} else {
			System.err.print("Setting of the target color temperature is not right.");
			System.err.println("Please set the target color temperature from "
			+InitialValue.MIN_TARGET_K+" to "+InitialValue.MAX_TARGET_K+".");
		}
	}
	public double  get_Target_K() {
		return Target_K;
	}

	//センサ座標の設定と取得
	public void set_Sensor_X(double X) {
		this.Sensor_X = X;
	}
	public double get_Sensor_X() {
		return Sensor_X;
	}
	public void set_Sensor_Y(double Y) {
		this.Sensor_Y = Y;
	}
	public double get_Sensor_Y() {
		return Sensor_Y;
	}
}
