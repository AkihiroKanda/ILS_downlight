
public class Sensor {
	private int ID;						//センサID
	private double Target_LX;		//目標照度
	private double Target_K;		//目標色温度
	private int X;						//センサのx座標
	private int Y;						//センサのy座標

	public Sensor(int ID) {
		this.ID = ID;
	}

	//センサIDを取得するメソッド
	public int get_ID() {
		return ID;
	}
	
	//目標照度・色温度の設定と取得
	public void set_Target_LX(double LX) {
		Target_LX = LX;
	}
	public double get_Target_LX(){
		return Target_LX;
	}
	public void set_Target_K(double K) {
		Target_K = K;
	}
	public double  get_Target_K() {
		return Target_K;
	}
	
	//センサ座標の設定と取得
	public void set_X(int X) {
		this.X = X;
	}
	public int get_X() {
		return X;
	}
	public void set_Y(int Y) {
		this.Y = Y;
	}
	public int get_Y() {
		return Y;
	}
}
