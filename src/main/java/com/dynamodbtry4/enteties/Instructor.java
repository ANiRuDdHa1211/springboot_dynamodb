package com.dynamodbtry4.enteties;


import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "instructors4")
public class Instructor
{
    private String pk;

    private String sk;

    private String name;

    private JobType type;

    @DynamoDBHashKey(attributeName = "pk")
    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @DynamoDBRangeKey(attributeName = "sk")
    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "type")
    @DynamoDBTypeConvertedEnum
//    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    public JobType getType() {
        return type;
    }


    public void setType(JobType type) {
        this.type = type;
    }
}
