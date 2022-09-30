package com.dynamodbtry4.controller;


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
public class InstructorController {


    @Autowired
    InstructorsService instructorservice;

    @Autowired
    CoursesService coursesService;

    @GetMapping(value = "/makeinstructortable")
    public ResponseEntity MakeInstructorTable()
    {
        try
        {
            instructorservice.createTable();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/saveinstructordetails", consumes = "application/json")
    public ResponseEntity saveInstructor(@RequestBody Instructor product)
    {
        try
        {
            instructorservice.saveInstructorDetails(product);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getinstructor/{id}", produces = {"application/json"})
    public ResponseEntity<Instructor> getInstructorDetails(@PathVariable("id") String id)
    {

        try
        {
            return new ResponseEntity(instructorservice.getInstructorDetails(id), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getinstructortable/{tableName}")
    public ResponseEntity<Table> getTable(@PathVariable("tableName") String tableName)
    {
        try
        {
//            Table t =  productService.getTable(tableName);

            return new ResponseEntity(instructorservice.getTable(tableName), HttpStatus.OK);

//            return t.getTableName();
//            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/addinstructorscourses/{courseid}/{instrid}/{jobtype}")
    public ResponseEntity updateCoursesInstructor
            (
                    @PathVariable("courseid") String courseid,
                    @PathVariable("instrid") String instrid,
                    @PathVariable("jobtype") String jobtype
            )
    {
        try
        {
//            coursesService.updateCourseInstructor(courseid, instrid);
            Courses gotcourse = coursesService.getCourses(courseid);
            String coursename = gotcourse.getName();
            instructorservice.addInstructorCourses(instrid,courseid,jobtype,coursename);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = "/deleteinstructortable/{tableName}")
    public ResponseEntity deleteTable(@PathVariable("tableName") String tableName)
    {
        try
        {
            instructorservice.deleteTable(tableName);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updateinstructorname/{id}")
    public ResponseEntity updateInstructor
            (
                    @PathVariable("id") String id,
                    @RequestBody String name
            )
    {
        try
        {
            instructorservice.updateInstructorName(id, name);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteinstructors/{id}")
    public ResponseEntity deleteProduct( @PathVariable("id") String id)
    {
        try
        {
            instructorservice.deleteInstructor(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteinstructorscourses/{instrid}/{courseid}")
    public ResponseEntity deleteProduct( @PathVariable("instrid") String instrid , @PathVariable("courseid") String courseid )
    {
        try
        {
            instructorservice.deleteInstructorCourse(instrid,courseid);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getallinstructorsdetails")
    public ResponseEntity<List<Instructor>> getAllInstructor()
    {
        try
        {
            return new ResponseEntity<>(instructorservice.getAllInstructorDetails(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getallcoursesofinstructor/{instrid}")
    public ResponseEntity<List<Instructor>> getAllInstructorsCourses(@PathVariable("instrid") String instrid )
    {
        try
        {
            return new ResponseEntity<>(instructorservice.getAllInstructorCourses(instrid) , HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
