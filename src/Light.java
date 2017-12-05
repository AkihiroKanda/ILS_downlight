
public class Light {
	private int ID;			//照明ID
	private double CD = InitialValue.INITIAL_CD;		//光度
	private double K = InitialValue.INITIAL_K;			//色温度

	public Light(int ID) {
		this.ID = ID;
	}

	//照明IDの取得
	public int get_ID() {
		return ID;
	}

	//光度の設定と取得
	public void set_CD(double CD) {		//設定
		this.CD = CD;
	}
	public double get_CD() {					//取得
		return CD;
	}

	//色温度設定と取得
	public void set_K(double K) {			//設定
		this.K = K;
	}
	public double get_K() {					//取得
		return K;
	}
}
