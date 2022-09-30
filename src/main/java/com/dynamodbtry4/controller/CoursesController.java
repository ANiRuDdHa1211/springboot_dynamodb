package com.dynamodbtry4.controller;

//import com.codingworld.dynamodb.bean.Product;
//import com.codingworld.dynamodb.service.ProductService;

import com.amazonaws.services.dynamodbv2.document.Table;

import com.dynamodbtry4.enteties.Courses;
import com.dynamodbtry4.enteties.Instructor;
import com.dynamodbtry4.enteties.JobType;
import com.dynamodbtry4.service.CoursesService;
import com.dynamodbtry4.service.InstructorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CoursesController
{

    @Autowired
    CoursesService coursesService;


    @Autowired
    InstructorsService instructorsService;

    @PostMapping(value = "/savecourse", consumes = "application/json")
    public ResponseEntity saveProduct(@RequestBody Courses course)
    {
        try
        {
            coursesService.saveCourses(course);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/makecoursetable")
    public ResponseEntity MakeCourseTable()
    {
        try
        {
            coursesService.createTable();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/getcourse/{id}", produces = {"application/json"})
    public ResponseEntity<Courses> getAllProduct(@PathVariable("id") String id)
    {

        try
        {
            return new ResponseEntity(coursesService.getCourses(id), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/gettable/{tableName}")
    public ResponseEntity<Table> getTable(@PathVariable("tableName") String tableName)
    {
        try
        {
//            Table t =  productService.getTable(tableName);

            return new ResponseEntity(coursesService.getTable(tableName), HttpStatus.OK);

//            return t.getTableName();
//            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deletetable/{tableName}")
    public ResponseEntity deleteTable(@PathVariable("tableName") String tableName)
    {
        try
        {
            coursesService.deleteTable(tableName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updatecoursesdescription/{id}")
    public ResponseEntity updateCoursesDescription
            (
                    @PathVariable("id") String id,
                    @RequestBody String description
            )
    {
        try
        {

            coursesService.updateCourseDescription(id, description);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delethecourses/{id}")
    public ResponseEntity deleteProduct( @PathVariable("id") String id)
    {
        try
        {
            coursesService.deleteCourse(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getallcourses")
    public ResponseEntity<List<Courses>> getAllCourses()
    {
        try
        {
            return new ResponseEntity<>(coursesService.getAllCourses(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // frim the brsbest wya to go on the part from a wher

}