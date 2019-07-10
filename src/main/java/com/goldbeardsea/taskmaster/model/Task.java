package com.goldbeardsea.taskmaster.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.UUID;

@DynamoDBTable(tableName = "ProductInfo")
public class Task {
    private UUID id;
    private String title;
    private String description;
    private String status;
    private String[] statuses = {"Available", "Assigned", "Accepted", "Finished"};
    private String picUrl;
    private String assignee;

    public Task() {}

    public Task(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = statuses[0];
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public UUID getId() {
        return id;
    }

    @DynamoDBAttribute
    public String getTitle() {
        return title;
    }

    @DynamoDBAttribute
    public String getDescription() {
        return description;
    }

    @DynamoDBAttribute
    public String getStatus() {
        return status;
    }

    @DynamoDBAttribute
    public void setId(UUID id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDBAttribute
    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute
    public void setStatus(String status) {
        this.status = status;
    }

    @DynamoDBAttribute
    public String[] getStatuses() {
        return statuses;
    }

    @DynamoDBAttribute
    public String getPicUrl() {
        return picUrl;
    }

    @DynamoDBAttribute
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @DynamoDBAttribute
    public String getAssignee() {
        return assignee;
    }

    @DynamoDBAttribute
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void advanceTask() {
        if (this.status.equals(statuses[0])) {
            this.status = statuses[1];
        } else if (this.status.equals(statuses[1])) {
            this.status = statuses[2];
        } else if (this.status.equals(statuses[2])) {
            this.status = statuses[3];
        }
    }
}