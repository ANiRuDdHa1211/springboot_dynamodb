package com.dynamodbtry4.enteties;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Set;

@DynamoDBTable(tableName = "split1")
public class UserBalance
{
    private String uid;

    private String uid_repl;

    private double totalAmountOwed = 0;

    private Set<OwedTo> ls;

    @DynamoDBHashKey(attributeName = "pk")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    @DynamoDBRangeKey(attributeName = "sk")
    public String getUid_repl() {
        return uid_repl;
    }

    public void setUid_repl(String uid_repl) {
        this.uid_repl = uid_repl;
    }

    @DynamoDBAttribute(attributeName = "amount")
    public double getTotalAmountOwed()
    {
        return totalAmountOwed;
    }

    public void setTotalAmountOwed(double totalAmountOwed) {
        this.totalAmountOwed = totalAmountOwed;
    }


    @DynamoDBAttribute(attributeName = "list")
    public Set<OwedTo> getLs()
    {
        return ls;
    }

    public void setLs(Set<OwedTo> ls)
    {
        this.ls = ls;
    }
}
