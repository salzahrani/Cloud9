/*
 * Cloud9: A MapReduce Library for Hadoop
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package edu.umd.cloud9.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import junit.framework.JUnit4TestAdapter;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class OHMapKITest {

	@Test
	public void testBasic() throws IOException {
		OHMapKI<Text> m = new OHMapKI<Text>();

		m.put(new Text("hi"), 5);
		m.put(new Text("there"), 22);

		Text key;
		int value;

		assertEquals(m.size(), 2);

		key = new Text("hi");
		value = m.get(key);
		assertEquals(value, 5);

		value = m.remove(key);
		assertEquals(m.size(), 1);

		key = new Text("there");
		value = m.get(key);
		assertEquals(value, 22);
	}

	@Test
	public void testPlus() throws IOException {
		OHMapKI<Text> m1 = new OHMapKI<Text>();

		m1.put(new Text("hi"), 5);
		m1.put(new Text("there"), 22);

		OHMapKI<Text> m2 = new OHMapKI<Text>();

		m2.put(new Text("hi"), 4);
		m2.put(new Text("test"), 5);

		m1.plus(m2);

		assertEquals(3, m1.size());
		assertTrue(m1.get(new Text("hi")) == 9);
		assertTrue(m1.get(new Text("there")) == 22);
		assertTrue(m1.get(new Text("test")) == 5);
	}

	@Test
	public void testDot() throws IOException {
		OHMapKI<Text> m1 = new OHMapKI<Text>();

		m1.put(new Text("hi"), 5);
		m1.put(new Text("there"), 2);
		m1.put(new Text("empty"), 3);

		OHMapKI<Text> m2 = new OHMapKI<Text>();

		m2.put(new Text("hi"), 4);
		m2.put(new Text("there"), 4);
		m2.put(new Text("test"), 5);

		int s = m1.dot(m2);

		assertEquals(s, 28);
	}

	@Test
	public void testSortedEntries1() {
		OHMapKI<Text> m = new OHMapKI<Text>();

		m.put(new Text("a"), 5);
		m.put(new Text("b"), 2);
		m.put(new Text("c"), 3);
		m.put(new Text("d"), 3);
		m.put(new Text("e"), 1);

		MapKI.Entry<Text>[] entries = m.getEntriesSortedByValue();
		MapKI.Entry<Text> e = null;

		assertEquals(5, entries.length);

		e = entries[0];
		assertEquals(new Text("a"), e.getKey());
		assertEquals(5, (int) e.getValue());

		e = entries[1];
		assertEquals(new Text("c"), e.getKey());
		assertEquals(3, (int) e.getValue());

		e = entries[2];
		assertEquals(new Text("d"), e.getKey());
		assertEquals(3, (int) e.getValue());

		e = entries[3];
		assertEquals(new Text("b"), e.getKey());
		assertEquals(2, (int) e.getValue());

		e = entries[4];
		assertEquals(new Text("e"), e.getKey());
		assertEquals(1, (int) e.getValue());
	}

	@Test
	public void testSortedEntries2() {
		OHMapKI<Text> m = new OHMapKI<Text>();

		m.put(new Text("a"), 5);
		m.put(new Text("b"), 2);
		m.put(new Text("c"), 3);
		m.put(new Text("d"), 3);
		m.put(new Text("e"), 1);

		MapKI.Entry<Text>[] entries = m.getEntriesSortedByValue(2);
		MapKI.Entry<Text> e = null;

		assertEquals(2, entries.length);

		e = entries[0];
		assertEquals(new Text("a"), e.getKey());
		assertEquals(5, (int) e.getValue());

		e = entries[1];
		assertEquals(new Text("c"), e.getKey());
		assertEquals(3, (int) e.getValue());
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(OHMapKITest.class);
	}

}