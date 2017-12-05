import java.util.concurrent.TimeUnit;

public class Manager {
	public static void main(String[] args){
		ILS ils =new ILS();

		for (int i = 0; i < 10; i++) {
			//get_Target();		//目標照度・色温度の取得
			//ils.add_newSensor(x,y,LX,K);		//照度センサの追加
			//ils.change_Target(ID,LX,K);		//目標照度・色温度の変更

			ils.Optimization();		//光度・色温度の最適化

			//change_to_signal(CD,K); //光度・色温度を信号値へ変換
			//send_signal_to_server(sgnal_cd,signal_k); //信号値を調光用サーバへ送信

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
