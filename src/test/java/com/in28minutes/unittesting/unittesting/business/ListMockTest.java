package com.in28minutes.unittesting.unittesting.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class ListMockTest {

	List<String> mock = mock(List.class);

	@Test
	public void size_basic() {
		when(mock.size()).thenReturn(5);
		assertEquals(5, mock.size());
	}

	@Test
	public void returnDifferentValues() {
		when(mock.size()).thenReturn(5).thenReturn(10);
		assertEquals(5, mock.size());
		assertEquals(10, mock.size());
		assertEquals(10, mock.size());
	}

	@Test
	@Disabled
	public void returnWithParameters() {
		when(mock.get(0)).thenReturn("in28Minutes").thenReturn("Abdullayev");
		when(mock.get(1)).thenReturn("Rauf");
		/*assertEquals("in28Minutes", mock.get(0));
		assertEquals(null, mock.get(1));*/
		assertEquals("in28Minutes", mock.get(0));
		assertEquals("Rauf", mock.get(1));
		assertEquals("Abdullayev", mock.get(0));
		System.out.println(mock.get(1) + " " + mock.get(0));
	}

	@Test
	public void returnWithGenericParameters() {
		when(mock.get(anyInt())).thenReturn("in28Minutes");

		assertEquals("in28Minutes", mock.get(0));
		assertEquals("in28Minutes", mock.get(1));
	}

	@Test
	public void verificationBasics() {
		// SUT
		String value1 = mock.get(0);
		String value2 = mock.get(1);
//		String value4 = mock.get(0);
//		String value3 = mock.get(2);
		// Verify

		// 0 ci indexin default olaraq 1 defe cagrilib cagrilmadigini mueyyen edir 1 den cox ve az ola bilmez
		verify(mock).get(0);

		// mock listinden 2 defe get edildiyini check eliyir 2 den cox veya az olmaz
		verify(mock, times(2)).get(anyInt());

		// en az 1 defe get edildiyini cek eliyir
		// yeni en azi 1 defe check edilmelidi
		verify(mock, atLeast(1)).get(anyInt());

		// ixtiyari indexin en az 1 defe get edilmesini teleb edir
		verify(mock, atLeastOnce()).get(anyInt());

		// mock listinden max 2 defe get edile bileceyini check eliyir
		verify(mock, atMost(2)).get(anyInt());

		// hec vaxt 2 indexinin cagrilmamasini check eliyir
		verify(mock, never()).get(2);
	}

	@Test
	public void argumentCapturing() {

		//SUT
		mock.add("SomeString");
		//Verification
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(mock).add(captor.capture());
		assertEquals("SomeString", captor.getValue());
	}

	@Test
	public void multipleArgumentCapturing() {

		//SUT
		mock.add("SomeString1");
		mock.add("SomeString2");

		//Verification
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

		verify(mock, times(2)).add(captor.capture());

		List<String> allValues = captor.getAllValues();

		assertEquals("SomeString1", allValues.get(0));
		assertEquals("SomeString2", allValues.get(1));

	}

	@Test
	public void mocking() {
		ArrayList arrayListMock = mock(ArrayList.class);

		// mocladigimiza gore asagidaki emeliyyat nullPointerException vermir
		System.out.println(arrayListMock.get(0));//null

		// size 0-dir
		System.out.println(arrayListMock.size());//0

		arrayListMock.add("Test");
		arrayListMock.add("Test2");

		// mocladigimiza gore helede 0-dir
		System.out.println(arrayListMock.size());//0

		when(arrayListMock.size()).thenReturn(5);

		// burda isə artıq size 5 dir
		System.out.println(arrayListMock.size());//5

		// ve yene de null qayidacaq asagidaki
		System.out.println(arrayListMock.get(0));

		// burda xeta verecek cunki Test4 u gormur listde
		// verify(arrayListMock).add("Test4");
	}

	@Test
	public void spying() {
		ArrayList arrayListSpy = spy(ArrayList.class);
		// burda mocladiqda nullPointerException vermir ancaq spy da exception verir
		// System.out.println(arrayListSpy.get(0));
		arrayListSpy.add("Test0");
		System.out.println(arrayListSpy.get(0));//Test0

		// spy da size da deyisir
		System.out.println(arrayListSpy.size());//1
		arrayListSpy.add("Test");
		arrayListSpy.add("Test2");
		System.out.println(arrayListSpy.size());//3

		// sonra arrayListSpy.size() cagrilanda 5 qaytarsin hemise
		when(arrayListSpy.size()).thenReturn(5);
		System.out.println(arrayListSpy.size());//5

		arrayListSpy.add("Test4");
		System.out.println(arrayListSpy.size());//5

		// Test4 un artirilib artirilmadigini yoxluyur
		verify(arrayListSpy).add("Test4");
	}



}
