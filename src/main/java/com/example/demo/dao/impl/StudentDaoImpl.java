package com.example.demo.dao.impl;


import com.example.demo.dao.StudentDao;
import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentEntity save(StudentEntity entity) {
        return studentRepository.save(entity);
    }

    @Override
    public void delete(StudentEntity entity) {
        studentRepository.delete(entity);
    }

    @Override
    public Optional<StudentEntity> findById(int id) {
        return studentRepository.findById(id);
    }

    @Override
    public Optional<StudentEntity> findByMobileNumber(String mobNo) {
        return studentRepository.findByMobileNumber(mobNo);
    }

    @Override
    public List<StudentEntity> findAll() {
        return studentRepository.findAll();
    }

}
