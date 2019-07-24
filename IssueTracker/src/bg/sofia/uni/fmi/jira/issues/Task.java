package bg.sofia.uni.fmi.jira.issues;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

import java.time.LocalDateTime;

public class Task extends ScheduledIssue {

    public Task(IssuePriority priority, Component component, User reporter, String description, LocalDateTime dueTime)
            throws InvalidReporterException {
        super(priority, component, reporter, description, dueTime);
        setType();
    }
    @Override
    public IssueType getType() {
        return IssueType.TASK;
    }
    private void setType()
    {
        this.type = IssueType.TASK;
    }
}
