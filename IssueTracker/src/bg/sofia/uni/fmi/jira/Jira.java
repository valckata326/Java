package bg.sofia.uni.fmi.jira;

import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.IssueTracker;
import bg.sofia.uni.fmi.jira.issues.Issue;
import jdk.jshell.spi.ExecutionControl;

import java.time.LocalDateTime;

public class Jira implements IssueTracker {

    private Issue[] issues;
    //private int size;

    public Jira(Issue[] issues)
    {
        this.issues = issues;
    }
    @Override
    public Issue[] findAll(Component component, IssueStatus status)
    {
        Issue[] statusIssues = new Issue[issues.length];
        int counter = 0;
        for(int index = 0; index < issues.length;index++)
        {
            Issue temporary = issues[index];
            if(temporary != null && issues[index].getComponent().getName().equals(component.getName()) && issues[index].getStatus() == status)
            {
                statusIssues[counter++] = temporary;
            }
        }
        return statusIssues;
    }
    @Override
    public Issue[] findAll(Component component, IssuePriority priority)
    {
        Issue[] priorityIssues = new Issue[issues.length];
        int counter = 0;
        for(int index = 0; index < issues.length;index++)
        {
            Issue temporary = issues[index];
            if(temporary != null && issues[index].getComponent().getName().equals(component.getName()) && issues[index].getPriority() == priority)
            {
                priorityIssues[counter++] = temporary;
            }
        }
        return priorityIssues;
    }
    @Override
    public Issue[] findAll(Component component, IssueType type)
    {
        Issue[] typeIssues = new Issue[issues.length];
        int counter = 0;
        for(int index = 0; index < issues.length;index++)
        {
            Issue temporary = issues[index];
            if(temporary != null && issues[index].getComponent().getName().equals(component.getName())  && issues[index].getType() == type)
            {
                typeIssues[counter++] = temporary;
            }
        }
        return typeIssues;
    }
    @Override
    public Issue[] findAll(Component component, IssueResolution resolution)
    {
        Issue[] resolutionIssues = new Issue[issues.length];
        int counter = 0;
        for(int index = 0; index < issues.length;index++)
        {
            Issue temporary = issues[index];
            if(temporary != null && issues[index].getComponent().getName().equals(component.getName()) && issues[index].getResolution() == resolution)
            {
                resolutionIssues[counter++] = temporary;
            }
        }
        return resolutionIssues;
    }
    @Override
    public Issue[] findAllIssuesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime)
    {
        Issue[] createdBetween = new Issue[issues.length];
        int counter = 0;
        for(int index = 0; index < issues.length; index++)
        {
            Issue temporary = issues[index];
            if(temporary != null && issues[index].getCreated().compareTo(startTime) >= 0
            && issues[index].getCreated().compareTo(endTime) <= 0)
            {
                createdBetween[counter++] = temporary;
            }
        }
        return createdBetween;
    }
    @Override
    public Issue[] findAllBefore(LocalDateTime dueTime)
    {
        Issue[] createdBefore = new Issue[issues.length];
        int counter = 0;
        for(int index = 0; index < issues.length; index++)
        {
            Issue temporary = issues[index];
            if(temporary != null && (issues[index].getType() == IssueType.NEW_FEATURE || issues[index].getType() == IssueType.TASK)
            && issues[index].getCreated().isBefore(dueTime))
            {
                createdBefore[counter++] = temporary;
            }
        }
        return createdBefore;

    }
}
