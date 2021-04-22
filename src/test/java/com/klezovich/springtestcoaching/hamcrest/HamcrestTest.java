package com.klezovich.springtestcoaching.hamcrest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.klezovich.springtestcoaching.hamcrest.HamcrestTest.IsOnlyDigits.onlyDigits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

// Key point: Hamcrest is basically syntactic-sugar over assertEquals()

//Link: https://www.baeldung.com/java-junit-hamcrest-guide
// Hamcrest API allows you to use a single assert command assertThat() with a lib of matchers
//https://dzone.com/articles/the-benefit-of-using-assertthat-over-other-assert
//There are 2 use cases really ...
// 1) Using existing matchers. Aim to use the existing code base
//   1.1) Using matchers for objects
//   1.2) Using matchers for collections and maps
// 2) Writing custom matchers.
public class HamcrestTest {

    @Test
    void testBasicMatcher() {
        assertThat("a", equalToIgnoringCase("A"));
    }

    @Test
        //Used when you have many different kinds of objects, but they share a property
    void testUsingBeanMatchers() {
        var person = new Person("AK", 31);
        var employee = new Employee("John", 10000);

        assertThat(person, hasProperty("name", equalTo("AK")));
        assertThat(employee, hasProperty("name", equalTo("John")));
    }

    @Test
        //Neat matchers for collections
        //I'd say this is one of the best features
    void testUsingMatchersForCollections() {
        assertThat(List.of(), empty());
        assertThat(List.of("a", "b"), hasSize(2));
        assertThat(List.of("a", "b", "c"), hasItem("c"));
        assertThat(List.of("a", "b", "c", "d"), hasItems("c", "d"));
        assertThat(List.of("a", "b", "c", "d"), containsInAnyOrder("d", "c", "b", "a"));

        assertThat(List.of(1, 2, 3), everyItem(greaterThan(0)));
    }

    @Test
    public void testUsingMatchersForMaps() {
        Map<String, String> map = new HashMap<>();
        map.put("blogname", "baeldung");
        assertThat(map, hasKey("blogname"));

        //Nice ! Can check the value directly !
        assertThat(map, hasValue("baeldung"));
    }


    @Test
        //If you need to test for complex conditions you can write a custom matcher
        //Super useful for testing complex scenarios
    void testUsingCustomMatchers() {
        assertThat("123", onlyDigits());
    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class Person {
        String name;
        Integer age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Employee {
        String name;
        Integer salary;
    }

    //Let's create a custom matcher
    //TODO Create your own matcher
    static public class IsOnlyDigits extends TypeSafeMatcher<String> {

        @Override
        //What the matcher does. Comparison logic
        protected boolean matchesSafely(String s) {
            try {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException nfe) {
                return false;
            }
        }

        //Create matcher name
        public static Matcher<String> onlyDigits() {
            return new IsOnlyDigits();
        }


        @Override
        public void describeTo(Description description) {
            description.appendText("only digits");
        }
    }
}
