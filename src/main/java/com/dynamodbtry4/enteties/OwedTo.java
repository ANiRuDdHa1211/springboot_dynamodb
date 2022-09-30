package com.dynamodbtry4.enteties;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class OwedTo
{

    @Override
    public boolean equals(Object  other)
    {
        return this.hashCode() == other.hashCode();
    }
    @Override
    public int hashCode()
    {
        return Integer.valueOf(this.id) ;
    }

    String id ;
    double amount = 0.0 ;

    public String getId()
    {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
