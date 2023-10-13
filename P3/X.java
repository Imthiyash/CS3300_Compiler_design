class Inheritance2 {
    public static void main(String[] args) {
        System.out.println(new C23().init(new B23()));
    }
}

class A23 {
    int i1;
    int i2;
    int i3;

    public int init(A23 a) {
		System.out.println(1000001);
        i2 = a.getI1();
        i3 = 222;
        i1 = this.setI1(i2 + i3);
		System.out.println(i1);
        return i1;
    }

    public int getI1() {
		System.out.println(1000002);
		System.out.println(i1);
        return i1;
    }

    public int setI1(int i) {
		System.out.println(1000003);
		System.out.println(i);
        return i;
    }
}

class B23 extends A23 {
    int i1;
    int i4;

    public int init(A23 a) {
        A23 a_local;
		System.out.println(1000004);
        a_local = new A23();
        i4 = a.getI1();
        i1 = this.setI1(i4);
		System.out.println(a_local.init(this));
        return a_local.init(this);
    }

    public int getI1() {
		System.out.println(1000005);
		System.out.println(i1);
        return i1;
    }

    public int setI1(int i) {
		System.out.println(1000006);
		System.out.println(i+111);
        return i + 111;
    }
}

class C23 extends B23 {
    int i1;
    int i5;

    public int init(A23 a) {
		System.out.println(1000007);
        i5 = 333;
        i1 = this.setI1(i5);
		System.out.println(a.init(this));
        return a.init(this);
    }

    public int getI1() {
		System.out.println(1000008);
		System.out.println(i1);
        return i1;
    }

    public int setI1(int i) {
		System.out.println(1000009);
		System.out.println(i*2);
        return i*2;
    }
}