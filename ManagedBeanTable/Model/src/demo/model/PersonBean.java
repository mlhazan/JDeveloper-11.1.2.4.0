package demo.model;

public class PersonBean {

        public PersonBean(String name, String moNo, Integer salary) {
            this.Name = name;
            this.mobNo = moNo;
            this.salary = salary;
        }
        private String Name;
        private String mobNo;
        private Integer salary;

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getName() {
            return Name;
        }

        public void setMobNo(String mobNo) {
            this.mobNo = mobNo;
        }

        public String getMobNo() {
            return mobNo;
        }

        public void setSalary(Integer salary) {
            this.salary = salary;
        }

        public Integer getSalary() {
            return salary;
        }
    }