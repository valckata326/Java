package bg.sofia.uni.fmi.jira;

import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.FilterIssues;
import bg.sofia.uni.fmi.jira.interfaces.IssueTracker;
import bg.sofia.uni.fmi.jira.issues.Issue;


import java.time.LocalDateTime;

public class Jira implements IssueTracker {

    private Issue[] issues;

    public Jira(Issue[] issues)
    {
        this.issues = issues;
    }
    @Override
    public Issue[] findAll(Component component, IssueStatus status)
    {
        return this.filterIssuesBy((int index)->issues[index].getComponent().getName().equals(component.getName()) && issues[index].getStatus() == status);
    }
    @Override
    public Issue[] findAll(Component component, IssuePriority priority)
    {
        return this.filterIssuesBy((int index)->issues[index].getComponent().getName().equals(component.getName()) && issues[index].getPriority() == priority);
    }
    @Override
    public Issue[] findAll(Component component, IssueType type)
    {
        return this.filterIssuesBy((int index)->issues[index].getComponent().getName().equals(component.getName())  && issues[index].getType() == type);
    }
    @Override
    public Issue[] findAll(Component component, IssueResolution resolution)
    {
        return this.filterIssuesBy((int index)->issues[index].getComponent().getName().equals(component.getName()) && issues[index].getResolution() == resolution);
    }
    @Override
    public Issue[] findAllIssuesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime)
    {
        return this.filterIssuesBy((int index)->issues[index].getCreated().compareTo(startTime) >= 0
                && issues[index].getCreated().compareTo(endTime) <= 0);
    }
    @Override
    public Issue[] findAllBefore(LocalDateTime dueTime)
    {
        return this.filterIssuesBy((int index)->(issues[index].getType() == IssueType.NEW_FEATURE || issues[index].getType() == IssueType.TASK)
                && issues[index].getCreated().isBefore(dueTime));
    }

    private Issue[] filterIssuesBy(FilterIssues filterCondition)
    {
        Issue[] filteredIssues = new Issue[issues.length];
        int counter = 0;
        for(int i = 0; i < issues.length;i++)
        {
            Issue temporary = issues[i];
            if(temporary != null && filterCondition.filterCondition(i))
            {
                filteredIssues[counter++] = temporary;
            }
        }
        return filteredIssues;
    }
}
