package entity;


public class MyMethod {
    private String className;

    private String methodName;

    public MyMethod(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public int hashCode() {
        return className.hashCode() * 31 + methodName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyMethod) {
            MyMethod m = (MyMethod) obj;
            if (m.className.equals(className) && m.methodName.equals(methodName)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return className + ": " + methodName;
    }
}
