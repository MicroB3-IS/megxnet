package net.megx.ws.genomes;

public class Test {
	public static void main(String[] args) {
		String nucSeq = "ACCATGACGGACTCAGGTCACTTACGCGTA";
		byte [] out = new byte [15];
		System.out.println(new SixFrameTranslation(null).translate2(nucSeq.getBytes(), out));
		System.out.println(new String(out));
	}
}
