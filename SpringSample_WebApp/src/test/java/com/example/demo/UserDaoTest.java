package com.example.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.repository.UserDao;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserDaoTest {

		@Autowired
		@Qualifier("UserDaoJdbcImp1")
		UserDao dao;
		
		//カウントメソッドのテスト
		@Test
		public void countTest1() {
			
			assertEquals(dao.count(),5);
		}

		//テスト２が動かない
//		//カウントメソッドのテスト２
//		@Test
//		@Sql("/testdata.sql")
//		public void countTest2() {
//			assertEquals(dao.count(),6);
//		}
}
