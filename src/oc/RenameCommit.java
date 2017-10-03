package oc;

import org.apache.log4j.Logger;



public class RenameCommit {//コミット内のパス名の変更情報(変更前後のパス名)を扱うクラス

	//DELETEについて扱うかは未定．
	String commitID;
	String author;
	String changeType;
	String oldName;;
	String newName;

	Logger logger = Logger.getLogger(RenameCommit.class);

	RenameCommit(String id, String author, String oldName, String newName){
		this(id,author,oldName,newName,"R");
	}

	RenameCommit(String id, String author, String oldName, String newName, String changeType){
		this.commitID = id;
		this.author = author;
		this.oldName=oldName;
		this.newName=newName;
		this.changeType = changeType;
	}



	public void showCommitData(){
		logger.info(changeType + "," + author + "," + oldName + "," + newName + "," + commitID);
	}


}
