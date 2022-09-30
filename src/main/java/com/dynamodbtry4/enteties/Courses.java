package com.dynamodbtry4.enteties;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable(tableName = "courses4")
public class Courses
{
    private String id;

    private String name;
    private String description;

//    private String instid_name;
    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @DynamoDBAttribute(attributeName = "instructor")
//    public String getInstid_name() {
//        return instid_name;
//    }
//
//    public void setInstid_name(String instid_name) {
//        this.instid_name = instid_name;
//    }


}
