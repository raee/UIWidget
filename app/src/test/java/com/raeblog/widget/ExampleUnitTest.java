package com.raeblog.widget;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Person xm = new Person("rae");
        Person rae = new Person("rae");

        System.out.printf("是否相等：" + (xm.equals(rae)));
    }

    private class Person {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Person(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Person) {
                return ((Person) obj).getName().equals(this.name);
            }
            return super.equals(obj);
        }
    }
}