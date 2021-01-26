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
	@Query("FROM Question s WHERE :location = s.location")
	Page<Question> getQuestionsByLocation(Pageable pageable, String location);
	
//	@Query("FROM Question s WHERE :location = s.location AND :userID = s.user_id")
	Page<Question> getQuestionsByUserIDAndLocation(Pageable pageable, int userID,String location);
}
