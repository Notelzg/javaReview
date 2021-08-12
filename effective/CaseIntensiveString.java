import java.util.Objects;

public class CaseIntensiveString {
    private final String s;

    public CaseIntensiveString(String s) {
        this.s = Objects.requireNonNull(s);;
    }


    public static void main(String[] args) {
        CaseIntensiveString cas = new CaseIntensiveString("ab");;
        System.out.println(cas.equals(null));
        CaseIntensiveString cas2 = new CaseIntensiveString("ab");
    }
    CaseIntensiveString cas = new CaseIntensiveString("ab");
    @Override
    public boolean equals(Object obj) {
        return  (obj instanceof  CaseIntensiveString && ((CaseIntensiveString) obj).s.equals(s));
    }
}

