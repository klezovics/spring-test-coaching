package com.klezovich.springtestcoaching.mockito;

import com.klezovich.springtestcoaching.calculator.Calculator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//Let's use this as base https://www.baeldung.com/mockito-series
// Principle uses cases for mockito
// 1) Mock behaviour -> when().thenReturn()
// 2) Inspect what happened with the mock -> verify().XXXXX
public class MockitoTest {

    @Test
    void testMockCreation() {
        var mockCalculator = mock(Calculator.class);
        //The assert below will fail because mocking behaviour did not finish
        //assertEquals(2, mockCalculator.add(1, 1));

        var mockA = mock(A.class);
        //The assert below will fail because mocking behaviour did not finish
        //assertEquals("hello", mockA.getString());
    }

    @Test
   //Test structure Given-When-Then
    void testMockingBasicBehaviour() {
        var fakePutin = mock(Putin.class);
        when(fakePutin.getAnswer()).thenReturn("Russia is great!");

        //When
        var answer = fakePutin.getAnswer();

        //Then
        assertEquals("Russia is great!", answer);
    }

    @Test
    void testMockingResponsesGivenAnArgument() {
        // Best country in the world ? -> Russia
        // Worst country in the world ? -> USA

        var fakePutin = mock(Putin.class);

        //What is the proper sequence to put these in ?
        //Meditation topic: Which mockito argument matchers exists ? How can I use them ?
        when(fakePutin.getAnswer(anyString())).thenReturn("Fuck off!");
        when(fakePutin.getAnswer(eq("Best country in the world ?"))).thenReturn("Russia");
        when(fakePutin.getAnswer(eq("Worst country in the world ?"))).thenReturn("USA");


        assertEquals("Russia", fakePutin.getAnswer("Best country in the world ?"));
        assertEquals("USA", fakePutin.getAnswer("Worst country in the world ?"));
        assertEquals("Fuck off!", fakePutin.getAnswer("What is your name ?"));
        assertEquals("Fuck off!", fakePutin.getAnswer("What is the weather today?"));
    }

    @Test
    void testMockResponseWhichTakeInputArgumentIntoAccount() {
        var fakePutin = mock(Putin.class);
        when(fakePutin.willRussiaBeGreatIn(anyInt()))
                .thenAnswer(new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        return (int) invocation.getArguments()[0] + 1;
                    }
                });

        assertEquals(2022,fakePutin.willRussiaBeGreatIn(2021));
        assertEquals(3001,fakePutin.willRussiaBeGreatIn(3000));
    }

    @Test
    void testUsingSpy() {
        var spyPutin = spy(Putin.class);
        assertEquals("Laika", spyPutin.getNameOfDog());
    }

    @Test
    //Mock == Debil
    //Spy == Very smart guy
    //Recommended reading https://www.baeldung.com/mockito-spy
    void testCalculatorUsingSpy() {
        var calculator = spy(Calculator.class);
        when(calculator.substract(eq(10),eq(3))).thenReturn(100);

        //Work like in real method
        assertEquals(2, calculator.add(1,1));
        //Works like  in real method
        assertEquals(1, calculator.substract(2,1));

        //Note the unusual behaviour
        //It is what we have configured
        assertEquals(100, calculator.substract(10,3));
    }

    @Test
    // Further reading: https://www.baeldung.com/mockito-verify
    void testSpyCanTellWhatHappened() {
        var youngPutin = spy(Putin.class);

        //When
        youngPutin.getAnswer();
        youngPutin.getNameOfDog();
        youngPutin.getNameOfDog();
        youngPutin.willRussiaBeGreatIn(42);


        //Assert
        //Captor is created for input ARGUMENT class
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(youngPutin, times(1)).getAnswer();
        verify(youngPutin, times(2)).getNameOfDog();

        //argumentCaptor.capture() -> sucks out the argument value
        verify(youngPutin, times(1)).willRussiaBeGreatIn(argumentCaptor.capture());

        // argumentCaptor.getAllValues() to get all values
        assertEquals(42, argumentCaptor.getValue());
    }

    static class Putin {
        String getAnswer() {
            return "Russia is great!";
        }

        String getAnswer(String question) {
            return "Russia is great!";
        }

        int willRussiaBeGreatIn(Integer year) {
            return year + 1;
        }

        String getNameOfDog() {
            return "Laika";
        }
    }

    static class A {

        String getString() {
            return "hello";
        }
    }

    static class Inner {

        int a() {return 1;}

        int b(int x) {return x+2;}

        String getFileExtension() {return ".jpg";}

    }

    static class Outer {

        Inner inner = new Inner();

        String f() {

            int answer1 = inner.a();
            int intermediateteResult = answer1 + inner.b(answer1);

            //How to test situation when we only need to modify inner.c()
            return answer1 + inner.getFileExtension();
        }
    }

}
