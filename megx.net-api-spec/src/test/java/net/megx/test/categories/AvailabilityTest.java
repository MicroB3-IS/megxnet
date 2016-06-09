package net.megx.test.categories;

/**
 * This category of test is to only check if something is available like e.g. a
 * test that a HEAD on web page returns HTTP code 200. Usually these test are
 * shallow and fast tests.
 * 
 * @author renzo.kottmann@gmail.com
 * 
 */
public interface AvailabilityTest extends IntegrationTest {};