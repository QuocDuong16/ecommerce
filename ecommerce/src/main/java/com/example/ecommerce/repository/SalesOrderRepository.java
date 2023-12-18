package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Order.SalesOrder;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {

	@Query("select s from SalesOrder s where YEAR(s.orderDateCreate) = ?1")
	List<SalesOrder> findByOrderDateCreateYear(int year);

	@Query("select s from SalesOrder s where YEAR(s.orderDateCreate) = ?1 and MONTH(s.orderDateCreate) = ?2")
	List<SalesOrder> findByOrderDateCreateYearAndMonth(int year, int month);

	@Query("SELECT s FROM SalesOrder s WHERE YEAR(s.orderDateCreate) = ?1 AND WEEK(s.orderDateCreate) = ?2 AND DAYOFWEEK(s.orderDateCreate) = ?3")
	List<SalesOrder> findByOrderDateCreateYearWeekAndDayOfWeek(int year, int week, int dayOfWeek);

	@Query("select DISTINCT YEAR(s.orderDateCreate) FROM SalesOrder s")
	List<Integer> findDistinctYears();
}
