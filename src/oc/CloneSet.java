package oc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CloneSet{

	int cloneSetNumber;

	public ArrayList<FileEditCounter> cloneFiles = new ArrayList<FileEditCounter>();

	Map<String,Integer> csFileEditors = new HashMap<>();
	double maxEditNum = 0;
	double totalEditNum = 0;
	double csOwnership = 0;

	String mostStrongEditor;

	CloneSet(int csNumber){
		cloneSetNumber = csNumber;
	}


	public void calculateCSOwnership(){
		//実装予定
		//cloneFilesに入っているファイル編集者とファイル回数を参照し，CSOwnershipを計算する
		for(FileEditCounter fec : cloneFiles){
			Map<String,Integer> fileEditors = fec.editNumberbyEditors;
			for(String editorName: fileEditors.keySet()){
				int editNum = 0;
				if(csFileEditors.containsKey(editorName)){
					editNum = csFileEditors.get(editorName)+fileEditors.get(editorName);
					if(editNum > maxEditNum){
						maxEditNum = editNum;
						mostStrongEditor = editorName;
					}
					csFileEditors.put(editorName,editNum);
				}else{
					editNum = fileEditors.get(editorName);
					csFileEditors.put(editorName,editNum);
				}
				totalEditNum++;
			}
		}
		showCloneFiles();
		csOwnership=maxEditNum/totalEditNum;
		System.out.println("csOwnership="+csOwnership);
	}



	public void showCloneFiles(){
		for(String name: csFileEditors.keySet()){
			System.out.println(name + "," + csFileEditors.get(name));
		}
		System.out.println("total="+totalEditNum + ",max="+maxEditNum+",mostStrongEditor=" + mostStrongEditor);
	}
}
