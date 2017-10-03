package oc;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

public class OwnershipCounter {


	public static void main(String[] args) throws IOException, NoHeadException, GitAPIException {

	    FileOwnership author = new FileOwnership();


		Logger logger = Logger.getLogger (OwnershipCounter.class);
		logger.trace ("Hello trace");


		//ファイル名と(編集者，編集回数)を保持するリスト
//		ArrayList<FileAuthor> fileAuthorList = new ArrayList<FileAuthor>();



//      Repository repository = new FileRepository("C:\\Users\\k-tsuji\\CaseStudy\\wildfly\\.git");
        Repository repository = new FileRepository("C:\\Users\\k-tsuji\\CaseStudy\\new-wildfly\\wildfly\\.git");

		//リポジトリが正常な状態かどうかの判定(HEADをresetできるのであれば正常であると判定する)
        if( ! repository.getRepositoryState().canResetHead() ){
        	  System.out.println("target path is not a valid repository");
        	  System.exit(0);
        }
      //全コミットから，authorとコミットメッセージを抽出している．
        Git git = new Git(repository);

        //ファイル名を与えて，その編集者たちと編集回数を得る．

//        Iterable<RevCommit> log = git.log().call();
//      Iterable<RevCommit> log = git.log().addPath("hogehoge").call();

        //ファイル名を与えてログが正しく取れるコードサンプル

      String fileName = "server/src/main/java/org/jboss/as/server/SystemExiter.java";
//      String fileName = "";

//        Iterable<RevCommit> log = git.log().addPath(fileName).call();
/*      Iterable<RevCommit> log = new LogFollowCommand(repository,fileName).call();
        author = new FileOwnership(fileName);

        for (Iterator<RevCommit> iterator = log.iterator(); iterator.hasNext();){
            RevCommit revC = iterator.next();
            author.authorEditIncrement(revC.getAuthorIdent().getName());
            System.out.println(revC.getAuthorIdent().getName());
            System.out.println(revC.getFullMessage());
        }
*/
      System.out.println();
      FileChangeTracker fileChangeTracker = new FileChangeTracker(repository,fileName);
      System.out.println(fileChangeTracker.fileChangeTrack());



        git.close();
        repository.close();

        author.showFileEditers();
        }
}

