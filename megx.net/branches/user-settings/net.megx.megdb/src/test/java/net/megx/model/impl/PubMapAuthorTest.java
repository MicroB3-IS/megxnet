package net.megx.model.impl;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsNot.*;
import static org.hamcrest.core.Is.*;
import net.megx.model.Author;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PubMapAuthorTest {

	Author author = new PubMapAuthor();

	@Before
	public void setUp() throws Exception {
		author.setFirstName("Renzo");
		author.setLastName("Kottmann");
		author.setInitials(null);
		author.setSex("male");
	}

	@After
	public void tearDown() throws Exception {
		author = null;
	}

	@Test
	public void testGetFirstName() {
		assertEquals("should be same", "Renzo", author.getFirstName());
	}

	@Test
	public void testSetFirstName() {
		String name = "Renzo ";
		this.author.setFirstName(name);
		String res = author.getFirstName();
		assertNotNull(res);
		assertThat(name, is(not(res)));
		assertEquals(name.trim(), res);
	}

	@Test
	public void testGetLastName() {
		assertEquals("should be same", "Kottmann", author.getLastName());
	}

	@Test
	public void testSetLastName() {
		String name = "Kottmann ";
		this.author.setLastName(name);
		String res = author.getLastName();
		assertNotNull(res);
		assertThat(name, not(res));
		assertThat(name.trim(), is(res));
	}

	@Test
	public void testGetInitials() {

	}

	@Test
	public void testSetInitials() {
		String name = "K T ";
		this.author.setInitials(name);
		String res = author.getInitials();

		assertNotNull(res);
		assertThat(name, not(res));
		assertThat(name.trim(), is(res));
	}

	@Test
	public void testGetSex() {
		// assume correct by string setting
		author.setSex("male");
		String res = author.getSex();
		assertEquals("male", res);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetSexWrongString() {
		author.setSex("wrong");
	}

	@Test()
	public void testSetSexCorrectString() {
		String sex = "female";
		author.setSex(sex);
		String res = author.getSex();
		assertEquals(sex, res);

		sex = "unknown";
		author.setSex(sex);
		res = author.getSex();
		assertEquals(sex, res);

		sex = "not applicable";
		author.setSex(sex);
		res = author.getSex();
		assertEquals(sex.replace(' ', '_'), res);

		sex = "not_applicable";
		author.setSex(sex);
		res = author.getSex();
		assertEquals(sex, res);

	}

	@Test
	public void testSetInstitute() {
		String institute = "MPI ";
		author.setInstitute(institute);
		String res = author.getInstitute();

		assertNotNull(res);
		assertThat(institute, not(res));
		assertThat(institute.trim(), is(res));
	}
}
