package bg.sofia.uni.fmi.mjt.git;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CurrentCommit {
    private Set<String> added;
    private Set<String> removed;
    public CurrentCommit()
    {
        added = new HashSet<>();
        removed = new HashSet<>();

    }
    public int getNumberOfFilesCommitted()
    {
        return added.size() + removed.size();
    }
    public Set<String> getAdded() {
        return added;
    }

    public Set<String> getRemoved() {
        return removed;
    }
    public boolean contains(String file)
    {
        return added.contains(file);
    }
    public void add(String[] files)
    {
        Collections.addAll(added, files);
    }
    public void remove(String[] files)
    {
        for(String file: files)
        {
            added.remove(file);
            removed.add(file);
        }
    }
}
