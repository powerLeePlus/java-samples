package com.lwq.spring.ioc.circular_reference;

/**
 * @author lwq
 * @date 2022/8/8 0008
 * @since
 */
public class StudentC {
	private StudentA studentA;

	public StudentC() {
	}

	public StudentC(StudentA studentA) {
		this.studentA = studentA;
	}

	public void setStudentA(StudentA studentA) {
		this.studentA = studentA;
	}
}
