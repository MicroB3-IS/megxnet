package net.megx.test.consumer;

public class Test {
	public static void main(String[] args) {
		String a = "/oauth/request_Token";
		System.out.println(a.matches("/(pubmap|oauth)/.*"));
	}
}
