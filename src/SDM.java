
public class SDM {
	private static int step = 0;	//現在のステップ数
	private static double stepsize = 0.0;		//α：ステップ幅
	private static String Design_variable ="";	//設計変数（光度orミレッド）
	private static double[] Ans_CD = new double[InitialValue.LIGHT_NUM] ;
	private static double[] Ans_M = new double[InitialValue.LIGHT_NUM];
	private static double[] Gradient_vector = new double[InitialValue.LIGHT_NUM];		//勾配ベクトル
	private static double[] Drop_direction = new double[InitialValue.LIGHT_NUM];		//降下方向


	//照度の最適化
	public static void mainSDM_CD(Light light[], Sensor sensor[]) {
		Initialization();			//初期化
		Design_variable = "CD";
		Calc_current_LX(light, sensor);		//初期光度における照度値計算
		while (true) {
			step++;
			Calc_gradient_vector(light, sensor);		//勾配ベクトルの計算
			Calc_drop_direction();							//降下方向の計算
			stepsize = Calc_stepsize(light, sensor);	//ステップ幅αの計算
			Ans_CD = Check_max_min(Calc_next_value(stepsize, light));
			for (int i = 0; i < light.length; i++) {
				light[i].set_CD(Ans_CD[i]);
				//System.out.println("CD:"+Ans_CD[i]);
			}
			Calc_current_LX(light, sensor);		//求めた光度値での各照度センサの現在照度の計算
			if (step == InitialValue.MAX_STEP) {
				break;
			}
		}
	}

	//色温度の最適化
	public static void mainSDM_K(Light light[], Sensor sensor[]) {
		Initialization();			//初期化
		Design_variable = "M";
		Calc_current_M(light, sensor);
		while (true) {
			step++;
			Calc_gradient_vector(light, sensor);		//勾配ベクトルの計算
			Calc_drop_direction();							//降下方向の計算
			stepsize = Calc_stepsize(light, sensor);	//ステップ幅αの計算
			Ans_M =  Check_max_min(Calc_next_value(stepsize, light));
			for (int i = 0; i < light.length; i++) {
				light[i].set_M(Ans_M[i]);
				//System.out.println("CD:"+Ans_CD[i]);
			}
			Calc_current_M(light, sensor);
			if (step == InitialValue.MAX_STEP) {
				break;
			}
		}
	}

	//初期化
	private static void Initialization(){
		step = 0;
		stepsize = 0.0;
	}

	//照度センサの現在照度の計算
	public static void Calc_current_LX(Light light[], Sensor sensor[]) {
		for(int sensor_num=0;sensor_num<InitialValue.SENSOR_NUM;sensor_num++){
			for(int light_num=0;light_num<InitialValue.LIGHT_NUM;light_num++){
				sensor[sensor_num].set_Each_Current_LX(light_num,
						light[light_num].get_Influence_deg(sensor_num) * light[light_num].get_CD());
			}
		}
	}

	//照度センサのミレッドの計算
	public static void Calc_current_M(Light light[], Sensor sensor[]) {
		for(int sensor_num=0;sensor_num<InitialValue.SENSOR_NUM;sensor_num++){
			double sum_M = 0;
			for(int light_num=0;light_num<InitialValue.LIGHT_NUM;light_num++){
				sum_M += (sensor[sensor_num].get_Each_Current_LX(light_num) /
						sensor[sensor_num].get_Current_LX())*light[light_num].get_M();
			}
			sensor[sensor_num].set_Current_M(sum_M);
		}
	}

	//勾配ベクトルの計算
	private static void Calc_gradient_vector(Light light[], Sensor sensor[]){
		double[] RRL = new double[InitialValue.LIGHT_NUM];		//偏微分値を格納する配列
		//照度に関数る制約条件項の光度による偏微分値
		for(int light_num=0;light_num<InitialValue.LIGHT_NUM;light_num++){
			for(int sensor_num=0;sensor_num<InitialValue.SENSOR_NUM;sensor_num++){
				if (Design_variable == "CD") {
					RRL[light_num] += light[light_num].get_Influence_deg(sensor_num)
							* (sensor[sensor_num].get_Current_LX() - sensor[sensor_num].get_Target_LX());

				}
				else if (Design_variable == "M"){
					RRL[light_num] +=
							(sensor[sensor_num].get_Each_Current_LX(light_num) / sensor[sensor_num].get_Current_LX()) *
							( sensor[sensor_num].get_Current_M() - sensor[sensor_num].get_Target_M());
				}
			}
		}
		//目的関数の各照明の光度に関数偏導関数
		for(int light_num=0;light_num<InitialValue.LIGHT_NUM;light_num++){
			if (Design_variable == "CD") {
				Gradient_vector[light_num] = InitialValue.ALPHA + 2 * InitialValue.WEIGHT * RRL[light_num];
			}
			else if (Design_variable == "M") {
				Gradient_vector[light_num] = 2*RRL[light_num];
			}
		}
	}

