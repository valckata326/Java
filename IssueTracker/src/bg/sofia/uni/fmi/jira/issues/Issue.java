package bg.sofia.uni.fmi.jira.issues;

import bg.sofia.uni.fmi.jira.Component;
import bg.sofia.uni.fmi.jira.IdGenerator;
import bg.sofia.uni.fmi.jira.User;
import bg.sofia.uni.fmi.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.jira.enums.IssueType;
import bg.sofia.uni.fmi.jira.interfaces.IIssues;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidComponentException;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidDescriptionException;

import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidPriorityException;
import bg.sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

import java.time.LocalDateTime;


public abstract class Issue implements IIssues {

    private String uniqueID;
    private IssuePriority priority;
    private IssueResolution resolution;
    private IssueStatus status;
    protected IssueType type;

    private User reporter;
    private String description;

    private Component component;
    private LocalDateTime created;
    private LocalDateTime lastModified;

    public Issue(IssuePriority priority, Component component, User reporter, String description) throws InvalidReporterException
    {
        validatePriority(priority);
        validateComponent(component);
        validateDescription(description);
        validateUser(reporter);

        this.setPriority(priority);
        this.setComponent(component);
        this.setDescription(description);
        this.setReporter(reporter);
        setCreated(LocalDateTime.now());


        this.uniqueID = component.getShortName() + " - " + IdGenerator.generate();
        setStatus(IssueStatus.OPEN);
        resolve(IssueResolution.UNRESOLVED);
        setCreated(LocalDateTime.now());

    }

    private void validateUser(User user) throws InvalidReporterException
    {
        if(user == null)
        {
            throw new InvalidReporterException("Invalid user!");
        }
    }
    private void validatePriority(IssuePriority priority)
    {
        if(priority == null)
        {
            throw new InvalidPriorityException("Invalid priority");
        }
    }
    private void validateComponent(Component component)
    {
        if(component == null)
        {
            throw new InvalidComponentException("Invalid component");
        }
    }
    private void validateDescription(String description)
    {
        if(description == null)
        {
            throw new InvalidDescriptionException(description);
        }
    }
    @Override
    public void resolve(IssueResolution resolution)
    {
        this.resolution = resolution;
        setLastModified(LocalDateTime.now());
    }

    @Override
    public void setStatus(IssueStatus status)
    {
        this.status = status;
        setLastModified(LocalDateTime.now());
    }

    @Override
    public String getId()
    {
        return uniqueID;

    }

    @Override
    public LocalDateTime getCreatedAt()
    {
        return this.created;

    }
    @Override
    public LocalDateTime getLastModifiedAt()
    {
        return this.lastModified;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    private void setPriority(IssuePriority priority) {
        this.priority = priority;
        setLastModified(LocalDateTime.now());
    }

    public IssueResolution getResolution() {
        return resolution;
    }

    public IssueStatus getStatus() {
        return status;
    }
    public abstract IssueType getType();

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
        setLastModified(LocalDateTime.now());
    }

    public LocalDateTime getCreated() {
        return created;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    private void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        setLastModified(LocalDateTime.now());
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
        setLastModified(LocalDateTime.now());
    }
}
