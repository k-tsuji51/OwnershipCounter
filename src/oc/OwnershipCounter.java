package oc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import oc.util.StringUtil;

public class OwnershipCounter {// rename.txtを基に，与えられたパス名がどんな名前に変更されるかを特定するクラス．

	static Logger logger = Logger.getLogger ("OwnershipCounter");


	HashMap<String,Integer> editNumber = new HashMap<String,Integer>();

	static ChangeLogGenerator changeLogGenerator = new ChangeLogGenerator();

	static CloneFileList cfl = new CloneFileList();
	static ArrayList<String> cloneFileNameList = cfl.makePathNameList();
	static ArrayList<CloneSet> cloneSetList = new ArrayList<CloneSet>();
	static CloneSet cs;
	static FileEditCounter fec;


	public static void main(String[] args) {
//		FileNameChangeTracer f = new FileNameChangeTracer("messaging/src/main/java/org/jboss/as/messaging/jms/SecurityActions.java");
		try {

		File outputFile = new File(StringUtil.OUTPUT_CSV);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
		int cloneSetCounter = 0;

		for(String str: cloneFileNameList){
			if(str.startsWith("#begin{set}")){
				cloneSetCounter++;
				pw.write("Set"+cloneSetCounter);
				cs = new CloneSet(cloneSetCounter);
			}else if(str.startsWith("#end{set}")){
				//クローンセットで数値をまとめる処理
				cloneSetList.add(cs);

				cs.calculateCSOwnership();
				System.out.println("==============");
			}else{
				//各ファイルごとに編集回数をカウントする処理
				fileNameChangeTrack(str);
				fec.showEditNumber();
//				fec.showResult();
				cs.cloneFiles.add(fec);

				pw.write(","+str+","+fec.maxEditNum+","+fec.totalEditNum+","+fec.ownership);
				pw.write("\n");

			}
		}
		pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fileNameChangeTrack(String path) {
		fec = new FileEditCounter(path);
		for (RenameCommit commit : ChangeLogGenerator.renameCommits){
			if (commit.oldName.contains(path)){
				fec.countEditNumber(commit);
				commit.showCommitData();
				if (commit.changeType.matches("R.*")) {
//					System.out.println("rename " + commit.oldName +","+ commit.newName);
					fileNameChangeTrack(commit.newName);
					break;
				}
			}
		}
	}


	public static void makeCSVFile(){

	}

}
