package com.example.demo.ServiceImpli;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.LeaveRepository;
import com.example.demo.model.EmployeeLeave;
import com.example.demo.model.OurEmployees;

@Service
public class SalaryServiceImple {

	@Autowired
	private LeaveRepository leaveRepository;

	public double calculateDeductions(OurEmployees employee, LocalDate month) {
		int monthValue = month.getMonthValue();
		int yearValue = month.getYear();
		YearMonth yearMonth = YearMonth.of(yearValue, monthValue);

		// Calculate the number of Sundays in the month
		int daysInMonth = yearMonth.lengthOfMonth();
		int sundays = 0;
		for (int day = 1; day <= daysInMonth; day++) {
			LocalDate date = LocalDate.of(yearValue, monthValue, day);
			if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
				sundays++;
			}
		}
		// Calculate effective working days excluding Sundays
		int workingDays = daysInMonth - sundays;

		List<EmployeeLeave> leaves = leaveRepository.findByGeniusEmployeeAndMonth(employee.getEmployee_id(), monthValue,
				yearValue);

		double deductions = 0.0;
		for (EmployeeLeave leave : leaves) {
			if ("Full Day".equals(leave.getLeaveType())) {
				deductions += employee.getBaseSalary() * 0.033;
			} else if ("Half Day".equals(leave.getLeaveType())) {
				deductions += (employee.getBaseSalary() * 0.033) / 2;
			}
		}
		return deductions;
	}

}
