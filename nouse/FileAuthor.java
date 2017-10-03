package oc;

import java.util.HashMap;



//編集者個人のデータ(編集したファイルとその編集回数)を格納するクラス．
public class FileAuthor{

	String authorName;
	HashMap<String,Integer> fileEditCounter = new HashMap<String,Integer>();


	public FileAuthor(){

	}

	public FileAuthor(String fName,String aName){
		authorName = aName;
		fileEditIncrement(fName);
	}






	//ファイルの編集回数をインクリメントする．
	public void fileEditIncrement(String fName){
		for(int i=0; i<fileEditCounter.size();i++){
			HashMap<String,Integer> auth = fileEditCounter;
			if(auth.get(fName)!=null){
				Integer count =(auth.get(fName));
				fileEditCounter.put(fName, ++count);
				return;
			}
		}
		//リストにファイルが無い場合，リストに新しいファイルを登録
		fileEditCounter.put(fName, 1);
	}

	public void showFileEditers(){
		for(String auth : fileEditCounter.keySet()){
			System.out.println(auth + "," + fileEditCounter.get(auth));
		}
	}

}
