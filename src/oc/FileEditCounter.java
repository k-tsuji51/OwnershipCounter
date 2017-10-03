package oc;

import java.util.ArrayList;
import java.util.HashMap;


//各ファイルごとの，Ownershipの計算用のクラス

public class FileEditCounter {

	public ArrayList<String> pathNames = new ArrayList<String>();
	public HashMap<String,Integer> editNumberbyEditors = new HashMap<String,Integer>();

	double maxEditNum = 0;
	double totalEditNum = 0;
	double ownership = 0.00;


	public FileEditCounter(String path){
		pathNames.add(path);
	}



	public void addRenamedPath(String path){
		pathNames.add(path);
	}


	public void countEditNumber(RenameCommit commit){
		String auth = commit.author;
		if(editNumberbyEditors.containsKey(auth)){
			editNumberbyEditors.put(auth, editNumberbyEditors.get(auth).intValue() + 1);
		}
		else{
			editNumberbyEditors.put(auth, 1);
		}
		totalEditNum++;
		calculateOwnership();
	}

	public void showEditNumber(){
		for(String key:editNumberbyEditors.keySet()){
			System.out.println(key + "=>" + editNumberbyEditors.get(key));
		}
		System.out.println("total=" + totalEditNum + ",ownership=" + String.format("%.2f", ownership));
	}

	public void calculateOwnership(){
		for(String key:editNumberbyEditors.keySet()){
			if(maxEditNum < editNumberbyEditors.get(key)){
					maxEditNum = editNumberbyEditors.get(key);
			}
		}
		ownership = maxEditNum/totalEditNum;
	}

	public void showResult(){
		System.out.println(totalEditNum+"\t"+String.format("%.2f", ownership));
	}
}
