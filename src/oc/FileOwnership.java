package oc;

import java.util.HashMap;



//ファイルの編集に関するデータ(編集者たちとその編集回数)を格納するクラス．
public class FileOwnership{

	String fileName;
	HashMap<String,Integer> authorEditCounter = new HashMap<String,Integer>();
	int ownership;

	public FileOwnership(String fName){
		fileName = fName;
	}

	public FileOwnership(){
		this("default");
	}



	//編集者の編集回数をインクリメントする．
	public void authorEditIncrement(String aName){
		for(int i=0; i<authorEditCounter.size();i++){
			HashMap<String,Integer> auth = authorEditCounter;
			if(auth.get(aName)!=null){
				Integer count =(auth.get(aName));
				authorEditCounter.put(aName, ++count);
				return;
			}
		}
		//リストに編集者が無い場合，リストに新しい編集者を登録
		authorEditCounter.put(aName, 1);
	}

	public void showFileEditers(){
		System.out.println(fileName);
		int counter = 0;
		for(String auth : authorEditCounter.keySet()){

			System.out.println(auth + "," + authorEditCounter.get(auth));

			counter = counter + authorEditCounter.get(auth);
		}
		System.out.println("total commits:" + counter);
	}


}
