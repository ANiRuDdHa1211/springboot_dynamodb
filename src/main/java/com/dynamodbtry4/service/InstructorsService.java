package com.dynamodbtry4.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;

import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.dynamodbtry4.enteties.JobType;
import com.dynamodbtry4.repo.DynamoDBRepo;
import com.dynamodbtry4.enteties.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InstructorsService {
    @Autowired
    DynamoDBRepo dynamoDBrepo;
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-south-1"))
            .build();

    DynamoDBMapper mapper = new DynamoDBMapper(client);
    public void createTable() throws Exception
    {
            dynamoDBrepo.createInstructorTable();
//        dynamoDBrepo.createInstructorTable();
    }
    public Table getTable(String tableName) throws Exception
    {
        return dynamoDBrepo.getTable(tableName);
    }
    public void deleteTable(String tableName) throws Exception
    {
        dynamoDBrepo.deleteTable(tableName);
    }

    public void saveInstructorDetails(Instructor course ) throws Exception
    {
//        createTable("instructors3");
//        dynamoDBrepo.createInstructorTable();

        Table table = dynamoDBrepo.getTable("instructors4");
        try
        {
//            String id = UUID.randomUUID().toString();
//            System.out.println("Id:" + id);
            PutItemOutcome outcome = table.putItem(new Item().withPrimaryKey("pk", course.getPk(),"sk",course.getSk() )
                    .with("name", course.getName()));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public Instructor getInstructorDetails(String uuid)
    {
        Instructor instructor = null;
        Table table = dynamoDBrepo.getTable("instructors4");
        if (table != null)
        {
            GetItemSpec spec = new GetItemSpec().withPrimaryKey("pk", uuid,"sk",uuid);
            try
            {
                System.out.println("Attempting to read the item...");
                Item outcome = table.getItem(spec);
                if (outcome != null)
                {
                    instructor = new Instructor();
                    instructor.setPk(outcome.getString("pk"));
                    instructor.setSk(outcome.getString("sk"));
                    instructor.setName(outcome.getString("name"));
                }
                System.out.println("GetItem succeeded: " + outcome);

            }
            catch (Exception e)
            {
                System.err.println("Unable to read item: " + uuid);
                System.err.println(e.getMessage());
            }
        }
        return instructor;
    }


    public List<Instructor> getAllInstructorDetails() throws Exception
    {

        List<Instructor> allcourses = new ArrayList<>();
        try
        {
            Table table = dynamoDBrepo.getTable("instructors4");
//            ItemCollection<ScanOutcome> items = table.scan(scanSpec);
            ScanSpec spec = new ScanSpec()
                    .withFilterExpression("begins_with ( sk, :sortkeyval )")
                    .withValueMap(new ValueMap()
                                    .withString(":sortkeyval", "instr") );


            ItemCollection<ScanOutcome> items = table.scan(spec);

            Iterator<Item> iter = items.iterator();

            while (iter.hasNext())
            {
                Item item = iter.next();
                if (item != null)
                {
                    Instructor product = new Instructor();
                    product.setPk(item.getString("pk"));
                    product.setSk(item.getString("sk"));
                    product.setName(item.getString("name"));
                    allcourses.add(product);
                }
                System.out.println(item.toString());
            }

        }
        catch (Exception e)
        {
            System.err.println("Unable to scan the table:");
            System.out.println("toString(): " + e);
            System.err.println(e.getMessage());
            throw new Exception("Error has been occured while getting list of product");
        }
        return allcourses;
    }

    public List<Instructor> getAllInstructorCourses(String instrid) throws Exception
    {
//        List<Instructor> allinstructorcourses = new ArrayList<>();
//        try
//        {
//            Table table = dynamoDBrepo.getTable("instructors4");
////            ItemCollection<ScanOutcome> items = table.scan(scanSpec);
//            QuerySpec spec = new QuerySpec()
//                    .withFilterExpression("pk = :primaryval and begins_with ( sk, :sortkeyval )")
//                    .withValueMap(new ValueMap()
//                            .withString(":sortkeyval", "course")
//                            .withString(":primaryval",instrid) );
//
//
//            ItemCollection<QueryOutcome> items = table.query(spec);
//
//            Iterator<Item> iter = items.iterator();
//            while (iter.hasNext())
//            {
//                Item item = iter.next();
//                if (item != null)
//                {
//                    Instructor product = new Instructor();
//                    product.setPk(item.getString("pk"));
//                    product.setSk(item.getString("sk"));
//                    product.setType(item.getString("type"));
//                    allinstructorcourses.add(product);
//                }
//                System.out.println(item.toString());
//            }
//        }
//        catch (Exception e)
//        {
//            System.err.println("Unable to scan the table:");
//            System.out.println("toString(): " + e);
//            System.err.println(e.getMessage());
//            throw new Exception("Error has been occured while getting list of product");
//        }
//        return allinstructorcourses;
//        DynamoDBScanExpression<Instructor> queryExpression = new DynamoDBScanExpression<Instructor>();
//        DynamoDBScanExpression

        String partitionKey = instrid;
        String ut = "course";
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":primaryval", new AttributeValue().withS(partitionKey));
        eav.put(":sortkeyval",new AttributeValue().withS(ut));

        DynamoDBQueryExpression<Instructor> queryExpression = new DynamoDBQueryExpression<Instructor>()
                .withKeyConditionExpression("pk = :primaryval and begins_with ( sk, :sortkeyval )")
                .withExpressionAttributeValues(eav);

        List<Instructor> allinstructorcourses = mapper.query(Instructor.class, queryExpression);
        return allinstructorcourses;

    }

    public void updateInstructorName(String uuid, String name1)
    {

//        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("pk", uuid)
//                .withUpdateExpression("set name = :name1")
//                .withValueMap(new ValueMap().withString(":name1", name1))
//                .withReturnValues(ReturnValue.UPDATED_NEW);
//
        Map<String, String> expressionAttributeNames = new HashMap<String, String>();
        Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();

        expressionAttributeNames.put("#A", "name");
        expressionAttributeValues.put(":val1", name1);
        try
        {
            Table table = dynamoDBrepo.getTable("instructors4");
            System.out.println("Updating the item...");
//            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            UpdateItemOutcome outcome =  table.updateItem("pk",uuid,"sk",uuid,"set #A = :val1",expressionAttributeNames,expressionAttributeValues) ;
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
        }
        catch (Exception e)
        {
            System.err.println("Unable to update item: " + uuid );
            System.out.println("toString(): " + e);
            System.err.println(e.getMessage());
        }
    }


//    public void updateInstructorCourses(String uuid, List<String> newcourses_ids)
//    {
//
////        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("pk", uuid)
////                .withUpdateExpression("set courses_ids = :courses_ids")
//////               .withValueMap(new ValueMap().withString(":courses_ids", courses_ids))
////                .withValueMap(new ValueMap().withList("courses_ids",courses_ids))
////                .withReturnValues(ReturnValue.UPDATED_NEW);
//        Map<String, String> expressionAttributeNames = new HashMap<String, String>();
//        Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
//
//        expressionAttributeNames.put("#A", "courses_ids");
//        expressionAttributeValues.put(":val1", newcourses_ids);
//
//        try
//        {
//            Table table = dynamoDBrepo.getTable("instructors4");
//            System.out.println("Updating the instructor item's courses_ids...");
////            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
//            UpdateItemOutcome outcome = table.updateItem("pk",uuid,"set #A = :val" , expressionAttributeNames,expressionAttributeValues);
//            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
//
//        }
//        catch (Exception e)
//        {
//            System.err.println("Unable to update item: " + uuid );
//            System.out.println("toString(): " + e);
//
//            System.err.println(e.getMessage());
//        }
//    }

    public void addInstructorCourses(String uuid, String newcourses_id , String job,String coursename)
    {

        Table table = dynamoDBrepo.getTable("instructors4");
        try
        {
//            String id = UUID.randomUUID().toString();
//            System.out.println("Id:" + id);
            String coursename1 = "coursename_"+ coursename;
             PutItemOutcome outcome = table.putItem(new Item().withPrimaryKey("pk",uuid,"sk",newcourses_id )
                    .with("type", job).with("name",coursename1) );

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    public void deleteInstructor(String uuid)
    {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("pk", uuid,"sk",uuid));
        try
        {
            Table table = dynamoDBrepo.getTable("instructors4");
            System.out.println("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
        }
        catch (Exception e)
        {
            System.err.println("Unable to delete item: " + uuid );
            System.err.println(e.getMessage());
        }
    }

    public void deleteInstructorCourse(String instrid,String courseid)
    {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey(new PrimaryKey("pk", instrid,"sk",courseid));

        try
        {
            Table table = dynamoDBrepo.getTable("instructors4");
            System.out.println("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
        }
        catch (Exception e)
        {
            System.err.println("Unable to delete item: " + instrid);
            System.err.println(e.getMessage());
        }
    }

}
