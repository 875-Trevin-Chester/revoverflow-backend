package com.revature.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.revature.Application;

import com.revature.entities.Question;
import com.revature.entities.User;
import com.revature.services.QuestionService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class QuestionControllerTests {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private WebApplicationContext context;

	static User u1;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private QuestionService questionService;

	@Before
	public void setUp() {
		u1 = new User(12, 26, 0, true, null, "admin@rss.com", "Admin", "Admin", "password");
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser(username = "user@rss.com", password = "Password123!", authorities = "user")
	public void testGetAllQuestionsHappyPath() throws Exception {

		// Create page of data
		List<Question> questions = new ArrayList<>();
		questions.add(new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, true, 1));
		Page<Question> pageResult = new PageImpl<>(questions);

		// Stub getAllQuestions to return page of data
		when(questionService.getAllQuestions(Mockito.any(Pageable.class))).thenReturn(pageResult);

		// Call API end point and assert result
		mvc.perform(get("/questions").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content[0].title", is("title")));

	}

	/* @Author ken */
	@Test
	@WithMockUser(username = "user@rss.com", password = "Password123!", authorities = "user")
	public void testGetAllQuestionsByUserId() throws Exception {
		// Create page of data
		List<Question> questions = new ArrayList<>();
		questions.add(new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, true, 1));
		Page<Question> pageResult = new PageImpl<>(questions);

		// Stub getAllQuestions to return page of data
		when(questionService.getAllQuestionsByUserId(Mockito.any(Pageable.class), Mockito.anyInt()))
				.thenReturn(pageResult);

		// Call API end point and assert result
		mvc.perform(get("/questions/user/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content[0].id", is(1)));

	}

	/* @Author ken */
	@Test
	@WithMockUser(username = "admin@rss.com", password = "Password123!", authorities = "admin")
	public void testGetAllQuestionsByStatus() throws Exception {

		// Create page of data
		List<Question> questions = new ArrayList<>();
		questions.add(new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, false, 1));
		Page<Question> pageResult = new PageImpl<>(questions);

		// Stub getAllQuestions to return page of data
		when(questionService.getAllQuestionsByStatus(Mockito.any(Pageable.class), Mockito.anyBoolean()))
				.thenReturn(pageResult);

		// Call API end point and assert result
		mvc.perform(get("/questions/status/false").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content[0].id", is(1)));

	}

	/**
	 * @author James
	 * @return Checks to make sure that the Question Controller method for the
	 *         update accepted id method works.
	 */
	@Test
	@WithMockUser(username = "admin@rss.com", password = "Password123!", authorities = "admin")
	public void updateStatus() throws Exception {
		Question questions, testQuestions;
		questions = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, false, 1);
		testQuestions = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, true, 1);

		when(questionService.updateQuestionStatus(Mockito.any(Question.class), Mockito.anyInt()))
				.thenReturn(testQuestions);
		String toUpdate = mapper.writeValueAsString(questions);
		org.springframework.test.web.servlet.MvcResult result = mvc
				.perform(MockMvcRequestBuilders.put("/questions/status").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(toUpdate).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		assertEquals(200, result.getResponse().getStatus());
		System.out.println("update status");
		System.out.println(result.getResponse().getContentAsString());
	}

	/**
	 * @author corbi
	 * @return Checks to see if the update faq endpoint is able to be accessed.
	 */
	@Test
	@WithMockUser(username = "admin@rss.com", password = "Password123!", authorities = "admin")
	public void updateIsFaq() throws Exception {
		Question question;
		question = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, false, true, 1);
		assertTrue(question.getIsFaq());
		question = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, false, 1);
		assertFalse(question.getIsFaq());
		when(questionService.updateQuestionisFaq(Mockito.any(Question.class))).thenReturn(
				new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, false, true, 1));
		String readiedQuestionString = mapper.writeValueAsString(question);
		org.springframework.test.web.servlet.MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post("/questions/faq").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(readiedQuestionString).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		assertEquals(200, result.getResponse().getStatus());
		System.out.println("update isFaq");
		System.out.println(result.getResponse().getContentAsString());
	}

	/**
	 * @author James
	 * @return Tests to ensure that the Question Controller method for the update
	 *         status method works.
	 */
	@Test
	@WithMockUser(username = "admin@rss.com", password = "Password123!", authorities = "user")
	public void updateQuestionAcceptedAnswerId() throws Exception {
		Question questions, testQuestions;
		questions = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, false, 1);
		testQuestions = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, true, 1);
		when(questionService.updateQuestionAcceptedAnswerId(Mockito.any(Question.class))).thenReturn(testQuestions);
		String toUpdate = mapper.writeValueAsString(questions);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/questions")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(toUpdate).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		assertEquals(200, result.getResponse().getStatus());
		assertTrue("This return object conains the string", content.contains("true"));
		assertNotEquals(null, content);
	}

	/* @Author ken */
	@Test
	@WithMockUser(username = "admin@rss.com", password = "Password123!", authorities = "user")
	public void testGetQuestionByQuestionId() throws Exception {

		// Create page of data
		Question question = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, false, 1);
		// Page<Question> pageResult = new PageImpl<>(question);

		// Stub getAllQuestions to return page of data
		when(questionService.findById(Mockito.anyInt())).thenReturn(question);

		// Call API end point and assert result
		mvc.perform(get("/questions/id/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	/** @author ken */
	@Test
	@WithMockUser(username = "admin@rss.com", password = "Password123!", authorities = "user")
	public void testSaveQuestion() throws Exception {
		Question question = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, true, 1);

		when(questionService.save(Mockito.any(Question.class))).thenReturn(question);

		String toUpdate = mapper.writeValueAsString(question);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/questions")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(toUpdate).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		/// System.out.println("result = " + content);
		assertEquals(200, result.getResponse().getStatus());
	}

	/** @author David */
	@Test
	@WithMockUser(username = "admin@rss.com", password = "Password123!", authorities = "admin")
	public void getQuestionsByLocation() throws Exception {
		Question q1 = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, false, 12, "UTA");
		Question q2 = new Question(2, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, false, 12, null);
		questionService.save(q1);
		questionService.save(q2);
		List<Question> listQs = new ArrayList<>();
		listQs.add(q1);
		Page<Question> p1 = new PageImpl<Question>(listQs);
		Pageable pageable = PageRequest.of(1, 10);
		p1.getContent().size();
		String toUpdate = mapper.writeValueAsString(p1);
		Mockito.when(questionService.getAllQuestionsByLocation(Mockito.any(Pageable.class), Mockito.anyString()))
				.thenReturn(p1);
		mvc.perform(get("/questions/location/UTA").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content[0].location", is("UTA")));
	}
	
	/** @author David */
	@Test
	@WithMockUser(username = "admin@rss.com", password = "Password123!", authorities = "admin")
	public void getQuestionsUserIdByLocation() throws Exception {
			Question q1 = new Question(1, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, false, 12, "UTA");
			Question q2 = new Question(2, 1, "title", "content", LocalDateTime.MIN, LocalDateTime.MIN, false, 12, null);
			questionService.save(q1);
			questionService.save(q2);
			List<Question> listQs = new ArrayList<>();
			listQs.add(q1);
			Page<Question> p1 = new PageImpl<Question>(listQs);
			Pageable pageable = PageRequest.of(1, 10);
			p1.getContent().size();
			String toUpdate = mapper.writeValueAsString(p1);
			Mockito.when(questionService.getUsersQuestionByLocationAndId(Mockito.any(Pageable.class), Mockito.anyString(),Mockito.anyInt()))
					.thenReturn(p1);
			
			mvc.perform(get("/questions/location/12/UTA").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.content[0].userID", is(12)));
			
	}
}
