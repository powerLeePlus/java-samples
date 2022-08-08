package com.lwq.spring.ioc.circular_reference;

/**
 * @author lwq
 * @date 2022/8/8 0008
 * @since
 */
public class StudentA {
	private StudentB studentB;

	public StudentA() {
	}

	public StudentA(StudentB studentB) {
		this.studentB = studentB;
	}

	public void setStudentB(StudentB studentB) {
		this.studentB = studentB;
	}
}
