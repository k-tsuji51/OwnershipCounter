package oc;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;

/*
 * ファイルの変更を追跡する
 * 最新のリポジトリ状態に変更し，できるだけ長期間のlogを得られるようにする．これはあらかじめ対象リポジトリに対して行っておく
 *
 * 対象ファイル名のgit logを調査する
 * 対象ファイルが開発途中で削除もしくは名称変更を受けている場合がある
 *
 *
 * DiffEntryについて調べたい．どこからDiffEntryを得ることができるのか．
 *
 * DiffEntryから，ChangeTypeとoldPathを取得し，対象ファイル名と一致しつつ変更タイプがdeleteなら調査終了，renameならnewPathを新しく調査する．
 *
 *
 */
public class FileChangeTracker {

    private final Repository repository;
    private String path;
    private Git git;

    public FileChangeTracker(Repository repository, String path){
        this.repository = repository;
        this.path = path;
    }



    String fileChangeTrack() throws NoHeadException, GitAPIException, IOException, IncorrectObjectTypeException, CorruptObjectException, IOException{
    	String newFilePath = "";

        git = new Git(repository);
        Iterable<RevCommit> allCommitsLater = git.log().addPath(path).call();
        RevCommit recentCommit = null;
        for (RevCommit commit : allCommitsLater) {
        	if(recentCommit == null) recentCommit = commit;
        	TreeWalk tw = new TreeWalk(repository);
        	tw.addTree(commit.getTree());
        	tw.addTree(recentCommit.getTree());
        	tw.setRecursive(true);
        	RenameDetector rd = new RenameDetector(repository);
        	rd.addAll(DiffEntry.scan(tw));
        	List<DiffEntry> files = rd.compute();
    		System.out.println("---------------------------------------------------------------------------------------------");
        	for (DiffEntry diffEntry : files) {
        		if(diffEntry.getNewPath().matches(path) || diffEntry.getOldPath().matches(path)){
        			showDiffEntryChangeType(diffEntry,commit,recentCommit);
        			if ((diffEntry.getChangeType() == DiffEntry.ChangeType.RENAME || diffEntry.getChangeType() == DiffEntry.ChangeType.COPY) && diffEntry.getNewPath().contains(path)) {
        				System.out.println("Found: " + diffEntry.toString() + " return " + diffEntry.getOldPath());
        				return diffEntry.getOldPath();
        			}
        		}
        	}
        		recentCommit = commit;

        	}

    	return newFilePath;
    }




    void showDiffEntryChangeType(DiffEntry de, RevCommit oldCom, RevCommit newCom){
    	System.out.println("prevId:" + oldCom.getName() + "\nthisId:" + newCom.getName());
    	System.out.println("prevPath:"+de.getOldPath() + "     thisPath:" + de.getNewPath());
    	System.out.println( de.getChangeType());
    }

}
