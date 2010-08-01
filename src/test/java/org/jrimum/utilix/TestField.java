/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:15:56
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:15:56
 * 
 */

package org.jrimum.utilix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;
import org.jrimum.utilix.text.MonetaryUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestField {

	private Field<String> campoString;

	private Field<Integer> campoInteger;

	private Field<Long> campoLong;

	private Field<Date> campoDate;

	private Field<BigDecimal> campoDecimal;

	private Field<BigDecimal> campoDecimal_v9;

	@Before
	public void setUp() {

		campoString = new Field<String>(StringUtils.EMPTY, 8);
		campoString.setFiller(Filler.WHITE_SPACE_RIGHT);

		campoDate = new Field<Date>(new GregorianCalendar(2007, Calendar.JULY,
				22).getTime(), 6, DateUtil.FORMAT_DDMMYY);

		campoInteger = new Field<Integer>(0, 6);
		campoInteger.setFiller(Filler.ZERO_LEFT);

		campoLong = new Field<Long>(0L, 6);
		campoLong.setFiller(Filler.ZERO_LEFT);

		campoDecimal = new Field<BigDecimal>(new BigDecimal("875.98"), 11,
				MonetaryUtil.FORMAT_REAL);
		campoDecimal.setFiller(Filler.ZERO_LEFT);

		campoDecimal_v9 = new Field<BigDecimal>(new BigDecimal("875.9"), 11,
				MonetaryUtil.FORMAT_REAL_UMA_CASA_DECIMAL);
		campoDecimal_v9.setFiller(Filler.ZERO_LEFT);
	}

	@After
	public void tearDown() {

		campoString = null;
		campoDate = null;
		campoInteger = null;
		campoLong = null;
		campoDecimal = null;
		campoDecimal_v9 = null;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCampo() {

		Format format = null;

		campoDate = new Field<Date>(new Date(), 0, DateUtil.FORMAT_DDMMYY);
		campoDate = new Field<Date>(null, 1, DateUtil.FORMAT_DDMMYY);
		campoDate = new Field<Date>(new Date(), 0, format);
	}

	@Test
	public void testLer() {

		campoString.read("COBRANCA");
		assertNotNull(campoString.getValue());
		assertTrue(campoString.getValue() instanceof String);
		assertEquals("COBRANCA", campoString.getValue().toString());

		campoDate.read("011002");
		assertNotNull(campoDate.getValue());
		assertTrue(campoDate.getValue() instanceof Date);
		assertEquals("011002", DateUtil.FORMAT_DDMMYY.format(campoDate
				.getValue()));

		campoInteger.read("000001");
		assertNotNull(campoInteger.getValue());
		assertTrue(campoInteger.getValue() instanceof Integer);
		assertTrue(new Integer(1).compareTo(campoInteger.getValue()) == 0);

		campoLong.read("000001");
		assertNotNull(campoLong.getValue());
		assertTrue(campoLong.getValue() instanceof Long);
		assertTrue(new Long(1L).compareTo(campoLong.getValue()) == 0);

		campoDecimal.read("00000523676");
		assertNotNull(campoDecimal.getValue());
		assertTrue(campoDecimal.getValue() instanceof BigDecimal);
		assertTrue(new BigDecimal("5236.76").compareTo(campoDecimal.getValue()) == 0);

		campoDecimal_v9.read("00000523676");
		assertNotNull(campoDecimal_v9.getValue());
		assertTrue(campoDecimal_v9.getValue() instanceof BigDecimal);
		assertTrue(new BigDecimal("52367.6").compareTo(campoDecimal_v9
				.getValue()) == 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLerException() {

		campoString.read(null);
		campoDate.read(null);
		campoDate.read("");
		campoDate.read("abcd");
		campoDate.read("1a2MA1205");
	}

	@Test
	public void testEscrever() {

		assertNotNull(campoString.write());
		assertEquals("        ", campoString.write());
		assertEquals(8, campoString.write().length());

		assertNotNull(campoDate.write());
		assertEquals("220707", campoDate.write());
		assertEquals(6, campoDate.write().length());

		campoDate.setValue(DateUtil.DATE_NULL);
		campoDate.setFiller(Filler.ZERO_LEFT);
		assertNotNull(campoDate.write());
		assertEquals("000000", campoDate.write());
		assertEquals(6, campoDate.write().length());

		assertNotNull(campoInteger.write());
		assertEquals("000000", campoInteger.write());
		assertEquals(6, campoInteger.write().length());

		assertNotNull(campoLong.write());
		assertEquals("000000", campoLong.write());
		assertEquals(6, campoLong.write().length());

		assertNotNull(campoDecimal.write());
		assertEquals("00000087598", campoDecimal.write());
		assertEquals(11, campoDecimal.write().length());

		assertNotNull(campoDecimal_v9.write());
		assertEquals("00000008759", campoDecimal_v9.write());
		assertEquals(11, campoDecimal_v9.write().length());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEscreverException() {

		Field<String> campo = new Field<String>("tamanho", 5);
		assertEquals(5, campo.write().length());

		Field<Integer> campo1 = new Field<Integer>(1234, 3);
		assertEquals(3, campo1.write().length());

		Field<Integer> campo2 = new Field<Integer>(12, 3);
		assertEquals(3, campo2.write().length());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetCampo() {

		campoInteger.setValue(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTamanho() {

		campoString.setLength(0);
		campoString.setLength(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetFiller() {

		campoString.setFiller(null);
	}

}
