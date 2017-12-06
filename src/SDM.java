
public class SDM {
	private static int step = 0;	//現在のステップ数
	private static double stepsize = 0.0;		//α：ステップ幅
	private static double[] Ans_CD = new double[InitialValue.LIGHT_NUM] ;
	private static double[] nextCD = new double[InitialValue.SENSOR_NUM];
	private static double[] Gradient_vector = new double[InitialValue.LIGHT_NUM];		//勾配ベクトル
	private static double[] Drop_direction = new double[InitialValue.LIGHT_NUM];		//降下方向
	
	
	//照度の最適化
	public static void mainSDM_CD(Light light[], Sensor sensor[]) {
		Initialization();			//初期化
		while (true) {
			step++;
			Calc_gradient_vector(light, sensor);		//勾配ベクトルの計算
			Calc_drop_direction();							//降下方向の計算
			stepsize = Calc_stepsize(light, sensor);	//ステップ幅αの計算
			Ans_CD = Check_max_min(Calc_next_CD(stepsize));
			if (step == InitialValue.MAX_STEP) {
				break;
			}
		}
	}
	
	//色温度の最適化
	public static void mainSDM_K(Light light[], Sensor sensor[]) {

	}
	
	//初期化
	private static void Initialization(){
		step = 0;
		stepsize = 0.0;
	}
	
	//勾配ベクトルの計算
	private static void Calc_gradient_vector(Light light[], Sensor sensor[]){
		
	}
	
	//降下方向の計算
	private static void Calc_drop_direction(){
		
	}
	
	//ステップ幅αの計算
	private static double Calc_stepsize(Light light[], Sensor sensor[]){
		return 0;
	}
	
	//次光度の計算
	private static double[] Calc_next_CD(double stepsize){
		return nextCD;
	}
	
	//光度値の最小値最大値のチェック
	private static double[] Check_max_min(double nextCD[]){
		return nextCD;
	}
	
}
