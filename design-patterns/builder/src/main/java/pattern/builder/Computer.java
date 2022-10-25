package pattern.builder;

/**
 * @author lwq
 * @date 2022/10/25 0025
 * @since
 */
public class Computer {
	private String CPU;
	private String GPU;
	private String memory;
	private String motherboard;
	private String hardDisk;

	public Computer() {
	}

	public Computer(String CPU, String GPU, String memory, String motherboard, String hardDisk) {
		this.CPU = CPU;
		this.GPU = GPU;
		this.memory = memory;
		this.motherboard = motherboard;
		this.hardDisk = hardDisk;
	}

	public static ComputerBuilder builder() {
		return new ComputerBuilder();
	}

	public static class ComputerBuilder {
		private String CPU;
		private String GPU;
		private String memory;
		private String motherboard;
		private String hardDisk;

		public ComputerBuilder() {
		}

		public ComputerBuilder CPU(String CPU) {
			this.CPU = CPU;
			return this;
		}

		public ComputerBuilder GPU(String GPU) {
			this.GPU = GPU;
			return this;
		}
		public ComputerBuilder memory(String memory) {
			this.memory = memory;
			return this;
		}

		public ComputerBuilder motherboard(String motherboard) {
			this.motherboard = motherboard;
			return this;
		}

		public ComputerBuilder hardDisk(String hardDisk) {
			this.hardDisk = hardDisk;
			return this;
		}

		public Computer build() {
			return new Computer(this.CPU, this.GPU, this.memory, this.motherboard, this.hardDisk);
		}
	}

	@Override
	public String toString() {
		return "you have a computer:\n" +
				"\t CPU: " + CPU + "\n" +
				"\t GPU: " + GPU + "\n" +
				"\t memory: " + memory + "\n" +
				"\t motherboard: " + motherboard + "\n" +
				"\t hardDisk: " + hardDisk + "\n";
	}

	public static void main(String[] args) {
		Computer computer = Computer.builder().CPU("i9-12900K")
				.GPU("RTX 3090 Ti")
				.memory("64GB")
				.motherboard("Z590 AORUS MASTER")
				.hardDisk("2TB SSD")
				.build();
		System.out.println(computer);
	}
}