	//降下方向の計算
	private static void Calc_drop_direction(){
		for(int light_num=0;light_num<InitialValue.LIGHT_NUM;light_num++){
			Drop_direction[light_num] = -1 * Gradient_vector[light_num];
		}
	}

	//ステップ幅αの計算
	private static double Calc_stepsize(Light light[], Sensor sensor[]){
		double h = InitialValue.INITIAL_STEP_SIZE;
		double TAU = InitialValue.TAU;
		double x1 = InitialValue.INITIAL_X1;
		double x2 = x1 + h;
		double x3 = x1 + ((TAU-1)/TAU) * (x2 - x1);
		double x4 = x1 + (1/TAU) * (x2 - x1);
		double func1 = 0.0;
		double func2 = 0.0;

		while((x2-x1)>InitialValue.EPS){
			if (Design_variable == "CD") {
				func1 = Calc_function_CD(x3, light, sensor);
				func2 = Calc_function_CD(x4, light, sensor);
			}
			else if (Design_variable == "M") {
				func1 = Calc_function_M(x3, light, sensor);
				func2 = Calc_function_M(x4, light, sensor);
			}
			//System.out.println("Function1:"+func1+",Function2:"+func2);
			if(func1>func2){
				x1 = x3;
				//x2 = x2;
				x3 = x1 + ((TAU-1)/TAU) * (x2 - x1);
				x4 = x1 + (1/TAU) * (x2 - x1);
			}
			else{
				//x1 = x1;
				x2 = x4;
				x3 = x1 + ((TAU-1)/TAU) * (x2 - x1);
				x4 = x1 + (1/TAU) * (x2 - x1);
			}
		}
		//System.out.println(x3);
		return x3;
	}

	//目的関数の計算(照度についての)
	private static double Calc_function_CD(double stepsize,Light light[],Sensor sensor[]){
		double temp_CD[];
		temp_CD =  Check_max_min(Calc_next_value(stepsize, light));
		double[] now_ill = new double[InitialValue.SENSOR_NUM];
		double func = 0.0;
		int power = 0;
		double penalty = 0.0;
		for(int i=0;i<InitialValue.LIGHT_NUM;i++){
			power += temp_CD[i];
		}
		for(int j=0;j<InitialValue.SENSOR_NUM;j++){
			for(int i=0;i<InitialValue.LIGHT_NUM;i++){
				now_ill[j] += temp_CD[i]*light[i].get_Influence_deg(j);
			}
		}
		for(int j=0;j<InitialValue.SENSOR_NUM;j++){
			penalty += Math.pow(now_ill[j]-sensor[j].get_Target_LX(),2);
		}
		func = power + InitialValue.WEIGHT * penalty;
		
		return func;
	}
	
	//目的関数の計算(ミレッドについての)
	private static double Calc_function_M(double stepsize,Light light[],Sensor sensor[]){
		double temp_M[];
		temp_M =  Check_max_min(Calc_next_value(stepsize, light));
		double[] now_M = new double[InitialValue.SENSOR_NUM];
		double func = 0.0;
		double penalty = 0.0;
		for(int sensor_num=0;sensor_num<InitialValue.SENSOR_NUM;sensor_num++){
			for(int light_num=0;light_num<InitialValue.LIGHT_NUM;light_num++){
				now_M[sensor_num] += (sensor[sensor_num].get_Each_Current_LX(light_num) /
						sensor[sensor_num].get_Current_LX())*temp_M[light_num];
			}
		}
		for(int sensor_num=0;sensor_num<InitialValue.SENSOR_NUM;sensor_num++){
			penalty += Math.pow(now_M[sensor_num]-sensor[sensor_num].get_Target_M(),2);
		}
		func = penalty;
		
		return func;
	}

	//次光度・次ミレッドの計算
	private static double[] Calc_next_value(double stepsize, Light light[]){
		double Next_value[] = new double[InitialValue.LIGHT_NUM];
		for(int i=0;i<InitialValue.LIGHT_NUM;i++){
			if (Design_variable == "CD")Next_value[i] = light[i].get_CD() + stepsize * Drop_direction[i];
			else if (Design_variable == "M")Next_value[i] = light[i].get_M() + stepsize * Drop_direction[i];
		}
		return Next_value;
	}

	//光度値の最小値最大値のチェック
	private static double[] Check_max_min(double nextValue[]){
		for(int i=0;i<InitialValue.LIGHT_NUM;i++){
			if (Design_variable == "CD"){
				if (nextValue[i] > InitialValue.MAX_CD) nextValue[i] = InitialValue.MAX_CD;
				else if (nextValue[i] < InitialValue.MIN_CD) nextValue[i] = InitialValue.MIN_CD;
			}
			else if (Design_variable == "M"){
				if (nextValue[i] < InitialValue.MAX_M) nextValue[i] = InitialValue.MAX_M;
				else if (nextValue[i] > InitialValue.MIN_M) nextValue[i] = InitialValue.MIN_M;
			}
		}
		return nextValue;
	}
}
