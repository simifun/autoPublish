package fun.qsong.utils;

import fun.qsong.utils.util.ReflectUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        List<Bean> beans = new ArrayList<>();
        beans.add(new Bean(1, "张", "1", "男",new Date(2000,10,10)));
        beans.add(new Bean(2, "杨", "2", "男",new Date(2000,10,10)));
        beans.add(new Bean(3, "李", "3", "女",new Date(2000,10,10)));
        beans.add(new Bean(4, "", "4", "女",new Date(2000,10,10)));
        beans.add(new Bean(5, null, "4", "女",new Date(2000,10,10)));
        beans.add(new Bean(6, "古", "5", "不男不女",new Date(2001,10,10)));

        List<Bean> list = (List<Bean>) ReflectUtils.getDistinctList(beans, "name","date");
        System.out.print("不重复个数：" + list.size());
    }

    private class Bean {
        private int id;
        private String name;
        private String age;
        private String sex;
        private Date date;

        public Bean(int id, String name, String age, String sex, Date date) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.sex = sex;
            this.date = date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}