package com.revature.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{

	/**@author ken*/
	Page<Question> getAllQuestionsByUserID(Pageable pageable, int id);

	/**@author ken*/
	@Query("FROM Question s WHERE :status = s.status")
	Page<Question> getQuestionsByStatus(Pageable pageable, boolean status);
	
	/**@author david*/
	Page<Question> getQuestionsByLocation(Pageable pageable, String location);
	
	Page<Question> getQuestionsByQuestionType(Pageable pageable, String type);
	
	/**
	 * This method gets all Questions with user id and location
	 * @Author David Lyu
	 * @param pageable
	 * @param userID
	 * @param location
	 * @return
	 */
	Page<Question> getQuestionsByUserIDAndLocation(Pageable pageable, int userID,String location);
}
