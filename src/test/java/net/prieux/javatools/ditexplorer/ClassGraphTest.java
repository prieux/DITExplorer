/*
 This program generates a JSON file that can be used to visualize A Depth
 Inheritance Tree (DIT) of a bunch of jar files, residing in a directory
 given the path directory as a input parameter.
 
 Copyright (C) 2014  Pascal Rieux

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Author: Pascal Rieux, pascalrieux64@gmail.com
 */

package net.prieux.javatools.ditexplorer;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClassGraphTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testBuildShortClassPathHavingLongClassPathWithDots() {
		String longClassPath = "net.prieux.javatools.ditexplorer";
        ClassGraph classGraph = new ClassGraph(longClassPath);
		String shortClassPath = classGraph.buildShortClassPath(longClassPath);
		assertEquals("Error", "n.p.j.d", shortClassPath);
	}
	
	@Test
	public final void testBuildShortClassPathHavingLongClassPathWithNoDot() {
		String longClassPath = "ditexplorer";
        ClassGraph classGraph = new ClassGraph(longClassPath);
		String shortClassPath = classGraph.buildShortClassPath(longClassPath);
		assertEquals("Error", "d", shortClassPath);
	}
}
