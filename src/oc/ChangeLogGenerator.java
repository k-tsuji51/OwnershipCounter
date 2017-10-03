package oc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oc.util.StringUtil;

public class ChangeLogGenerator {

	static Logger logger = Logger.getLogger ("OwnershipCounter");

	static String outputFileName = StringUtil.OUTPUT_CHANGE_LOG;
	static File outputFile = new File(outputFileName);

	static String inputFileName = StringUtil.INPUT_GIT_ALL_LOGLIST;
	static File inputFile = new File(inputFileName);

	static ArrayList<RenameCommit> renameCommits = new ArrayList<RenameCommit>();

	ChangeLogGenerator(){
		makeChangeLogFile(inputFile);
		logger.info("complete making change log file \"rename.txt\"");
	}


	  private static boolean checkBeforeWritefile(File file){
		    if (file.exists()){
		      if (file.isFile() && file.canWrite()){
		        return true;
		      }
		    }
		    return false;
	  }




	  public static void makeChangeLogFile(File input){
			try {
				BufferedReader br = new BufferedReader(new FileReader(input));
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
				String str;
				String commitID = null;
				String author = null;

				String[] spl;

				while((str = br.readLine()) != null){
					if(str.matches("[0-9a-f].*")){
						spl = str.split("\t");
						commitID = spl[0];
						author = spl[1];

					}
					else if(str.matches("R.*java")){
						spl = str.split("\t");
//						System.out.println(commitID);
//						System.out.println(str);
						renameCommits.add(new RenameCommit(commitID,author,spl[1],spl[2],spl[0]));
					      if (checkBeforeWritefile(outputFile)){
						        pw.write(str + "\n");
					      }
					}
					else if(str.matches("D.*java" )||str.matches( "M.*java")||str.matches("A.*java")){
						spl = str.split("\t");
//						System.out.println(commitID);
//						System.out.println(str);
						renameCommits.add(new RenameCommit(commitID,author,spl[1],"",spl[0]));
					      if (checkBeforeWritefile(outputFile)){
						        pw.write(str + "\n");
					      }
					}
				}
				br.close();
		        pw.close();
			} catch (FileNotFoundException e) {
				logger.error(e);
			} catch (IOException e) {
				logger.error(e);
			}
	  }

	  public static void writeLog(String log){//追加の書き込みをする
			try{
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
			      if (checkBeforeWritefile(outputFile)){
			        pw.write(log);

			        pw.close();
			      }
			} catch (IOException e) {
				logger.error(e);
			}
	  }

	  public void showCommitsData(){//コミットデータが正しく格納されているのかを確認するためのメソッド
		  for(RenameCommit commit: renameCommits){
			  commit.showCommitData();
		  }
	  }




}
