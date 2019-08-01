package bg.sofia.uni.fmi.mjt.git;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static bg.sofia.uni.fmi.mjt.git.SHA1.hexDigest;

public class Commit {
    private String hash;
    private String message;
    private LocalDateTime dateTime;
    private Set<String> addedFiles;
    private Set<String> deletedFiles;
    public Commit(Set<String> addedFiles, Set<String> deletedFiles)
    {
        this.addedFiles = new HashSet<>(addedFiles);
        this.deletedFiles = new HashSet<>(deletedFiles);
        dateTime = LocalDateTime.now();
    }
    public Commit()
    {

        dateTime = LocalDateTime.now();
        addedFiles = new HashSet<>();
        deletedFiles = new HashSet<>();
    }
    public Commit(Commit newCommit)
    {
        this.hash = newCommit.getHash();
        this.message = newCommit.getMessage();
        this.dateTime = newCommit.getDateTime();
        this.addedFiles = newCommit.getAddedFiles();
        this.deletedFiles = newCommit.getDeletedFiles();
    }
    public String getHash()
    {
        return this.hash;
    }
    public String getMessage()
    {
        return this.message;
    }
    public String getFormattedDateTime()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return getDateTime().format(formatter);
    }
    /*public Commit getPrevious()
    {
        return this.previous;
    }*/

    public Set<String> getAddedFiles() {
        return addedFiles;
    }

    public Set<String> getDeletedFiles() {
        return deletedFiles;
    }
    public void addFiles(String... files)
    {
        for(String file : files)
        {
            deletedFiles.remove(file);
        }
        Collections.addAll(addedFiles, files);
    }
    public void removeFiles(String... files)
    {
        for(String file : files)
        {
            addedFiles.remove(file);
        }
        Collections.addAll(deletedFiles,files);
    }
    public int getNumberOfFilesCommitted()
    {
        return addedFiles.size() + deletedFiles.size();
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString()
    {
        return String.format("commit %s\nDate: %s\n\n\t%s", hash, getFormattedDateTime(), message);
    }
    public void setHash(String message) throws NoSuchAlgorithmException
    {
        this.hash = hexDigest(message);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
