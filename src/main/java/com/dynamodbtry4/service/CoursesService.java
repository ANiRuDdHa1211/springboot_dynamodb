package com.dynamodbtry4.service;


import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.dynamodbtry4.enteties.Courses;
import com.dynamodbtry4.repo.DynamoDBRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CoursesService
{

    @Autowired
    DynamoDBRepo dynamoDBrepo;

    public void createTable() throws Exception
    {
      // removing this if condition beacucse .getTable() never return null
             dynamoDBrepo.createCoursesTable();
//        dynamoDBrepo.createCoursesTable();
    }

    public Table getTable(String tableName) throws Exception
    {
       return dynamoDBrepo.getTable(tableName);
    }

    public void deleteTable(String tableName) throws Exception
    {
        dynamoDBrepo.deleteTable(tableName);
    }

    public void saveCourses(Courses course ) throws Exception
    {
//        createTable("courses3");
//        dynamoDBrepo.createCoursesTable();

        Table table = dynamoDBrepo.getTable("courses4");
        try
        {
//            String id = UUID.randomUUID().toString();
//            System.out.println("Id:" + id);
                 PutItemOutcome outcome = table.putItem(new Item().withPrimaryKey("id", course.getId()).
                    with("name", course.getName())
                    .with("description", course.getDescription()) );

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public Courses getCourses(String uuid)
    {
        Courses course = null;
        Table table = dynamoDBrepo.getTable("courses4");
        if (table != null)
        {
            GetItemSpec spec = new GetItemSpec().withPrimaryKey("id", uuid);

            try
            {
                System.out.println("Attempting to read the item...");
                Item outcome = table.getItem(spec);
//                table.query()
                if (outcome != null)
                {
                    course = new Courses();
                    course.setId(outcome.getString("id"));
                    course.setName(outcome.getString("name"));
                    course.setDescription(outcome.getString("description"));
                }
                System.out.println("GetItem succeeded: " + outcome);

            }
            catch (Exception e)
            {
                System.err.println("Unable to read item: " + uuid);
                System.err.println(e.getMessage());
            }
        }
        return course;
    }

    public List<Courses> getAllCourses() throws Exception
    {
        ScanSpec scanSpec = new ScanSpec();
        List<Courses> allcourses = new ArrayList<>();
        try
        {
            Table table = dynamoDBrepo.getTable("courses4");
            ItemCollection<ScanOutcome> items = table.scan(scanSpec);
            Iterator<Item> iter = items.iterator();
            while (iter.hasNext())
            {
                Item item = iter.next();
                if (item != null)
                {
                    Courses product = new Courses();
                    product.setId(item.getString("id"));
                    product.setName(item.getString("name"));
                    product.setDescription(item.getString("description"));
                    allcourses.add(product);
                }
                System.out.println(item.toString());
            }
        }
        catch (Exception e)
        {
            System.err.println("Unable to scan the table:");
            System.err.println(e.getMessage());
            throw new Exception("Error has been occured while getting list of product");
        }
        return allcourses;
    }

    public void updateCourseDescription(String uuid, String description)
    {

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("id", uuid)
                .withUpdateExpression("set description = :description")
                .withValueMap(new ValueMap().withString(":description", description))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try
        {
            Table table = dynamoDBrepo.getTable("courses4");
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
        }
        catch (Exception e)
        {
            System.err.println("Unable to update item: " + uuid );
            System.err.println(e.getMessage());
        }
    }

    public void deleteCourse(String uuid)
    {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey(new PrimaryKey("id", uuid));
        try
        {
            Table table = dynamoDBrepo.getTable("courses4");
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


//
//    public void updateCourseInstructor(String id, String instr)
//    {
//        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("id", id)
//                .withUpdateExpression("set instructor = :instructor")
//                .withValueMap(new ValueMap().withString(":instructor", instr))
//                .withReturnValues(ReturnValue.UPDATED_NEW);
//
//        try {
//            Table table = dynamoDBrepo.getTable("courses4");
//            System.out.println("Updating the course's instructor...");
//            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
//            System.out.println("UpdateItem Courses's instructor succeeded:\n" + outcome.getItem().toJSONPretty());
//
//        } catch (Exception e) {
//            System.err.println("Unable to update item: " + id);
//            System.err.println(e.getMessage());
//        }
//    }



}
