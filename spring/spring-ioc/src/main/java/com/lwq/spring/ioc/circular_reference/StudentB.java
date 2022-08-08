package com.lwq.spring.ioc.circular_reference;

/**
 * @author lwq
 * @date 2022/8/8 0008
 * @since
 */
public class StudentB {
	private StudentC studentC;

	public StudentB() {
	}

	public StudentB(StudentC studentC) {
		this.studentC = studentC;
	}

	public void setStudentC(StudentC studentC) {
		this.studentC = studentC;
	}
}
