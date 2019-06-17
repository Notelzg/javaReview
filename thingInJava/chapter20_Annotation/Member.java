package chapter20_Annotation;

@SqlAnnotation.DBTable(name="MEMBER")
public class Member {
    @SqlAnnotation.SqlString(30)
    String firstName;
    @SqlAnnotation.SqlString(30)
    String lastName;
    @SqlAnnotation.SqlInteger
    Integer age;

    @SqlAnnotation.SqlString(value = 30, constraints = @SqlAnnotation.Constraints(primaryKey = true))
    String handle;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
}
