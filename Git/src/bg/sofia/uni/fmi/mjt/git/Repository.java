package bg.sofia.uni.fmi.mjt.git;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Repository {
    private String currentBranch;
    private Set<String> allAdded;
    private Set<String> allDeleted;
    private Map<String, ArrayList<Commit>> branches;
    public Repository()
    {
        currentBranch = "master";
        branches = new HashMap<>();
        branches.put(currentBranch,new ArrayList<>());
        allAdded = new HashSet<>();
        allDeleted = new HashSet<>();
    }
    public Result checkoutBranch(String branch)
    {
        if(branches.containsKey(branch))
        {
            currentBranch = branch;
            //head = getHead();
            return Result.successful("switched to branch " + branch);
        }
        return Result.failure("branch " + branch + " does not exists");
    }
    public Result add(String... files)
    {
        for(String file : files)
        {
            if(allAdded.contains(file))
            {
                return Result.failure("'" + file + "'" + " already exists");
            }
        }
        allAdded.addAll(Arrays.asList(files));
        allDeleted.removeAll(Arrays.asList(files));
        String message = String.format("added %s to stage", String.join(", ", files));
        return Result.successful(message);
    }
    public Result commit(String message)
    {
        Result toReturn;
        Commit toCommit = new Commit(allAdded,allDeleted);
        if(toCommit.getNumberOfFilesCommitted() == 0)
        {
            toReturn = Result.failure("nothing to commit, working tree clean");
        }
        else
        {
            Commit newCommit = new Commit(toCommit);
            //allAdded.removeAll(head.getDeletedFiles());
            //allAdded.addAll(head.getAddedFiles());

            newCommit.setMessage(message);
            toCommit.setMessage(message);
            try
            {
                newCommit.setHash(message);
                toCommit.setHash(message);
            }
            catch (NoSuchAlgorithmException e)
            {
                System.out.println("...");
            }
            branches.get(currentBranch).add(toCommit);
            toReturn = Result.successful(toCommit.getNumberOfFilesCommitted() + " files changed");
        }
        //head = new Commit();
        return toReturn;
    }
    public Result remove(String... files)
    {
        for(String file : files)
        {
            if(!(allAdded.contains(file) || allDeleted.contains(file)))
            {
                return Result.failure("'" + file + "'" + " did not match any files");
            }
        }
        allDeleted.addAll(Arrays.asList(files));
        allAdded.removeAll(Arrays.asList(files));
        String message = String.format("added %s for removal", String.join(", ", files));
        return Result.successful(message);
    }
    public Commit getHead()
    {
        if(branches.get(currentBranch).size() == 0)
        {
            return null;
        }
        return branches.get(currentBranch).get(branches.get(currentBranch).size() - 1);
    }
    public Result log()
    {
        if(branches.get(currentBranch).size() == 0)
        {
            return Result.failure("branch " + currentBranch + " does not have any commits yet");
        }
        else
        {
            String finalString = "";
            for(int i = branches.get(currentBranch).size() - 1; i >= 0;i --)
            {
                finalString += branches.get(currentBranch).get(i).toString();
            }
            return Result.successful(finalString);
        }
    }
    public String getBranch()
    {
        return this.currentBranch;
    }
    public Result createBranch(String newBranch)
    {
        if(branches.containsKey(newBranch))
        {
            return Result.failure("branch " + newBranch + " already exists");
        }
        else
        {
            branches.put(newBranch, new ArrayList<>(branches.get(currentBranch)));
            currentBranch = newBranch;
            return Result.successful("created branch " + newBranch);
        }
    }
    public Result checkoutCommit(String hash) {
        int indexOfCommit = -1;
        boolean isAvailable = false;
        for (Commit commit : branches.get(currentBranch)) {
            if (commit.getHash().equals(hash)) {
                indexOfCommit = branches.get(currentBranch).indexOf(commit);
                //head = commit;
                isAvailable = true;
                break;
            }
        }
        if (isAvailable && indexOfCommit != -1) {
            while (branches.get(currentBranch).size() != indexOfCommit) {
                branches.get(currentBranch).remove(branches.get(currentBranch).size() - 1);
            }
            //head = branches.get(currentBranch).get(indexOfCommit);
            return Result.successful("HEAD is now at " + hash);
        }
        return Result.failure("commit + " + hash + " does not exist");
    }
}
