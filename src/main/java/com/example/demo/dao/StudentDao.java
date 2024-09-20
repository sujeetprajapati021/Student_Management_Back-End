package com.example.demo.dao;



import com.example.demo.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
	
	StudentEntity save(StudentEntity entity);
	
	void delete(StudentEntity entity);
	
	Optional<StudentEntity> findById(int id);

	Optional<StudentEntity> findByMobileNumber(String mobNo);

	List<StudentEntity> findAll();

}
