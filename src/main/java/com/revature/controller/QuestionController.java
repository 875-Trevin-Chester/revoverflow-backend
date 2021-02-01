package com.revature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.Question;
import com.revature.services.QuestionService;




@RestController
@RequestMapping("/questions")

public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	/**	 *@author ken 
	 * get all the questions*/
	@GetMapping
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getAllQuestions(Pageable pageable)
	{
		return questionService.getAllQuestions(pageable);
	}

	/**
	 * @param status = true/false
	 * get all the questions by the status of the question
	 */
	/**@author ken*/
	@GetMapping("/status/{status}")
	@PreAuthorize("hasAuthority('admin')")
	public Page<Question> getAllQuestionsByStatus(Pageable pageable, @PathVariable boolean status)
	{
		return questionService.getAllQuestionsByStatus(pageable, status);
	}

	/**@author ken*/
	/** get all the questions by user id
	 * @param pageable
	 * @param id = the id of the user
	 * @return
	 */
	@GetMapping("/user/{id}")
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getAllQuestionsByUserId(Pageable pageable, @PathVariable int id)
	{
		return questionService.getAllQuestionsByUserId(pageable, id);
	}

	/** @Author James Walls */
	/** Adds new questions and updates existing ones. */
	@PostMapping
	@PreAuthorize("hasAuthority('user')")
	public Question saveQuestion(@RequestBody Question question) {
		System.out.println(question);
		return questionService.save(question);
	}

	/** 
	 * @author Hugh Thornhill 
	 * @return This is the updateQuestionAcceptedAnswerId endpoint which updates the
	 * acceptedId to the answer that is deemed the most acceptable.
	 */
	@PutMapping
	@PreAuthorize("hasAuthority('user')")
	public Question updateQuestionAcceptedAnswerId(@RequestBody Question question) {
		return questionService.updateQuestionAcceptedAnswerId(question);
	}

	/** 
	 * @author Hugh Thornhill 
	 * @return This is the updateStatus endpoint which updates the question status and 
	 * awards 20 points to the user who answered the question.
	 */
	@PutMapping("/status")
	@PreAuthorize("hasAuthority('admin')")
	public Question updateStatus(@RequestBody Question question) {
		return questionService.updateQuestionStatus(question, 20);
	}
	
	/** @Author Natasha Poser 
	 * @return the is the GetQuestionById end-point. It retrieves a question by it's ID*/
	@GetMapping("/id/{id}")
	@PreAuthorize("hasAuthority('user')")
	public Question getQuestionById(@PathVariable int id) {
		return questionService.findById(id);
	}
	
	/** @author david lyu
	 *  This method is called in the front-end to get all questions for a specific location, if location is null it will assume
	 *  the questions are not location based questions but revature based.
	 *  @param pageable: The inputs of the endpoint (size(10):int and page:int).
	 *  @param location: The location to get all Questions in the query
	 *  @return This gets all the questions of a specified location, or revature based questions and returns it in a Page Object, which
	 *  	jackson should parse into JSON.
	 */
	@GetMapping("/location/{location}")
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getQuestionsByLocation(Pageable pageable, @PathVariable String location) {
		return questionService.getAllQuestionsByLocation(pageable, location);
	}
	
	@GetMapping("/type/{type}")
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getQuestionsByType(Pageable pageable, @PathVariable String type) {
		return questionService.getAllQuestionsByQuestionType(pageable, type);
	}
	
	/**
	 * @author david lyu
	 * @param pageable: The inputs of the endpoint (size(10): int and page: int).
	 * @param location: the location in query to find questions
	 * @param userId: the user's id in query to specify the user's question to a particular location
	 * @return This get's all the questions of a specified location for a user, or revature based questions and returns it in a Page Object,
	 * 		which jackson shouls parse into JSON.
	 */
	@GetMapping("/location/{userId}/{location}")
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getUserQuestionsByLocationandId(Pageable pageable, @PathVariable String location, @PathVariable int userId) {
		return questionService.getUsersQuestionByLocationAndId(pageable, location, userId);
	}

	@GetMapping("/type/{userId}/{questionType}")
	@PreAuthorize("hasAuthority('user')")
	public Page<Question> getUsersQuestionsByTypeandId(Pageable pageable, @PathVariable String questionType, @PathVariable int userId) {
		return questionService.getAllUsersQuestionByType(pageable, questionType, userId);
	}
	
	/**
	 * @author Corbin Creedon
	 *	@return This is the make a question a faq endpoint, it's sole purpose is to update the model of questions to make them have true in isFaq
	 */
	@PostMapping("/faq")
	@PreAuthorize("hasAuthority('admin')")
	public Question updateisFaq(@RequestBody Question question) {
		return questionService.updateQuestionisFaq(question);
	}
		
}
